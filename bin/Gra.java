import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Canvas;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import java.awt.Transparency;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;

import java.awt.TexturePaint;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.Rectangle;
/**
 *Klasa Gra rozszerza klasê Canvas, implementuje Stage oraz KeyListener
 *Umiejscowiona jest w niej g³ówna pêtla gry 
 */
public class Gra extends Canvas implements  Stage, KeyListener{

	/** Wyœwietlanie jest buforowane - zapobiega migotaniu. */
	public BufferStrategy strategia;
	/**
	Instancja klasy SpriteCache
	 */
	private SpriteCache spriteCache;
	/** Tablica aktorów - potworów. */
	private ArrayList<Actor> actors;
	/**
	 * Instancja klasy Player
	 */
	private Player  player;
	/**
	 * Instancja klasy Player2
	 */
	private Player2 player2;
	/**
	 * Instancja klasy SoundCache
	 */
	private SoundCache soundCache;
	/** Flaga, która informuje, czy wyœwietlic bossa. */
	private boolean bossFlag = false;	
	/** Flaga, która informuje, czy gra siê zakoñczy³a. */
	private boolean gameEnd = false;
	/**
	 * Instancje klasy BufferedImage s³u¿¹ce do przewijania t³a gry
	 * Polu bacgroundY przypisywany jest rozmiar obrazu t³a, który podczas przewijania jest dekrementowany,
	 * po przejœciu ca³ego t³a zostaje ono przewiniête na pocz¹tek
	 */
	private BufferedImage background, backgroundTile;
	private int backgroundY;
	Image image;
	Graphics gc;
	Wyniki wyniki;
	/**
	 * Konstruktor 
	 * Utworzone zostaje okno, ustawione zostaj¹ jego parametry
	 * Dodana zostaje mo¿liwoœæ wy³¹czenia okna za pomoc¹ "krzy¿yka"
	 * Dla okna zostaje zablokowana mo¿liwoœæ zmiany rozmiaru
	 */
	public Gra() {
		spriteCache = new SpriteCache();

	JFrame okno = new JFrame("GRA");
	okno.setBounds(0,0,SZEROKOSC,WYSOKOSC);
	okno.setVisible(true);
	okno.addWindowListener(new WindowAdapter() {
		public void windowClosing(WindowEvent e){System.exit(0);}
		});
	okno.setResizable(false);
	JPanel panel = (JPanel)okno.getContentPane();
	setBounds(0,0,Stage.SZEROKOSC,Stage.WYSOKOSC);
	panel.setPreferredSize(new Dimension(Stage.SZEROKOSC,Stage.WYSOKOSC));
	panel.setLayout(null);
	panel.add(this);
	createBufferStrategy(2);
	strategia = getBufferStrategy();
	requestFocus();
	addKeyListener(this);
	soundCache = new SoundCache();
	}
	
		/** 
		 * Dodanie pierwszych potworów na pocz¹tku rozgrywki.
		 */	
	public void addMonsters(int ile){
		for(int i = 0; i < ile; i++)
		{
			Monster m = new Monster(this);
			m.setX(((int)Math.random()* 30 + i * 7));
			m.setY(((int)(Math.random() * 100) * 45 ) % 500);
			m.setVx((int)(Math.random() * 7 ) + 2);	
			actors.add(m);
		}	
	}
	
		/** 
		 * Dodanie bossa na mapê.
		 */	
	public void addBoss(){
		
		Boss b = new Boss(this);
		b.setX(((int)Math.random()* 234));
		b.setY(100);
		b.setVx((int)(Math.random() * 3 ) + 4);	
		b.setVy((int)(Math.random() * 2) + 3);	
		actors.add(b);	
	}
	
