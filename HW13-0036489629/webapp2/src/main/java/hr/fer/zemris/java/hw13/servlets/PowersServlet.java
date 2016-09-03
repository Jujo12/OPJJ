package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * The Servlet used for generating XLS document containing powers of integers. The generated document contains n sheets, each containing (b-a) rows.<br>
 * Accepts parameters a, b, and n. a and b should be in range [-100,100], while n should be in range [1,5].
 *
 * @author Juraj Juričić
 */
public class PowersServlet extends HttpServlet{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
		int a, b, n;

		try {
			a = Integer.parseInt(req.getParameter("a"));
			b = Integer.parseInt(req.getParameter("b"));
			n = Integer.parseInt(req.getParameter("n"));
		} catch (Exception e) {
			req.setAttribute("message", "Invalid attribute value");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req,
					resp);
			return;
		}

		if (n < 1 || n > 5) {
			req.setAttribute("message", "Invalid attribute <i>n</i> value");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req,
					resp);
			return;
		}
		if (a < -100 || a > 100 || b < -100 || b > 100) {
			req.setAttribute("message", "<i>a</i> and <i>b</i> must be in range [-100, 100]");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req,
					resp);
			return;
		}

		HSSFWorkbook document = createPowersXLS(a, b, n);
		
		resp.setContentType("application/xls");
        resp.setHeader("Content-Disposition", "attachment; filename=powers_"+a+"_"+b+"_"+n+".xls");
        
        //write xls to output stream
		document.write(resp.getOutputStream());
	}
	
	/**
	 * Creates the xls document according to the specifications..
	 *
	 * @param a the a (lower int bound)
	 * @param b the b (upper int bound)
	 * @param n the n (number of sheets)
	 * @return the workbook
	 */
	private HSSFWorkbook createPowersXLS(int a, int b, int n) {
		HSSFWorkbook document = new HSSFWorkbook();

		for (int i = 1; i <= n; i++) {
			HSSFSheet sheet = document.createSheet("x^"+i);
			
			HSSFRow rowhead = sheet.createRow(0);
			rowhead.createCell(0).setCellValue("x");
			rowhead.createCell(1).setCellValue(String.format("x^%d", i));

			for (int j = a; j <= b; j++) {
				HSSFRow row = sheet.createRow(j);
				row.createCell(0).setCellValue(j);
				row.createCell(1).setCellValue(Math.pow(j, i));
			}
		}
		
		return document;
	}
}
