/**
 * @author Konrad S³oniewski, ks321221
 * @version 1.0, 29 Apr 2012
 */

public class IndexElement {
	
	private String lex, sign, grammar, form, ending;
	
	IndexElement( String lex, String specialSigns, String line ) {
		this.lex = lex;
		//System.out.println("dodany ind elem: "+lex);
		sign = specialSigns;
		//line format [ column2;column3;column4/ ]
		int semicolon = line.indexOf(';');
		String tmp;
		System.out.println("iii");
		if ( line.charAt(0) == ';' ) {
			grammar = "";
			form = "";
		} else {
			System.out.println("jjj");
			tmp = line.substring(0, semicolon);
			int space = tmp.indexOf(' ');
			System.out.println("kkk");
			if( space < 0) {
				System.out.println("mmm");
				grammar = tmp;
				form = "";
			} else {
				grammar.substring(0, space);
				System.out.println("lll");
				tmp = tmp.substring(space + 1);
				form = tmp;
			}
		}
		System.out.println("hhh");
		line = line.substring(semicolon + 1); 
		//line format [ column3;column4/ ]
		
		semicolon = line.indexOf(';');
		tmp = line.substring(0, semicolon);
		if ( line.charAt(0) == ';' || tmp.contains("*")) 
			ending = "";
		else {
			ending = line.substring(0, semicolon);
			ending = ending.replaceAll("[^\\p{L}\\p{N}]", "");
			ending = ending.replaceAll("[CV]", "");
		}
	}
	
	IndexElement( String lex, String line ) {
		//System.out.println("dodany ind elem: "+lex);
		this.lex = lex;
		//line format [column2 other]
		sign = form = ending = "";
		int space = line.indexOf(' ');
		if ( space == 0 ) //no space in line or space is first character
			grammar = "";
		else if ( space < 0 ) {
			grammar = line;
		} else {
			grammar = line.substring(0, space);
			if ( grammar.contains("*") ) 
				grammar = "";
		}
	}
	
	public String getSign() {
		return sign;
	}
	
	public String getEnding() {
		if ( ending == "" ) {
			//System.out.println("skorzystalem z lex");
			return lex;			
		}
		else {
			//System.out.println("skorzystalem z ending");
			return ending;
		}
	}
	
	public String getGrammar() {
		//System.out.println("gramatyka to: "+grammar);
		return grammar;
	}
	
	public String getForm() {
		return form;
	}
	
	@Override
	public String toString() {
		return sign;
	}
}
