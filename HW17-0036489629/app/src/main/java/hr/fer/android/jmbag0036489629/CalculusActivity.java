package hr.fer.android.jmbag0036489629;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * <p>The main activity used for input of numbers and selection of the operator.</p>
 * <p>Sends the result or error to @link{DisplayActivity}, along with the following parameters, as
 * expected by @link{DisplayActivity}:</p>
 * <pre>
 *     <b>operator</b> - the used operator symbol (+, -, x or ÷)
 *     <b>val1</b> - the first number
 *     <b>val2</b> - the second number
 *     <b>error</b> - generated error message. Will not be set if the operation has completed
 *                  successfully.
 * </pre>
 *
 * @author Juraj Juričić
 */
public class CalculusActivity extends AppCompatActivity {

    /**
     * The operation radio group.
     */
    private RadioGroup operation;

    /**
     * The currently selected operator string. Should be updated on every operation RadioGroup
     * change.
     */
    private String operatorString = "+";

    /**
     * The text input field for the first number.
     */
    private EditText number1;

    /**
     * The text input field for the second number.
     */
    private EditText number2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculus);

        operation = (RadioGroup) findViewById(R.id.operation);

        assert operation != null;
        operation.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                operatorString = ((RadioButton) findViewById(checkedId)).getText().toString();
            }
        });

        number1 = (EditText) findViewById(R.id.number1);
        number2 = (EditText) findViewById(R.id.number2);

        Button submitButton = (Button) findViewById(R.id.buttonDoTheMeth);

        assert submitButton != null;
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CalculusActivity.this, DisplayActivity.class);
                try{
                    double val1 = Double.parseDouble(number1.getText().toString());
                    double val2 = Double.parseDouble(number2.getText().toString());

                    double result = performOperation(operation.getCheckedRadioButtonId(), val1, val2);

                    i.putExtra("val1", val1);
                    i.putExtra("val2", val2);
                    i.putExtra("operator", operatorString);

                    i.putExtra("result", result);
                }catch(Exception e){
                    i.putExtra("error", e.getMessage());
                }

                startActivityForResult(i, 1);
            }
        });
    }

    /**
     * Performs the selected operation on two doubles and returns the result as a double number.
     * @param operationID the ID of operation radio button. Expected to be operationAdd,
     *                    operationMultiply, operationSubtract, or operationDivide. Will throw an
     *                    UnsupportedOperationException if invalid operationID is provided.
     * @param a the first number
     * @param b the second number
     * @return the result of the requested operation.
     */
    private double performOperation(int operationID, double a, double b){
        switch(operationID){
            /*
            Too late was it when I discovered the saddening truth that
            Android SDK supported only Java versions up to 7. My code
            had already been written with the all-powerful lambdas, thus
            forcing me to change them into poor switch-case expression.
             */
            case R.id.operationAdd:
                return a+b;
            case R.id.operationMultiply:
                return a*b;
            case R.id.operationSubtract:
                return a-b;
            case R.id.operationDivide:
                return a/b;
            default:
                throw new UnsupportedOperationException("Unsupported operator provided.");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("TEST", String.valueOf(resultCode));
        if (requestCode == 1){
            if (resultCode == RESULT_OK){
                number1.setText("");
                number2.setText("");
            }
        }
    }
}
