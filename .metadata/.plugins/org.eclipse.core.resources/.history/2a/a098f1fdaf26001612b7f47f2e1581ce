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

public class Gra extends Canvas implements  Stage, KeyListener{

	public BufferStrategy strategia;
	public long FPS;
	private SpriteCache spriteCache;
	private ArrayList<Actor> actors;
	private Player player;
	private Player2 player2;
	private SoundCache soundCache;
	private boolean bossFlag = false;	//flaga ktora informuje czy wyswietlic bossa
	private boolean gameEnd = false;
	
	private BufferedImage background, backgroundTile;
	private int backgroundY;
	Image image;
	Graphics gc;
	Wyniki wyniki;
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
	
	public void addBoss(){
		
		Boss b = new Boss(this);
		b.setX(((int)Math.random()* 234));
		b.setY(100);
		b.setVx((int)(Math.random() * 3 ) + 4);	
		b.setVy((int)(Math.random() * 2) + 3);	
		actors.add(b);	
	}
	
	
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
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		
	}

public void gameOver() {
	if(player.getHealth()<= 0 && player2.getHealth() <= 0 ) gameEnd = true;} //koniec gry jesli obaj gracze zgina

public void gameWon() {
	if(Boss.getHp() <= 0 && player.getHealth() > 0){ gameEnd = true;} // koniec gry po wygranej 
	if(Boss.getHp() <= 0 && player2.getHealth() > 0){ gameEnd = true;}
	
	
		
}
	
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
		
		
		//HP BOSSA
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
public void paintStatus(Graphics2D g) {
		paintPoints(g);
		paintHealth(g);
		paintBombsLeft(g);
	}
	
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
	
	public void paintWorld() {
		Graphics2D g = (Graphics2D)strategia.getDrawGraphics();
		g.drawImage(background, 0, 0, Stage.SZEROKOSC, Stage.WYSOKOSC, 0, backgroundY, Stage.SZEROKOSC, backgroundY+Stage.WYSOKOSC,this);
 
		
		for(int i=0 ; i< actors.size(); i++) {
			Actor m = (Actor)actors.get(i);
			m.paint(g);
		}
			
		if(player.getHealth() > 0){ // rysujemy playera1 jak zdrowie wieksze od zera
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
		
		if(player.getHealth() > 0){    // playerem mozna poruszac jesli zdrowie > 0
			player.act();
		}
		if(player2.getHealth() > 0 ){    // playerem2  --------||--------------------
			player2.act();
		}
	}
	
	public void updateWorld() {
		int i = 0;
		while(i < actors.size()) {
			Actor m = (Actor) actors.get(i);
			if(m.isTrashObject()) {
				actors.remove(i);
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
	
		//BOSS - dodawanie
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
	
public void checkCollisions() {
	
	Rectangle playerBounds2 = player2.getBounds();
	Rectangle playerBounds = player.getBounds(); // intersects sprawdza przecinanie sie obiektów
	//obiekt zwraca swoje rozmiwary getBounds i zostaje porownany
	
	for(int i=0; i < actors.size(); i++){
	
		Actor x1 = (Actor)actors.get(i);
		Rectangle r1 = x1.getBounds();
		
		if(r1.intersects(playerBounds)) {
			if(player.getHealth() > 0){ //jesli gracz 1 zyje to sa kolizje
				x1.collision(player);
				player.collision(x1);
			}
		}
		
		if(r1.intersects(playerBounds2)) {
			if(player2.getHealth() > 0) { // lub jesli gracz drugi zyje tez sa jego kolizje
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
	}
}

public void game() {
	

	initWorld();
	
	while (isVisible() && !gameEnd) {
		
		backgroundY--;
		if(backgroundY < 0)
			backgroundY = backgroundTile.getHeight();
		
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

@Override
public void keyPressed(KeyEvent evt) {
	player.keyPressed(evt);
	player2.keyPressed(evt);
}

@Override
public void keyReleased(KeyEvent evt) {
	player.keyReleased(evt);
	player2.keyReleased(evt);
}

@Override
public void keyTyped(KeyEvent e) {}

@Override
public void addActor(Actor a) {
	actors.add(a);
}

public SoundCache getSoundCache() {
return soundCache;
}

}	






