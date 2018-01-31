package gamestates;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class GameStateManager
{
	private ArrayList<GameState> gameStates;
	private int currentState;
	
	public static final int MENUSTATE = 0;
	public static final int PLAYSTATE = 1;
	public static final int STATSTATE = 2;
	public static final int OPTIONSTATE = 3;
	public static final int ENDSTATE = 4;
	
	
	public GameStateManager()
	{
		gameStates = new ArrayList<GameState>();
		
		gameStates.add(new MenuState(this));
		gameStates.add(new PlayState(this));
		gameStates.add(new StatState(this));
		gameStates.add(new OptionState(this));
		gameStates.add(new EndState(this));
		
		currentState = MENUSTATE;
	}
	
	public void setCurrentState(int i)
	{
		assert(i>=0);
		currentState = i;
	}
	
	public GameState getCurrentState()
	{
		return gameStates.get(currentState);
	}
	
	public GameState getState(int index)
	{
		assert(index >= 0 && index < gameStates.size());
		return gameStates.get(index);
	}
	
	public void update()
	{
		gameStates.get(currentState).update();
	}
	
	public void draw(Graphics2D g)
	{
		gameStates.get(currentState).draw(g);
	}
	
	public void mousePressed(MouseEvent e)
	{
		gameStates.get(currentState).mousePressed(e);
	}
	
	//Unused overridden methods
	public void mouseClicked(MouseEvent e){gameStates.get(currentState).mouseClicked(e);}
	public void mouseEntered(MouseEvent e){gameStates.get(currentState).mouseEntered(e);}
	public void mouseExited(MouseEvent e){gameStates.get(currentState).mouseExited(e);}
	public void mouseReleased(MouseEvent e){gameStates.get(currentState).mouseReleased(e);}
	public void mouseDragged(MouseEvent e){gameStates.get(currentState).mouseDragged(e);}
	public void mouseMoved(MouseEvent e){gameStates.get(currentState).mouseMoved(e);}
}
