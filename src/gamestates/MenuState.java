package gamestates;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLabel;

import imgscalr.Scalr;
import main.GamePanel;
import main.Sound;

public class MenuState extends GameState
{	
	//***Title
	private BufferedImage titleImg;
	private final int TITLE_IMG_WIDTH = 200;
	private final int TITLE_IMG_HEIGHT = 150;
	private final int TITLE_IMG_X = (GamePanel.WIDTH/2) - (TITLE_IMG_WIDTH/2);
	private final int TITLE_IMG_Y = 50;
	
	//***Buttons
	private final int BUTTON_VERTICAL_OFFSET = 50;//space between button images
	private final int BUTTON_X = 25;//x coordinate of buttons
	private final int BUTTON_IMG_WIDTH = 126;
	private final int BUTTON_IMG_HEIGHT = 45;
	
	
	private boolean playImgHover;
	private BufferedImage playImgDefault;
	private BufferedImage playImgPressed;
	private final int PLAY_IMG_Y = 200;//hardcoded value
	
	private boolean statsImgHover;
	private BufferedImage statsImgDefault;
	private BufferedImage statsImgPressed;
	private final int STATS_IMG_Y = PLAY_IMG_Y + BUTTON_VERTICAL_OFFSET;
	
	private boolean optionsImgHover;
	private BufferedImage optionsImgDefault;
	private BufferedImage optionsImgPressed;
	private final int OPTIONS_IMG_Y = STATS_IMG_Y + BUTTON_VERTICAL_OFFSET;
	
	private boolean quitImgHover;
	private BufferedImage quitImgDefault;
	private BufferedImage quitImgPressed;
	private final int QUIT_IMG_Y = OPTIONS_IMG_Y + BUTTON_VERTICAL_OFFSET;
	
	//**Rectangles
	private final int RECT_WIDTH = BUTTON_IMG_WIDTH;
	private final int RECT_HEIGHT = 15;
	
	private Rectangle playButton;
	private Rectangle statsButton;
	private Rectangle optionsButton;
	private Rectangle quitButton;
	
	
	public MenuState(GameStateManager gsm)
	{
		super();
		this.gsm = gsm;
		init();
	}
	
	protected void init()
	{	
		playImgHover = false;
		statsImgHover = false;
		optionsImgHover = false;
		quitImgHover = false;
		
		playButton = new Rectangle(BUTTON_X, PLAY_IMG_Y, RECT_WIDTH, RECT_HEIGHT);
		statsButton = new Rectangle(BUTTON_X, STATS_IMG_Y, RECT_WIDTH, RECT_HEIGHT);
		optionsButton = new Rectangle(BUTTON_X, OPTIONS_IMG_Y, RECT_WIDTH, RECT_HEIGHT);
		quitButton = new Rectangle(BUTTON_X, QUIT_IMG_Y, RECT_WIDTH, RECT_HEIGHT);
		
		try
		{	
			//Load and scale title images
			titleImg = ImageIO.read(getClass().getResourceAsStream("/res/imgs/title.png"));
			titleImg = Scalr.resize(titleImg, Scalr.Method.QUALITY, Scalr.Mode.FIT_TO_WIDTH, TITLE_IMG_WIDTH, TITLE_IMG_HEIGHT, Scalr.OP_ANTIALIAS);
			
			//Load and scale button images
			playImgDefault = ImageIO.read(getClass().getResourceAsStream("/res/imgs/play_default.png"));
			playImgDefault = Scalr.resize(playImgDefault, Scalr.Method.QUALITY, Scalr.Mode.FIT_TO_WIDTH, BUTTON_IMG_WIDTH, BUTTON_IMG_HEIGHT, Scalr.OP_ANTIALIAS);
			playImgPressed = ImageIO.read(getClass().getResourceAsStream("/res/imgs/play_pressed.png"));
			playImgPressed = Scalr.resize(playImgPressed, Scalr.Method.QUALITY, Scalr.Mode.FIT_TO_WIDTH, BUTTON_IMG_WIDTH, BUTTON_IMG_HEIGHT, Scalr.OP_ANTIALIAS);
			
			statsImgDefault = ImageIO.read(getClass().getResourceAsStream("/res/imgs/statistics_default.png"));
			statsImgDefault = Scalr.resize(statsImgDefault, Scalr.Method.QUALITY, Scalr.Mode.FIT_TO_WIDTH, BUTTON_IMG_WIDTH, BUTTON_IMG_HEIGHT, Scalr.OP_ANTIALIAS);
			statsImgPressed = ImageIO.read(getClass().getResourceAsStream("/res/imgs/statistics_pressed.png"));
			statsImgPressed = Scalr.resize(statsImgPressed, Scalr.Method.QUALITY, Scalr.Mode.FIT_TO_WIDTH, BUTTON_IMG_WIDTH, BUTTON_IMG_HEIGHT, Scalr.OP_ANTIALIAS);
			
			optionsImgDefault = ImageIO.read(getClass().getResourceAsStream("/res/imgs/options_default.png"));
			optionsImgDefault = Scalr.resize(optionsImgDefault, Scalr.Method.QUALITY, Scalr.Mode.FIT_TO_WIDTH, BUTTON_IMG_WIDTH, BUTTON_IMG_HEIGHT, Scalr.OP_ANTIALIAS);
			optionsImgPressed = ImageIO.read(getClass().getResourceAsStream("/res/imgs/options_pressed.png"));
			optionsImgPressed = Scalr.resize(optionsImgPressed, Scalr.Method.QUALITY, Scalr.Mode.FIT_TO_WIDTH, BUTTON_IMG_WIDTH, BUTTON_IMG_HEIGHT, Scalr.OP_ANTIALIAS);
			
			quitImgDefault = ImageIO.read(getClass().getResourceAsStream("/res/imgs/quit_default.png"));
			quitImgDefault = Scalr.resize(quitImgDefault, Scalr.Method.QUALITY, Scalr.Mode.FIT_TO_WIDTH, BUTTON_IMG_WIDTH, BUTTON_IMG_HEIGHT, Scalr.OP_ANTIALIAS);
			quitImgPressed = ImageIO.read(getClass().getResourceAsStream("/res/imgs/quit_pressed.png"));
			quitImgPressed = Scalr.resize(quitImgPressed, Scalr.Method.QUALITY, Scalr.Mode.FIT_TO_WIDTH, BUTTON_IMG_WIDTH, BUTTON_IMG_HEIGHT, Scalr.OP_ANTIALIAS);
		}
		catch(IOException e)
		{
			System.err.println("***Error Reading Image***");
			e.printStackTrace();
			System.exit(1);
		}
	}

