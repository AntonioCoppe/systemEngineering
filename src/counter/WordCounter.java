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
import java.util.function.Function;
import java.util.stream.Collectors;
import Exception.MYException;
import java.util.Map.Entry;

public class WordCounter {
	/**
	 * @author Antonio Coppe(ID 17913) This class has the aim to count the 20th most
	 *         frequent words of the lyrics. Through 2 methods, I first count them
	 *         then I write the results in the files
	 */
	public static ArrayList<String> ignoreWords;
	static {
		try {
			ignoreWords = IgnoreWordReader.readSongsFromCSV();
		} catch (Exception e) {
			// TODO Auto-generated catch block

			e.printStackTrace();

		}
	}

	/**
	 * @param fileName
	 * @return
	 * @throws IOException
	 * 
	 */
	/*
	 * In this method, firstly I read the cleaned files, then I remove the blank
	 * spaces and the words that are in the ignoreWords.csv file. Secondly, I put
	 * the results of the list in a Map where I count the number of occurrence. In
	 * the end i saved the Map in a List<Map.Entry<String, Long>> that I used for
	 * putting the results in the reverse order and for taking the 20th most present
	 * words.
	 */
	public static List<Entry<String, Long>> wordCount(String fileName) throws IOException {
		ArrayList<String> list = new ArrayList<String>();
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		String s = br.readLine();
		while ((s = br.readLine()) != null) {
			String[] words = s.replaceAll("[^\\dA-Za-z ]", "").replaceAll("\"", "\"\"").replaceAll("\\R", "")
					.toLowerCase().split("\\W+");
			for (String word : words) {
				if (word.isEmpty()) {
					list.remove(word);
				} else if (!IgnoreWordReader.readSongsFromCSV().contains(word)) {
					list.add(word);

				} else
					list.remove(word);

			}
		}
		Map<String, Long> map = list.stream()
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

		List<Map.Entry<String, Long>> result = map.entrySet().stream()
				.sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).limit(20).collect(Collectors.toList());

		br.close();
		return result;
	}

	/**
	 * @param input
	 * @param destination
	 * @throws IOException
	 */
	/*
	 * In this method I write the results in the file.
	 * 
	 * @throws MYException
	 */
	@SuppressWarnings("resource")
	public static void writeCount(String input, String destination) throws MYException, IOException {
		PrintWriter writer;
		String s = "";
		try {
			writer = new PrintWriter("Results/" + destination + ".html.wordcount.csv");
			for (Entry<String, Long> i : wordCount(input)) {
				String g = i.getValue() + "";
				String h = i.getKey();
				if (h == null) {
					throw new MYException();
				}
				s = g.concat(";" + h);
				writer.println(s);
			}
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}