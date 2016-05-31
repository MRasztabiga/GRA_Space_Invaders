import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.HashMap;
import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.ImageObserver;
/**
	Klasa rozszerzaj¹ca klasê ResourceCache, implementuje ImageObserver
 */
public class SpriteCache extends ResourceCache implements ImageObserver
{
	
	public HashMap<String, Object> sprites;
	/**Konstruktor, tworzy now¹ hashmapê nazwa_zasobu - obiekt
	 */
	public SpriteCache() {
		sprites = new HashMap<String, Object>();
	}
/**
 * Metoda pobiera ustawienia konfiguracyjne œrodowiska graficznego. 
 * Umo¿liwia dodanie obrazka z przezroczystym t³em 
 */
	public BufferedImage createCompatible(int width, int height, int transparency) {
		GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
		BufferedImage compatible = gc.createCompatibleImage(width, height, transparency);
		return compatible;
	}
	/**
	 * Metoda getSprite tworzy obraz "dostosowany" na podstawie za³adowanego wczeœniej obrazka 
	 * Umo¿liwia jego narysowanie na miejscu pierwotnie pobranego obrazka 
	 */
	public BufferedImage getSprite(String sciezka) {
		BufferedImage loaded = (BufferedImage)getResource(sciezka);
		BufferedImage compatible = createCompatible(loaded.getWidth(), loaded.getHeight(), Transparency.BITMASK);
		Graphics g = compatible.getGraphics();
		g.drawImage(loaded,  0, 0, this);
		return compatible;		
	}
	/**
	 * Metoda zwracaj¹ca wartoœæ false jeœli œledzony obraz jest ca³kowicie za³adowany, w przeciwnym razie - true
	 * odœwie¿ana jest tylko czêœæ obrazu, która ulega zmianie, przyspiesza to dzia³anie
	 */
	public boolean imageUpdate(Image img, int infoflags, int x, int y, int w, int h) {
		return (infoflags & (ALLBITS|ABORT)) == 0;
	}
	/**
	 * Metoda wczytuj¹ca zasób
	 * W przypadku gdy zasobu nie uda siê odczytaæ, 
	 * wyrzucony zostanie wyj¹tek informujacy o b³êdzie przy próbie dostêpu
	 */
	protected Object loadResource(URL url) {
		try {
			return ImageIO.read(url);
		} 
		catch (Exception e) {
			System.out.println("Przy otwieraniu " + url);
			System.out.println("Wystapil blad : " + e.getClass().getName()+"" + e.getMessage());
			System.exit(0);
			return null;
		}
	}
}
	

