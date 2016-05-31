import java.awt.event.KeyEvent;
/** 
 * Klasa odpowiedzialna za dzia³anie statku gracza.
 * Rozszerza klasê Actor. Statek jest sterowany za pomoc¹ klawiatury.
 */
public class Player extends Actor {
		/** 
		 * Prêdkoœæ pozioma - d³ugoœæ wektora, o jaki statek przesuwa siê w jednostce czasu.
		 */
	protected int vx;
		/** 
		 * Prêdkoœæ pionowa - d³ugoœæ wektora, o jaki statek przesuwa siê w jednostce czasu.
		 */
	protected int vy;
		/** 
		 * Moc silników statku. Im wiêksza, tym szybciej statek zwiêksza prêdkoœæ.
		 */
	static final int SHIP_SPEED = 3;
		/** 
		 * Maksymalna - pocz¹tkowa wartoœæ punktów ¿ycia.
		 */
	public static final int MAX_HEALTH = 100;
		/** 
		 * Punkty, które zdoby³ gracz za zabijanie wrogów.
		 */
	protected int points;
		/** 
		 * Obecny stan punktów ¿ycia.
		 */
	protected int health;
		/** 
		 * Pocz¹tkowa iloœæ rakiet - bomb gracza.
		 */
	public static final int MAX_BOMBS = 100;
		/** 
		 * Obecna iloœæ rakiet - bomb gracza.
		 *  */
	protected int clusterBombs;
		/** 
		 * Zmienne mowi¹ce o tym, w jakich kierunkach statek ma sie poruszaæ.  
		 */
	protected boolean up, down, left, right;
	
		/** 
		 * Konstruktor u¿ywa konstruktora klasy Actor.
		 * Wczytywane s¹ obrazy u¿ywane do animacji.
		 * Zdrowie i liczba bomb ustawiane s¹ na wartoœci pocz¹tkowe.
		 */
	public Player (Stage stage) {
		super(stage);
		setSpriteNames(new String[] {"samolot1.png","samolot2.png", "samolot3.png", "samolot4.png"} ); //wczytanie statku
		setFrameSpeed(10);
		health = MAX_HEALTH;

		clusterBombs = MAX_BOMBS;
		}
		/** 
		 * Metoda odpowiadaj¹ca za wystrza³ g³ównego dzia³a statku. 
		 */
	public void fire() {
		if(Gun.CURRENT_BULLETS < Gun.MAX_BULLETS) {
			Gun shoot = new Gun(stage);
			shoot.setX(x + 35);
			shoot.setY(y - shoot.getHeight());
			shoot.czyj = 1;
			stage.addActor(shoot);
			stage.getSoundCache().playSound("strzalPlayer.wav");
			}
		if(Gun.CURRENT_BULLETS<0)Gun.CURRENT_BULLETS=0;
	}
	
