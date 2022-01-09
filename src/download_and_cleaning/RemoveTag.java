package download_and_cleaning;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * @author Antonio Coppe(ID 17913) the scope of this class is to clean the html
 *         file from all the noise through regex.
 * 
 */
public class RemoveTag {

	/**
	 * @param fileName
	 * @param clean
	 * @throws Exception
	 */
	/*
	 * This method takes as input the html files(fileName) and the title of the
	 * songs(clean) and through the use of regex it cleans all the noise that is not
	 * lyrics if the song. Afterwords with the use of ByteBuffer it encodes the
	 * lyrics and writes the cleaned version in a new file inside the folder
	 * CleanedFiles.
	 * 
	 */
	public static void remove(String fileName, String clean) throws Exception {
		StringBuilder sb = new StringBuilder();
		@SuppressWarnings("resource")
		BufferedReader br = new BufferedReader(new FileReader(fileName));

		String line;
		while ((line = br.readLine()) != null) {

			sb.append(line).append(System.getProperty("line.separator"));
		}
		String nohtml = sb.toString().replaceAll("\\<[^>]*>", "").replaceAll("href=\"", "")
				.replaceAll("Lyrics \\| Genius Lyrics", "").replaceAll("\\A(?s).*?(Lyrics)\\s{2,}(?-s)", "")
				.replaceAll("(?s)\\s*(More on Genius).*\\z", "")
				.replaceAll("<(\\\"[^\\\"]*\\\"|'[^']*'|[^'\\\">])*>", "");

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
