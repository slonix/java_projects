

/**
 * @author Konrad S�oniewski, ks321221
 * @date 30.05.2012
 * @version 1.0
 */
import java.util.ArrayList;
import java.util.HashSet;

/*
 * Implementacja robota Tarantogi.
 */
public class Tarantoga extends Robot {

	Tarantoga() {
		countries = new ArrayList<String>();
		airports = new HashSet<Airport>();
		ritz = new HashSet<Hotel>();
		hilton = new HashSet<Hotel>();
		synonyms = new HashSet<Synonym>();
		homeAirport = new Airport("KJFK:JFK:JOHN F KENNEDY INTERNATIONAL:NEW YORK:USA:40:38:23:N:73:46:44:W:0013");
	}
	
/***********************************************************************************************************************************/
	
	public String findShortest() throws Exception {
		ArrayList<String> countriesToVisit = cloneList(countries);
		ArrayList<Airport> travelList = new ArrayList<Airport>();
		travelList.add(homeAirport);
		double travelLength = 0;
		
		while (! countriesToVisit.isEmpty()) {
			/*
			 * Szukam lotniska najmniej wyd�u�aj�cego tras�
			 */
			double shortestDistance = 40000;
			double additionalDistance = 40000;
			int positionOfNearestAirport = 0;
			int bestPosition = 0;
			Airport nearestAirport = null;
			for (Airport airport: airports) {
				String destination = airport.getCountry();
				if (onTheList(countriesToVisit, destination)) {
					/*
					 * Szukam miejsca na li�cie podr�y najmniej wyd�u�aj�cego tras�
					 */
					bestPosition = 0;
					additionalDistance = 40000;
					double newDistance;
					for (int i = 0; i < travelList.size(); i++) {
						if (i < travelList.size() - 1) {
							newDistance = distance(travelList.get(i), airport) + distance(airport, travelList.get(i+1))
									- distance(travelList.get(i), travelList.get(i+1));
						}
						else {
							newDistance = distance(travelList.get(i), airport) + distance(airport, travelList.get(0))
									- distance(travelList.get(i), travelList.get(0));
						}
							
						if (newDistance < additionalDistance) {
							bestPosition = i;
							additionalDistance = newDistance;
						}
					}
				}
				
				if (additionalDistance < shortestDistance) {
					shortestDistance = additionalDistance;
					nearestAirport = airport;
					positionOfNearestAirport = bestPosition;
				}
			}
			
			/*
			 * Wstawiam lotnisko do listy podr�y
			 */
			travelList.add(positionOfNearestAirport+1, nearestAirport);
			countriesToVisit.remove(nearestAirport.getCountry());
			travelLength += shortestDistance;			
		}
		
		/*
		 * Wypisuj� wynik oblicze�.
		 */		
		return output(travelList, travelLength);
	}
	
/***********************************************************************************************************************************/

	public String findMostConvenient() throws Exception {
		ArrayList<String> countriesToVisit = cloneList(countries);
		ArrayList<Airport> travelList = new ArrayList<Airport>();
		travelList.add(homeAirport);
		double travelLength = 0;
					
		/*
		 * Dziel� lotniska z pierwszego pa�stwa z listy na te z hotelami Ritz, Hilton i innymi.
		 */
		ArrayList<Airport> citiesWithRitz = new ArrayList<Airport>();
		ArrayList<Airport> citiesWithHilton = new ArrayList<Airport>();
		ArrayList<Airport> citiesWithOther = new ArrayList<Airport>();
		
		for (Airport airport: airports) {
			if (onTheList(countriesToVisit, airport.getCountry())) {
				if (haveRitz(airport.getCity(), airport.getCountry())) {
					citiesWithRitz.add(airport);
				}
				else if (haveHilton(airport.getCity(), airport.getCountry())) {
					citiesWithHilton.add(airport);
				}
				else {
					citiesWithOther.add(airport);
				}
			}
		}		
	
		boolean procede = true;
		while (procede) {
			/*
			 * Szukam najwygodniejszego lotniska najmniej wyd�u�aj�cego tras� i dodaj� je do listy podr�y.
			 */
			if (! citiesWithRitz.isEmpty()) {
				travelLength = addBestAirport(citiesWithRitz, travelList, travelLength, citiesWithHilton, citiesWithOther);
			}
			else if (! citiesWithHilton.isEmpty()) {
				travelLength = addBestAirport(citiesWithHilton, travelList, travelLength, citiesWithRitz, citiesWithOther);
			}
			else if (! citiesWithOther.isEmpty()) {
				travelLength = addBestAirport(citiesWithOther, travelList, travelLength, citiesWithRitz, citiesWithHilton);
			}
			else {
				procede = false;
			}			
		}
		
		/*
		 * Wypisuj� wynik oblicze�.
		 */		
		return output(travelList, travelLength);
	}
	
/***********************************************************************************************************************************/
	
