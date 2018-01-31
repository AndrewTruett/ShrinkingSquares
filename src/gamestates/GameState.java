package gamestates;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.io.IOException;

public abstract class GameState
{
	protected GameStateManager gsm;
	private Font font;
	protected Font UIFont;
	protected Font statFont;
	
	public GameState()
	{
		initFont();
	}
	
	private void initFont()
	{
		try
		{
			font = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/res/font/eras-demi-itc/ERASDEMI.TTF"));
			UIFont = font.deriveFont(Font.PLAIN, 15);
			statFont = font.deriveFont(Font.ITALIC, 30);
		}
		catch(FontFormatException ffe)
		{
			System.err.println("***Error with font***");
			ffe.printStackTrace();
		}
		catch(IOException ioe)
		{
			System.err.println("***Error loading font***");
			ioe.printStackTrace();
		}
	}
	
	protected abstract void init();
	public abstract void update();
	public abstract void draw(Graphics2D g);
	public abstract void mouseClicked(MouseEvent e);
	public abstract void mouseEntered(MouseEvent e);
	public abstract void mouseExited(MouseEvent e);
	public abstract void mousePressed(MouseEvent e);
	public abstract void mouseReleased(MouseEvent e);
	public abstract void mouseDragged(MouseEvent e);
	public abstract void mouseMoved(MouseEvent e);
	
}
