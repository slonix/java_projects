
public class Main {

	private static final String DATA = "data/";
	private static final String AIRPORTS = DATA+"AirportDataBase.txt";
	private static final String HILTON_HOTELS = DATA+"hilton_s.txt";
	private static final String RITZ_HOTELS = DATA+"ritz_s.txt";
	private static final String SYNONYMS = DATA+"synonimy.txt";
	
	public static void main(String[] args) throws Exception {
		String[] countries = {"c0.txt", "c1.txt", "c2.txt", "c3.txt", "c4.txt"};
		
		Robot tarantoga = new Tarantoga();
		for(String c:countries) {
		 tarantoga.readData(DATA+c, AIRPORTS, RITZ_HOTELS, HILTON_HOTELS, SYNONYMS);
		 System.out.println("Shortest: "+c);
		 System.out.print(tarantoga.findShortest());
		 System.out.println("Most convenient: "+c);
		 System.out.print(tarantoga.findMostConvenient());
		 System.out.println();
		}		
	}
}
