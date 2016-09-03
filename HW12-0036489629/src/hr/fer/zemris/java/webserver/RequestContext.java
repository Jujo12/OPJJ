package hr.fer.zemris.java.webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The RequestContext objects store a HTTP request context.
 *
 * @author Juraj Juričić
 */
public class RequestContext {

	/** The output stream. */
	private OutputStream outputStream;

	/** The charset. */
	private Charset charset;

	/** The encoding. Write-only. */
	private String encoding = "UTF-8";

	/** The status code. Write-only. */
	private int statusCode = 200;

	/** The status text. Write-only. */
	private String statusText = "OK";

	/** The mime type. Write-only. */
	private String mimeType = "text/html";

	/** The read-only map parameters. */
	private Map<String, String> parameters;

	/** The temporary parameters map. */
	private Map<String, String> temporaryParameters;

	/** The persistent parameters map. */
	private Map<String, String> persistentParameters;

	/** The list of output cookies. */
	private List<RCCookie> outputCookies;

	/** True if the header is generated, false otherwise. */
	private boolean headerGenerated = false;

	/**
	 * Instantiates a new request context.
	 *
	 * @param outputStream
	 *            the output stream. Cannot be null.
	 * @param parameters
	 *            the parameters. If null, will be assumed empty.
	 * @param persistentParameters
	 *            the persistent parameters. If null, will be assumed empty.
	 * @param outputCookies
	 *            the output cookies. If null, will be assumed empty.
	 * @throws NullPointerException
	 *             Thrown if outputStream argument is set to null.
	 */
	public RequestContext(OutputStream outputStream,
			Map<String, String> parameters,
			Map<String, String> persistentParameters,
			List<RCCookie> outputCookies) throws NullPointerException {
		this.outputStream = Objects.requireNonNull(outputStream);

		this.parameters = (parameters == null) ? new HashMap<>() : parameters;
		this.persistentParameters = (persistentParameters == null)
				? new HashMap<>() : persistentParameters;
		this.outputCookies = (outputCookies == null) ? new ArrayList<>()
				: outputCookies;
	
		this.temporaryParameters = new HashMap<>();

		setEncoding(encoding);
	}

	/**
	 * Checks if the header data has been sent. Breaks if headerGenerated is
	 * true.
	 *
	 * @throws HeadersAlreadySentException
	 *             thrown if the header data has already been sent.
	 */
	private void checkHeaderNotSent() throws HeadersAlreadySentException {
		if (headerGenerated) {
			throw new HeadersAlreadySentException(
					"The headers have already been sent.");
		}
	}

	/**
	 * Sets the encoding.
	 *
	 * @param encoding
	 *            the encoding to set
	 */
	public void setEncoding(String encoding) {
		checkHeaderNotSent();

		this.encoding = Objects.requireNonNull(encoding);
		this.charset = Charset.forName(encoding);
	}

	/**
	 * Sets the status code.
	 *
	 * @param statusCode
	 *            the statusCode to set
	 */
	public void setStatusCode(int statusCode) {
		checkHeaderNotSent();

		this.statusCode = Objects.requireNonNull(statusCode);
	}

	/**
	 * Sets the status text.
	 *
	 * @param statusText
	 *            the statusText to set
	 */
	public void setStatusText(String statusText) {
		checkHeaderNotSent();

		this.statusText = Objects.requireNonNull(statusText);
	}

	/**
	 * Sets the mime type.
	 *
	 * @param mimeType
	 *            the mimeType to set
	 */
	public void setMimeType(String mimeType) {
		checkHeaderNotSent();

		this.mimeType = Objects.requireNonNull(mimeType);
	}

	/**
	 * Adds the cookie to the cookies list.
	 *
	 * @param cookie
	 *            the cookie to add
	 */
	public void addRCCookie(RCCookie cookie) {
		checkHeaderNotSent();

		outputCookies.add(Objects.requireNonNull(cookie));
	}

	/**
	 * Retrieves the value from parameters map.
	 *
	 * @param name
	 *            the name
	 * @return the parameter
	 */
	public String getParameter(String name) {
		return parameters.get(Objects.requireNonNull(name));
	}

	/**
	 * Retrieves names of all parameters in parameters map.
	 *
	 * @return the parameter names
	 */
	public Set<String> getParameterNames() {
		return Collections.unmodifiableSet(parameters.keySet());
	}

	/**
	 * Retrieves value from persistentParameters map.
	 *
	 * @param name
	 *            the name
	 * @return the persistent parameter
	 */
	public String getPersistentParameter(String name) {
		return persistentParameters.get(Objects.requireNonNull(name));
	}

	/**
	 * Retrieves names of all parameters in persistent parameters map.
	 *
	 * @return the persistent parameter names
	 */
	public Set<String> getPersistentParameterNames() {
		return Collections.unmodifiableSet(persistentParameters.keySet());
	}

	/**
	 * Sets the persistent parameter pair.
	 *
	 * @param name
	 *            the name
	 * @param value
	 *            the value
	 */
	public void setPersistentParameter(String name, String value) {
		persistentParameters.put(Objects.requireNonNull(name),
				Objects.requireNonNull(value));
	}

