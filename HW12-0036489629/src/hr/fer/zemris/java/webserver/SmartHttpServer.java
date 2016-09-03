package hr.fer.zemris.java.webserver;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * The SmartHttpServer program. A usable HTTP server that offers basic file
 * server functionality, as well as usage of configuration-based and
 * convention-over-configuration based workers. Supports execution of
 * smartscripts.<br>
 * Accepts one argument: path to server.properties configuration file.
 *
 * @author Juraj Juričić
 */
public class SmartHttpServer {

	/**
	 * The main method that is executed when the program is run.
	 *
	 * @param args
	 *            Command line arguments
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void main(String[] args) throws IOException {
		if (args.length != 1) {
			System.err.println(
					"One argument expected: path to server.properties configuration file.");
		}

		SmartHttpServer server = new SmartHttpServer(args[0]);
		server.start();
	}

	/** The server address. */
	private String address;

	/** The listening port. */
	private int port;

	/** The count of worker threads. */
	private int workerThreads;

	/** The session timeout, in seconds. */
	private int sessionTimeout;

	/** The mime types map. */
	private Map<String, String> mimeTypes = new HashMap<>();

	/** The server thread. */
	private ServerThread serverThread;

	/** The pool of threads. */
	private ExecutorService threadPool;

	/** The document root folder. */
	private Path documentRoot;

	/** The map of workers. */
	private Map<String, IWebWorker> workersMap = new HashMap<>();

	/** The map of user session parameters. */
	private Map<String, SessionMapEntry> sessions = new HashMap<>();

	/** The session random generator. */
	private Random sessionRandom = new Random();

	/** The default length of session id (SID). */
	private final static int SID_LENGTH = 20;

	/**
	 * Generates a session id with the given length. The ID consists of
	 * uppercase letters. Thread-safe implementation.
	 *
	 * @param length
	 *            the length
	 * @return the generated string
	 */
	private synchronized String generateSID(int length) {
		// capital letters only
		char[] chars = new char[length];
		for (int i = 0; i < length; i++) {
			chars[i] = (char) (sessionRandom.nextInt(26) + 'A');
		}

		return new String(chars);
	}

	/**
	 * Instantiates a new smart http server. Loads configuration from given
	 * config file.
	 *
	 * @param configFileName
	 *            the config file name
	 */
	public SmartHttpServer(String configFileName) {
		Properties properties = new Properties();
		try (InputStreamReader reader = new InputStreamReader(
				new FileInputStream(configFileName), StandardCharsets.UTF_8)) {
			properties.load(reader);
		} catch (IOException e) {
			System.err.println("Could not load configuration file.");
		}

		try {
			address = properties.getProperty("server.address");
			port = Integer.parseInt(properties.getProperty("server.port"));
			workerThreads = Integer
					.parseInt(properties.getProperty("server.workerThreads"));
			sessionTimeout = Integer
					.parseInt(properties.getProperty("session.timeout"));
			documentRoot = Paths
					.get(properties.getProperty("server.documentRoot"));

			loadMimeTypes(properties.getProperty("server.mimeConfig"));
			loadWorkers(properties.getProperty("server.workers"));
		} catch (Exception e) {
			System.err.println("Error parsing configuration file.");
		}
	}

