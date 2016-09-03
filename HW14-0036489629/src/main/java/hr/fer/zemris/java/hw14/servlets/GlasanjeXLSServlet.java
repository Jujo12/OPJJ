package hr.fer.zemris.java.hw14.servlets;

import hr.fer.zemris.java.hw14.dao.DAOProvider;
import hr.fer.zemris.java.hw14.models.PollOption;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * The Servlet that parses the voting results and outputs them as an XLS
 * spreadsheet document.<br>
 * The servlet accepts one request parameter: poll ID.
 *
 * @author Juraj Juričić
 */
@WebServlet("/glasanje-xls")
public class GlasanjeXLSServlet extends HttpServlet {

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		int id;
		try {
			id = Integer.parseInt(req.getParameter("pollID"));
		} catch (Exception e) {
			req.setAttribute("message", "Poll ID is invalid");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req,
					resp);
			return;
		}

		try {
			List<PollOption> options = DAOProvider.getDao().getAllPollOptions(id);

			HSSFWorkbook document = createVotingXLS(options);

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
	 * @param options the list of options
	 * @return the HSSF workbook
	 */
	private HSSFWorkbook createVotingXLS(List<PollOption> options) {
		HSSFWorkbook document = new HSSFWorkbook();

		HSSFSheet sheet = document.createSheet("Results");

		HSSFRow header = sheet.createRow(0);
		header.createCell(0).setCellValue("Band name");
		header.createCell(1).setCellValue("Score");

		int i = 0;
		for (PollOption option : options) {
			HSSFRow row = sheet.createRow(++i);

			row.createCell(0).setCellValue(option.getOptionTitle());
			row.createCell(1).setCellValue(option.getVotesCount());
		}
		;

		return document;
	}

}