	public void update(){}//nothing to update

	public void draw(Graphics2D g)
	{
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		
		g.drawImage(titleImg, TITLE_IMG_X, TITLE_IMG_Y, null);
		
		if(GamePanel.devOptions)
		{
			g.setColor(Color.BLUE);
			g.draw(playButton);
			g.draw(statsButton);
			g.draw(optionsButton);
			g.draw(quitButton);
		}
		
		if(playImgHover)
			g.drawImage(playImgPressed, BUTTON_X, PLAY_IMG_Y, null);
		else
			g.drawImage(playImgDefault, BUTTON_X, PLAY_IMG_Y, null);
		
		if(statsImgHover)
			g.drawImage(statsImgPressed, BUTTON_X, STATS_IMG_Y, null);
		else
			g.drawImage(statsImgDefault, BUTTON_X, STATS_IMG_Y, null);
		
		if(optionsImgHover)
			g.drawImage(optionsImgPressed, BUTTON_X, OPTIONS_IMG_Y, null);
		else
			g.drawImage(optionsImgDefault, BUTTON_X, OPTIONS_IMG_Y, null);
		
		if(quitImgHover)
			g.drawImage(quitImgPressed, BUTTON_X, QUIT_IMG_Y, null);
		else
			g.drawImage(quitImgDefault, BUTTON_X, QUIT_IMG_Y, null);
	}
	
	public void mousePressed(MouseEvent e)
	{
		Point point = e.getPoint();
		
		if(playButton.contains(point))
		{
			gsm.setCurrentState(GameStateManager.PLAYSTATE);
		}
		else if(statsButton.contains(point))
		{
			gsm.setCurrentState(GameStateManager.STATSTATE);
		}
		else if(optionsButton.contains(point))
		{
			gsm.setCurrentState(GameStateManager.OPTIONSTATE);
		}
		else if(quitButton.contains(point))
		{
			System.exit(0);
		}
	}

	
	public void mouseMoved(MouseEvent e)
	{
		Point point = e.getPoint();
		
		if(playButton.contains(point))
		{
			if(!playImgHover)
					Sound.mouseHover.play();
			playImgHover = true;
		}
		else if(statsButton.contains(point))
		{
			if(!statsImgHover)
				Sound.mouseHover.play();
			statsImgHover = true;
		}
		else if(optionsButton.contains(point))
		{
			if(!optionsImgHover)
				Sound.mouseHover.play();
			optionsImgHover = true;
		}
		else if(quitButton.contains(point))
		{
			if(!quitImgHover)
				Sound.mouseHover.play();
			quitImgHover = true;
		}
		else
		{
			playImgHover = false;
			statsImgHover = false;
			optionsImgHover = false;
			quitImgHover = false;
		}
	}
	
	//Unused overridden methods
	public void mouseClicked(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseDragged(MouseEvent e){}

	
}
