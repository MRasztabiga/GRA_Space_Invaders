		 /** 
		 * Klasa jest odpowiedzialna za strza³y gracza. Dziedziczy po klasie Actor.
		 */
public class Gun extends Actor {
		/** 
		 * Szybkoœæ pocisków - wektor o jaki przesuwaj¹ siê w jednostce czasu. 
		 */
	protected static final int BULLET_SPEED = 5;
		/** 
		 * Pocz¹tkowa maksymalna liczba pocisków graczy. 
		 */
	public static final int MAX_BULLETS = 5;
	 	/** 
	 	 * Liczba pocisków graczy znajduj¹cych siê na mapie. 
	 	 */
	public static int CURRENT_BULLETS = 0;
		/** 
		 * Pole okreœlaj¹ce który z graczy wystrzeli³ pocisk. 
		 */
	public int czyj;
		/** 
		 * Konstruktor. Wczytuje obraz i zwiêksza liczbê obecnych pocisków.  
		 */
	public Gun(Stage stage)	{
		super(stage);
		setSpriteNames(new String[] {"bullet.jpg"});
		//setFrameSpeed(3);
			CURRENT_BULLETS++;
	}
		/** 
		 * Metoda wykorzystuje metodê act klasy matki oraz sprawdza czy pocisk nie "wylecia³" poza mapê.
		 */
	@Override
	public void act() {
		super.act();
		y-=BULLET_SPEED;
			if( y < 0)
			{
				remove();
				CURRENT_BULLETS--;
			}
	}
}