	/**
	 * Loads mime types from mime config file into mimeTypes map.
	 *
	 * @param mimeConfig
	 *            the mime config file
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private void loadMimeTypes(String mimeConfig) throws IOException {
		Properties mimeProperties = new Properties();
		try (InputStreamReader reader = new InputStreamReader(
				new FileInputStream(mimeConfig), StandardCharsets.UTF_8)) {
			mimeProperties.load(reader);
		}

		mimeProperties.entrySet().forEach(line -> {
			mimeTypes.put(line.getKey().toString(), line.getValue().toString());
		});
	}

	/**
	 * Loads workers from worker config file into workers map..
	 *
	 * @param workersPath
	 *            the workers path
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private void loadWorkers(String workersPath) throws IOException {
		Properties workersProp = new Properties();

		try (InputStreamReader reader = new InputStreamReader(
				new FileInputStream(workersPath), StandardCharsets.UTF_8)) {
			workersProp.load(reader);
		}

		workersProp.entrySet().forEach(line -> {
			String path = (String) line.getKey();
			String fqcn = (String) line.getValue();

			Class<?> referenceToClass;
			try {
				referenceToClass = this.getClass().getClassLoader()
						.loadClass(fqcn);

				Object newObject = referenceToClass.newInstance();
				IWebWorker iww = (IWebWorker) newObject;

				if (workersMap.containsKey(path)) {
					return;
				}
				workersMap.put(path, iww);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

	}

	/**
	 * Starts the server.
	 */
	protected synchronized void start() {
		if (serverThread == null) {
			serverThread = new ServerThread();
		}

		threadPool = Executors.newFixedThreadPool(workerThreads);
		serverThread.start();
	}

	/**
	 * Stops the server.
	 */
	protected synchronized void stop() {
		serverThread.shutdown();
		threadPool.shutdown();
	}

	/**
	 * The single ServerThread, initiated upon start of a server.
	 *
	 * @author Juraj Juričić
	 */
	protected class ServerThread extends Thread {

		/**
		 * True if the server is running. When set to false, the server will no
		 * longer accept HTTP requests.
		 */
		private boolean running = true;

		@Override
		public void run() {
			try (ServerSocket serverSocket = new ServerSocket(port)) {

				while (running) {
					Socket client = serverSocket.accept();
					ClientWorker cw = new ClientWorker(client);
					threadPool.submit(cw);
				}
			} catch (IOException e) {
				throw new SmartServerException(e);
			}

		}

		/**
		 * Shuts the thread down.
		 */
		public void shutdown() {
			running = false;
		}
	}

	/**
	 * The client worker runnable class.
	 *
	 * @author Juraj Juričić
	 */
	private class ClientWorker implements Runnable {

		/** The socket used for communication. */
		private Socket csocket;

		/** The input stream. */
		private PushbackInputStream istream;

		/** The ooutput stream. */
		private OutputStream ostream;

		/** The version of HTTP. */
		private String version;

		/** The method type (GET, POST, PUT, ...). */
		private String method;

		/** The REQUEST parameters. */
		private Map<String, String> params = new HashMap<String, String>();

		/** The permanent parameters for current session. */
		private Map<String, String> permParams = null;

		/** The output cookies. */
		private List<RCCookie> outputCookies = new ArrayList<RequestContext.RCCookie>();

		/**
		 * Instantiates a new client worker.
		 *
		 * @param csocket
		 *            the csocket
		 */
		public ClientWorker(Socket csocket) {
			super();
			this.csocket = csocket;
		}

		@Override
		public void run() {
			try {
				istream = new PushbackInputStream(csocket.getInputStream());
				ostream = csocket.getOutputStream();
			} catch (IOException e) {
				throw new SmartServerException(e);
			}

			List<String> request = readRequest();

			if (request.isEmpty()) {
				sendStatus(400, "Invalid header");
				closeSocket();
				return;
			}

			String firstLine = request.get(0);
			String[] firstLineData = firstLine.split(" ");

			method = firstLineData[0];
			String path = firstLineData[1];
			version = firstLineData[2];
			if (!method.equals("GET") || !version.equals("HTTP/1.0")
					&& !version.equals("HTTP/1.1")) {
				sendStatus(400, "Bad Request");
				closeSocket();
				return;
			}

			// session
			checkSession(request);

			String paramString;
			String[] pathData = path.split("\\?");
			path = pathData[0].trim();
			if (pathData.length > 1) {
				paramString = pathData[1];
				parseParameters(paramString);
			}

			Path requestedPath = documentRoot.resolve("." + path)
					.toAbsolutePath();
			if (!requestedPath.startsWith(documentRoot.toAbsolutePath())) {
				sendStatus(403, "Forbidden");
				closeSocket();
				return;
			}

			if (workersMap.containsKey(path)) {
				// worker path
				executeWebWorker(path);
				closeSocket();
				return;
			}

			if (path.startsWith("/ext/")) {
				executeWebWorkerExt(path);
				closeSocket();
				return;
			}

			File requestedFile = requestedPath.toFile();
			if (!requestedFile.exists() || !requestedFile.canRead()) {
				sendStatus(404, "Not found");
				closeSocket();
				return;
			}

			String mimeType = getMimeType(requestedPath);
			if (mimeType == null) {
				mimeType = "application/octet-stream";
			}

			if (requestedPath.toString().endsWith(".smscr")) {
				// smartscript
				executeScript(requestedPath);
				closeSocket();
				return;
			}

			RequestContext rc = new RequestContext(ostream, params, permParams,
					outputCookies);
			rc.setMimeType(mimeType);
			rc.setStatusCode(200);
			rc.setStatusText("OK");

			sendFileContents(requestedFile, rc);

			closeSocket();
		}

