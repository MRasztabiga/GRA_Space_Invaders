		 /** 
		 * Klasa jest odpowiedzialna za rakiety-bomby, czyli bro� dodatkow�.
		 * Dziedziczy po klasie Actor.
		 */
public class Bomb extends Actor{
	
	/**
	 *  Pola przypisuj�ce kierunkom lotu rakiet konkretne warto�ci liczbowe
	 */
	public static final int Up_Left = 0;
	public static final int Up = 1;
	public static final int Up_Right= 2;
	public static final int Right = 3;
	public static final int Down_Right = 4;
	public static final int Down = 5;
	public static final int Down_Left = 6;
	public static final int Left = 7;
		/** 
		 * Pole okre�laj�ce kt�ry z graczy wystrzeli� rakiety. 
		 */
	public int czyj;
	
		/** 
		 * Pole okre�laj�ce og�ln� pr�dko�� rakiety - d�ugo�� wektora.
		 */
	protected static final int Bomb_Speed = 5;
		/** 
		 * Pr�dko�� pozioma - d�ugo�� wektora, o jaki przesuwa si� rakieta w jednostce czasu.
		 */
	protected int vx;
		/** 
		 * Pr�dko�� pionowa - d�ugo�� wektora, o jaki przesuwa si� rakieta w jednostce czasu.
		 */
	protected int vy;
		/** 
		 * Konstruktor kt�ry tworzy pojedyncz� rakiet�.
		 * W zale�no�ci od warto�ci argumentu heading
		 * tworzona jest rakieta lec�ca w odpowiednim kierunku,
		 * posiadaj�ca odpowiedni� grafik�.
		 */
	public Bomb(Stage stage, int heading, int x, int y, int czyj) {
		super(stage);
		
		this.x = x + 20;
		this.y = y + 20;
		this.czyj = czyj;
		
		/**
		 * W zale�no�ci od kierunku w kt�rym ma lecie� rakieta ustawiane s� r�ne pr�dko�ci sk�adowe vx oraz vy 
		 * oraz wstawiany jest odpowiedni obrazek
		 */
		switch(heading) {
			case Up_Left: 	 vx = -Bomb_Speed; 	vy = -Bomb_Speed; 	setSpriteNames(new String[] {"r8.png"}); break;
			case Up: 		 vx = 0; 			vy = -Bomb_Speed; 	setSpriteNames(new String[] {"r1.png"}); break;
			case Up_Right: 	 vx = Bomb_Speed; 	vy = -Bomb_Speed; 	setSpriteNames(new String[] {"r5.png"}); break;
			case Right: 	 vx = Bomb_Speed; 	vy = 0; 			setSpriteNames(new String[] {"r2.png"}); break;
			case Down_Right: vx = Bomb_Speed; 	vy = Bomb_Speed; 	setSpriteNames(new String[] {"r6.png"}); break;
			case Down: 		 vx = 0; 			vy = Bomb_Speed; 	setSpriteNames(new String[] {"r3.png"}); break;
			case Down_Left:  vx = -Bomb_Speed; 	vy = Bomb_Speed;	setSpriteNames(new String[] {"r7.png"}); break;
			case Left: 		 vx = -Bomb_Speed; 	vy = 0; 			setSpriteNames(new String[] {"r4.png"}); break;
		}
	}
		/** 
		 * Metoda wykorzystuje metod� act klasy matki oraz sprawdza,
		 *  czy pocisk nie "wylecia�" poza map�.
		 */
	public void act() {
		super.act();
		y+=vy;
		x+=vx;
		if(y < 0 || y > Stage.WYSOKOSC || x < 0 || x > Stage.SZEROKOSC) remove();
	}
	
}
