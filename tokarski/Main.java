/**
 * @author Konrad S³oniewski, ks321221
 * @version 1.0, 29 Apr 2012
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {
	
	public static BinarySearchTree bstIndex, bstDict;
	
	private static void readIndex() throws IOException {
		FileReader fr = new FileReader("dataFiles/indeksTok.txt");
		BufferedReader br = new BufferedReader(fr);
		bstIndex = new BinarySearchTree();
		String line;
		while((line = br.readLine()) != null) {
			//line format [ ;column1;column2;column3;column4/ ]
			if(line.charAt(0) != '%') {

				System.out.println("aaa");
				line = line.substring(1);
				//line format [ column1;column2;column3;column4/ ]
				int firstSemicolon = line.indexOf(';');
				String lex = line.substring(0, firstSemicolon);
				line = line.substring(firstSemicolon + 1);
				//line format [ column2;column3;column4/ ]
				System.out.println("bbb");
				String specialSigns = "";
				while( (lex != "") && ( (lex.charAt(0) == '-') || (lex.charAt(0) == '(')  || 
						(lex.charAt(0) == 'C') || (lex.charAt(0) == 'V') || (lex.charAt(0) == '.') ) ) {
					specialSigns += lex.charAt(0);
					lex = lex.substring(1);
				}
				
				System.out.println("ccc");
				
				// We are checking if there is an index 'lex' in BST. If not,
				// we are adding new one. If yes, we are adding new element
				// to this index.
				Index ind = bstIndex.find(lex);
				if( ind == null ) {
					System.out.println("eee" + specialSigns + " " + lex + " " + line);
					ind = new Index(specialSigns, lex, line);
					System.out.println("fff");
					bstIndex.insert(ind);
					
				} else {
					bstIndex.remove(lex);
					ind.addElement(specialSigns, line);
					bstIndex.insert(ind);
				}
				System.out.println("zzz");
			}
		}
		br.close();
		fr.close();	
	}
	
	private static void readDictionary() throws IOException{
		FileReader fr = new FileReader("dataFiles/s³ownik.txt");
		BufferedReader br = new BufferedReader(fr);
		bstDict = new BinarySearchTree();
		String line; 
		while((line = br.readLine()) != null) {
			//line format [column1 column2 other]
			int firstSpace = line.indexOf(' ');
			String lex = line.substring(0, firstSpace);
			line = line.substring(firstSpace + 1);
			//line format [column2 other]
			
			// We are checking if there is an index 'lex' in Dictionary BST
			// Tree. If not, we are adding new one. If yes, we are adding
			// new element to this index.
			Index ind = bstDict.find(lex);
			if( ind == null ) {
				ind = new Index(lex, line);
				bstDict.insert(ind);
			} else {
				bstDict.remove(lex);
				ind.addElement(line);
				bstDict.insert(ind);
			}
		}
		br.close();
		fr.close();
	}
	
	private static void analizeFile( String fileName ) throws IOException {
		FileReader fr = new FileReader(fileName);
		Scanner scan = new Scanner(fr);
		String word, analizedEnding, wordBeginning, candidate;
		while( scan.hasNext() ) {
			word = scan.next();
			word = word.replaceAll("[^\\p{L}\\p{N}]", "");
			word = word.toLowerCase();
			analizedEnding = word;
			wordBeginning = "";
			while( analizedEnding != "" && !word.equals("") ) {
				//Searching for an index
				Index found = bstIndex.find(analizedEnding);
				if( found != null ) {
					IndexElement[] foundElements = found.getElements();
					IndexElement element;
					Character lastChar;
					String last;
					if ( wordBeginning.equals("") ) {
						lastChar = null;
						last = "";
					}
					else {
						lastChar = wordBeginning.charAt(wordBeginning.length() - 1);
						last = lastChar.toString();
					}
					for (int i = 0; i < foundElements.length; i++) {
						element = foundElements[i];
						candidate = "";						
						if( element.getSign().contains("(") && wordBeginning.equals("") ) {
							candidate = element.getEnding();
						} else if ( element.getSign().contains("-") && !wordBeginning.equals("") ) {
							candidate = wordBeginning + element.getEnding();
						} else if ( element.getSign().contains(".") && wordBeginning.length() <= 1 ) {
							candidate = wordBeginning + element.getEnding();
						} else if ( element.getSign().contains("C") && wordBeginning.length() <= 1 &&
								last.matches("b|c|æ|d|f|g|h|j|k|l|³|m|n|ñ|p|q|r|s|œ|t|v|w|x|y|z") ) {
							candidate = wordBeginning + element.getEnding();
						} else if ( element.getSign().contains("V") && ((wordBeginning.length() <= 1 &&
								last.matches("a|¹|e|ê|o|ó|u|y")) || wordBeginning.equals(""))) {
							candidate = wordBeginning + element.getEnding();
						} else {
							candidate = wordBeginning + element.getEnding();
						}
						
						if( !candidate.equals("") ) {
							System.out.println("sprawdzam tego kandydata. kandydat to: "+ candidate);
							Index dict = bstDict.find(candidate);
							if( dict != null ) {
								System.out.println("jest taki kandydat, sprawdzam gramatyki");
								IndexElement[] dictElements = dict.getElements();
								IndexElement dictElement;
								
								for (int j = 0; j < dictElements.length; j++) {
									dictElement = dictElements[j];
									String grammar1 = element.getGrammar();
									System.out.println("gramatyka sprawdzanego elementu: "+grammar1);
									String grammar2 = dictElement.getGrammar();
									System.out.println("gramatyka slownikowa: "+grammar2);
									if( grammar1.equals(grammar2) ) {
										System.out.println(":"+word+":"+candidate+":"+element.getGrammar()+":"+element.getForm());
									}
								}
							}
							else System.out.println("nie ma kandydata w slowniku");
						}						
					}
				}
				
				
				
				//Changing ending and beginning part of the word
				wordBeginning += analizedEnding.charAt(0);
				if( analizedEnding.length() > 1 ) 
					analizedEnding = analizedEnding.substring(1);
				else
					analizedEnding = "";
			}
		}
		fr.close();
	}
	
	public static void main( String[] args ) throws IOException {
		readIndex();
		
		readDictionary();
		analizeFile("dataFiles/analiza.txt");
	}
}
