package reading;

public class FileMissingException extends Exception {

	private static final long serialVersionUID = 1L;

	public FileMissingException(String message) {
		message = "File not found";
	}

}
