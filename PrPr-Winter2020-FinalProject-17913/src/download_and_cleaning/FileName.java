package download_and_cleaning;

import reading.FileMissingException;
import reading.InvalidException;
import reading.SongsReader;

/**
 * @author Antonio Coppe(ID 17913)
 * The overall goal of this class is to give the right name to the files
 */
/**
 * @param author
 * @param nSong
 * @throws FileMissingException
 * @throws InvalidException
 */

public class FileName extends SongsReader {
	/*
	 * Constructor
	 */
	public FileName(String author, String nSong) {
		super(author, nSong);
		// TODO Auto-generated constructor stub
	}

	public String author;
	public String nSong;

	/*
	 * Getter
	 */
	public String getAuthor() throws FileMissingException {
		return author;
	}

	/*
	 * set method that checks the validity of the author
	 */
	public void setAuthor(String author) throws InvalidException {
		if (author != null && author.length() > 0)
			this.author = author;
		else
			throw new InvalidException("Author invalid.");
	}

	/*
	 * getter that retrieves every time a new song
	 */
	public static String getSong(int i) throws FileMissingException {
		return SongsReader.readSongsFromCSV().get(i);

	}

	/*
	 * set method that checks the validity of the name of the song
	 */
	public void setnSong(String nSong) throws InvalidException {
		if (nSong != null && nSong.length() > 0)
			this.nSong = nSong;
		else
			throw new InvalidException("Name of the song is invalid.");

	}

	/*
	 * The main method of this class is this one, because it takes the name from the
	 * file and put in the right form for an internet lookup. It takes a the name of
	 * the song and of the author, and divides them with a "-", in order to search
	 * the songs in the site.
	 */
	public static String getTitle(String s) {
		s = s.toLowerCase();
		String son = s.replaceAll("(;).*", "");
		String a = s.replaceAll(".*(;)", "");
		s = a.concat(" " + son);
		String newTitle = s.replaceAll("[\\s\\-]", "-").replaceAll("\\'", "");
		newTitle = "https://genius.com/" + newTitle.concat("-lyrics");
		return newTitle;
	}
}
