package hr.fer.zemris.java.webserver.workers;

import java.io.IOException;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * The web worker that returns all the GET parameters to the client.
 *
 * @author Juraj Juričić
 */
public class EchoWorker implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) {
		context.setMimeType("text/html");

		String html = "<html><head><title>Parameters</title></head><body>";
		html += "<table style='min-width: 300px; border-collapse: collapse;' border='1'>";
		html += "<tr><th>Key</th><th>Value</th></tr>";
		
		String value;
		for(String name : context.getParameterNames()){
			value = context.getParameter(name);
			
			html += "<tr><td>"+name+"</td><td>"+value+"</td></tr>";
		}
		
		html += "</table>";
		
		try {
			context.write(html);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
