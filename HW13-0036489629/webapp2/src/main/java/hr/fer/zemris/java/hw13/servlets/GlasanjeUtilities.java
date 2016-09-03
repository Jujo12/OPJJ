package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * The utility class used for voting system. Offsers several helper methods and
 * a {@link Band} public class.
 *
 * @author Juraj Juričić
 */
public class GlasanjeUtilities {

	/** The path to bands definition file. */
	public static final String FILE_DEF = "/WEB-INF/glasanje-definicija.txt";

	/** The path to voting results file. */
	public static final String FILE_RES = "/WEB-INF/glasanje-rezultati.txt";

	/** The data separator used in definition and results files. */
	private static final String SEPARATOR = "\t";

	/**
	 * Loads the bands from given defFile into a List. If resFile is not null,
	 * will also load the scores. Otherwise, score of each band will be set to
	 * null.<br>
	 * The list will be sorted by scores descendingly.
	 *
	 * @param defFile
	 *            the def file
	 * @param resFile
	 *            the res file. If null, scores will not be loaded.
	 * @return the list of bands
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static List<Band> loadBands(Path defFile, Path resFile)
			throws IOException {
		Map<Integer, Integer> scores = null;

		if (resFile != null) {
			List<String> linesRes = Files.readAllLines(resFile);
			scores = new HashMap<>();

			for (String line : linesRes) {
				String[] parts = line.split(SEPARATOR);

				int score = Integer.parseInt(parts[1]);
				scores.put(Integer.parseInt(parts[0]), score);
			}
		}

		List<String> linesDef = Files.readAllLines(defFile);
		List<Band> bands = new ArrayList<>();

		for (String line : linesDef) {
			Band band = Band.fromLine(line);

			if (scores != null) {
				Integer score = scores.get(band.getId());
				if (score == null) {
					score = 0;
				}
				band.setScore(score);
			}
			bands.add(band);
		}

		if (scores != null) {
			Collections.sort(bands);
		}

		return bands;
	}

	/**
	 * Gets the current votes and returns them as a map. The map's keys are band
	 * IDs, while the map's values are scores for the band in question.
	 *
	 * @param resFile
	 *            the path to results file
	 * @return map<ID, score> containing votes data
	 * @throws NumberFormatException
	 *             thrown if an error with parsing occured.
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static Map<Integer, Integer> getCurrentVotes(Path resFile)
			throws NumberFormatException, IOException {
		// tree map so that the entries are ordered by ID
		Map<Integer, Integer> votes = new TreeMap<>();

		for (String line : Files.readAllLines(resFile)) {
			// parse lines
			String[] parts = line.split(SEPARATOR);
			int id = Integer.parseInt(parts[0]);
			int score = Integer.parseInt(parts[1]);

			votes.put(id, score);
		}

		return votes;
	}

	/**
	 * Saves the votes stored in a map to the given result file.
	 *
	 * @param votes
	 *            the votes map. Keys should be band IDs, while values should be
	 *            scores to save.
	 * @param resultsPath
	 *            the path to results file to write.
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void saveVotes(Map<Integer, Integer> votes, Path resultsPath)
			throws IOException {
		List<String> lines = new LinkedList<>();

		votes.forEach((id, score) -> {
			lines.add(id + SEPARATOR + score);
		});

		Files.write(resultsPath, lines, StandardCharsets.UTF_8);
	}

	/**
	 * The class that represents a single Band.
	 *
	 * @author Juraj Juričić
	 */
	public static class Band implements Comparable<Band> {

		/** The band id. */
		private int id;

		/** The band name. */
		private String name;

		/** The band's song url. */
		private String songUrl;

		/** The band's voting score. If null, will be evaluated to 0. */
		private Integer score = null;

		/**
		 * Instantiates a new band.
		 *
		 * @param id
		 *            the id
		 * @param name
		 *            the name
		 * @param songUrl
		 *            the song url
		 */
		public Band(int id, String name, String songUrl) {
			super();
			this.id = id;
			this.name = name;
			this.songUrl = songUrl;
		}

		/**
		 * Gets the score.
		 *
		 * @return the score
		 */
		public int getScore() {
			if (score == null) {
				return 0;
			}
			return score;
		}

		/**
		 * Sets the score.
		 *
		 * @param score
		 *            the score to set
		 */
		public void setScore(int score) {
			this.score = score;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + id;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Band other = (Band) obj;
			if (id != other.id)
				return false;
			return true;
		};

		/**
		 * Constructs a new Band from given line. Line should be in format
		 * ID\tNAME\SONGURL.
		 *
		 * @param line
		 *            the line
		 * @return the band. Null if an error occured while parsing the given String.
		 */
		public static Band fromLine(String line) {
			try {
				String[] parts = line.split("\t");
				int id = Integer.parseInt(parts[0]);

				return new Band(id, parts[1], parts[2]);
			} catch (Exception e) {
				return null;
			}
		}

		/**
		 * Gets the id.
		 *
		 * @return the id
		 */
		public int getId() {
			return id;
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
		 * Gets the song url.
		 *
		 * @return the songUrl
		 */
		public String getSongUrl() {
			return songUrl;
		}

		@Override
		public int compareTo(Band o) {
			return o.score - score;
		}

	}
}
