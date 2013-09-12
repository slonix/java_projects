/**
 * @author Konrad S³oniewski, ks321221
 * @version 1.0, 29 Apr 2012
 */

/*
 * Exception class for duplicate item errors
 * in binary search tree insertions.
 */
public class DuplicateItemException extends RuntimeException {
    
	private static final long serialVersionUID = 1L;
	
	/*
	 * Constructors of this exception.
	 */
	public DuplicateItemException( ) {
		super( );
	}
    
	public DuplicateItemException( String message ) {
		super( message );
	}
}