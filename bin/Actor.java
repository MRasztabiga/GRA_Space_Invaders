import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;

public class Actor {
		/** 
		 * Wsp�rz�dne obiektu na mapie.
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
		 * Instancja klasy Stage - "na scenie" znajduj� si� wszystkie obiekty.
		 */	
	protected Stage stage;
		/** 
		Tworzona jest instancja klasy SpriteCache
		 */	
	protected SpriteCache spriteCache;
		/** 
		 * M�wi o tym, czy obiekt nale�y "usun��".
		 */	
	protected boolean TrashObject;
		/** 
		 * Obecna "klatka" animacji.
		 */	
	protected int currentFrame;
		/** 
		 * Tablica nazw obiekt�w - grafik.
		 */	
	protected String[] spriteNames;
	
		/** 
		 * Szybko�� zmiany klatek.
		 */	
	protected int frameSpeed;
		/** 
	Pole u�ywane w metodzie act() do wy�wietlania konkretnego obrazka animacji 
		 */	
	protected int nrKlatki;
	
	/** 
	Konstruktor. Pobierane zostaj� obrazki dla obiekt�w klasy Actor
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
	 Metoda zwracaj�ca cz�stotliwo�� zmiany klatek
	 */
	public int  getFrameSpeed()      { return frameSpeed; }
	/**
	 * Ustawia cz�stotliwo�� zmiany klatek 
	 */
	public void setFrameSpeed(int i) { frameSpeed = i; }
	
		/** 
		 * Narysowanie obiektu w odpowienim miejscu (wsp�rz�dne).
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
		 * Ustawia pole TrashObject na true - oznacza to, i� obiekt nadaje si� do usuni�cia.
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
	 Metoda zwracaj�ca tytu�/nazw� obrazka kt�ry ma by� wczytany
	 */
	public String getSpriteName() {return spriteName;}
		/** 
		 * Wczytuje grafiki do obiekt�w spriteCache z tablicy nazw plik�w,
		 * ustawia ich szeroko��, wysoko��
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
		 * Domy�lnie metoda collision nie robi nic. 
		 * Dopiero przes�oni�cie jej w klasach dziedzicz�cych
		 * po Actor przynosi po��dane dla obiektu danej klasy efekty.
		 */	
	public void collision(Actor a) { }
	/**
	 * Metoda zwracaj�ca wysoko�� obiektu
	 */
	public int  getHeight()    	 {return height; }
	/**
	 * Metoda zwracaj�ca szeroko�� obiektu
	 */
	public int  getWidth()  	 {return width;}
	/**
	 * Metoda ustawiaj�ca wysoko�� obiektu
	 */
	public void setHeight(int i) {height = i;}
	/**
	 * Metoda ustawiaj�ca szeroko�� obiektu
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