		/** 
		 * Inicjalizacja œwiata.
		 * Wczytanie t³a, graczy, pocz¹tkowych potworów, muzyki
		 */	
	public void initWorld() {
		actors = new ArrayList<Actor>();
	
		addMonsters(20);
		
		player = new Player(this);
		player.setX(Stage.SZEROKOSC/2);
		player.setY(Stage.WYSOKOSC - 2*player.getHeight());
		
		player2 = new Player2(this);
		player2.setX(Stage.SZEROKOSC/2 - 150);
		player2.setY(Stage.WYSOKOSC - 2 * player.getHeight());
		
		soundCache.loopSound("music2.wav");
		
		
		backgroundTile = spriteCache.getSprite("tlo.png");
		background = spriteCache.createCompatible(Stage.SZEROKOSC, Stage.WYSOKOSC+backgroundTile.getHeight(), Transparency.OPAQUE);
		Graphics2D g = (Graphics2D)background.getGraphics();
		g.setPaint(new TexturePaint(backgroundTile, new Rectangle(0,0, background.getWidth(), background.getHeight())));
		g.fillRect(0, 0, background.getWidth(),  background.getHeight());
		backgroundY = backgroundTile.getHeight();
		
		wyniki = new Wyniki();
						try {
							wyniki.odczyt();
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						}
		
	}
		/** 
		 * Sprawdzanie, czy gra siê nie zakoñczy³a z powodu œmierci obydwu graczy.
		 */	
	public void gameOver() {
		if(player.getHealth()<= 0 && player2.getHealth() <= 0 ) gameEnd = true;
		}
		
