package counter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Map.Entry;
import reading.FileMissingException;

public class WordCounter {

	public static ArrayList<String> ignoreWords;
	static {
		try {
			ignoreWords = IgnoreWordReader.readSongsFromCSV();
		} catch (FileMissingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author Antonio Coppe(ID 17913)
	 * @param fileName
	 * @return
	 * @throws IOException
	 * @throws FileMissingException
	 */
	/*
	 * This class is divided into two methods, the first one read the cleaned files
	 * and count the most frequent words excluding the ignore words; the second one
	 * write the results of the previous method in a folder called Results with a
	 * name: "author_title.html.wordcount.csv"
	 * 
	 */
	public static List<Entry<String, Long>> wordCount(String fileName) throws IOException, FileMissingException {
		ArrayList<String> list = new ArrayList<String>();
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		String s = br.readLine();
		while ((s = br.readLine()) != null) {
			String[] words = s.replaceAll("[^\\dA-Za-z ]", "").replace("\"", "\"\"").replaceAll("\\R", "").toLowerCase()
					.split("\\s+");
			for (String word : words) {
				if (IgnoreWordReader.readSongsFromCSV().contains(word)) {
					list.remove(word);
				} else
					list.add(word);
			}
		}

		Map<String, Long> map = list.stream().collect(Collectors.groupingBy(w -> w, Collectors.counting()));
		List<Map.Entry<String, Long>> result = map.entrySet().stream()
				.sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).limit(20).collect(Collectors.toList());
		br.close();

		return result;
	}

	public static void writeCount(String input, String destination) throws IOException, FileMissingException {
		PrintWriter writer;

		try {
			writer = new PrintWriter("Results/" + destination + ".html.wordcount.csv");
			for (Entry<String, Long> i : wordCount(input)) {
				writer.println(i);
				writer.append("\n");
			}
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}