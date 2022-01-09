package counter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Antonio Coppe(ID 17913) This class has the purpose to read the
 *         ignoreWords.csv file
 *
 */
public class IgnoreWordReader {
	/**
	 * @return
	 * 
	 */
	public static ArrayList<String> readSongsFromCSV() {
		String csvFile = "ignoreWords.csv";
		String s = "";

		ArrayList<String> songs = new ArrayList<String>();
		try {

			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(new FileReader(csvFile));
			while ((s = br.readLine()) != null) {
				s.split("_");
				songs.add(s);

			}

		} catch (IOException e) {
			e.printStackTrace();

		}
		return songs;
	}
}
