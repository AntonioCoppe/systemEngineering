import reading.SongsReader;

/**
 * @author Antonio Coppe(ID 17913)
 *
 */
public class Runner {
	public static void main(String[] args) throws Exception {
		Thread d = new Thread(new Study(SongsReader.readSongsFromCSV()));
		d.start();
		try {
			d.join();
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}
}
