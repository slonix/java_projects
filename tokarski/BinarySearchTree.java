/**
 * @author Konrad S³oniewski, ks321221
 * @version 1.0, 29 Apr 2012
 */

/*
 * The implementation of Binary Search Tree
 */
public class BinarySearchTree {

	protected BinaryNode root;
	
	public BinarySearchTree( ) {
		root = null;
	}
	
	public void insert( Index ind ) {
		root = insert( ind, root );
	}
	
	protected BinaryNode insert( Index ind, BinaryNode node ) {
		if( node == null )
			node = new BinaryNode( ind );
		else if( ind.compareTo( node.element ) < 0 )
			node.left = insert( ind, node.left );
		else if( ind.compareTo( node.element ) > 0 )
			node.right = insert( ind, node.right );
		else
			throw new DuplicateItemException( ind.toString( ) );
		return node;
	}
	
	public void remove( String str ){
		remove( new Index(str) );
	}
	
	public void remove( Index ind ) {
		root = remove( ind, root );
	}
	
	protected BinaryNode remove( Index ind, BinaryNode node ) {
		if( node == null )
			throw new ItemNotFoundException( ind.toString( ) );
		if( ind.compareTo( node.element ) < 0 )
			node.left = remove( ind, node.left );
		else if( ind.compareTo( node.element ) > 0 )
			node.right = remove( ind, node.right );
		else if( node.left != null && node.right != null ) {
			node.element = findMin( node.right ).element;
			node.right = removeMin( node.right );
		} else
			node = ( node.left != null ) ? node.left : node.right;
		return node;
	}
	
	protected BinaryNode findMin( BinaryNode t ) {
		if( t != null )
			while( t.left != null )
				t = t.left;
	        
		return t;
	}
	
	protected BinaryNode removeMin( BinaryNode t ) {
		if( t == null )
			throw new ItemNotFoundException( );
		else if( t.left != null ) {
			t.left = removeMin( t.left );
			return t;
		} else
			return t.right;
	}
	
	private Index elementAt( BinaryNode t ) {
		return t == null ? null : t.element;
    }
    
	public Index find( Index ind ) {
		return elementAt( find( ind, root ) );
	}
    
	public Index find( String lex ) {
		return find( new Index( lex ) );
	}
    
	private BinaryNode find( Index x, BinaryNode t ) {
		while( t != null ) {
			if( x.compareTo( t.element ) < 0 )
				t = t.left;
			else if( x.compareTo( t.element ) > 0 )
				t = t.right;
			else
				return t;    // Found
		}
		return null;         // Not found
	}

}

/*
 * The class of Binary Search Tree node objects 
 */
class BinaryNode {
    
	Index element;           // The data in the node
	BinaryNode left;         // Left child
	BinaryNode right;        // Right child
	
	BinaryNode( Index theElement ) {
		element = theElement;
		left = right = null;
	}   

}