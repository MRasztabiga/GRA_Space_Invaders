		/** 
		 * Klasa odpowiada za strza³y przeciwników - potworów.  
		 * Rozszerza klasê Actor.
		 */
public class MonsterGun extends Actor {
		/** 
		 * Prêdkoœæ pocisków - wektor o jaki przemieszczaj¹ siê w jednostce czasu.
		 */
	protected static final int BULLET_SPEED = 10;
		/** 
		 * Konstruktor. Wczytuje obrazy do animacji.  
		 */
	public MonsterGun(Stage stage)	{
		super(stage);
		setSpriteNames(new String[] {"monsterbullet.jpg", "monsterbullet1.jpg", "monsterbullet2.jpg","monsterbullet3.jpg"});
		setFrameSpeed(10);
	}
		/** 
		 * Metoda korzysta z metody act() klasy nadrzednej. Sprawdza czy pocisk nie "wylecia³" poza mapê.  
		 */
	@Override
	public void act() {
		super.act();
		y+=BULLET_SPEED;
			if( y > Stage.WYSOKOSC )
			{
				remove();
			}
		
	}

}