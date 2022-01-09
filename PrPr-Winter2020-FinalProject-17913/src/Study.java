import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import counter.EmotionCounter;
import counter.WordCounter;
import download_and_cleaning.Download;
import download_and_cleaning.FileName;
import download_and_cleaning.RemoveTag;
import reading.SongsReader;
/*
 * @author Antonio Coppe(ID 17913)
 * 
 * This class is used for calling the methods in the other classes
 * and starting the process from setting up the connection to the internet
 * to looking on genius for the specific song, downloading the html file,
 * cleaning it writing on new file and counting the different words.
 */

public class Study implements Runnable {
	/*
	 * @param song
	 */
	public ArrayList<String> song;

	/*
	 * constructor
	 */
	public Study(ArrayList<String> songs) {
		setSongs(songs);
	}

	@Override
	public void run() {

		System.out.println("Start process ");
		FileHandler fh;
		Logger logger = Logger.getLogger(Download.class.getName());
		Logger logger1 = Logger.getLogger(RemoveTag.class.getName());
		Logger logger2 = Logger.getLogger(WordCounter.class.getName());
		Logger logger3 = Logger.getLogger(EmotionCounter.class.getName());
		try {
			fh = new FileHandler("log.dat");
			SimpleFormatter formatter = new SimpleFormatter();
			fh.setFormatter(formatter);
			logger.addHandler(fh);
			logger.info("Downloading the html files");
			logger1.addHandler(fh);
			logger1.info("Cleaning those files");
			logger2.addHandler(fh);
			logger2.info("Counting ignore words");
			logger3.addHandler(fh);
			logger3.info("Counting emotion words");

		} catch (SecurityException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			for (int i = 0; i < SongsReader.readSongsFromCSV().size(); i++) {
				String cleanedFile = RemoveTag.getFileName(FileName.getSong(i));
				String htmlFile = RemoveTag.getFileName(FileName.getSong(i)) + ".html";
				String s = FileName.getTitle(FileName.getSong(i));
				Download.DownloadAndSave(s, FileName.getSong(i));
				RemoveTag.remove("HTMLFiles/" + htmlFile, cleanedFile);
				WordCounter.writeCount("CleanedFiles/" + htmlFile + ".clean.md", cleanedFile);
				EmotionCounter.writeCount("CleanedFiles/" + htmlFile + ".clean.md", cleanedFile);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("End process");
	}

	/*
	 * setter
	 */
	void setSongs(ArrayList<String> song) {
		// TODO Auto-generated method stub
		this.song = song;
	}

	/*
	 * getter
	 */
	public ArrayList<String> getSongs() {
		return song;
	}
}
