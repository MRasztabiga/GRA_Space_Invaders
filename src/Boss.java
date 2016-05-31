/** 
* Klasa odpowiedzialna dzia³anie bossa - przeciwnika 
* pojawiaj¹cego siê po zabiciu 100 mniejszych potworów. 
* Rozszerza klasê Actor.
*/
public class Boss extends Actor {
		/** 
		 * Prêdkoœæ pozioma - wektor o jaki boss przemieszcza siê w jednostce czasu.
		 */
	protected int vx;
		/** 
		 * Prêdkoœæ pionowa - wektor o jaki boss przemieszcza siê w jednostce czasu.
		 */
	protected int vy;
		/** 
		 * Prêdkoœæ poruszania. Im wiêksza, tym statek szybciej zmienia po³o¿enie.
		 */
	static final int SHIP_SPEED = 1; 
		/** 
		 * Pocz¹tkowe - maksymalne punkty ¿ycia bossa.
		 */
	public static final int MAX_HEALTH = 200;
		/** 
		 * Obecne punkty ¿ycia bossa.
		 */
	private static int HP;
		/** 
		 * Konstruktor korzystaj¹cy z konstruktora klasy Actor.
		 * Wczytuje obraz bossa, ustawia punkty ¿ycia na maksymalne.
		 */
	public Boss (Stage stage) {
		super(stage);
		setSpriteNames(new String[] {"bos.png"}); 
		setFrameSpeed(10);
		HP = MAX_HEALTH;
	}
		/** 
		 * Metoda tworz¹ca strza³ bossa oraz odtwarzaj¹ca efekt dzwiêkowy po wystrzale
		 */
	public void fireBoss() {
			
			BossGun shoot = new BossGun(stage);
			shoot.setX(x + 10);
			shoot.setY(y + shoot.getHeight());
			stage.addActor(shoot);
			stage.getSoundCache().playSound("strzalpotwor.wav");
		}
		/** 
		 * Metoda tworz¹ca rakietowy strza³ bossa.
		 */
	public void fireBossCluster() {
		
		stage.addActor(new BossGun(stage, BossGun.Up_Left, x, y));
		stage.addActor(new BossGun(stage, BossGun.Up, x, y));
		stage.addActor(new BossGun(stage, BossGun.Up_Right, x, y));
		stage.addActor(new BossGun(stage, BossGun.Left, x, y));
		stage.addActor(new BossGun(stage, BossGun.Right, x, y));
		stage.addActor(new BossGun(stage, BossGun.Down_Left, x, y));
		stage.addActor(new BossGun(stage, BossGun.Down, x, y));
		stage.addActor(new BossGun(stage, BossGun.Down_Right, x, y));
		
	}

	public void        setVx(int i) { vx = i; }
	public void        setVy(int i) { vy = i; }
	public static int  getHp() 		{ return HP;}
		/** 
		 * Metoda korzysta z metody act() klasy nadrzêdnej.
		 * Wyzwala losowa strza³y dodatkowe bossa oraz zarz¹dza jego poruszaniem.  
		 */
	public void act() {
		super.act();
		x+=vx;
		y+=vy;
		if(x < 0 || x > Stage.SZEROKOSC - 100) {
			vx = -vx;
		}
		if(y < 0 || y > Stage.WYSOKOSC - 500) {
			vy = -vy;
		}
		if((int)(Math.random() * 30) == 1){
			fireBoss();
		}
		if((int)(Math.random() * 400) == 1){
			fireBossCluster();
		}
	}
	

		/** 
		 * Metoda sprawdza, czy nast¹pi³a kolizja bossa z innymi obiektami na mapie. Dla kolizji z ró¿nymi obiektami 
		 * zabierana jest ró¿na iloœæ punktów ¿ycia. Jeœli zdrowie spadnie do 0 obrazek bossa zostaje podmieniony na wybuch,
		 * wywo³ana zostaje metoda gameWon(), koñcz¹ca grê zwyciêstwem. 
		 * Dla kolizji z instancjami broni graczy sprawdzane jest komu maj¹ zostaæ przydzielone punkty
		 */
	public void collision(Actor a) {
		
		if(a instanceof Gun ) {
	
			HP-=5; // zwykla bron zabiera 5 HP BOSSA
			if(HP < 1) {
				remove();
				setSpriteNames(new String[] {"bum2.png"});
				setFrameSpeed(10);
				currentFrame = 0;
				stage.gameWon();
			}
			
			a.remove();	//usuwanie strzalu
			stage.getSoundCache().playSound("boom.wav");
			if(((Gun)a).czyj == 1) {
				stage.getPlayer().addPoints(20);
			}
			if(((Gun)a).czyj == 2) {
				stage.getPlayer2().addPoints(20);
			}
		
			if(Gun.CURRENT_BULLETS>0) {
				Gun.CURRENT_BULLETS--;
			}
			currentFrame = 0;
		}
	if(a instanceof Bomb) {
		
		HP-=10; //bomba zabiera 10 HP
		if(HP < 1){
			remove(); // jesli HP BOSSA < 1 usun go 
			setSpriteNames(new String[]  {"wybuch.png"});
			setFrameSpeed(10);
			currentFrame = 0;
			stage.gameWon();
		}
		a.remove(); // usuwanie bomby po strzale
		if(((Bomb)a).czyj == 1) {
			stage.getPlayer().addPoints(50);
			stage.getPlayer().getClusterBombs();
			
		}
		if(((Bomb)a).czyj == 2) {
			stage.getPlayer2().addPoints(50);
			stage.getPlayer2().getClusterBombs();
	}
		currentFrame = 0;
}
		
	if(a instanceof Player ){
		
			HP -= 20;
			if(HP < 1) {
				remove();
				setSpriteNames(new String[] {"wybuch.png"});
				setFrameSpeed(10);
				currentFrame = 0;
			}
			stage.getSoundCache().playSound("boom.wav");
			stage.getPlayer().addPoints(50);
			stage.gameWon();
		}
	if(a instanceof Player2 ){
		
			HP -= 20;
			if(HP < 1) {
				remove();
				setSpriteNames(new String[] {"wybuch.png"}); 
				setFrameSpeed(10);
				currentFrame = 0;
				stage.gameWon();
				
			}
			stage.getSoundCache().playSound("boom.wav");
			stage.getPlayer2().addPoints(50);
			}
		}	
}


	