	/*
	 * Metoda szukaj�ca na danej li�cie lotniska najmniej wyd�u�aj�cego tras� podr�y, usuwaj�ca z podanych list lotniska z pa�stwa,
	 * kt�re wybrano oraz zwracaj�ca zaktualizowan� ��czn� d�ugo�� trasy podr�y.
	 */
	private double addBestAirport(ArrayList<Airport> list, ArrayList<Airport> travelList,
		double travelLength, ArrayList<Airport> list2, ArrayList<Airport> list3) {
		Airport mostConvenientAirport = null;
		double lowestDistance = 40000;
		int positionOfMostConvenientAirport = 0;
		for (Airport airport: list) {
			/*
			 * Szukam najmniej wyd�u�aj�cego tras� miejsca na li�cie.
			 */
			int bestPosition = 0;
			double additionalDistance = 40000;
			double newDistance;
			for (int i = 0; i < travelList.size(); i++) {
				if (i < travelList.size() - 1) {
					newDistance = distance(travelList.get(i), airport) + distance(airport, travelList.get(i+1))
							- distance(travelList.get(i), travelList.get(i+1));
				}
				else {
					newDistance = distance(travelList.get(i), airport) + distance(airport, travelList.get(0))
							- distance(travelList.get(i), travelList.get(0));
				}
				
				if (newDistance < additionalDistance) {
					bestPosition = i;
					additionalDistance = newDistance;
				}
			}
		
			if (additionalDistance < lowestDistance) {
				lowestDistance = additionalDistance;
				positionOfMostConvenientAirport = bestPosition;
				mostConvenientAirport = airport;
			}				
		}
		
		/*
		 * Wstawiam lotnisko do listy podr�y.
		 */
		travelList.add(positionOfMostConvenientAirport + 1, mostConvenientAirport);
		travelLength += lowestDistance;
		
		/*
		 * Usuwam z list z podzia�em na hotele te lotniska, kt�re s� z tego samego
		 * pa�stwa co mostConvenientAirport.
		 */				
		int i = 0;
		while(i < list.size()) {
			Airport airport = list.get(i);
			String country = airport.getCountry();
			if (country.equals(mostConvenientAirport.getCountry()) || synonyms(country, mostConvenientAirport.getCountry())) {
				list.remove(airport);
			}
			else {
				i++;
			}
		
		}
		
		i = 0;
		while(i < list2.size()) {
			Airport airport = list2.get(i);
			String country = airport.getCountry();
			if (country.equals(mostConvenientAirport.getCountry()) || synonyms(country, mostConvenientAirport.getCountry())) {
				list2.remove(airport);
			}
			else {
				i++;
			}
		
		}
		
		i = 0;
		while(i < list3.size()) {
			Airport airport = list3.get(i);
			String country = airport.getCountry();
			if (country.equals(mostConvenientAirport.getCountry()) || synonyms(country, mostConvenientAirport.getCountry())) {
				list3.remove(airport);
			}
			else {
				i++;
			}		
		}
		
		return travelLength;
	}		
}
