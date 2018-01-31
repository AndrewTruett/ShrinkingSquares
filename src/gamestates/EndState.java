package gamestates;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import imgscalr.Scalr;
import main.GamePanel;

public class EndState extends PlayState
{
	private int finalScore;
	
	private final int GAME_OVER_IMG_WIDTH = 200;
	private final int GAME_OVER_IMG_HEIGHT = 150;
	private final int GAME_OVER_IMG_X = (GamePanel.WIDTH/2) - (GAME_OVER_IMG_WIDTH/2);
	private final int GAME_OVER_IMG_Y = 50;
	
	private BufferedImage gameOverImg;
	
	public EndState(GameStateManager gsm)
	{
		super(gsm);
		this.gsm = gsm;
		init();
	}
	
	protected void init()
	{
		finalScore = 0;
		
		try
		{
			gameOverImg = ImageIO.read(getClass().getResourceAsStream("/res/imgs/game_over.png"));
			gameOverImg= Scalr.resize(gameOverImg, Scalr.Method.QUALITY, Scalr.Mode.FIT_TO_WIDTH, GAME_OVER_IMG_WIDTH, GAME_OVER_IMG_HEIGHT, Scalr.OP_ANTIALIAS);
		}
		catch(IOException e)
		{
			System.err.print("***Error Reading Image***");
			e.printStackTrace();
		}
	}
	
	public void update(){}//do nothing

	@Override
	public void draw(Graphics2D g)
	{
		g.setColor(Color.BLACK);
		g.drawRect(150, 150, 100, 25);
		g.setColor(Color.WHITE);
		
		g.drawImage(gameOverImg, GAME_OVER_IMG_X, GAME_OVER_IMG_Y, null);
		
		
		g.setFont(UIFont);
		g.drawString("Game Over!", 150, 150);
		g.drawString("Final Score: "+finalScore, 150, 160);
		
	}
	
	@Override
	public void mousePressed(MouseEvent e)
	{
		//super.init();
		gsm.setCurrentState(GameStateManager.MENUSTATE);
	}
	
	//Getters and Setters
	public int getFinalScore()
	{
		return finalScore;
	}

	public void setFinalScore(int finalScore)
	{
		this.finalScore = finalScore;
	}
	//End Getters and Setters

	//Unused overridden methods
	public void keyPressed(int k){}
	public void keyReleased(int k){}
	public void mouseClicked(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}
}
