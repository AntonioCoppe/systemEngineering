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
import java.util.Map.Entry;
import java.util.stream.Collectors;
import counter.EmotionWordsCounter;
import reading.FileMissingException;

public class EmotionCounter {

	/**
	 * @author Antonio Coppe(ID 17913)
	 * @param fileName
	 * @return
	 * @throws FileMissingException
	 * @throws IOException
	 */
	/*
	 * This class is divided into two methods, the first one read the cleaned files
	 * and count how many emotion words are present; the second one writes the
	 * results of the previous method in a new file inside the Result folder as:
	 * "author_title.html.emotioncount.csv"
	 */
	public static List<Entry<String, Long>> countEmotion(String fileName) throws FileMissingException, IOException {
		ArrayList<String> list = new ArrayList<String>();
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		String s = br.readLine();
		while ((s = br.readLine()) != null) {
			String[] words = s.replaceAll("[^\\dA-Za-z ]", "").replace("\"", "\"\"").replaceAll("\\R", "").toLowerCase()
					.split("\\s+");
			for (String word : words) {
				if (EmotionWordsCounter.readSongsFromCSV().contains(word)) {
					list.add(word);
				} else
					list.remove(word);
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
			writer = new PrintWriter("Results/" + destination + ".html.emotioncount.csv");
			for (Entry<String, Long> i : countEmotion(input)) {
				writer.println(i);
				writer.append("\n");
			}
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}