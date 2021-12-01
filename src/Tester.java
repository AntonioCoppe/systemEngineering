import static org.junit.Assert.*;
import static org.junit.Assert.fail;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import counter.EmotionCounter;
import counter.EmotionWordsCounter;
import counter.IgnoreWordReader;
import counter.WordCounter;
import download_and_cleaning.Download;


/**
 * @author Matteo Fiore (ID 17140)
 * 
 *
 */
public class Tester {
	private EmotionCounter ec = new EmotionCounter();
	private Download d = new Download();
	private WordCounter wc = new WordCounter();

	@Test
	public void checkWordCounter() {
		if (wc.getClass() == null)
			fail("This class does not exist, cannot count the words");
	}

	@Test
	public void checkDownload() {
		if (d.getClass() == null)
			fail("This class does not exist, cannot download the lyrics");
	}

	@Test
	public void checkEmotionCounter() {
		if (ec.getClass() == null)
			fail("This class does not exist, cannot read emotionWords.csv");
	}

	/**
	 * @throws FileMissingException
	*/
	@Test
	public void testReadIgnoreWord() {
		ArrayList<String> ignoreList = IgnoreWordReader.readSongsFromCSV();
		if (ignoreList == null || ignoreList.size() != 39)
			fail("Some ignore words haven't been read.");
	} 

	/**
	 * @throws FileMissingException
	*/
	@Test
	public void testReadEmotionWord() {
		ArrayList<String> emotionList = EmotionWordsCounter.readSongsFromCSV();
		if (emotionList == null || emotionList.size() != 18)
			fail("Some emotion words haven't been read.");
	} 

}