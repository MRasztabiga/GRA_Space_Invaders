/** 
* Klasa odpowiedzialna za strza³y bossa.  
*/
public class BossGun extends Actor {
		/** 
		 * Prêdkoœæ pocisków bossa - d³ugoœæ wektora, o jaki przesuwaj¹ siê w jednostce czasu. 
		 */
	protected static final int BULLET_SPEED = 10;
		/** 
		 * Prêdkoœæ bomb bossa - wektor o jaki przesuwaj¹ siê w jednostce czasu. 
		 */
	protected static final int Bomb_Speed = 4;
		/** 
		 * Prêdkoœæ pozioma bossa - wektor o jaki przesuwa siê w jednostce czasu.
		 */
	protected int vx;
		/** 
		 * Prêdkoœæ pionowa bossa - wektor o jaki przesuwa siê w jednostce czasu.
		 */
	protected int vy;
		/** 
		 * Pomocnicza zmienna s³u¿¹ca do losowania broni.
		 */
	private double whichWeapon;

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
	 * Konstruktor, który tworzy wybrany losowo wystrza³.
	 * £aduje odpowiednie obrazy. Korzysta z metody act() z klasy Actor.
	 */
	public BossGun(Stage stage)	{ //wybiera z jakiej broni strzelic
		super(stage);
		whichWeapon = Math.random();
		if(whichWeapon < 0.4){
		setSpriteNames(new String[] {"BossBullet.png", "BossBullet1.png"});
		setFrameSpeed(10);
		currentFrame = 0;
		}
		if(whichWeapon >= 0.4) {
			setSpriteNames(new String[] {"monsterbullet.jpg", "monsterbullet1.jpg", "monsterbullet2.jpg","monsterbullet3.jpg"});
			setFrameSpeed(10);
			currentFrame = 0;
		}
		
	}
	/** 
	 * Konstruktor, który tworzy broñ specjaln¹ bossa - u¿ywa jej najrzadziej.
	 * £aduje odpowiednie obrazy. Korzysta z metody act() z klasy Actor.
	 * Strza³y kierowane s¹ w odpowiedni¹ stronê i otrzymuj¹ odpowiedni¹ grafikê.
	 * Dzia³anie podobne do bomb - rakiet.
	 */
	public BossGun(Stage stage, int heading, int x, int y){
		super(stage);
		
		this.x = x;
		this.y = y;
		
		switch(heading) {
		case Up_Left: 	 vx = -Bomb_Speed; 	vy = -Bomb_Speed; 	setSpriteNames(new String[] {"specialBoss.png", "specialBoss1.png"}); break;
		case Up: 		 vx = 0; 			vy = -Bomb_Speed; 	setSpriteNames(new String[] {"specialBoss.png", "specialBoss1.png"}); break;
		case Up_Right: 	 vx = Bomb_Speed; 	vy = -Bomb_Speed; 	setSpriteNames(new String[] {"specialBoss.png", "specialBoss1.png"}); break;
		case Right: 	 vx = Bomb_Speed; 	vy = 0; 			setSpriteNames(new String[] {"specialBoss.png", "specialBoss1.png"}); break;
		case Down_Right: vx = Bomb_Speed; 	vy = Bomb_Speed; 	setSpriteNames(new String[] {"specialBoss.png", "specialBoss1.png"}); break;
		case Down: 		 vx = 0; 			vy = Bomb_Speed; 	setSpriteNames(new String[] {"specialBoss.png", "specialBoss1.png"}); break;
		case Down_Left:  vx = -Bomb_Speed; 	vy = Bomb_Speed;	setSpriteNames(new String[] {"specialBoss.png", "specialBoss1.png"}); break;
		case Left: 		 vx = -Bomb_Speed; 	vy = 0; 			setSpriteNames(new String[] {"specialBoss.png", "specialBoss1.png"}); break;
	}
	}
	
	/** 
	 * Metoda korzysta z metody act() klasy nadrzêdnej.
	 * "Usuwa" strza³ jeœli wypad³ poza mapê.
	 */
	@Override
	public void act() {
		super.act();
		y+=vy;
		x+=vx;
		y+=BULLET_SPEED;
			if( y > Stage.WYSOKOSC || x < 0 || x > Stage.SZEROKOSC)
			{
				remove();
			}
	}

}


