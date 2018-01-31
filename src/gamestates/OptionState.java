package gamestates;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import imgscalr.Scalr;
import main.GamePanel;
import main.Sound;

public class OptionState extends GameState
{	
	
	
	private final int BUTTON_IMG_WIDTH = 300;
	private final int BUTTON_IMG_HEIGHT = 150;
	
	private final int BUTTON_VERTICAL_OFFSET = 50;//space between button images
	
	private final int BUTTON_X = 25;
	
	private final int SOUND_BUTTON_Y = 20;
	private BufferedImage soundOnImgDefault;
	private BufferedImage soundOnImgPressed;
	private BufferedImage soundOffImgDefault;
	private BufferedImage soundOffImgPressed;
	private boolean soundButtonHover;
	
	private final int DEV_BUTTON_Y = SOUND_BUTTON_Y + BUTTON_VERTICAL_OFFSET;
	private BufferedImage devOnImgDefault;
	private BufferedImage devOnImgPressed;
	private BufferedImage devOffImgDefault;
	private BufferedImage devOffImgPressed;
	private boolean devButtonHover;
	
	
	
	private final int RECT_WIDTH = BUTTON_IMG_WIDTH;
	private final int RECT_HEIGHT = 40;
	private Rectangle soundButton;	
	private Rectangle devButton;
	
	private Rectangle backButton;
	
	public OptionState(GameStateManager gsm)
	{
		super();
		this.gsm = gsm;
		init();
	}
	
	@Override
	protected void init()
	{
		soundButtonHover = false;
		soundButton = new Rectangle(BUTTON_X, SOUND_BUTTON_Y, RECT_WIDTH, RECT_HEIGHT);
		
		devButtonHover = false;
		devButton = new Rectangle(BUTTON_X, DEV_BUTTON_Y, RECT_WIDTH, RECT_HEIGHT);
				
		backButton = new Rectangle(GamePanel.WIDTH-40, 5, 30, 30);
		
		
		try
		{																								   
			soundOnImgDefault = ImageIO.read(getClass().getResourceAsStream("/res/imgs/sound_on_default.png"));
			soundOnImgDefault = Scalr.resize(soundOnImgDefault, Scalr.Method.QUALITY, Scalr.Mode.FIT_TO_WIDTH, BUTTON_IMG_WIDTH, BUTTON_IMG_HEIGHT, Scalr.OP_ANTIALIAS);
			soundOnImgPressed = ImageIO.read(getClass().getResourceAsStream("/res/imgs/sound_on_pressed.png"));
			soundOnImgPressed = Scalr.resize(soundOnImgPressed, Scalr.Method.QUALITY, Scalr.Mode.FIT_TO_WIDTH, BUTTON_IMG_WIDTH, BUTTON_IMG_HEIGHT, Scalr.OP_ANTIALIAS);
			
			soundOffImgDefault = ImageIO.read(getClass().getResourceAsStream("/res/imgs/sound_off_default.png"));
			soundOffImgDefault = Scalr.resize(soundOffImgDefault, Scalr.Method.QUALITY, Scalr.Mode.FIT_TO_WIDTH, BUTTON_IMG_WIDTH, BUTTON_IMG_HEIGHT, Scalr.OP_ANTIALIAS);
			soundOffImgPressed = ImageIO.read(getClass().getResourceAsStream("/res/imgs/sound_off_pressed.png"));
			soundOffImgPressed = Scalr.resize(soundOffImgPressed, Scalr.Method.QUALITY, Scalr.Mode.FIT_TO_WIDTH, BUTTON_IMG_WIDTH, BUTTON_IMG_HEIGHT, Scalr.OP_ANTIALIAS);
			
			devOnImgDefault = ImageIO.read(getClass().getResourceAsStream("/res/imgs/dev_on_default.png"));
			devOnImgDefault = Scalr.resize(devOnImgDefault, Scalr.Method.QUALITY, Scalr.Mode.FIT_TO_WIDTH, BUTTON_IMG_WIDTH, BUTTON_IMG_HEIGHT, Scalr.OP_ANTIALIAS);
			devOnImgPressed = ImageIO.read(getClass().getResourceAsStream("/res/imgs/dev_on_pressed.png"));
			devOnImgPressed = Scalr.resize(devOnImgPressed, Scalr.Method.QUALITY, Scalr.Mode.FIT_TO_WIDTH, BUTTON_IMG_WIDTH, BUTTON_IMG_HEIGHT, Scalr.OP_ANTIALIAS);
			
			devOffImgDefault = ImageIO.read(getClass().getResourceAsStream("/res/imgs/dev_off_default.png"));
			devOffImgDefault = Scalr.resize(devOffImgDefault, Scalr.Method.QUALITY, Scalr.Mode.FIT_TO_WIDTH, BUTTON_IMG_WIDTH, BUTTON_IMG_HEIGHT, Scalr.OP_ANTIALIAS);
			devOffImgPressed = ImageIO.read(getClass().getResourceAsStream("/res/imgs/dev_off_pressed.png"));
			devOffImgPressed = Scalr.resize(devOffImgPressed, Scalr.Method.QUALITY, Scalr.Mode.FIT_TO_WIDTH, BUTTON_IMG_WIDTH, BUTTON_IMG_HEIGHT, Scalr.OP_ANTIALIAS);
			
			
		}
		catch(IOException e)
		{
			System.err.println("***Error Reading Image***");
			e.printStackTrace();
			System.exit(0);
		}
	}