		/** 
		 * Sprawdzanie, czy gra siê nie zakoñczy³a z powodu œmierci bossa - któryœ z graczy musi ¿yæ.
		 */	
	public void gameWon() {
		if(Boss.getHp() <= 0 && (player.getHealth() > 0 || player2.getHealth() > 0) ) gameEnd = true;  		
		}
		/** 
		 * Narysowanie pasków ¿ycia.
		 */	
	public void paintHealth(Graphics2D g) {
		g.setPaint(Color.red);
		g.fillRect(280, Stage.WYSOKOSC - 55, Player.MAX_HEALTH , 15);
		g.setPaint(Color.blue);
		g.fillRect(280, Stage.WYSOKOSC - 55, (player.getHealth()<0) ? 0 : player.getHealth() , 15);
		g.setFont(new Font ("Verdana" , Font.BOLD, 20));	
		g.setPaint(Color.yellow);
		g.drawString("¯YCIE: ", 190, Stage.WYSOKOSC - 40);
		
		
		g.setPaint(Color.magenta);
		g.fillRect(880, Stage.WYSOKOSC - 55, Player2.MAX_HEALTH , 15);
		g.setPaint(Color.GREEN);
		g.fillRect(880, Stage.WYSOKOSC - 55, (player2.getHealth()<0) ? 0 : player2.getHealth() , 15);
		g.setFont(new Font ("Verdana" , Font.BOLD, 20));	
		g.setPaint(Color.yellow);
		g.drawString("¯YCIE: ", 790, Stage.WYSOKOSC - 40);
		
		
		/*
		 * Rysowanie paska ¿ycia Bossa
		 */
		if(Monster.deathCounter > 97){
			g.setPaint(Color.red);
			g.fillRect(680, Stage.WYSOKOSC - 750, Boss.MAX_HEALTH , 15);
			g.setPaint(Color.GREEN);
			g.fillRect(680, Stage.WYSOKOSC - 750, (Boss.getHp()<0) ? 0 : Boss.getHp() , 15);
			g.setFont(new Font ("Verdana" , Font.BOLD, 20));	
			g.setPaint(Color.yellow);
			g.drawString("BOSS HP: ", 550, Stage.WYSOKOSC - 735);
		}
	}
		/** 
		 * Narysowanie pól z punktami.
		 */	
	public void paintPoints(Graphics2D g) {
			
			g.setFont(new Font ("Verdana" , Font.BOLD, 20));	
			g.setPaint(Color.yellow);
			g.drawString("P1:", 20, Stage.WYSOKOSC - 80);
			g.drawString("P2:", 600 , Stage.WYSOKOSC - 80);
			g.drawString("PUNKTY:  ", 20, Stage.WYSOKOSC - 40);
			g.setPaint(Color.red);
			g.drawString(player.getPoints()+ "  ", 130, Stage.WYSOKOSC - 40);
			
			
			g.setFont(new Font ("Verdana" , Font.BOLD, 20));	
			g.setPaint(Color.yellow);
			g.drawString("PUNKTY:  ", 600, Stage.WYSOKOSC - 40);
			g.setPaint(Color.red);
			g.drawString(player2.getPoints()+ "  ", 710, Stage.WYSOKOSC - 40);
		}
		/** 
	 	* Narysowanie pól z liczb¹ pozosta³ych rakiet.
	 	*/
	public void paintBombsLeft(Graphics2D g) {
				g.setFont(new Font ("Verdana" , Font.BOLD, 20));
				g.setPaint(Color.BLUE);
				g.drawString("BOMBY: " , 20 ,Stage.WYSOKOSC - 60 );
				g.setPaint(Color.RED);
				g.drawString(player.getClusterBombs() + " ", 130, Stage.WYSOKOSC - 60);
				
				g.setFont(new Font ("Verdana" , Font.BOLD, 20));
				g.setPaint(Color.BLUE);
				g.drawString("BOMBY: " , 600 ,Stage.WYSOKOSC - 60 );
				g.setPaint(Color.RED);
				g.drawString(player2.getClusterBombs() + " ", 710, Stage.WYSOKOSC - 60);
			}
		/** 
		 * Narysowanie wyniku graczy w przypadku przegranej.
		 */
	public void paintScoresLost() {
			
			Graphics2D g = (Graphics2D)strategia.getDrawGraphics();
			g.setFont(new Font ("Verdana" , Font.BOLD, 40));	
			g.setPaint(Color.yellow);
			g.drawString("Gracz1:  ", 340, (Stage.WYSOKOSC / 2) + 50);
			g.setPaint(Color.red);
			g.drawString(player.getPoints()+ " pkt ", 550, (Stage.WYSOKOSC / 2) +50);
			
			g.setFont(new Font ("Verdana" , Font.BOLD, 40));	
			g.setPaint(Color.yellow);
			g.drawString("Gracz2:  ", 340, (Stage.WYSOKOSC / 2) +90);
			g.setPaint(Color.red);
			g.drawString(player2.getPoints()+ " pkt  ", 550, (Stage.WYSOKOSC / 2) +90);
			
			soundCache.stopSound("BossWave_1.wav");
		
			
			strategia.show();
		}
		/** 
		 * Narysowanie wyniku graczy w przypadku wygranej.
		 */
	public void paintScoresWon() {
			Graphics2D g = (Graphics2D)strategia.getDrawGraphics();
			
			g.setFont(new Font ("Times New Roman", Font.BOLD, 90));
			g.setColor(Color.GREEN);
			g.drawString("ZWYCIÊSTWO!" , 150, (Stage.WYSOKOSC /2 ));
			g.setFont(new Font ("Verdana" , Font.BOLD, 40));
			g.setPaint(Color.BLUE);
			g.drawString("Gracz1:  ", 320, (Stage.WYSOKOSC / 2) + 50);
			g.setPaint(Color.red);
			g.drawString(player.getPoints()+ "  ", 500, (Stage.WYSOKOSC / 2) +50);
			
			g.setFont(new Font ("Verdana" , Font.BOLD, 40));	
			g.setPaint(Color.BLUE);
			g.drawString("Gracz2:  ", 320, (Stage.WYSOKOSC / 2) +90);
			g.setPaint(Color.red);
			g.drawString(player2.getPoints()+ "  ", 500, (Stage.WYSOKOSC / 2) + 90);
			
			soundCache.stopSound("BossWave_1.wav");
			soundCache.loopSound("MCHAMMER_1.wav");
			
			strategia.show();
			
			if(wyniki.najgorszyWynik()<player.getPoints()){
				System.out.println("\n1\n");
				String imieGracza1=JOptionPane.showInputDialog(""+ player.getPoints()+"ptk Gratulacje graczu1! Podaj imiê:");
				try {
					wyniki.dodanie_wyniku(player.getPoints(), imieGracza1);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				};
				
				if(wyniki.najgorszyWynik()<player2.getPoints()){
					System.out.println("\n2\n");
					String imieGracza2=JOptionPane.showInputDialog(""+ player2.getPoints()+"ptk Gratulacje graczu2! Podaj imiê:");
					try {
						wyniki.dodanie_wyniku(player2.getPoints(), imieGracza2);
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					};
							try {
					wyniki.zapis();
							} catch (FileNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}	
			JOptionPane.showMessageDialog(null,"WYNIKI:\n" + wyniki.toString());
				
			
		}
		/** 
		 * Zbiorcza metoda - rysuje licznik punktów zdobywanych przez graczy,
		 * paski zdrowia, liczbê pozosta³ych bomb. 
		 */
	public void paintStatus(Graphics2D g) {
		paintPoints(g);
		paintHealth(g);
		paintBombsLeft(g);
	}
		/** 
		 * Metoda u¿yta, gdy gracze zakoñcz¹ grê nie zabijaj¹c bossa.
		 * Wyœwietlany jest napis "GameOver".
		 * Nastêpuje zmiana odtwarzanej muzyki.
		 */
	public void paintGameOver() {
		Graphics2D g = (Graphics2D)strategia.getDrawGraphics();
		g.setColor(Color.RED);
		g.setFont(new Font("Broadway", Font.BOLD, 90));
		g.drawString("GAME OVER",Stage.SZEROKOSC/2 -300  , Stage.WYSOKOSC/2 );
		
		soundCache.stopSound("music2.wav");
		soundCache.loopSound("music_1.wav");
		
		strategia.show();
	}
	

	public Player getPlayer(){ return player;}
	public Player2 getPlayer2(){ return player2;}
	
		/** 
		 * Metoda rysuj¹ca wszystkie obiekty.
		 */
	public void paintWorld() {
		Graphics2D g = (Graphics2D)strategia.getDrawGraphics();
		g.drawImage(background, 0, 0, Stage.SZEROKOSC, Stage.WYSOKOSC, 0, backgroundY, Stage.SZEROKOSC, backgroundY+Stage.WYSOKOSC,this);
 
		
		for(int i=0 ; i< actors.size(); i++) {
			Actor m = (Actor)actors.get(i);
			m.paint(g);
		}
			
		if(player.getHealth() > 0){ // rysujemy playera1 jeœli zdrowie wieksze od zera
			player.paint(g);
		}
		else if(player.getHealth() <= 0 && player.getHealth()  > -50 ) // zdrowie spada po obrazeniach
			//ponizej zera, dla takiego przedzialu zdazy narysowac, potem -200 hp i nie narysuje go wiecej
			{
			player.setSpriteNames(new String[] {"wybuch.png"});
			player.setFrameSpeed(10);
			player.currentFrame = 0;
			player.health=0;
			player.paint(g);
			player.health-=200;
			}
		
		if(player2.getHealth() > 0) {
			player2.paint(g);
		}
		else if(player2.getHealth() <= 0 && player2.getHealth() > -50 ){
			player2.setSpriteNames(new String[] {"wybuch.png"});
			player2.setFrameSpeed(10);
			player2.currentFrame = 0;
			player2.health=0;
			player2.paint(g);
			player2.health-=200;
		}
		
		paintStatus(g);
		
		strategia.show();
		
		g.setColor(Color.white);
		
		if(player.getHealth() > 0){    // graczem mozna poruszac jesli zdrowie > 0
			player.act();
		}
		if(player2.getHealth() > 0 ){
			player2.act();
		}
	}
		/** 
		 * Metoda sprawdza, czy nale¿y "usun¹æ" obiekty z mapy,
		 * wywo³uje metody act() i decyduje kiedy dodaæ bossa.
		 */
	public void updateWorld() {
		int i = 0;
		while(i < actors.size()) {
			Actor m = (Actor) actors.get(i);
			if(m.isTrashObject()) {
				actors.remove(i);	//faktyczne usuwanie
			}
			else {
				m.act();
				i++;
			}
		}
	
		if(player.getHealth() > 0){
		player.act();
		}
		
		if(player2.getHealth() >0){
		player2.act();
		}
	
		/**
		 * Dodawanie bossa do gry w przypadku gdy spe³nione s¹ warunki ilosci zabojstw oraz gdy
		 * flaga Bossa jest opuszczona
		 */
		if(Monster.deathCounter > 97 && bossFlag==false){
			
			soundCache.stopSound("music2.wav");
			soundCache.loopSound("BossWave_1.wav");
			bossFlag = true;
			addBoss();
		}
	}

	public SpriteCache getSpriteCache() {
		return spriteCache;
	}
	/**
	 * Metoda sprawdzaj¹ca kolizje statków graczy z z potworami 
	 * U¿ywane s¹ obiekty klasy rectangle oraz metoda getBounds zwracaj¹ca ich wymiary 
	 */
	public void checkCollisions() {
		
		Rectangle playerBounds2 = player2.getBounds();
		Rectangle playerBounds = player.getBounds(); // intersects sprawdza przecinanie sie obiektów
		//obiekt zwraca swoje rozmiary getBounds i zostaje porownany
		
		for(int i=0; i < actors.size(); i++){
		
				Actor x1 = (Actor)actors.get(i);
				Rectangle r1 = x1.getBounds();
				
				if(r1.intersects(playerBounds)) {
					if(player.getHealth() > 0){ //jesli gracz 1 zyje to wystêpuj¹ kolizje
						x1.collision(player);
						player.collision(x1);
					}
				}
				
				if(r1.intersects(playerBounds2)) {
					if(player2.getHealth() > 0) { // lub jesli gracz drugi zyje tez wystêpuj¹ jego kolizje
						x1.collision(player2);
						player2.collision(x1);
					}
				}
				
				for(int j = i+1 ; j < actors.size(); j++) {
					
					Actor x2 = (Actor)actors.get(j);
					Rectangle r2 = x2.getBounds();
					if(r1.intersects(r2)) {
						x1.collision(x2);
						x2.collision(x1);
					}
				}
		}//koniec pierwszego fora
	}
		/** 
		 * G³ówna metoda programu.
		 * Inicjalizuje œwiat. Steruje prêdkoœci¹ rozgrywki.
		 * Rysuje wszystkie obiekty, przesuwa t³o.
		 */	
	public void game() {
		
	
		initWorld();
		
		while (isVisible() && !gameEnd) {
			
			backgroundY--;
			if(backgroundY < 0) backgroundY = backgroundTile.getHeight();
			
			updateWorld();
			checkCollisions();
			paintWorld();
			
		try {
			Thread.sleep(Stage.SZYBKOSC);
		} 
		catch (InterruptedException e) {}
		}
	
		if((Boss.getHp() <=0 && (player.getHealth() > 0 || player2.getHealth() > 0 ))) {
			paintScoresWon(); // statystyki po wygranej
		}
		
		if(player.getHealth() <= 0 && player2.getHealth() <=0){
			paintScoresLost(); //statysyki po przegranej
			paintGameOver();
		}
	}
	
	synchronized public void paint(Graphics g){
	    g.drawImage(image,0,0,this);
	}
		/** 
		 * Reakcja na wciœniêty klawisz - sterowanie statkami graczy.
		 */
	@Override
	public void keyPressed(KeyEvent evt) {
		player. keyPressed(evt);
		player2.keyPressed(evt);
	}
	/** 
	 	* Reakcja na "puszczony" klawisz - sterowanie statkami graczy.
	 */
	@Override
	public void keyReleased(KeyEvent evt) {
		player. keyReleased(evt);
		player2.keyReleased(evt);
	}
		/** 
		 * Brak akcji - wymagana przez interfejs KeyListener implementacja.
		 */
	@Override
	public void keyTyped(KeyEvent e) {}
		/** 
		 * Dodanie obiektu do tablicy aktorów.
		 */
	@Override
	public void addActor(Actor a) {
		actors.add(a);
	}
		/** 
		 * Reakcja na wciœniêty klawisz - sterowanie statkami graczy.
		 */
	public SoundCache getSoundCache() {
		return soundCache;
	}

}	
