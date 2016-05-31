		 /** 
		 * Klasa jest odpowiedzialna za rakiety-bomby, czyli broñ dodatkow¹.
		 * Dziedziczy po klasie Actor.
		 */
public class Bomb extends Actor{
	
	/**
	 *  Pola przypisuj¹ce kierunkom lotu rakiet konkretne wartoœci liczbowe
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
		 * Pole okreœlaj¹ce który z graczy wystrzeli³ rakiety. 
		 */
	public int czyj;
	
		/** 
		 * Pole okreœlaj¹ce ogóln¹ prêdkoœæ rakiety - d³ugoœæ wektora.
		 */
	protected static final int Bomb_Speed = 5;
		/** 
		 * Prêdkoœæ pozioma - d³ugoœæ wektora, o jaki przesuwa siê rakieta w jednostce czasu.
		 */
	protected int vx;
		/** 
		 * Prêdkoœæ pionowa - d³ugoœæ wektora, o jaki przesuwa siê rakieta w jednostce czasu.
		 */
	protected int vy;
		/** 
		 * Konstruktor który tworzy pojedyncz¹ rakietê.
		 * W zale¿noœci od wartoœci argumentu heading
		 * tworzona jest rakieta lec¹ca w odpowiednim kierunku,
		 * posiadaj¹ca odpowiedni¹ grafikê.
		 */
	public Bomb(Stage stage, int heading, int x, int y, int czyj) {
		super(stage);
		
		this.x = x + 20;
		this.y = y + 20;
		this.czyj = czyj;
		
		/**
		 * W zale¿noœci od kierunku w którym ma lecieæ rakieta ustawiane s¹ ró¿ne prêdkoœci sk³adowe vx oraz vy 
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
		 * Metoda wykorzystuje metodê act klasy matki oraz sprawdza,
		 *  czy pocisk nie "wylecia³" poza mapê.
		 */
	public void act() {
		super.act();
		y+=vy;
		x+=vx;
		if(y < 0 || y > Stage.WYSOKOSC || x < 0 || x > Stage.SZEROKOSC) remove();
	}
	
}
