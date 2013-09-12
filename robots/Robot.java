

/**
 * @author Konrad S�oniewski, ks321221
 * @date 30.05.2012
 * @version 1.0
 */
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Abstrakcyjna klasa Robot przetrzymuj�ca metody wsp�lne dla  
 * robot�w Klapaucjusza, Trurla i Tarantogi.
 */
public abstract class Robot {

	protected ArrayList<String> countries;
	protected Collection<Airport> airports;
	protected Collection<Hotel> ritz;
	protected Collection<Hotel> hilton;
	protected Collection<Synonym> synonyms;
	protected Airport homeAirport;
	
/*****************************************************************************/

	public String findShortest() throws Exception {
		return null;
	}
	
/*****************************************************************************/

	public String findMostConvenient() throws Exception {
		return null;
	}
	
/*****************************************************************************/	
	
	/*
	 * Metoda wczytuj�ca dane do robota.
	 */
	public void readData(String countries, String airports, String ritz,
			String hilton, String synonyms) throws Exception {
		
		FileReader fr;
		BufferedReader br;
		String line;
		
		/*
		 * Listy kraj�w do odwiedzenia
		 */
		try {
		fr = new FileReader(countries);
		br = new BufferedReader(fr);
		
		while ((line = br.readLine()) != null) {
			line = line.toUpperCase();
			this.countries.add(line);		
		} }
		catch (FileNotFoundException e) {
			System.out.println(System.getProperty("user.dir"));
			System.out.println(e.toString());
		}
		
		/*
		 * Wczytywanie lotnisk
		 */
		try {
		fr = new FileReader(airports);
		br = new BufferedReader(fr);
		
		while ((line = br.readLine()) != null) {
			if (! line.contains(":U:")) {
				Airport airport = new Airport(line);
				this.airports.add(airport);
			}			
		} }
		catch (FileNotFoundException e) {
			System.out.println(e.toString());
		}
		
		/*
		 * Wczytywanie hoteli Ritz
		 */
		try {
		fr = new FileReader(ritz);
		br = new BufferedReader(fr);
		
		while ((line = br.readLine()) != null) {
			line = line.toUpperCase();
			Hotel hotel = new Hotel(line);
			this.ritz.add(hotel);
		} }
		catch (FileNotFoundException e) {
			System.out.println(e.toString());
		}
		
		/*
		 * Wczytywanie hoteli Hilton
		 */
		try {
		fr = new FileReader(hilton);
		br = new BufferedReader(fr);
		
		while ((line = br.readLine()) != null) {
			line = line.toUpperCase();
			Hotel hotel = new Hotel(line);
			this.hilton.add(hotel);
		} }
		catch (FileNotFoundException e) {
			System.out.println(e.toString());
		}
		
		/*
		 * Wczytywanie synonim�w				
		 */
		try {
		fr = new FileReader(synonyms);
		br = new BufferedReader(fr);
		while ((line = br.readLine()) != null) {
			line = line.toUpperCase();
			Synonym synonym = new Synonym(line);
			this.synonyms.add(synonym);
		} 
		fr.close();
		br.close(); }
		catch (FileNotFoundException e) {
			System.out.println(e.toString());
		}
		
		
		
	}
	
/*****************************************************************************/
	
	/*
	 * Metoda tworz�ca kopi� listy (u�ywana do stworzenia kopii listy pa�stw
	 * do odwiedzenia w celu wykre�lania z niej odwiedzonych ju� pa�stw).
	 */
	protected static ArrayList<String> cloneList(ArrayList<String> list) {
		ArrayList<String> clone = new ArrayList<String>(list.size());
	    for (String name: list) {
	    	clone.add(name);
	    }
	    return clone;
	}
	
/*****************************************************************************/
	
	/*
	 * Metoda sprawdzaj�ca czy podane dwie nazwy kraj�w to synonimy.
	 */
	protected boolean synonyms(String country1, String country2) {
		boolean found = false;
		for (Synonym syno: synonyms) {
			if(syno.contains(country1) && syno.contains(country2)) {
				found = true;
			}
		}
		return found;
	}
	
/*****************************************************************************/
	
	/*
	 * Metoda sprawdzaj�ca czy dany kraj jest na li�cie pa�stw do odwiedzenia.
	 */
	protected boolean onTheList(ArrayList<String> list, String country) {
		boolean found = false;
		for (String element: list) {
			if (element.equals(country) || synonyms(element, country)) {
				found = true;
			}
		}
		return found;
	}
	
/*****************************************************************************/
	
	/*
	 * Metoda obliczaj�ca dystans mi�dzy danymi lotniskami.
	 */
	protected double distance(Airport airport1, Airport airport2) {
		double distance = (Math.acos(
				(Math.sin(airport1.getLatitude()) * Math.sin(airport2.getLatitude()))
				+
				(Math.cos(airport1.getLatitude()) * Math.cos(airport2.getLatitude()) * Math.cos(airport1.getLongitude() - airport2.getLongitude()))
				) * 6372.8);
				
				
				/*(int) ( Math.sqrt( Math.pow( Math.cos( Math.PI * 
			airport1.getLongitude() / 180 ) * ( airport2.getLatitude() - 
			airport1.getLatitude() ) , 2 ) + 
			Math.pow( airport2.getLongitude()
			- airport1.getLongitude(), 2 ) ) * Math.PI * 12745.6 / 360 );*/
		
		return distance;
	}

/*****************************************************************************/
	
	/*
	 * Metoda sprawdzaj�ca czy w danym mie�cie jest hotel Ritz.
	 */
	protected boolean haveRitz(String city, String country) {
		boolean found = false;
		Iterator<Hotel> iterator = ritz.iterator();

		while (iterator.hasNext() && !found) {
			Hotel hotel = iterator.next();
			String hotelCity = hotel.getCity();
			String hotelCountry = hotel.getCountry();
			if (hotelCity.equals(city) && (hotelCountry.equals(country) || 
				synonyms(hotelCountry, country))) {
				found = true;
			}			
		}
		return found;
	}
	
/*****************************************************************************/
	
	/*
	 * Metoda sprawdzaj�ca czy w danym mie�cie jest hotel Hilton.
	 */
	protected boolean haveHilton(String city, String country) {
		boolean found = false;
		Iterator<Hotel> iterator = hilton.iterator();

		while (iterator.hasNext() && !found) {
			Hotel hotel = iterator.next();
			String hotelCity = hotel.getCity();
			String hotelCountry = hotel.getCountry();
			if (hotelCity.equals(city) && hotelCountry.equals(country)) {
				found = true;
			}			
		}
		return found;
	}
	
/*****************************************************************************/
	
	/*
	 * Metoda zwracaj�ca wynik oblicze�.
	 */
	protected String output(ArrayList<Airport> travelList, double travelLength){
		String output = ""+(int)travelLength;
		for (Airport airport: travelList) {
			output += "\n"+airport.getCountry()+","+airport.getCity()+","+airport.getICAO();
			if (haveRitz(airport.getCity(), airport.getCountry())) {
				output += ",R";
			}
			else if (haveHilton(airport.getCity(), airport.getCountry())) {
				output += ",H";
			}
			else {
				output += ",O";
			}
		}
		return output;
	}
		
}