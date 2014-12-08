package com.turkey.LD31.Game;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import VolatiliaAPI.game.Game;
import VolatiliaAPI.graphics.Font;
import VolatiliaAPI.graphics.ImageSheet;
import VolatiliaAPI.graphics.Text;
import VolatiliaAPI.graphics.basic.BasicObject;
import VolatiliaAPI.main.APIMain;
import VolatiliaAPI.screen.Screen;

import com.turkey.LD31.Entity.Player;
import com.turkey.LD31.Game.objects.Brick;

public class GameMain extends Game
{
	public Player player;

	private GameState state;

	private PongGame pong;
	private BreakoutGame breakout;
	private SpaceInvadersGame spaceInvaders;

	public Screen screen;
	
	public boolean paused = false;
	private Text pausedText;
	private Font font;


	public GameMain(Screen s)
	{
		super(APIMain.width, APIMain.height);

		state = GameState.Pong;

		screen = s;
		
		font = new Font(new ImageSheet(PongGame.class, "/Font/LargeFont.png", 640, 240), 40);

		pausedText = new Text(font.getStringImage("PAUSED"), 300, 250);
		pausedText.hide();
		this.screen.addText(pausedText);

		pong = new PongGame(this);
		pong.loadPongBoard();
		breakout = new BreakoutGame(this);
		spaceInvaders = new SpaceInvadersGame(this);

		player = new Player(this);
		super.addEntity(player);
		
		//nextGame();
	}

	public void render()
	{
		if(paused)
			return;
		clear();
		switch(state)
		{
			case Breakout:
				//breakout.render();
				break;
			case Pong:
				pong.render();
				break;
			case SpaceInvaders:
				break;
			default:
				break;

		}
		super.render();
	}

	public void update()
	{
		if(paused)
			return;
		switch(state)
		{
			case Breakout:
				breakout.update();
				break;
			case Pong:
				pong.upadate();
				break;
			case SpaceInvaders:
				spaceInvaders.update();
				break;
			default:
				break;

		}
		super.update();
	}
	
	public void pause()
	{
		paused=!paused;
		if(paused)
			pausedText.show();
		else
			pausedText.hide();
	}

	public void clear()
	{
		for(int x = 0; x < getWidth(); x++)
		{
			for(int y = 0; y < getHeight(); y++)
			{
				pixels[getWidth() * y + x] = 0x000000;
			}
		}
	}
	
	public void OnKeyEvent(KeyEvent e, boolean pressed)
	{
		player.OnKeyEvent(e, pressed);
	}

	public void nextGame()
	{
		super.clearBasicObjects();
		switch(state)
		{
			case Breakout:
				state = GameState.SpaceInvaders;
				player.setSpeed(2);
				player.MorphIntoSI();
				spaceInvaders.loadSpaceInvaders();
				break;
			case Pong:
				player.setSpeed(3);
				state = GameState.Breakout;
				breakout.loadBreakout();
				break;
			case SpaceInvaders:
				break;
			default:
				break;

		}
	}

	public GameState getGameState()
	{
		return state;
	}

	public boolean isWall(int x, int y)
	{
		switch(state)
		{
			case Breakout:
				if(x < 205 || x + 10 > 600 || y < 50)
					return true;
				for(BasicObject obj: super.getBasicObjects())
				{
					if(obj instanceof Brick)
					{
						Brick b = (Brick) obj;
						Rectangle ball = new Rectangle(x,y,10,10);
						Rectangle brick = new Rectangle(obj.getX(),obj.getY(),obj.getWidth(),obj.getHeight());
						if(ball.intersects(brick) && b.canInteract())
						{	
							b.hit();
							return true;
						}
					}
				}
				return false;
			case Pong:
				if(x < 205 || x > 600)
					return true;
				return false;
			case SpaceInvaders:
				if(x < 205 || x > 600)
					return true;
				return false;
			default:
				return false;

		}
	}
	
	public void restart()
	{
		super.clearBasicObjects();
		if(state.equals(GameState.SpaceInvaders))
			player.MorphBackPong();
		state = GameState.Pong;
		pong.loadPongBoard();
	}


	public enum GameState{
		Pong,
		Breakout,
		SpaceInvaders;
	}
}
