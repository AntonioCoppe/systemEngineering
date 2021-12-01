package download_and_cleaning;

import java.io.IOException;
import reading.SongsReader;

/**
 * @author Antonio Coppe(ID 17913)
 * The overall goal of this class is to give the right name to the files
 */
/**
 * @param author
 * @param nSong
 * 
 */

public class FileName extends SongsReader {
	/*
	 * constructor
	 */
	public FileName(String author, String nSong) {
		super(author, nSong);
		// TODO Auto-generated constructor stub
	}

	public String author;
	public String nSong;

	/*
	 * getter
	 */
	/**
	 * @return
	 * 
	 */
	public String getAuthor() {
		return author;
	}

	/*
	 * set method that checks the validity of the author
	 */
	/**
	 * @param author
	 * @throws Exception
	 * @throws InvalidException
	 */
	public void setAuthor(String author) throws Exception {
		if (author != null && author.length() > 0)
			this.author = author;
		else
			throw new Exception("Author invalid.");
	}

	/*
	 * getter that retrieves every time a new song
	 */
	/**
	 * @param i
	 * @return
	 * @throws IOException
	 * 
	 */
	public static String getSong(int i) {
		return SongsReader.readSongsFromCSV().get(i);

	}

	/*
	 * set method that checks the validity of the name of the song
	 */
	/**
	 * @param nSong
	 * @throws Exception
	 */
	public void setnSong(String nSong) throws Exception {
		if (nSong != null && nSong.length() > 0)
			this.nSong = nSong;
		else
			throw new Exception("Name of the song is invalid.");

	}

	/*
	 * This method is used to build the correct name of the file
	 */
	/**
	 * @param s
	 * @return
	 */

	public static String getFileName(String s) {

		boolean isLastSpace = true;
		String title = s.replaceAll("(;).*", "");
		String author = s.replaceAll(".*(;)", "");

		String la = author.concat("_" + title).replaceAll("['-]", "");

		StringBuilder builder = new StringBuilder(la);

		for (int i = 0; i < builder.length(); i++) {
			char ch = builder.charAt(i);

			if (isLastSpace && ch >= 'a' && ch <= 'z') {
				builder.setCharAt(i, (char) (ch + ('A' - 'a')));
				isLastSpace = false;
			} else if (ch != ' ')
				isLastSpace = false;
			else
				isLastSpace = true;
		}

		return builder.toString().replaceAll("\\s", "");
	}

	/*
	 * This method allows to search the lyrics on genius
	 */
	/**
	 * @param s
	 * @return
	 * 
	 */
	@SuppressWarnings("null")
	public static String getTitle(String s) {
		s = s.toLowerCase();
		String son = s.replaceAll("(;).*", "");
		if (son == null && son.length() < 0) {
			System.out.println("Name of the song is invalid");

		}
		String a = s.replaceAll(".*(;)", "");

		s = a.concat(" " + son);
		String newTitle = s.replaceAll("[\\s\\-]", "-").replaceAll("\\'", "");
		newTitle = "https://genius.com/" + newTitle.concat("-lyrics");
		return newTitle;
	}

}
