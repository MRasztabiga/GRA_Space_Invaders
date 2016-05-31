import java.awt.image.ImageObserver;
	/** 
	 * Interfejs porz¹dkuj¹cy obiekty na planszy.
	 */
public interface Stage extends ImageObserver {
		/** 
		 * Szerokoœæ okna.
		 */
	public static final int SZEROKOSC = 1024;
		/** 
		 * Wysokoœæ okna.
		 */
	public static final int WYSOKOSC = 768;
		/** 
		 * Prêdkoœæ rozgrywki.
		 */
	public static final int SZYBKOSC = 15;
		/** 
		 * Wysokoœæ, na jak¹ mo¿e maksymalnie wylecieæ statek gracza.
		 */
	public static final int WYSOKOSC_GRY = 550;
	public SpriteCache getSpriteCache();
	public void addActor(Actor a);
	public Player getPlayer();
	public Player2 getPlayer2();
	public SoundCache getSoundCache();
	public void gameOver();
	public void gameWon();

}
