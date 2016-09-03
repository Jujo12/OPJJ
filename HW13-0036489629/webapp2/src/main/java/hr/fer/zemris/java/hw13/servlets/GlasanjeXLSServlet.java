package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import hr.fer.zemris.java.hw13.servlets.GlasanjeUtilities.Band;

/**
 * The Servlet that parses the voting results and outputs them as an XLS
 * spreadsheet document. Definition file and results file paths are stored in
 * {@link GlasanjeUtilities} class. <br>
 * The servlet accepts no request parameters.
 *
 * @author Juraj Juričić
 */
public class GlasanjeXLSServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String defFile = req.getServletContext()
				.getRealPath("/WEB-INF/glasanje-definicija.txt");
		String resFile = req.getServletContext()
				.getRealPath("/WEB-INF/glasanje-rezultati.txt");

		try {
			List<Band> bands = GlasanjeUtilities.loadBands(Paths.get(defFile),
					Paths.get(resFile));

			HSSFWorkbook document = createVotingXLS(bands);

			resp.setContentType("application/xls");
			resp.setHeader("Content-Disposition",
					"attachment; filename=results.xls");

			document.write(resp.getOutputStream());

		} catch (Exception e) {
			req.setAttribute("message", e.getMessage());
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req,
					resp);
		}
	}

	/**
	 * Creates the {@link HSSFWorkbook} that represents the XLS document to be
	 * sent to end-user.
	 *
	 * @param bands
	 *            the list of bands
	 * @return the HSSF workbook
	 */
	private HSSFWorkbook createVotingXLS(List<Band> bands) {
		HSSFWorkbook document = new HSSFWorkbook();

		HSSFSheet sheet = document.createSheet("Results");

		HSSFRow header = sheet.createRow(0);
		header.createCell(0).setCellValue("Band name");
		header.createCell(1).setCellValue("Score");

		int i = 0;
		for (Band band : bands) {
			HSSFRow row = sheet.createRow(++i);
			row.createCell(0).setCellValue(band.getName());
			row.createCell(1).setCellValue(band.getScore());
		}

		return document;
	}

}
