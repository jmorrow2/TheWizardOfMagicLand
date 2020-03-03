package game;

import java.awt.Font;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import HelperClasses.FileHelper;
import HelperClasses.ImageHelper;
import HelperClasses.MusicHandler;

public class TitleMenu extends BasicGameState {
	
	private Image playButtonImg;
	private Image instructionsButtonImg;
	private Image exitButtonImg;
	private Image backgroundImg;
	private Image gameTitleImg;
	private Image instructionsImg;
	private TrueTypeFont ttf;
	
	private int state;
	
	private boolean instructionsShown;
	
	float[] titleImgCenteredCoords;
	float[] imgButtonCenteredCoords;
	float[] instructionsImgCenteredCoords;
	
	public TitleMenu(int state) {
		this.state = state;
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		MusicHandler.playMusic("res/sounds/music/gamePlay.wav");
		playButtonImg = new Image("res/UI sprites/play button.png");
		instructionsButtonImg = new Image("res/UI sprites/instructions button.png");
		exitButtonImg = new Image("res/UI sprites/exit button.png");
		backgroundImg = new Image("res/UI sprites/title menu background.png");
		gameTitleImg = new Image("res/UI sprites/game title.png");
		instructionsImg = new Image("res/UI sprites/instructions.png");
		
		titleImgCenteredCoords = ImageHelper.centerImg(gameTitleImg);
		imgButtonCenteredCoords = ImageHelper.centerImg(playButtonImg);
		instructionsImgCenteredCoords = ImageHelper.centerImg(instructionsImg);
		
		Font f = new Font("high score font", Font.BOLD, 22);
		ttf = new TrueTypeFont(f, false);
		int highScore = FileHelper.getHighScoreFromFile();
		LevelHandler.setHighScore(highScore);
		LevelHandler.setOldHighScore(highScore);
		
		instructionsShown = false;
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g) throws SlickException {
		g.drawImage(backgroundImg, 0, 0);
	    g.drawImage(gameTitleImg, titleImgCenteredCoords[0], 30);
		playButtonImg.draw(imgButtonCenteredCoords[0], 250);
		instructionsButtonImg.draw(imgButtonCenteredCoords[0], 300);
		exitButtonImg.draw(imgButtonCenteredCoords[0], 350);
		
		g.setFont(ttf);
		g.setColor(Color.black);
		g.drawString("HIGH SCORE: " + LevelHandler.getHighScore(), 235, 185);
		
		if(instructionsShown) {
			instructionsImg.draw(instructionsImgCenteredCoords[0], instructionsImgCenteredCoords[1]);
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int arg2) throws SlickException {		
		int xpos = Mouse.getX();
		int ypos = Mouse.getY();
		
		if(instructionsShown) {
			if((xpos>99 && xpos<228) && (ypos>317 && ypos<346)) {
				if(Mouse.isButtonDown(0)){
					instructionsShown = false;
				}
			}
		}else {
			if((xpos>171 && xpos<468) && (ypos>123 && ypos<167)) {
				if(Mouse.isButtonDown(0)){
					sbg.enterState(1);
				}
			}			
			if((xpos>171 && xpos<468) && (ypos>73 && ypos<114)) {
				if(Mouse.isButtonDown(0)){
					instructionsShown = true;
				}
			}	
			
			if((xpos>171 && xpos<468) && (ypos>24 && ypos<64)) {
				if(Mouse.isButtonDown(0)){
					System.exit(0);
				}
			}
		}	
	}

	@Override
	public int getID() {
		return state;
	}

}
