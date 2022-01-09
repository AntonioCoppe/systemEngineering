import reading.SongsReader;

/**
 * @author Antonio Coppe(ID 17913)
 *
 */
public class Runner {
	/**
	 * @param args
	 * 
	 */
	public static void main(String[] args) {
		Facade f = new Facade(SongsReader.readSongsFromCSV());
		Thread d = new Thread(f);
		d.start();
		try {
			d.join();

		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}

	}

}
