

/**
 * @author Konrad S�oniewski, ks321221
 * @date 30.05.2012
 * @version 1.0
 */

/*
 * Klasa obiekt�w przetrzymuj�cych parametry danego hotelu.
 */
public class Hotel {
	private String city;
	private String country;
	
	Hotel(String line) {
		int comma = line.indexOf(',');
		city = line.substring(0,comma);
		country = line.substring(comma+2);
	}
	
	public String getCity() {
		return city;
	}
	
	public String getCountry() {
		return country;
	}
}
