package hr.fer.zemris.java.hw16.trazilica;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.stream.Collectors;

/**
 * The simple document search engine. Uses TF-IDF algorithm for determining similarity.
 *
 * @author Juraj Juričić
 */
public class SearchEngine {
	
	/** The path to stop words file. */
	private final Path STOP_WORDS_PATH = Paths.get("hrvatski_stoprijeci.txt");
	
	/** The stop words. */
	private Set<String> stopWords;
	
	/** The dictionary map. Maps word to the number of documents that use that word. */
	private Map<String, Integer> dictionary = new HashMap<>();
	
	/** The documents map. Maps the path to a document's map that maps the word to the
	 * number of occurences of a word in the document. */
	private Map<Path, Map<String, Integer>> documents = new HashMap<>();
	
	/** Map of calculated tf-idf (word, document) values. */
	private Map<Tuple<String, Path>, Double> tfidf = new HashMap<>();
	
	/** The map of calculated IDF values for the words. */
	private Map<String, Double> idfMap = new HashMap<>();

	/** The results map. */
	private Map<Path, Double> results;

	/**
	 * Instantiates a new search engine.
	 *
	 * @param homePath the home path
	 */
	public SearchEngine(Path homePath){
		stopWords = loadStopWords(STOP_WORDS_PATH);
		
		try {
			Files.walkFileTree(homePath, new FileTreeWalker());
		}catch(IOException e){
			throw new RuntimeException(e);
		}

		calculateTFIDF();
	}
	
	/**
	 * Loads the stop words from given file and returns a set of stop words.
	 *
	 * @param filePath the path to stop words file
	 * @return the set of stop words
	 */
	private Set<String> loadStopWords(Path filePath){
		Set<String> set = new HashSet<>();
		
		try{
			Files.readAllLines(filePath, StandardCharsets.UTF_8).forEach(set::add);
		}catch(IOException e){
			System.err.println("An error occured while reading stop words.");
		}
		
		return set;
	}

	/**
	 * The File Visitor used for walking through the articles. Scans them for words
	 * and extracts to dictionary maps.
	 *
	 * @author Juraj Juričić
	 */
	private class FileTreeWalker extends SimpleFileVisitor<Path>{
		
		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attr) throws IOException{
			List<String> lines = Files.readAllLines(file, StandardCharsets.UTF_8);

			Map<String, Integer> documentVector = new HashMap<>();
			documents.put(file, documentVector);

			lines.forEach(l -> {
				String[] words = splitQuery(l, false);
				for(String w : words) {
					w = w.trim().toLowerCase();
					if (w.isEmpty()) continue;
					if (stopWords.contains(w)) continue;

					if (!documentVector.containsKey(w)) {
						dictionary.merge(w, 1, Integer::sum);
					}
					documentVector.merge(w, 1, Integer::sum);
				}
			});

			return FileVisitResult.CONTINUE;
		}
	}

	/**
	 * Calculates TF-IDF value for all word-document pairs.
	 */
	private void calculateTFIDF(){
		dictionary.keySet().forEach(word -> {
			double idf = idf(word);
			idfMap.put(word, idf);
			documents.keySet().forEach(document -> {
				double calcTFIDF = tf(word, document) * idf;
				tfidf.put(new Tuple<>(word, document), calcTFIDF);
			});
		});
	}


	/**
	 * Calculated TF for the given word-document pair.
	 *
	 * @param word the word
	 * @param document the document
	 * @return the TF value
	 */
	private int tf(String word, Path document){
		Integer result = documents.get(document).get(word);

		if (result == null) return 0;
		return result;
	}

	/**
	 * Calculates the IDF value for the given word.
	 *
	 * @param word the word
	 * @return the double
	 */
	private double idf(String word){
		// log(|D| / | d€D : w€d |);
		double count = 0;

		for(Map<String, Integer> map : documents.values()){
			Integer c = map.get(word);
			if (c != null && c != 0){
				count += c;
			}
		}

		if (count == 0){
			return 0;
		}
		return Math.log(documents.size() / count);
	}

	/**
	 * Determines the similarity of given query array and document.
	 *
	 * @param query the query
	 * @param doc the doc
	 * @return the double
	 */
	private double similarity(String[] query, Path doc){
		double sum = 0;

		for(String w : query){
			double docVal = tfidf.get(new Tuple<>(w, doc));
			double queryVal = idfMap.get(w);

			sum += docVal * queryVal;
		}

		double v2size = documents.get(doc).size();
		double norm = (query.length * v2size);

		if (norm == 0){
			return 0;
		}

		return sum / norm;
	}

	/**
	 * Gets the dictionary size.
	 *
	 * @return the dictionary size
	 */
	public int getDictionarySize(){
		return dictionary.size();
	}

	/**
	 * Performs a search for given query array. Stores the results in member variable, and returns it.
	 *
	 * @param query the query
	 * @return the results map
	 */
	public Map<Path, Double> search(String[] query){
		Map<Path, Double> resultsUnsorted = new HashMap<>();

		documents.keySet().parallelStream().forEach(doc -> {
			resultsUnsorted.put(doc, similarity(query, doc));
		});

		results = new LinkedHashMap<>();

		resultsUnsorted.entrySet().stream().sorted((x, y) -> {
			if (x.getValue().equals(y.getValue())) return 0;

			return (x.getValue() - y.getValue() < 0) ? 1 : -1;
		}).collect(Collectors.toList()).forEach(x -> {
			results.put(x.getKey(), x.getValue());
		});

		return results;
	}

	/**
	 * Splits the query, filtering the words not contained in vocabulary.
	 *
	 * @param query the query
	 * @param filter if true, words that are not contained in vocabulary will not be returned
	 * @return the words array.
	 */
	public String[] splitQuery(String query, boolean filter){
		String[] words = query.split("[^\\p{L}]+");

		List<String> containedWords = new LinkedList<>();
		for(String w : words){
			if (filter && !dictionary.containsKey(w)){
				continue;
			}
			containedWords.add(w);
		}

		return containedWords.toArray(new String[containedWords.size()]);
	}

	/**
	 * Prints the results to standard output.
	 *
	 * @param limit the limit
	 */
	public void printResults(int limit){
		if (results == null || results.size() == 0){
			System.out.println("There are no results.");
			return;
		}

		int i = 0;

		for(Map.Entry<Path, Double> row : results.entrySet()){
			if (i >= limit){
				break;
			}

			if (row.getValue() < 1E-10){
				return;
			}

			String out = String.format(Locale.US, "[%2d] (%.4f) %s" + row.getKey(), i++, row.getValue(), row.getKey().toAbsolutePath());
			System.out.println(out);
		}
	}

	/**
	 * Gets the path of the i-th document in results array.
	 *
	 * @param i the i
	 * @return the result path
	 */
	public Path getResultPath(int i){
		if (results == null){
			return null;
		}

		for(Map.Entry<Path, Double> e : results.entrySet()){
			return e.getKey();
		}

		return null;
	}
}
