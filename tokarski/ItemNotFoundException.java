/**
 * @author Konrad S³oniewski, ks321221
 * @version 1.0, 29 Apr 2012
 */

/*
 * Exception class for failed item removes or finds
 * in binary search tree.
 */
public class ItemNotFoundException extends RuntimeException {
    
	private static final long serialVersionUID = 1L;

	/*
	 * Constructors of this exception.
	 */
    public ItemNotFoundException( ) {
        super( );
    }
    
    public ItemNotFoundException( String message ) {
        super( message );
    }
}