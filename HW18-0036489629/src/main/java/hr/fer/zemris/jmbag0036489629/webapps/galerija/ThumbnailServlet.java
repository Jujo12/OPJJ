package hr.fer.zemris.jmbag0036489629.webapps.galerija;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The servlet that serves thumbnails. Thumbnails are generated upon first request. Image path should be queried as a GET parameter path.
 *
 * @author Juraj Juričić
 */
@WebServlet("/thumbnail")
public class ThumbnailServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6326921562320852084L;

	/** The thumbnails directory. */
	private final static String thumbnailsFolder = "/WEB-INF/thumbnails/";
	
	/** The images directory. */
	private final static String imagesFolder = "/WEB-INF/slike/";
	
	/** The maximum width of generated thumbnail. */
	private final static int MAX_WIDTH = 150;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String path = req.getParameter("path");
		System.out.println(path);
		if (path == null){
			resp.sendError(404);
			return;
		}
				
		File f = new File(getServletContext().getRealPath(imagesFolder + path)); 
		if (!f.exists() || !f.canRead() || !f.isFile()){
			resp.sendError(404);
			return;
		}
		
		BufferedImage thumbImg;
		File thumb = new File(getServletContext().getRealPath(thumbnailsFolder + path));
		if (!thumb.exists()){
			//create file
			try{
				thumbImg = resize(f, MAX_WIDTH);
				ImageIO.write(thumbImg, "jpg", thumb);
			}catch(IOException e){
				resp.sendError(500);
				return;
			}
		}else{
			thumbImg = ImageIO.read(thumb);
		}
		
		resp.setContentType("image/jpeg");
		ImageIO.write(thumbImg, "jpg", resp.getOutputStream());
		resp.getOutputStream().close();
	}
	
	/**
	 * Resizes the given image from file to given maximum width, keeping ratio between width and height.
	 *
	 * @param source the path to image to resize
	 * @param maxWidth the max width
	 * @return the buffered image, resized
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private BufferedImage resize(File source, int maxWidth) throws IOException{
		BufferedImage bsrc = ImageIO.read(source);
        
		double ratio = (double) bsrc.getWidth() / bsrc.getHeight();
		double width = Math.min(maxWidth, bsrc.getWidth());
		double height = width/ratio;

        BufferedImage bdest = new BufferedImage((int)width, (int)height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bdest.createGraphics();
        
        AffineTransform at = AffineTransform.getScaleInstance(width / bsrc.getWidth(), height / bsrc.getHeight());
        g.drawRenderedImage(bsrc, at);

        return bdest;
	}
}
