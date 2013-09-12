/**
 * @author Konrad S³oniewski, ks321221
 * @version 1.0, 29 Apr 2012
 */

public class Index {
	
	private String lex;
	private IndexElement[] elem;
	
	
	Index( String specialSigns, String lex, String line ) {
		this.lex = lex;
		elem = new IndexElement[1];
		elem[0] = new IndexElement(lex, specialSigns, line);
	}
	
	Index( String lex, String line) {
		this.lex = lex;
		elem = new IndexElement[1];
		elem[0] = new IndexElement(lex, line);
	}
	
	Index( String lex ) {
		this.lex = lex;
	}
	
	public void addElement( String specialSigns, String line ) {
		int size = elem.length;
		IndexElement[] enhancedElem = new IndexElement[size + 1];
		for (int i = 0; i < size; i++) 
			enhancedElem[i] = elem[i];
		enhancedElem[size] = new IndexElement(lex, specialSigns, line);
		elem = enhancedElem;
	}
	
	public void addElement( String line ) {
		int size = elem.length;
		IndexElement[] enhancedElem = new IndexElement[size + 1];
		for (int i = 0; i < size; i++)
			enhancedElem[i] = elem[i];
		enhancedElem[size] = new IndexElement(lex, line);
		elem = enhancedElem;
	}
	
	public IndexElement[] getElements() {
		return elem;
	}
	
	public int compareTo( Index ind ) {
		if( lex.compareTo(ind.toString()) > 0) {
			return 1;
		}
		else if( lex.compareTo(ind.toString()) == 0) {
			return 0;
		}
		else return -1;
	}
	
	@Override
	public String toString() {
		return lex;
	}
}