		/** 
		 * Metoda odpowiadaj¹ca za wystrza³ rakiet - bomb, czyli dodatkowej broni statku. 
		 * Za jednym naciœniêciem klawisza wystrzeliwane jest 8 pocisków. 
		 */
	public void fireCluster() {
		if(clusterBombs == 0)
			return;
			clusterBombs--;
		stage.addActor(new Bomb(stage, Bomb.Up_Left, x, y, 1));
		stage.addActor(new Bomb(stage, Bomb.Up, x, y, 1));
		stage.addActor(new Bomb(stage, Bomb.Up_Right, x, y, 1));
		stage.addActor(new Bomb(stage, Bomb.Left, x, y, 1));
		stage.addActor(new Bomb(stage, Bomb.Right, x, y, 1));
		stage.addActor(new Bomb(stage, Bomb.Down_Left, x, y, 1));
		stage.addActor(new Bomb(stage, Bomb.Down, x, y, 1));
		stage.addActor(new Bomb(stage, Bomb.Down_Right, x, y, 1));
		
	}
		/** 
		 * Metoda sprawdza, czy nast¹pi³a kolizja statku z innymi obiektami na mapie.  
		 */
	public void collision(Actor a) {
		if(a instanceof Monster) {
			
			a.remove();	//usuwanie potwora
			Monster.LICZBA_POTWOROW--;
			
			this.addPoints(20);
			stage.getSoundCache().playSound("boom.wav");
			this.health-=30;// w przypadku zderzenia
			
			if(this.health <=  0) {
				stage.getSoundCache().playSound("boom.wav");
				stage.gameOver();
				currentFrame=0;
			}
		}
		
		if(a instanceof MonsterGun){

			a.remove(); //strzal
			this.health-=10;
		
			if(health <=  0) {
				stage.getSoundCache().playSound("boom.wav");
				setSpriteNames(new String[] {"wybuch.png"});
				setFrameSpeed(10);
				stage.gameOver();
				currentFrame=0;	
			}
		}
			if(a instanceof Boss) {
			
				this.health-=100;  // dotkniecie bossa - natychmiastowa smierc
				
				if(health <=  0) {
					stage.getSoundCache().playSound("boom.wav");
					setSpriteNames(new String[] {"wybuch.png"});
					setFrameSpeed(10);
					stage.gameOver();
					currentFrame=0;		
				}
			}
			if(a instanceof BossGun) {
				a.remove();
				this.health-=30;  // boss zabiera fpyte HP (dead)
				
				if(health <=  0) {
					
					stage.getSoundCache().playSound("boom.wav");
					setSpriteNames(new String[] {"wybuch.png "});
					setFrameSpeed(10);
					stage.gameOver();
					currentFrame=0;		
			}
			currentFrame=0;
	}
}
		/** 
		 * Metoda korzysta z metody act() klasy nadrzêdnej.
		 * "Nie pozwala" statkowi gracza przemieœciæ siê poza obszar wyznaczony.  
		 */
	@Override
	public void act() {
		super.act();
		x += vx;
		y += vy;
		//warunki na koniec mapy
		if(x< 20 || x > Stage.SZEROKOSC - 70)
			vx = -vx;
		if(y < 300 || y > Stage.WYSOKOSC - 150)
			vy = -vy;
			
	}
	
	public int  getVx() 	 {return vx; }
	public void setVx(int i) {vx = i; }
	public int  getVy() 	 {return vy;}
	public void setVy(int i) {vy = i;}
	
		/** 
		 * Zmienia prêdkoœæ statku gracza. 
		 */
	protected void UpdateSpeed() {
		vx = 0;
		vy = 0;
		if(down)  vy =  SHIP_SPEED;
		if(up) 	  vy = -SHIP_SPEED;
		if(left)  vx = -SHIP_SPEED;
		if(right) vx =  SHIP_SPEED;
	}
	
	public int  getPoints()      {return points;}
	public void setPoints(int i) { points = i;}
	public int  getHealth()      {return health;}
	public void setHealth(int i) { health = i;}

	public int  getClusterBombs()	   { return clusterBombs; }
	public void setClusterBombs(int i) {clusterBombs = i; }
		
	public void addPoints(int i) { points = points + i;}
	public void addHealth(int i) { health = health + i;}
	
		/** 
	 	 * Reakcje na "puszcznie" klawisza.  
	 	 */		
	public void keyReleased(KeyEvent evt) {
		
		int key = evt.getKeyCode();
		 switch(key){
		 	case KeyEvent.VK_LEFT:
		 			left = false;
		 			break;
		 	case KeyEvent.VK_UP:
		 			up = false;
		 			break;
		 	case KeyEvent.VK_RIGHT:
		 			right = false;
		 			break;
		 	case KeyEvent.VK_DOWN:
		 			down = false;
		 			break; 
		 	} 
		 UpdateSpeed();
		}	
		/** 
		 * Reakcje na "wcisniecie" klawisza.  
		 */	
	public void keyPressed(KeyEvent evt) {
		 int key = evt.getKeyCode();
		 System.out.println(key);
		 	switch(key){
		      	case KeyEvent.VK_LEFT:
			      		left = true;
			      		break;
		      	case KeyEvent.VK_UP:
		      			up  = true;
		      			break;
		      	case KeyEvent.VK_RIGHT:
		      			right = true;
		      			break;   	    
		      	case KeyEvent.VK_DOWN:
		      			down = true;
		      			break;
		      	case KeyEvent.VK_OPEN_BRACKET:
		      		if(this.health > 0){
		      			fire(); 
		      		}
		      			break;
		     	case KeyEvent.VK_CLOSE_BRACKET:
		     		if(this.health > 0){
		    		    fireCluster(); 
		     		}
		    		    break;	
		 	}
		 	UpdateSpeed();
		}
}
