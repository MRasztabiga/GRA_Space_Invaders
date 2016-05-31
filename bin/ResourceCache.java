import java.net.URL;
import java.util.HashMap;
/** 
 * Klasa ResourceCache odpowiada za przechowywawanie obiekt�w pod w�a�ciw� nazw�,
 * a tak�e ich �adowanie
 * */
public abstract class ResourceCache {
	
	protected HashMap<String,Object> resources;
	/**
	 * Konstruktor 
	 * Tworzona jest nowa Hashmapa nazwa-obiekt
	 */
	public ResourceCache() {
		resources = new HashMap<String, Object>();
	}
	/**
	 * Metoda umo�liwiaj�ca za�adowanie obrazka o danej nazwie
	 */
	protected Object loadResource(String name) {
		URL url=null;
		url = getClass().getClassLoader().getResource(name);
		return loadResource(url);
	}
	/**
	 * Metoda pobieraj�ca obeikt o danej nazwie z Hashmapy.
	 * Je�li nie istnieje obiekt o takiej nazwie, to zostaje on dodany do Hashmapy
	 * oraz przypisany do tej nazwy
	 */
	protected Object getResource(String name) {
		Object res = resources.get(name);
		if (res == null) {
			res = loadResource(name);
			resources.put(name,res);
		}
		return res;
	}
	
	protected abstract Object loadResource(URL url);
}
