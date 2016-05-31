import java.awt.event.KeyEvent;
	/** 
	 * Klasa odpowiedzialna za dzia³anie statku drugiego gracza.
	 * Dziedziczy po klasie Player.
	 */
public class Player2 extends Player{
		/** 
		 * Konstruktor u¿ywa konstruktora klasy Actor.
		 * Wczytywane s¹ obrazy u¿ywane do animacji.
		 * Zdrowie i liczba bomb ustawiane s¹ na wartoœci pocz¹tkowe.
		 */
	public Player2 (Stage stage) {
		super(stage);
		setSpriteNames(new String[] {"samolot5.png","samolot6.png","samolot7.png", "samolot8.png"} ); //wczytanie statku
		setFrameSpeed(10);
		health= MAX_HEALTH;
		clusterBombs = MAX_BOMBS;
		}

	public int  getPoints()      {return points;}
	public void setPoints(int i) { points = i;}
	public int  getHealth()      {return health;}
	public void setHealth(int i) { health = i;}

	public int  getClusterBombs() 		{ return clusterBombs; }
	public void setClusterBombs(int i)  {clusterBombs = i; }
	
	public void addPoints(int i) { points += i;}
	
		/** 
		 * Metoda odpowiadaj¹ca za wystrza³ g³ównego dzia³a statku. 
		 */
	public void fire() {
		if(Gun.CURRENT_BULLETS < Gun.MAX_BULLETS) {
			Gun shoot = new Gun(stage);
			shoot.setX(x + 35);
			shoot.setY(y - shoot.getHeight());
			shoot.czyj = 2;
			stage.addActor(shoot);
			stage.getSoundCache().playSound("strzalPlayer.wav");
			}
		if(Gun.CURRENT_BULLETS<0)Gun.CURRENT_BULLETS=0;
	}
	
		/** 
		 * Metoda odpowiadaj¹ca za wystrza³ rakiet - bomb, czyli dodatkowej broni statku. 
		 */
	public void fireCluster() {
		if(clusterBombs == 0)
			return;
			clusterBombs--;
		stage.addActor(new Bomb(stage, Bomb.Up_Left, x, y, 2));
		stage.addActor(new Bomb(stage, Bomb.Up, x, y, 2));
		stage.addActor(new Bomb(stage, Bomb.Up_Right, x, y, 2));
		stage.addActor(new Bomb(stage, Bomb.Left, x, y, 2));
		stage.addActor(new Bomb(stage, Bomb.Right, x, y, 2));
		stage.addActor(new Bomb(stage, Bomb.Down_Left, x, y, 2));
		stage.addActor(new Bomb(stage, Bomb.Down, x, y, 2));
		stage.addActor(new Bomb(stage, Bomb.Down_Right, x, y, 2));
	}
	
	/** 
	 * Metoda sprawdza, czy nast¹pi³a kolizja statku z innymi obiektami na mapie.  
	 */
	public void collision(Actor a) {
		if(a instanceof Monster) {
			
			a.remove();	//usuwanie potwora
			Monster.LICZBA_POTWOROW--;
			addPoints(20);
			stage.getSoundCache().playSound("boom.wav");
			this.health-=30;// w przypadku zderzenia
			
			if(this.health <=  0) {
				stage.getSoundCache().playSound("boom.wav");
				
				setSpriteNames(new String[] {"wybuch.png"});
				setFrameSpeed(10);
				stage.gameOver();
				currentFrame=0;
				
			}
		}
		
		if(a instanceof MonsterGun){

			a.remove(); //STRZALU
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
			remove(); // usuwanie playera2 po kolizji z bossem
			this.health-=100;  // boss zabiera duzo HP (dead)
		
			if(health <=  0) {
				stage.getSoundCache().playSound("boom.wav");
				setSpriteNames(new String[] {"wybuch.png"});
				setFrameSpeed(10);
				stage.gameOver();
				currentFrame=0;
				
			}
		}
		if(a instanceof BossGun) {
			a.remove(); // usuwanie strzalu bossa
			this.health-=30;  // boss zabiera fpyte HP (dead)
			//this.addPoints(30); 
			
			if(health <=  0) {
				
				stage.getSoundCache().playSound("boom.wav");
				setSpriteNames(new String[] {"wybuch.png"});
				setFrameSpeed(10);
				stage.gameOver();
				currentFrame=0;
				
				}
		}
	}

		/** 
		 * Reakcje na "puszcznie" klawisza.  
		 */		
	public void keyReleased(KeyEvent evt) {
		
		int key = evt.getKeyCode();
		 switch(key){
		 	case KeyEvent.VK_A:
		 			left = false;
		 			break;
		 	case KeyEvent.VK_W:
		 			up = false;
		 			break;
		 	case KeyEvent.VK_D:
		 			right = false;
		 			break;
		 	case KeyEvent.VK_S:
		 			down = false;
		 			break; 
		 	} 
		 UpdateSpeed();
		}	
		/** 
		 * Reakcje na "wciœniêcie" klawisza.  
		 */	
	public void keyPressed(KeyEvent evt) {
		 int key = evt.getKeyCode();
		 System.out.println(key);
		 	switch(key){
		      	case KeyEvent.VK_A:
			      		left = true;
			      		break;
		      	case KeyEvent.VK_W:
		      			up  = true;
		      			break;
		      	case KeyEvent.VK_D:
		      			right = true;
		      			break;   	    
		      	case KeyEvent.VK_S:
		      			down = true;
		      			break;
		      	case KeyEvent.VK_T:
		      		if(this.health > 0){
		      			fire(); 
		      		}
		      			break;
		     	case KeyEvent.VK_Y:
		     		if(this.health > 0) {
		     			fireCluster(); 
		     		}  
		    		     break;
		 		}
		 	UpdateSpeed();
		}
	}
