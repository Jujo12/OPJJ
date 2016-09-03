package hr.fer.android.jmbag0036489629;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * <p>The activity used for display of result or error message.</p>
 *
 * <p>Expects parameters sent through intent as follows:</p>
 * <pre>
 *     <b>error</b> - generated error message. If null (not set), the result of operations will be displayed.
 *     <b>operator</b> - the used operator symbol (+, -, x or ÷)
 *     <b>val1</b> - the first number
 *     <b>val2</b> - the second number
 * </pre>
 * <br />
 * Returns RESULT_OK if the user clicked on "OK" button, which should reset the input fields.
 *
 * @author Juraj Juričić
 */
public class DisplayActivity extends AppCompatActivity {

    /**
     * The response used for returning to parent activity.
     */
    private Intent response = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_display);

        TextView message = (TextView) findViewById(R.id.message);

        Intent i = getIntent();
        String errorMsg = i.getStringExtra("error");

        String op = i.getStringExtra("operator");
        double v1 = i.getDoubleExtra("val1", 0);
        double v2 = i.getDoubleExtra("val2", 0);
        if (op == null){
            op = "";
        }

        Button okButton = (Button) findViewById(R.id.buttonOk);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK, response);
                finish();
            }
        });

        Button sendReportButton = (Button) findViewById(R.id.buttonSendReport);
        if (errorMsg != null) {
            //note: the typo in word "grešlke" is intentional (it's like that in the problem definition).
            final String msg = "Prilikom obavljanja operacije "+op+" nad unosima "+v1+" i "+v2+" došlo je do sljedeće grešlke: " + errorMsg;
            message.setText(msg);
            sendReportButton.setVisibility(View.VISIBLE);

            sendReportButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Intent emailIntent = new Intent(Intent.ACTION_SEND);

                    emailIntent.setType("message/rfc882");
                    emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"ana.baotic@infinum.com"});
                    emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "0036489629: dz report");
                    emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, msg);

                    try {
                        startActivity(emailIntent);
                    }catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(DisplayActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else{
            sendReportButton.setVisibility(View.GONE);
            double result = getIntent().getDoubleExtra("result", 0);

            message.setText("Rezultat operacija "+v1+op+v2+" je "+result);
        }
    }
}