	@Override
	public void update(){}//nothing to update

	@Override
	public void draw(Graphics2D g)
	{
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		
		g.setColor(Color.BLUE);
		g.draw(backButton);
		g.setColor(Color.WHITE);
		g.drawString("Back", GamePanel.WIDTH-40, 20);
		
		if(GamePanel.devOptions)
		{
			g.setColor(Color.BLUE);
			g.draw(soundButton);
			g.draw(devButton);
		}
		
		if(GamePanel.playMusic && soundButtonHover)//if music is playing and hovering over button
			g.drawImage(soundOnImgPressed, BUTTON_X, SOUND_BUTTON_Y, null);
		else if(GamePanel.playMusic && !soundButtonHover)//if music is playing and not hovering over button
			g.drawImage(soundOnImgDefault, BUTTON_X, SOUND_BUTTON_Y, null);
		else if(!GamePanel.playMusic && soundButtonHover)
			g.drawImage(soundOffImgPressed, BUTTON_X, SOUND_BUTTON_Y, null);
		else
			g.drawImage(soundOffImgDefault, BUTTON_X, SOUND_BUTTON_Y, null);
		
		if(GamePanel.devOptions && devButtonHover)//if devOptions is on and hovering over button
			g.drawImage(devOnImgPressed, BUTTON_X, DEV_BUTTON_Y, null);
		else if(GamePanel.devOptions && !devButtonHover)//if music is playing and not hovering over button
			g.drawImage(devOnImgDefault, BUTTON_X, DEV_BUTTON_Y, null);
		else if(!GamePanel.devOptions && devButtonHover)
			g.drawImage(devOffImgPressed, BUTTON_X, DEV_BUTTON_Y, null);
		else
			g.drawImage(devOffImgDefault, BUTTON_X, DEV_BUTTON_Y, null);
		
	}

	
	@Override
	public void mousePressed(MouseEvent e)
	{
		Point point = e.getPoint();
		
		if(backButton.contains(point))
			gsm.setCurrentState(GameStateManager.MENUSTATE);
		
		if(soundButton.contains(point))
			GamePanel.playMusic = !GamePanel.playMusic;
		
		if(devButton.contains(point))
			GamePanel.devOptions = !GamePanel.devOptions;
	}
	
	public void mouseMoved(MouseEvent e)
	{
		Point point = e.getPoint();
		
		
		if(soundButton.contains(point))
		{
			if(!soundButtonHover)
				Sound.mouseHover.play();
			soundButtonHover = true;
		}
		else
			soundButtonHover = false;
		
		if(devButton.contains(point))
		{
			if(!devButtonHover)
				Sound.mouseHover.play();
			devButtonHover = true;
		}
		else
			devButtonHover = false;
		
	}
	
	//Unused overridden methods
	public void keyPressed(int k){}
	public void keyReleased(int k){}
	public void mouseClicked(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}
	public void mouseDragged(MouseEvent e){}


	

	
}