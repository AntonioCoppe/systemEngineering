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
 * In this class I simply re-call all the other
 * classes' methods in order to run them and executing the tasks that this
 * project should do
 */

public class Facade implements Runnable {
	/*
	 * @param song
	 */
	public ArrayList<String> song;

	/*
	 * constructor
	 */
	/**
	 * @param songs
	 */
	public Facade(ArrayList<String> songs) {
		setSongs(songs);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {

		System.out.println("Start process ");
		FileHandler fh;
		Logger logger = Logger.getLogger("Download");
		Logger logger1 = Logger.getLogger("RemoveTag");
		Logger logger2 = Logger.getLogger("WordCounter");
		Logger logger3 = Logger.getLogger("EmotionCounter");
		try {
			fh = new FileHandler("log.dat");
			SimpleFormatter formatter = new SimpleFormatter();
			fh.setFormatter(formatter);
			logger.addHandler(fh);
			logger.info("Downloading ");
			logger1.addHandler(fh);
			logger1.info("Cleaning");
			logger2.addHandler(fh);
			logger2.info("Counting words");
			logger3.addHandler(fh);
			logger3.info("Counting emotion words");

		} catch (SecurityException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			for (int i = 0; i < SongsReader.readSongsFromCSV().size(); i++) {
				String cleanedFile = FileName.getFileName(FileName.getSong(i));
				String htmlFile = FileName.getFileName(FileName.getSong(i)) + ".html";
				String s = FileName.getTitle(FileName.getSong(i));
				Download.DownloadAndSave(s, FileName.getSong(i));
				RemoveTag.remove("HTMLFiles/" + htmlFile, cleanedFile);
				WordCounter.writeCount("CleanedFiles/" + htmlFile + ".clean.md", cleanedFile);
				EmotionCounter.emotionCount("CleanedFiles/" + htmlFile + ".clean.md", cleanedFile);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("End process");

	}

	/*
	 * setter
	 */
	/**
	 * @param song
	 */
	void setSongs(ArrayList<String> song) {
		// TODO Auto-generated method stub
		this.song = song;
	}

	/*
	 * getter
	 */
	/**
	 * @return
	 */
	public ArrayList<String> getSongs() {
		return song;
	}
}
