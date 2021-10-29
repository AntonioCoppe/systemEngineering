import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.Test;
import counter.EmotionWordsCounter;
import counter.IgnoreWordReader;
import reading.FileMissingException;

/**
 * @author Antonio Coppe (ID 17913)
 */
public class Tester {
	/*
	 * This class is used for testing if there are "ignore words" or "emotion words"
	 * that have not been readed.
	 */

	@Test
	public void testReadIgnoreWord() throws FileMissingException {
		ArrayList<String> ignoreList = IgnoreWordReader.readSongsFromCSV();
		if (ignoreList == null || ignoreList.size() != 39)
			fail("Some ignore words haven't been read.");
	}

	@Test
	public void testReadEmotionWord() throws FileMissingException {
		ArrayList<String> emotionList = EmotionWordsCounter.readSongsFromCSV();
		if (emotionList == null || emotionList.size() != 18)
			fail("Some emotion words haven't been read.");
	}

}