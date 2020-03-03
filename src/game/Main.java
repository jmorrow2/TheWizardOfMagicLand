package game;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;


public class Main extends StateBasedGame{

	public static final String gamename = "The Wizard of Magic Land";
	
	/* Our states of the game will be represented by int
	 * For this game we only have a menu and a game play state
	 */
	public static final int menu = 0;
	public static final int play = 1;
	
	public static final int screenWidth = 640;
	public static final int screenHeight = 416;

	/* constructor for Game
     * takes in name and creates a window that will display the name on the boarder using superclass constructor
     * 
     * this is also where you add each game state to the game individually
     * each state is it's own class
     */
	public Main(String name) {
		super(name);
		this.addState(new TitleMenu(menu));
		this.addState(new Game(play));
	}

	/* GameContainer handles stuff like buffering, frame rate and background stuff like that
	 * you need to give the game container the information on what kind of screens you're going to have
	 */
	@Override
	public void initStatesList(GameContainer arg0) throws SlickException {
		
		/* getState returns GameState obj, init takes in GameContainer obj and StateBasedGame obj and initializes the state 
		 */
		this.getState(menu).init(arg0, this);
		this.getState(play).init(arg0, this);
		
		/* we need to tell the game what state to begin in
		 */
		this.enterState(menu);
	}
	
	public static void main(String[] args) {
		/* AppGameContainer is the actual window
		 * constructor takes in Game obj
		 */
		
		AppGameContainer appgc;
		try {
			appgc = new AppGameContainer(new Main(gamename));
			appgc.setDisplayMode(screenWidth, screenHeight, false);
			appgc.setShowFPS(false);
			appgc.start();
		}catch(SlickException e) {
			e.printStackTrace();
		}
	}
	
	public static int getScreenwidth() {
			return screenWidth;
	}

	public static int getScreenheight() {
		return screenHeight;
	}

}
