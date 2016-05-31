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
	Klasa rozszerzaj�ca klas� ResourceCache, implementuje ImageObserver
 */
public class SpriteCache extends ResourceCache implements ImageObserver
{
	
	public HashMap<String, Object> sprites;
	/**Konstruktor, tworzy now� hashmap� nazwa_zasobu - obiekt
	 */
	public SpriteCache() {
		sprites = new HashMap<String, Object>();
	}
/**
 * Metoda pobiera ustawienia konfiguracyjne �rodowiska graficznego. 
 * Umo�liwia dodanie obrazka z przezroczystym t�em 
 */
	public BufferedImage createCompatible(int width, int height, int transparency) {
		GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
		BufferedImage compatible = gc.createCompatibleImage(width, height, transparency);
		return compatible;
	}
	/**
	 * Metoda getSprite tworzy obraz "dostosowany" na podstawie za�adowanego wcze�niej obrazka 
	 * Umo�liwia jego narysowanie na miejscu pierwotnie pobranego obrazka 
	 */
	public BufferedImage getSprite(String sciezka) {
		BufferedImage loaded = (BufferedImage)getResource(sciezka);
		BufferedImage compatible = createCompatible(loaded.getWidth(), loaded.getHeight(), Transparency.BITMASK);
		Graphics g = compatible.getGraphics();
		g.drawImage(loaded,  0, 0, this);
		return compatible;		
	}
	/**
	 * Metoda zwracaj�ca warto�� false je�li �ledzony obraz jest ca�kowicie za�adowany, w przeciwnym razie - true
	 * od�wie�ana jest tylko cz�� obrazu, kt�ra ulega zmianie, przyspiesza to dzia�anie
	 */
	public boolean imageUpdate(Image img, int infoflags, int x, int y, int w, int h) {
		return (infoflags & (ALLBITS|ABORT)) == 0;
	}
	/**
	 * Metoda wczytuj�ca zas�b
	 * W przypadku gdy zasobu nie uda si� odczyta�, 
	 * wyrzucony zostanie wyj�tek informujacy o b��dzie przy pr�bie dost�pu
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
	

