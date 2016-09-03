package hr.fer.zemris.java.hw14;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.beans.PropertyVetoException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * The listener called at application boot time. Used for setting up database
 * connection pool and intiializing tables.
 *
 * @author Juraj Juričić
 */
@WebListener
public class Initialization implements ServletContextListener {

	/** The db properties. */
	Properties dbProperties = new Properties();

	/** The path to database settings file. */
	private static final String PROPS_PATH = "/WEB-INF/dbsettings.properties";

	/** The db host. */
	private String host;

	/** The db port. */
	private int port;

	/** The db name. */
	private String dbName;

	/** The db user. */
	private String dbUser;

	/** The db password. */
	private String dbPass;

	/**
	 * Loads the properties from given file path.
	 *
	 * @param propFilePath
	 *            the prop file path
	 */
	private void loadProps(String propFilePath) {
		InputStreamReader reader = null;

		try {
			reader = new InputStreamReader(new FileInputStream(propFilePath),
					StandardCharsets.UTF_8);
			dbProperties.load(reader);

			host = dbProperties.getProperty("host");
			port = Integer.parseInt(dbProperties.getProperty("port"));
			dbName = dbProperties.getProperty("name");
			dbUser = dbProperties.getProperty("user");
			dbPass = dbProperties.getProperty("password");
		} catch (IOException e) {
			throw new RuntimeException(
					"Could not load configuration file. Please check if it exists.");
		} catch (NumberFormatException e) {
			throw new RuntimeException(
					"Invalid configuration file format: port");
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (Exception ignorable) {
				}
			}
		}
	}

	/**
	 * Escapes the provided input String so it can be safely used in side a
	 * Derby URI. ; is considered illegal and each instance will be removed.
	 * 
	 * @param input
	 *            The input parameter
	 * @return the escaped parameter
	 */
	private String escapeDerbyURI(String input) {
		return input.replace("\"", "\\\"").replace(";", "");
	}

	/**
	 * Creates the database tables if they do not exist.
	 *
	 * @param con
	 *            the con
	 * @throws SQLException
	 *             the SQL exception
	 */
	private void createTables(Connection con) throws SQLException {
		Statement st = con.createStatement();

		String query = "CREATE TABLE Polls\n"
				+ " (id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,\n"
				+ " title VARCHAR(150) NOT NULL,\n"
				+ " message CLOB(2048) NOT NULL\n" + ")";
		try {
			st.execute(query);
		} catch (SQLException ignorable) {
			// table already exists, ignore;
		}

		query = "CREATE TABLE PollOptions\n"
				+ " (id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,\n"
				+ " optionTitle VARCHAR(100) NOT NULL,\n"
				+ " optionLink VARCHAR(150) NOT NULL,\n" + " pollID BIGINT,\n"
				+ " votesCount BIGINT,\n"
				+ " FOREIGN KEY (pollID) REFERENCES Polls(id)\n" + ")\n";
		try {
			st.execute(query);
		} catch (SQLException ignorable) {
			// table already exists, ignore;
		}
	}

	/**
	 * Populates the database tables if they do are empty.
	 *
	 * @param con
	 *            the con
	 * @throws SQLException
	 *             the SQL exception
	 */
	private void populateTables(Connection con) throws SQLException {
		Statement st = con.createStatement();

		String query = "SELECT COUNT(*) FROM Polls";
		ResultSet rs = st.executeQuery(query);
		if (rs == null || !rs.next()) {
			return;
		}

		int count = rs.getInt(1);

		if (count == 0) {
			long insertId;
			st.executeUpdate(
					"INSERT INTO Polls (title, message) VALUES ('Bands poll:', 'Which of these bands is your favorite?')",
					Statement.RETURN_GENERATED_KEYS);
			rs = st.getGeneratedKeys();

			if (rs != null && rs.next()) {
				insertId = rs.getLong(1);
				st.executeUpdate(
						"INSERT INTO PollOptions (optionTitle, optionLink, pollID, votesCount) VALUES\n"
								+ "('Alter Bridge', 'https://www.youtube.com/watch?v=yz_j7nVCJJ0', "
								+ insertId + ", 0),\n"
								+ "('Dream Theater', 'https://www.youtube.com/watch?v=ZVMIk3xYaYo', "
								+ insertId + ", 0),\n"
								+ "('Iron Maiden', 'https://www.youtube.com/watch?v=0NYiOHGapRk', "
								+ insertId + ", 0),\n"
								+ "('Pink Floyd', 'https://www.youtube.com/watch?v=vi7cuAjArRs', "
								+ insertId + ", 0),\n"
								+ "('Porcupine Tree', 'https://www.youtube.com/watch?v=iBfY86cktN0', "
								+ insertId + ", 0),\n"
								+ "('Yes', 'https://www.youtube.com/watch?v=-Tdu4uKSZ3M', "
								+ insertId + ", 0)");
			}

			st.executeUpdate(
					"INSERT INTO Polls (title, message) VALUES ('Java IDE poll:', 'Which of these Java IDEs is your favorite?')",
					Statement.RETURN_GENERATED_KEYS);
			rs = st.getGeneratedKeys();

			if (rs != null && rs.next()) {
				insertId = rs.getLong(1);
				st.executeUpdate(
						"INSERT INTO PollOptions (optionTitle, optionLink, pollID, votesCount) VALUES\n"
								+ "('IntelliJ IDEA', 'https://www.jetbrains.com/idea/', "
								+ insertId + ", 0),"
								+ "('Eclipse', 'https://eclipse.org/', "
								+ insertId + ", 0),"
								+ "('NetBeans', 'https://netbeans.org/', "
								+ insertId + ", 0),"
								+ "('I do not use IDEs; I write my code in JNotepad++ and compile manually', 'http://lmgtfy.com/?q=javac&l=1', "
								+ insertId + ", 0)");
			}
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		loadProps(sce.getServletContext().getRealPath(PROPS_PATH));

		String connectionURL = "jdbc:derby://" + escapeDerbyURI(host) + ":"
				+ escapeDerbyURI(Integer.toString(port)) + "/"
				+ escapeDerbyURI(dbName) + ";user=" + escapeDerbyURI(dbUser)
				+ ";password=" + escapeDerbyURI(dbPass);

		ComboPooledDataSource cpds = new ComboPooledDataSource();
		try {
			cpds.setDriverClass("org.apache.derby.jdbc.ClientDriver");
		} catch (PropertyVetoException e1) {
			throw new RuntimeException("Error while initializing DB pool.", e1);
		}
		cpds.setJdbcUrl(connectionURL);

		sce.getServletContext().setAttribute("hr.fer.zemris.dbpool", cpds);

		try {
			createTables(cpds.getConnection());
			populateTables(cpds.getConnection());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ComboPooledDataSource cpds = (ComboPooledDataSource) sce
				.getServletContext().getAttribute("hr.fer.zemris.dbpool");
		if (cpds != null) {
			try {
				DataSources.destroy(cpds);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}