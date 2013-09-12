

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.*;
import static org.junit.Assert.*;

public class TrurlTests {
	
	private static final String AIRPORTS = "data/AirportDatabase.txt";
	private static final String HILTON_HOTELS = "data/hilton_s.txt";
	private static final String RITZ_HOTELS = "data/ritz_s.txt";
	private static final String SYNONYMS = "data/synonimy.txt";

	private static final String COUNTRIES_0 = "data/c0.txt";
	private static final String COUNTRIES_1 = "data/c1.txt";
	private static final String COUNTRIES_2 = "data/c2.txt";
	private static final String COUNTRIES_3 = "data/c3.txt";
	private static final String COUNTRIES_4 = "data/c4.txt";
	
	private Robot trurl;
	
	private String readExpected(String input) {
		String expected = "";
		try {
			Scanner scanner = new Scanner(new File(input));
			while (scanner.hasNextLine()) {
				expected += scanner.nextLine()+"\n";
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();		
		}
		return expected;
	}
	
	@Before
	public void setUp() throws Exception {
		trurl = new Trurl();		
	}
		
	@Test
	public void testShortest0() throws Exception {
		trurl.readData(COUNTRIES_0, AIRPORTS, RITZ_HOTELS, HILTON_HOTELS, SYNONYMS);
		String actual, expected;		
		expected = "0\n"
				+"USA,NEW YORK,KJFK,R\n";
		actual = trurl.findShortest();
		assertEquals(expected.trim(), actual.trim());		
	}
	
	@Test
	public void testShortest1() throws Exception {
		System.out.println("TEST 2");
		trurl.readData(COUNTRIES_1, AIRPORTS, RITZ_HOTELS, HILTON_HOTELS, SYNONYMS);
		String actual, expected;		
		expected = "12820\n"
			+"USA,NEW YORK,KJFK,R\n"
			+"POLAND,SZCZECIN,EPSD,O\n";
		actual = trurl.findShortest();
		assertEquals(expected.trim(), actual.trim());
	}
		
	@Test
	public void testMostConvenient1() throws Exception {
		System.out.println("TEST 3");
		trurl.readData(COUNTRIES_1, AIRPORTS, RITZ_HOTELS, HILTON_HOTELS, SYNONYMS);
		String actual, expected;		
		expected = "13168\n"
			+"USA,NEW YORK,KJFK,R\n"
			+"POLAND,GDANSK,EPGD,H\n";
		actual = trurl.findMostConvenient();
		assertEquals(expected.trim(), actual.trim());		
	}	
	
	@Test
	public void testShortest2() throws Exception {
		trurl.readData(COUNTRIES_2, AIRPORTS, RITZ_HOTELS, HILTON_HOTELS, SYNONYMS);
		String actual, expected;		
		expected = readExpected("output/trurl_2s.txt");
		actual = trurl.findShortest();
		assertEquals(expected.trim(), actual.trim());		
	}	
	
	@Test
	public void testMostConvenient2() throws Exception {
		trurl.readData(COUNTRIES_2, AIRPORTS, RITZ_HOTELS, HILTON_HOTELS, SYNONYMS);
		String actual, expected;		
		expected = readExpected("output/trurl_2mc.txt");
		actual = trurl.findMostConvenient();
		assertEquals(expected.trim(), actual.trim());		
	}	
	
	@Test
	public void testShortest3() throws Exception {
		trurl.readData(COUNTRIES_3, AIRPORTS, RITZ_HOTELS, HILTON_HOTELS, SYNONYMS);
		String actual, expected;		
		expected = readExpected("output/trurl_3s.txt");
		actual = trurl.findShortest();
		assertEquals(expected.trim(), actual.trim());		
	}	
	
	@Test
	public void testMostConvenient3() throws Exception {
		trurl.readData(COUNTRIES_3, AIRPORTS, RITZ_HOTELS, HILTON_HOTELS, SYNONYMS);
		String actual, expected;		
		expected = readExpected("output/trurl_3mc.txt");
		actual = trurl.findMostConvenient();
		assertEquals(expected.trim(), actual.trim());		
	}
	
	@Test
	public void testShortest4() throws Exception {
		trurl.readData(COUNTRIES_4, AIRPORTS, RITZ_HOTELS, HILTON_HOTELS, SYNONYMS);
		String actual, expected;		
		expected = readExpected("output/trurl_4s.txt");
		actual = trurl.findShortest();
		assertEquals(expected.trim(), actual.trim());		
	}
		
	@Test
	public void testMostConvenient4() throws Exception {
		trurl.readData(COUNTRIES_4, AIRPORTS, RITZ_HOTELS, HILTON_HOTELS, SYNONYMS);
		String actual, expected;		
		expected =  readExpected("output/trurl_4mc.txt");
		actual = trurl.findMostConvenient();
		assertEquals(expected.trim(), actual.trim());		
	}	
	
}