		/**
		 * Checks if current user has a valid session initiated. If not, creates
		 * a new session ID for the user. Loads user's session parameters into
		 * the permParam map for the thread.
		 *
		 * @param request
		 *            the request
		 */
		private synchronized void checkSession(List<String> request) {
			Pattern p = Pattern.compile("Cookie:[\\s]*(.*)=\"(.*)\"");
			String sidCandidate = null;
			for (String line : request) {
				if (!line.startsWith("Cookie:")) {
					continue;
				}

				Matcher m = p.matcher(line);
				if (m.find() && m.group(1).equals("sid")) {
					sidCandidate = m.group(2);
					break;
				}
			}

			long now = new Date().getTime() / 1000;
			SessionMapEntry entry = null;

			if (sidCandidate != null) {
				entry = sessions.get(sidCandidate);
				if (entry != null && entry.getValidUntil() < now) {
					entry = null;
				}
			}

			if (entry == null) {
				// new SID
				sidCandidate = generateSID(SID_LENGTH);
				long validUntil = now + sessionTimeout;

				entry = new SessionMapEntry(sidCandidate, validUntil);
				sessions.put(sidCandidate, entry);

				outputCookies.add(new RCCookie("sid", sidCandidate,
						sessionTimeout, address, "/"));
			} else {
				entry.setValidUntil(now + sessionTimeout);
			}

			permParams = entry.getMap();
		}

		/**
		 * Loads the requested file and outputs it to the socket.
		 *
		 * @param requestedFile
		 *            the requested file
		 * @param rc
		 *            the rc
		 */
		private void sendFileContents(File requestedFile, RequestContext rc) {
			try (BufferedInputStream fileOutput = new BufferedInputStream(
					new FileInputStream(requestedFile))) {
				int length = 0;
				while (true) {
					byte[] output = new byte[1024];

					length = fileOutput.read(output);
					if (length == -1)
						break;

					rc.write(output, 0, length);
				}
			} catch (IOException e) {
				closeSocket();
				throw new SmartServerException(e);
			}
		}

		/**
		 * Executes the smart script.
		 *
		 * @param path
		 *            the path
		 */
		private void executeScript(Path path) {
			try {
				String script = readFromFile(path.toString());
				RequestContext rc = new RequestContext(ostream, params,
						permParams, outputCookies);
				rc.setMimeType("text/plain");
				new SmartScriptEngine(
						new SmartScriptParser(script).getDocumentNode(), rc)
								.execute();
				closeSocket();

			} catch (IOException e) {
				sendStatus(404, "Not Found");
			}
		}

		/**
		 * Executes the web worker.
		 *
		 * @param path
		 *            the path
		 */
		private void executeWebWorker(String path) {
			IWebWorker iww = workersMap.get(path);
			if (iww == null) {
				return;
			}

			RequestContext rc = new RequestContext(ostream, params, permParams,
					outputCookies);
			iww.processRequest(rc);
		}

