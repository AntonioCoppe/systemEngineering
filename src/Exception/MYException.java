package Exception;

public class MYException extends Exception {
	/**
	 * This exception was created in order to see if the counter of the words is
	 * equal to 0 or not
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Throwable#getMessage()
	 */
	@Override
	public String getMessage() {
		return "There word counter is equal to 0";
	}

}
