import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;

public class Actor {
		/** 
		 * Wspó³rzêdne obiektu na mapie.
		 */	
	protected int x,y;
		/** 
		 * Wymiary obiektu.
		 */	
	protected int width, height;
		/** 
		 * Nazwa obiektu - grafiki.
		 */	
	protected String spriteName;
		/** 
		 * Instancja klasy Stage - "na scenie" znajduj¹ siê wszystkie obiekty.
		 */	
	protected Stage stage;
		/** 
		Tworzona jest instancja klasy SpriteCache
		 */	
	protected SpriteCache spriteCache;
		/** 
		 * Mówi o tym, czy obiekt nale¿y "usun¹æ".
		 */	
	protected boolean TrashObject;
		/** 
		 * Obecna "klatka" animacji.
		 */	
	protected int currentFrame;
		/** 
		 * Tablica nazw obiektów - grafik.
		 */	
	protected String[] spriteNames;
	
		/** 
		 * Szybkoœæ zmiany klatek.
		 */	
	protected int frameSpeed;
		/** 
	Pole u¿ywane w metodzie act() do wyœwietlania konkretnego obrazka animacji 
		 */	
	protected int nrKlatki;
	
	/** 
	Konstruktor. Pobierane zostaj¹ obrazki dla obiektów klasy Actor
	 */	
	public Actor (Stage stage) {
		this.stage = stage;
		spriteCache = stage.getSpriteCache();
		currentFrame = 0;
		frameSpeed = 1;
		nrKlatki = 0;
	}
	/**
	 * 
	 Metoda zwracaj¹ca czêstotliwoœæ zmiany klatek
	 */
	public int  getFrameSpeed()      { return frameSpeed; }
	/**
	 * Ustawia czêstotliwoœæ zmiany klatek 
	 */
	public void setFrameSpeed(int i) { frameSpeed = i; }
	
		/** 
		 * Narysowanie obiektu w odpowienim miejscu (wspó³rzêdne).
		 * Grafika pochodzi z tablicy spriteNames. 
		 *	
		*/
	public void paint(Graphics2D g){
		g.drawImage( spriteCache.getSprite(spriteNames[currentFrame]), x, y, stage);

	}
	
	public int  getX() 		{ return x;}
	public void setX(int i)	{x = i;}
	public int  getY() 		{return y;}
	public void setY(int i) {y = i;}
		/** 
		 * Ustawia pole TrashObject na true - oznacza to, i¿ obiekt nadaje siê do usuniêcia.
		 */	
	public void remove() {
		TrashObject = true;
	}
	/**
	 * 
	 */
	public boolean isTrashObject() {
		return TrashObject;
	}
	/**
	 * 
	 Metoda zwracaj¹ca tytu³/nazwê obrazka który ma byæ wczytany
	 */
	public String getSpriteName() {return spriteName;}
		/** 
		 * Wczytuje grafiki do obiektów spriteCache z tablicy nazw plików,
		 * ustawia ich szerokoœæ, wysokoœæ
		 */	
	public void setSpriteNames(String[] names) {
		spriteNames = names;
		height = 0;
		width = 0;
		
		for(int i =0 ; i < names.length; i++) {
			BufferedImage image = spriteCache.getSprite(spriteNames[i]);
			height = Math.max(height, image.getHeight());
			width = Math.max(width, image.getWidth());		
		}
	}
		/** 
		 * Metoda zwraca granice obiektu - potrzebne do wykrywania kolizji.
		 */	
	public Rectangle getBounds() {
		return new Rectangle (x, y, width, height);
	}
	
		/** 
		 * Domyœlnie metoda collision nie robi nic. 
		 * Dopiero przes³oniêcie jej w klasach dziedzicz¹cych
		 * po Actor przynosi po¿¹dane dla obiektu danej klasy efekty.
		 */	
	public void collision(Actor a) { }
	/**
	 * Metoda zwracaj¹ca wysokoœæ obiektu
	 */
	public int  getHeight()    	 {return height; }
	/**
	 * Metoda zwracaj¹ca szerokoœæ obiektu
	 */
	public int  getWidth()  	 {return width;}
	/**
	 * Metoda ustawiaj¹ca wysokoœæ obiektu
	 */
	public void setHeight(int i) {height = i;}
	/**
	 * Metoda ustawiaj¹ca szerokoœæ obiektu
	 */
	public void setWidth(int i)  {width = i;}
	
		/** 
		 * Metoda podmienia "klatki" animacji.
		 */	
	public void act(){
		nrKlatki++;
		if(nrKlatki % frameSpeed == 0){
			nrKlatki = 0;
			currentFrame = (currentFrame + 1) % spriteNames.length;
		}
	}
	
	
}
