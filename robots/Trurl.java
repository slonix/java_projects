

/**
 * @author Konrad S�oniewski, ks321221
 * @date 30.05.2012
 * @version 1.0
 */
import java.util.*;

/*
 * Implementacja robota Trurla.
 */
public class Trurl extends Robot {

	public Trurl() {
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
			Airport nearestAirport = null;
			double distance = 40000;
			
			/*
			 * Szukam najbli�szego lotniska od ko�ca trasy.			
			 */
			for(Airport airport: airports) {
				if (onTheList(countriesToVisit, airport.getCountry())) {
					double newDistance;
					newDistance = distance(lastAirport, airport);
					
					if (newDistance < distance) {
						nearestAirport = airport;
						distance = newDistance;
					}
				}
			}
			
			/*
			 * Dodaj� lotnisko do listy podr�y i usuwam pa�stwo
			 * z listy pa�stw do odwiedzenia.
			 */
			travelList.add(nearestAirport);
			lastAirport = nearestAirport;
			countriesToVisit.remove(nearestAirport.getCountry());
			System.out.println("Zwiąkszono dystans o: "+distance);
			travelLength += distance;
		}
		
		travelLength += distance(lastAirport, homeAirport);
		
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
			String nearestCountry = "";
			double distance = 40000;
			
			/*
			 * Szukam najbli�szego pa�stwa
			 */
			for (Airport airport: airports) {
				if (onTheList(countriesToVisit, airport.getCountry())) {
					double newDistance;
					newDistance = distance(lastAirport, airport);
				
					if (newDistance < distance) {
						distance = newDistance;
						nearestCountry = airport.getCountry();
					}
				}
			}
			
			/*
			 * Dziel� lotniska z najbli�szego pa�stwa na te z hotelami Ritz, Hilton i innymi.
			 */
			Collection<Airport> citiesWithRitz = new ArrayList<Airport>();
			Collection<Airport> citiesWithHilton = new ArrayList<Airport>();
			Collection<Airport> citiesWithOther = new ArrayList<Airport>();
			
			for (Airport airport: airports) {
				String destination = airport.getCountry();
				if (destination.equals(nearestCountry) || synonyms(destination, nearestCountry)) {
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
			 * Szukam najwygodniejszego lotniska.
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
			 * Dodaj� lotnisko do listy podr�y i usuwam pa�stwo
			 * z listy pa�stw do odwiedzenia.
			 */
			travelList.add(mostConvenientAirport);
			lastAirport = mostConvenientAirport;
			countriesToVisit.remove(nearestCountry);
			travelLength += distance;
		}
		
		
		travelLength += distance(lastAirport, homeAirport);
		
		/*
		 * Wypisuj� wynik oblicze�.
		 */		
		return output(travelList, travelLength);
	}
	
}
