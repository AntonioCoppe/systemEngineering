package download_and_cleaning;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

/**
 * @author Antonio Coppe(ID 17913)
 * 
 *         The overall goal of this class is to retrieve the html file of the
 *         songs.
 */

public class Download {
	private static HttpsURLConnection conn;

	/**
	 * This method opens the connection to the web with a timeout(5000ms) with the
	 * use of a User-Agent for preventing a 403 error to occur.
	 * 
	 */
	/**
	 * @param url
	 * @return
	 * @throws IOException
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
	 * This method write the html in the file
	 */
	/**
	 * @param url
	 * @param fileName
	 * @throws IOException
	 * 
	 */
	static void writer(String url, String fileName) throws IOException {
		String page = Download.getDownloadUrl(url);
		PrintWriter writer;
		writer = new PrintWriter(fileName);
		writer.println(page);
		writer.close();
	}

	/*
	 * This method recall the previous two in order to give the file the name with
	 * the song that is associated to
	 */
	/**
	 * @param url
	 * @param song
	 * @throws IOException
	 * 
	 */
	public static void DownloadAndSave(String url, String song) throws IOException {
		getDownloadUrl(url);
		String html = "HTMLFiles/" + FileName.getFileName(song) + ".html";
		writer(url, html);
	}
}