package download_and_cleaning;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import reading.FileMissingException;

/**
 * @author Antonio Coppe (ID 17913)
 * 
 *  The overall goal of this class is to retrieve the html file of the songs.
 */
/**
 * @param url
 * @param fileName
 * @param song
 * @throws IOException
 * @throws FileMissingException
 */

public class Download {
	private static HttpsURLConnection conn;

	/**
	 * This method opens the connection to the web with a timeout(5000ms) with the
	 * use of a User-Agent for preventing a 403 error to occur.
	 * 
	 */
	public static String getDownloadUrl(String url) throws IOException {
		String count = "";
		URL u = new URL(url);
		conn = (HttpsURLConnection) u.openConnection();
		conn.setReadTimeout(5000);
		conn.addRequestProperty("Accept-Language", "en-US,en;q=0.8");
		conn.addRequestProperty("User-Agent", "Mozilla");
		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String content = "";
		while ((content = in.readLine()) != null) {
			count += content + "\n";
		}
		return count;
	}

	/**
	 * This method write the html in a file
	 */
	static void writer(String url, String fileName) throws IOException, FileMissingException {
		String page = Download.getDownloadUrl(url);
		PrintWriter writer;
		writer = new PrintWriter(fileName);
		writer.println(page);
		writer.close();
	}

	/*
	 * This method recall the previous two in order to associate the file with its
	 * own name
	 */
	public static void DownloadAndSave(String url, String song) throws IOException, FileMissingException {
		getDownloadUrl(url);
		String html = "HTMLFiles/" + RemoveTag.getFileName(song) + ".html";
		writer(url, html);
	}
}