		/**
		 * Execute the web worker, based on convention-over-configuration.
		 * Requested path should be /ext/NAME.
		 *
		 * @param name
		 *            the requested path
		 */
		private void executeWebWorkerExt(String name) {
			// "/ext/" - 5 chars
			name = name.substring(5).trim();

			String fqcn = "hr.fer.zemris.java.webserver.workers." + name;

			Class<?> referenceToClass;
			try {
				referenceToClass = this.getClass().getClassLoader()
						.loadClass(fqcn);

				Object newObject = referenceToClass.newInstance();
				IWebWorker iww = (IWebWorker) newObject;

				RequestContext rc = new RequestContext(ostream, params,
						permParams, outputCookies);

				iww.processRequest(rc);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		/**
		 * Reads the whole file and returns it as a String.
		 *
		 * @param filepath
		 *            the filepath
		 * @return the string
		 * @throws IOException
		 *             Signals that an I/O exception has occurred.
		 */
		private String readFromFile(String filepath) throws IOException {
			String docBody = new String(Files.readAllBytes(Paths.get(filepath)),
					StandardCharsets.UTF_8);

			return docBody;
		}

		/**
		 * Gets the mime type for the file path.
		 *
		 * @param path
		 *            the path
		 * @return the mime type
		 */
		private String getMimeType(Path path) {
			String pathString = path.toString();
			int i = pathString.lastIndexOf('.');
			if (i == -1) {
				return null;
			}

			String ext = pathString.substring(i + 1);

			return mimeTypes.get(ext);
		}

		/**
		 * Parses the parameters.
		 *
		 * @param paramString
		 *            the param string
		 */
		private void parseParameters(String paramString) {
			String[] parts = paramString.split("&");
			for (String p : parts) {
				String[] data = p.split("=");
				params.put(data[0].trim(), data[1].trim());
			}
		}

		/**
		 * Sends the status code. Usually used for error code handling.
		 *
		 * @param statusCode
		 *            the status code
		 * @param message
		 *            the message
		 */
		private void sendStatus(int statusCode, String message) {
			RequestContext rcx = new RequestContext(ostream, null, null, null);
			rcx.setStatusCode(statusCode);
			rcx.setStatusText(message);
			try {
				rcx.write(new byte[0]);
			} catch (IOException ignorable) {

			}
		}

		/**
		 * Closes the socket.
		 */
		private void closeSocket() {
			try {
				ostream.flush();
				csocket.close();
			} catch (IOException ignorable) {
				ignorable.printStackTrace();
			}
		}

		/**
		 * Reads the request and returns it as a list of lines.
		 *
		 * @return the list
		 */
		private List<String> readRequest() {
			List<String> list = new ArrayList<String>();

			String line = null;
			try {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(istream, StandardCharsets.UTF_8));
				while ((line = reader.readLine()) != null) {
					if (line.isEmpty()) {
						break;
					}

					list.add(line);
				}
			} catch (IOException e) {
				throw new SmartServerException(e);
			}

			return list;
		}
	}

	/**
	 * The entry of session map. Used for mapping session ID to user param map.
	 *
	 * @author Juraj Juričić
	 */
	private static class SessionMapEntry {

		/** The session id. */
		String sid;

		/** The valid until. */
		long validUntil;

		/** The session params map. */
		Map<String, String> map;

		/**
		 * Instantiates a new session map entry.
		 *
		 * @param sid
		 *            the sid
		 * @param validUntil
		 *            the valid until
		 */
		public SessionMapEntry(String sid, long validUntil) {
			super();
			this.sid = sid;
			this.validUntil = validUntil;

			this.map = new ConcurrentHashMap<>();
		}

		/**
		 * Gets the session id.
		 *
		 * @return the sid
		 */
		@SuppressWarnings("unused")
		public String getSid() {
			return sid;
		}

		/**
		 * Gets the valid until.
		 *
		 * @return the valid until
		 */
		public long getValidUntil() {
			return validUntil;
		}

		/**
		 * Sets the valid until value.
		 *
		 * @param validUntil
		 *            the new valid until
		 */
		public void setValidUntil(long validUntil) {
			this.validUntil = validUntil;
		}

		/**
		 * Gets the map.
		 *
		 * @return the map
		 */
		public Map<String, String> getMap() {
			return map;
		}
	}
}