	/**
	 * Removes the persistent parameter with the given name.
	 *
	 * @param name
	 *            the name
	 */
	public void removePersistentParameter(String name) {
		persistentParameters.remove(Objects.requireNonNull(name));
	}

	/**
	 * Retrieves value from temporaryParameters map.
	 *
	 * @param name
	 *            the name
	 * @return the temporary parameter
	 */
	public String getTemporaryParameter(String name) {
		return temporaryParameters.get(Objects.requireNonNull(name));
	}

	/**
	 * Retrieves names of all parameters in temporary parameters map.
	 *
	 * @return the temporary parameter names
	 */
	public Set<String> getTemporaryParameterNames() {
		return Collections.unmodifiableSet(temporaryParameters.keySet());
	}

	/**
	 * Stores a value to temporaryParameters map.
	 *
	 * @param name
	 *            the name
	 * @param value
	 *            the value
	 */
	public void setTemporaryParameter(String name, String value) {
		temporaryParameters.put(Objects.requireNonNull(name),
				Objects.requireNonNull(value));
	}

	/**
	 * Removes the temporary parameter with the given name.
	 *
	 * @param name
	 *            the name
	 */
	public void removeTemporaryParameter(String name) {
		temporaryParameters.remove(Objects.requireNonNull(name));
	}

	/**
	 * Writes the given byte data to output stream. Writes whole array's length.
	 *
	 * @param data
	 *            the data
	 * @return the request context
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public RequestContext write(byte[] data) throws IOException {
		write(data, 0, data.length);

		return this;
	}
	
	/**
	 * Write the given byte data to output stream.
	 *
	 * @param data the data
	 * @param offset the offset
	 * @param length the length
	 * @return the request context
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public RequestContext write(byte[] data, int offset, int length) throws IOException{
		if (!headerGenerated) {
			outputStream.write(
					generateHeader().getBytes(StandardCharsets.ISO_8859_1));
		}

		outputStream.write(data, offset, length);
		
		return this;
	}

	/**
	 * Writes the given String to output stream using the set encoding.
	 *
	 * @param text
	 *            the text
	 * @return the request context
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public RequestContext write(String text) throws IOException {
		byte[] data = text.getBytes(charset);
		return write(data);
	}

	/**
	 * Generates the header and sets the headerGenerated flag to true.
	 *
	 * @return the header string
	 */
	private String generateHeader() {
		StringBuilder header = new StringBuilder();

		// first line
		header.append("HTTP/1.1 ");
		header.append(statusCode);
		header.append(' ');
		header.append(statusText);
		header.append("\r\n");

		// Second line
		header.append("Content-Type: ");
		header.append(mimeType);
		if (mimeType.startsWith("text/")) {
			header.append("; charset=");
			header.append(encoding);
		}
		header.append("\r\n");

		// Cookies
		header.append(generateCookiesString());

		header.append("\r\n");

		headerGenerated = true;

		return header.toString();
	}

	/**
	 * Generates the cookies header string.
	 *
	 * @return the cookies header string
	 */
	private String generateCookiesString() {
		if (outputCookies.isEmpty()) {
			return "";
		}

		StringBuilder sb = new StringBuilder();

		for (RCCookie cookie : outputCookies) {
			sb.append("Set-Cookie: ");

			// name
			sb.append(cookie.getName());
			sb.append("=\"");
			sb.append(cookie.getValue());
			sb.append("\";");

			// domain
			if (cookie.getDomain() != null) {
				sb.append(" Domain=");
				sb.append(cookie.getDomain());
				sb.append(";");
			}

			// path
			if (cookie.getPath() != null) {
				sb.append(" Path=");
				sb.append(cookie.getPath());
				sb.append(";");
			}

			// max-age
			if (cookie.getMaxAge() != null) {
				sb.append(" Max-Age=");
				sb.append(cookie.getMaxAge());
				sb.append(";");
			}

			sb.deleteCharAt(sb.length() - 1);
			sb.append("\r\n");
		}

		return sb.toString();
	}

	/**
	 * The Class RCCookie.
	 *
	 * @author Juraj Juričić
	 */
	public static class RCCookie {

		/** The name. */
		private final String name;

		/** The value. */
		private final String value;

		/** The domain. */
		private final String domain;

		/** The path. */
		private final String path;

		/** The max age. */
		private final Integer maxAge;

		/**
		 * Instantiates a new RC cookie.
		 *
		 * @param name
		 *            the name
		 * @param value
		 *            the value
		 * @param domain
		 *            the domain
		 * @param path
		 *            the path
		 * @param maxAge
		 *            the max age
		 */
		public RCCookie(String name, String value, Integer maxAge,
				String domain, String path) {
			super();
			this.name = name;
			this.value = value;
			this.domain = domain;
			this.path = path;
			this.maxAge = maxAge;
		}

		/**
		 * Gets the name.
		 *
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * Gets the value.
		 *
		 * @return the value
		 */
		public String getValue() {
			return value;
		}

		/**
		 * Gets the domain.
		 *
		 * @return the domain
		 */
		public String getDomain() {
			return domain;
		}

		/**
		 * Gets the path.
		 *
		 * @return the path
		 */
		public String getPath() {
			return path;
		}

		/**
		 * Gets the max age.
		 *
		 * @return the maxAge
		 */
		public Integer getMaxAge() {
			return maxAge;
		}

	}
}
