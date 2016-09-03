package hr.fer.zemris.java.tecaj.hw6.demo5;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * The solution of the HW06 problem 5. Demonstration of Java streams API.<br> 
 * The program does not accept command line arguments.
 *
 *
 * @author Juraj Juričić
 */
public class StudentDemo {
	
	/**
	 * The main method that is executed when the program is run.
	 *
	 * @param args Command line arguments
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("./studenti.txt"),
				StandardCharsets.UTF_8);
		List<StudentRecord> records = convert(lines);

		class OdlikasiPredicate implements Predicate<StudentRecord> {
			@Override
			public boolean test(StudentRecord t) {
				return t.getGrade() == 5;
			}
		}

		// COUNT(MI+ZI+LAB > 25b)
		long broj = records.stream()
				.filter(t -> t.getTotalScore() > 25)
				.count();

		// COUNT(grade = 5)
		long broj5 = records.stream()
				.filter(new OdlikasiPredicate())
				.count();

		// LIST(grade = 5)
		List<StudentRecord> odlikasi = records.stream()
				.filter(new OdlikasiPredicate())
				.collect(Collectors.toList());

		// LIST(grade = 5), SORT BY totalScore DESC
		List<StudentRecord> odlikasiSortirano = records.stream()
				.filter(new OdlikasiPredicate())
				.sorted((o1, o2) -> -Double.compare(o1.getTotalScore(), o2.getTotalScore()))
				.collect(Collectors.toList());
		
		// LIST(jmbag : grade = 1), SORT BY JMBAG ASC
		List<String> nepolozeniJMBAGovi = records.stream()
				.filter(t -> t.getGrade() == 1)
				.map(StudentRecord::getJmbag)
				.sorted((o1,o2) -> o1.compareTo(o2))
				.collect(Collectors.toList());

		// Mapa<int : ocjene, LIST(grade = key)>
		Map<Integer, List<StudentRecord>> mapaPoOcjenama = records.stream()
				.collect(Collectors.groupingBy(StudentRecord::getGrade));
		
		// Mapa<int : ocjene, COUNT(grade = key)>
		Map<Integer, Integer> mapaPoOcjenama2 = records.stream()
				.collect(Collectors.toMap(StudentRecord::getGrade,
							(t) -> 1,
							Integer::sum
						));

		// Mapa<boolean, List<StudentRecord>>:
		// true : LIST(grade > 1)
		// false : LIST(grade = 1)
		Map<Boolean, List<StudentRecord>> prolazNeprolaz = records.stream()
				.collect(Collectors.partitioningBy(t -> t.getGrade() > 1));
	}

	/**
	 * Parses the list of lines into a list of student records.
	 *  
	 * @param lines the lines to parse; each line should have data separated by \t.
	 * @return the list of StudentRecords
	 */
	private static List<StudentRecord> convert(List<String> lines) {
		List<StudentRecord> records = new ArrayList<>();

		for (String line : lines) {
			line = line.trim();

			if (!line.isEmpty()) {
				String[] data = line.split("\\t");
				records.add(new StudentRecord(data));
			}
		}

		return records;
	}
}
