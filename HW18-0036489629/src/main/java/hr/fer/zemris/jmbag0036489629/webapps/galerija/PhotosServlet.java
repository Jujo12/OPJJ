package hr.fer.zemris.jmbag0036489629.webapps.galerija;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * Servlet that returns photos' data in JSON format or a single photo (jpeg).
 * 
 * @author Juraj Juričić
 *
 */
@WebServlet("/photo")
public class PhotosServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4935999713389274274L;
	/** The path to images directory. */
	private final static String imagesFolder = "/WEB-INF/slike/";
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String path = req.getParameter("path");
		@SuppressWarnings("unchecked")
		Map<String, Photo> photosMap = (Map<String, Photo>) getServletContext().getAttribute("photosMap");
		if (photosMap == null){
			resp.sendError(500);
		}
		
		if (path != null){
			File photo = new File(getServletContext().getRealPath(imagesFolder + path));
			BufferedImage photoImg = ImageIO.read(photo);
			
			resp.setContentType("image/jpeg");
			ImageIO.write(photoImg, "jpg", resp.getOutputStream());
			resp.getOutputStream().close();
			
			return;
		}
		resp.setCharacterEncoding("UTF-8");
		
		//return all photos' data
		resp.setContentType("application/json");
		resp.getWriter().print(new Gson().toJson(photosMap));
		resp.getWriter().flush();
	}
}
