package reading;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Antonio Coppe (ID 17913) in this class, this method, reads the
 *         songs.csv File
 */

public class SongsReader {

	@SuppressWarnings("unused")
	private String author;

	@SuppressWarnings("unused")
	private String nSong;

	/**
	 * @param author
	 * @param nSong
	 */
	public SongsReader(String author, String nSong) {
		// TODO Auto-generated constructor stub
		this.author = author;
		this.nSong = nSong;
	}

	/**
	 * @return
	 * 
	 */
	public static ArrayList<String> readSongsFromCSV() {
		String csvFile = "songs.csv";
		String s = "";

		ArrayList<String> songs = new ArrayList<String>();
		try {
			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(new FileReader(csvFile));

			while ((s = br.readLine()) != null) {
				s.split("_");
				songs.add(s);
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return songs;
	}

}
