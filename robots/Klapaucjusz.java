

/**
 * @author Konrad S�oniewski, ks321221
 * @date 30.05.2012
 * @version 1.0
 */
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

/*
 * Implementacja robota Klapaucjusza.
 */
public class Klapaucjusz extends Robot {

	public Klapaucjusz() {
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
		Airport lastAirport = homeAirport;
		travelList.add(homeAirport);
		double travelLength = 0;
		
		while (! countriesToVisit.isEmpty()) {
			/*
			 * Szukam najbli�szego lotniska od ko�ca trasy.
			 */
			Airport nearestAirport = null;
			double distance = 40000;
			for (Airport airport: airports) {
				String destination = airport.getCountry();
				if (destination.equals(countriesToVisit.get(0)) || synonyms(destination, countriesToVisit.get(0))) {
					double newDistance;
					newDistance = distance(lastAirport, airport);
							
					if (newDistance < distance) {
						nearestAirport = airport;
						distance = newDistance;
					}
				}
			}
			
			/*
			 * Szukam najmniej wyd�u�aj�cego tras� miejsca na li�cie podr�y.
			 */
			int bestPosition = 0;
			double additionalDistance = 40000;
			double newDistance;
			for (int i = 0; i < travelList.size(); i++) {
				if (i < travelList.size() - 1) {
					newDistance = distance(travelList.get(i), nearestAirport) + distance(nearestAirport, travelList.get(i+1))
							- distance(travelList.get(i), travelList.get(i+1));
				}
				else {
					newDistance = distance(travelList.get(i), nearestAirport) + distance(nearestAirport, travelList.get(0))
							- distance(travelList.get(i), travelList.get(0));
				}
					
				if (newDistance < additionalDistance) {
					bestPosition = i;
					additionalDistance = newDistance;
				}
			} 		
			
			if (bestPosition == travelList.size() - 1) {
				lastAirport = nearestAirport;
			}
			
			/*
			 * Wstawiam lotnisko do listy podr�y.
			 */
			travelList.add(bestPosition+1, nearestAirport);
			
			/*
			 * Usuwam odwiedzone pa�stwo z listy pa�stw do odwiedzenia
			 * i zwi�kszam ��czn� d�ugo�c trasy.
			 */
			countriesToVisit.remove(nearestAirport.getCountry());
			travelLength += additionalDistance;
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
		Airport lastAirport = homeAirport;
		travelList.add(homeAirport);
		double travelLength = 0;
		
		while (! countriesToVisit.isEmpty()) {
			Airport mostConvenientAirport = null;
			double distance = 40000;
						
			/*
			 * Dziel� lotniska z pierwszego pa�stwa na li�cie z ONZ
			 * na te z hotelami Ritz, Hilton lub innymi.
			 */
			Collection<Airport> citiesWithRitz = new ArrayList<Airport>();
			Collection<Airport> citiesWithHilton = new ArrayList<Airport>();
			Collection<Airport> citiesWithOther = new ArrayList<Airport>();
			
			for (Airport airport: airports) {
				String destination = airport.getCountry();
				if (destination.equals(countriesToVisit.get(0)) || synonyms(destination, countriesToVisit.get(0))) {
					if (haveRitz(airport.getCity(), destination)) {
						citiesWithRitz.add(airport);
					}
					else if (haveHilton(airport.getCity(), destination)) {
						citiesWithHilton.add(airport);
					}
					else {
						citiesWithOther.add(airport);
					}
				}
			}
			
			/*
			 * Szukam najwygodniejszego lotniska
			 */
			distance = 40000;
			if (! citiesWithRitz.isEmpty()) {
				for (Airport airport: citiesWithRitz) {
					double newDistance = distance(lastAirport, airport);
					if (newDistance < distance) {
						distance = newDistance;
						mostConvenientAirport = airport;
					}
				}
			}
			else if (! citiesWithHilton.isEmpty()) {
				for (Airport airport: citiesWithHilton) {
					double newDistance = distance(lastAirport, airport);
					if (newDistance < distance) {
						distance = newDistance;
						mostConvenientAirport = airport;
					}
				}
			}
			else {
				for (Airport airport: citiesWithOther) {
					double newDistance = distance(lastAirport, airport);
					if (newDistance < distance) {
						distance = newDistance;
						mostConvenientAirport = airport;
					}
				}
			}
			
			/*
			 * Szukam najmniej wyd�u�aj�cego tras� miejsca na li�cie
			 */
			int bestPosition = 0;
			double additionalDistance = 40000;
			double newDistance;
			for (int i = 0; i < travelList.size(); i++) {
				if (i < travelList.size() - 1) {
					newDistance = distance(travelList.get(i), mostConvenientAirport) + distance(mostConvenientAirport, travelList.get(i+1))
							- distance(travelList.get(i), travelList.get(i+1));
				}
				else {
					newDistance = distance(travelList.get(i), mostConvenientAirport) + distance(mostConvenientAirport, travelList.get(0))
							- distance(travelList.get(i), travelList.get(0));
				}
					
				if (newDistance < additionalDistance) {
					bestPosition = i;
					additionalDistance = newDistance;
				}
			} 	
			
			
			if (bestPosition == travelList.size() - 1) {
				lastAirport = mostConvenientAirport;
			}
			
			/*
			 * Wstawiam lotnisko do listy
			 */
			travelList.add(bestPosition+1, mostConvenientAirport);
			
			/*
			 * Usuwam odwiedzone pa�stwo z listy pa�stw do odwiedzenia
			 * i zwi�kszam ��czn� d�ugo�c trasy.
			 */
			countriesToVisit.remove(mostConvenientAirport.getCountry());
			travelLength += additionalDistance;
		}
		
		/*
		 * Wypisuj� wynik oblicze�.
		 */		
		return output(travelList, travelLength);
	}
	
}
