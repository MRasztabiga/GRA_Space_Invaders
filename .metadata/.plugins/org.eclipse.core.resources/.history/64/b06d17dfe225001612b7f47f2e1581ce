import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
	/** 
	 * Klasa odpowiedzialna za utrzymywanie tablicy najlepszych wyników uzyskanych przez graczy.
	 * Przechowuje zgromadzone dane w pliku wyniki.txt.
	 */
public class Wyniki {
		/** 
		 * Mapa jest odwzorowaniem listy wyników - liczb i nazw gracza - typ String.
		 */
	private Map<Integer, String> lista = new HashMap<Integer, String>();
		/** 
		 * Lista nazw graczy.
		 */
	private  ArrayList<String> nazwy = new ArrayList<String>();
		/** 
		 * Lista liczb - wyników uzyskanych przez graczy.
		 */
	private  ArrayList<Integer> wyniki = new ArrayList<Integer>();
		/** 
		 * Plik tekstowy, w ktorym trzymane s¹ wyniki.
		 */
	private File plik = new File("wyniki.txt");
		/** 
		 * Metoda wczytuj¹ca dane z pliku do list klasy. 
		 */
	synchronized public void odczyt() throws FileNotFoundException{
		Scanner odczyt = new Scanner(plik);
		String linijka="";
		
		 while (odczyt.hasNextLine()) {
			 linijka=odczyt.nextLine();
			 String dane [] = linijka.split(" ");
			 
			 nazwy.add(dane[0]);
			 wyniki.add(Integer.parseInt(dane[1]));
			 lista.put( Integer.parseInt(dane[1]), dane[0]);
         }
		 odczyt.close();
		
	}
		/** 
		 * Metoda dodaj¹ca now¹ parê: wynik gracz i imiê do list klasy. 
		 */
	synchronized public void dodanie_wyniku(int p, String nazwa) throws FileNotFoundException{
		while(lista.containsKey(p))p++;
		lista.put(p, nazwa);
		wyniki.add(p);
		nazwy.add(nazwa);
		
		wyniki.sort(null);
	}
		/** 
		 * Metoda zapisuj¹ca 5 najelpszych wyników z list istniej¹cych obecnie w klasie.
		 */
	synchronized public void zapis() throws FileNotFoundException{
		wyniki.sort(null);
		PrintWriter zapis = new PrintWriter("wyniki.txt");
		for(int i=lista.size()-1; i>lista.size()-6; i--){
		      zapis.println(lista.get(wyniki.get(i)) + " " + wyniki.get(i));
			}
		      zapis.close();
	}
		/** 
		 * Metoda zwracaj¹ca listê wyników zapisan¹ w pliku jako ci¹g znaków - String.
		 */
	synchronized public String toString(){
		Scanner odczyt=null;
					try {
		odczyt = new Scanner(plik);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
		String wynik="";
		
		 while (odczyt.hasNextLine()) {
			 wynik+=odczyt.nextLine()+"\n";
		 	}
		 odczyt.close();
	return wynik;
	}
		 /** 
	 	 * Metoda zwracaj¹ca najgorszy wynik z wyników obecnie znajduj¹cych siê w klasie.
	 	 */
	public int najgorszyWynik(){
		
		return  wyniki.get(wyniki.size()-1);
	}
	
}
