package hr.fer.zemris.jmbag0036489629.webapps.galerija;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * The servlet that serves data of all images for single tag, or list of all tags if no tag is queried.
 *
 * @author Juraj Juričić
 */
@WebServlet("/tag")
public class TagsServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -642676828361522918L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String tag = req.getParameter("tag");
		@SuppressWarnings("unchecked")
		Map<String, Set<Photo>> photosMap = (Map<String, Set<Photo>>) getServletContext().getAttribute("photosMap");
		if (photosMap == null){
			//TODO error
		}
		
		if (tag != null){
			Set<Photo> tagPhotos = photosMap.get(tag);
			if (tagPhotos == null){
				resp.sendError(404);
			}
			
			resp.setCharacterEncoding("UTF-8");
			
			//return all photos' data
			resp.setContentType("application/json");
			resp.getWriter().print(new Gson().toJson(tagPhotos));
			resp.getWriter().flush();
			return;
		}
		
		resp.setCharacterEncoding("UTF-8");
		
		//return tag list
		resp.setContentType("application/json");
		resp.getWriter().print(new Gson().toJson(photosMap.keySet()));
		resp.getWriter().flush();
	}
}
