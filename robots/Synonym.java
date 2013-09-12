

/**
 * @author Konrad S�oniewski, ks321221
 * @date 30.05.2012
 * @version 1.0
 */
import java.util.ArrayList;
import java.util.List;

/*
 * Klasa obiekt�w przetrzymuj�cych list� synonim�w.
 */
public class Synonym {
	private List<String> synonyms;
	
	/*
	 * Konstruktor.
	 */
	Synonym(String line) {
		synonyms = new ArrayList<String>();
		int semicolon;
		String synonym;
		while ((semicolon = line.indexOf(';')) != -1) {
			Character sign = line.charAt(0);
			while (sign.equals(';') || Character.isWhitespace(sign)) {
				line = line.substring(1);
				sign = line.charAt(0);
			}
			synonym = line.substring(0,semicolon);
			synonyms.add(synonym);
			line = line.substring(semicolon+1);
		}
		Character sign = line.charAt(0);
		if (sign.equals(' ')) {
			line = line.substring(1);
		}
		synonym = line;
		synonyms.add(synonym);
	}
	
	/*
	 * Metoda sprawdzaj�ca czy w li�cie synonim�w jest dane pa�stwo.
	 */
	public boolean contains(String country) {
		if (synonyms.contains(country)) {
			return true;
		}
		else {
			return false;
		}
	}
}
