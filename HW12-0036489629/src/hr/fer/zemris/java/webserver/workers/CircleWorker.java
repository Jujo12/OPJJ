package hr.fer.zemris.java.webserver.workers;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * The web worker that generates a png image with a circle and outputs it to HTTP client.
 *
 * @author Juraj Juričić
 */
public class CircleWorker implements IWebWorker {
	
	/** Radius of the circle, in pixels. */
	private final static int CIRCLE_RADIUS = 100;
	
	@Override
	public void processRequest(RequestContext context) {
		context.setMimeType("image/png");
		BufferedImage bim = new BufferedImage(2*CIRCLE_RADIUS, 2*CIRCLE_RADIUS, BufferedImage.TYPE_3BYTE_BGR);
		
		Graphics2D g2d = bim.createGraphics();

		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, 2*CIRCLE_RADIUS, 2*CIRCLE_RADIUS);
		g2d.setColor(Color.BLACK);
		g2d.fillOval(0, 0, 2*CIRCLE_RADIUS, 2*CIRCLE_RADIUS);
		
		g2d.dispose();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			ImageIO.write(bim, "png", bos);
			context.write(bos.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
