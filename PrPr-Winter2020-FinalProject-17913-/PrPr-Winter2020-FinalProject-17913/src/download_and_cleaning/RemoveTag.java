package download_and_cleaning;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * @author Antonio Coppe(ID 17913)	the scope of this class is to clean the html
 * 									file from all the noise through regex.
 * 									
 */
public class RemoveTag {
	/**
	 * @param fileName
	 * @param clean
	 * @throws Exception
	 */
	/*
	 * This method takes as input the html files(fileName) and the title of the songs(clean)
	 * and through the use of regex it cleans all the noise that is not lyrics if the song.
	 * Afterwords with the use of ByteBuffer it encodes the lyrics and writes the cleaned 
	 * version in a new file inside the folder CleanedFiles.
	 * 
	 */
	public static void remove(String fileName, String clean) throws Exception {
		StringBuilder sb = new StringBuilder();
		@SuppressWarnings("resource")
		BufferedReader br = new BufferedReader(new FileReader(fileName));

		String line;
		while ((line = br.readLine()) != null) {

			sb.append(line).append(System.getProperty("line.separator"));

			String nohtml = sb.toString().replaceAll("<(\\\"[^\\\"]*\\\"|'[^']*'|[^'\\\">])*>", "")
					.replaceAll("href=\"", "").replaceAll("Lyrics \\| Genius Lyrics", "")
					.replaceAll("\\A(?s).*?(Lyrics)\\s{2,}(?-s)", "").replaceAll("(?s)\\s*(More on Genius).*\\z", "")
					.replaceAll("(.|\\n)*?", "").replaceAll("(\\[\\d\\d:\\d\\d\\])(\\[\\d\\d:\\d\\d\\])(.+)", "");
			@SuppressWarnings("unused")
			ByteBuffer byteBuffer = StandardCharsets.UTF_16.encode(nohtml);
			PrintWriter writer;

			try {
				writer = new PrintWriter("CleanedFiles/" + clean + ".html.clean.md");
				writer.println(nohtml);

				writer.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * This method is part of the downloading and sleanign the title and
	 * to give it a name like: author_songTitle
	 */
	public static String getFileName(String s) {
		String title = s.replaceAll("(;).*", "");
		String author = s.replaceAll(".*(;)", "");

		String newon = author.concat("_" + title).replaceAll("['-]", "");

		StringBuilder builder = new StringBuilder(newon);

		boolean isLastSpace = true;
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
}
