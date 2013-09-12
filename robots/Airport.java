

/**
 * @author Konrad S�oniewski, ks321221
 * @date 30.05.2012
 * @version 1.0
 */

/*
 * Klasa obiekt�w przetrzymuj�cych wszystkie parametry danego lotniska.
 */
public class Airport {
	private String ICAO;
	private String IATA;
	private String name;
	private String city;
	private String country;
	private double degreeNS;
	private double minuteNS;
	private double secondNS;
	private String hemisphereNS;
	private double degreeWE;
	private double minuteWE;
	private double secondWE;
	private String hemisphereWE;
	private String altitude;
	
	Airport(String line) {
		int colon = line.indexOf(':');
		ICAO = line.substring(0,colon);
		line = line.substring(colon+1);
		
		colon = line.indexOf(':');
		IATA = line.substring(0,colon);
		line = line.substring(colon+1);
		
		colon = line.indexOf(':');
		name = line.substring(0,colon);
		line = line.substring(colon+1);
		
		colon = line.indexOf(':');
		city = line.substring(0,colon);
		line = line.substring(colon+1);
		
		colon = line.indexOf(':');
		country = line.substring(0,colon);
		line = line.substring(colon+1);
		
		colon = line.indexOf(':');
		degreeNS = Double.parseDouble(line.substring(0,colon));
		line = line.substring(colon+1);
		
		colon = line.indexOf(':');
		minuteNS = Double.parseDouble(line.substring(0,colon));
		line = line.substring(colon+1);
		
		colon = line.indexOf(':');
		secondNS = Double.parseDouble(line.substring(0,colon));
		line = line.substring(colon+1);
		
		colon = line.indexOf(':');
		hemisphereNS = line.substring(0,colon);
		line = line.substring(colon+1);
		
		colon = line.indexOf(':');
		degreeWE = Double.parseDouble(line.substring(0,colon));
		line = line.substring(colon+1);
		
		colon = line.indexOf(':');
		minuteWE = Double.parseDouble(line.substring(0,colon));
		line = line.substring(colon+1);
		
		colon = line.indexOf(':');
		secondWE = Double.parseDouble(line.substring(0,colon));
		line = line.substring(colon+1);
		
		colon = line.indexOf(':');
		hemisphereWE = line.substring(0,colon);
		line = line.substring(colon+1);
		
		altitude = line;
		
	}
	
	public String getName() {
		return name;
	}
	
	public String getCountry() {
		return country;
	}
	
	public String getCity() {
		return city;
	}
	
	public String getICAO() {
		return ICAO;
	}
	
	public String getIATA() {
		return IATA;
	}
	
	public String getAltitude() {
		return altitude;
	}
	
	public double getLatitude() {
		double sign;
		if (hemisphereNS.equals("N")) {
			sign = 1;
		}
		else {
			sign = -1;
		}
		double outcome = sign * (degreeNS + minuteNS/60 + secondNS/3600);
		return outcome;
	}
	
	public double getLongitude() {
		double sign;
		if (hemisphereWE.equals("W")) {
			sign = 1;
		}
		else {
			sign = -1;
		}
		double outcome = sign * (degreeWE + minuteWE/60 + secondWE/3600);
		return outcome;
	}
	
}
