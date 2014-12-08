package com.turkey.LD31.Entity;

import java.awt.event.KeyEvent;

import VolatiliaAPI.entity.Entity;
import VolatiliaAPI.graphics.Image;
import VolatiliaAPI.listeners.KeyListener;
import VolatiliaAPI.util.Location;

import com.turkey.LD31.Game.GameMain;
import com.turkey.LD31.Game.GameMain.GameState;
import com.turkey.LD31.Resources.Resources;

public class Player extends Entity
{
	private GameMain gm;

	private int speed = 2;

	private String morph = "";
	private int tick = 0;
	private int morphstate = 0;

	private boolean dotick = false;

	private Image[] SIImages = new Image[7];

	private Laser l;

	public Player(GameMain game)
	{
		super(Resources.player_Pong, 32, 16, game);
		gm = game;

		this.y = 526;
		this.x = 400;

		SIImages[0] = Resources.player_SIMorph1;
		SIImages[1] = Resources.player_SIMorph2;
		SIImages[2] = Resources.player_SIMorph3;
		SIImages[3] = Resources.player_SIMorph4;
		SIImages[4] = Resources.player_SIMorph5;
		SIImages[5] = Resources.player_SIMorph6;
		SIImages[6] = Resources.player_SIMorph7;
	}

	public void render()
	{

	}

	public void update()
	{
		/*if(KeyListener.isKeyPressed(KeyEvent.VK_W))
		{
			if(gm.getGameState().equals(GameState.))
				y--;
		}*/
		if(KeyListener.isKeyPressed(KeyEvent.VK_A))
		{
			if(!gm.isWall(x, y))
				x-=speed;
		}
		/*if(KeyListener.isKeyPressed(KeyEvent.VK_S))
		{
			y++;
		}*/
		if(KeyListener.isKeyPressed(KeyEvent.VK_D))
		{
			if(!gm.isWall(x+32, y))
				x+=speed;
		}

		if(dotick)
		{
			tick++;
			if(tick%20 == 0)
			{
				morphstate++;
				tick = 0;
			}
		}


		if(morph.equalsIgnoreCase("SpaceInvader"))
		{
			if(morphstate < 7)
				this.image = SIImages[morphstate];
			else
			{
				morph = "";
				dotick = false;
				tick = 0;
				morphstate = 0;
			}
		}
		else if(morph.equalsIgnoreCase("Pong"))
		{
			if(morphstate < 7)
				this.image = SIImages[6-morphstate];
			else
			{
				morph = "";
				dotick = false;
				tick = 0;
				morphstate = 0;
			}
		}
	}

	public void OnKeyEvent(KeyEvent e, boolean pressed)
	{
		if(pressed)
		{
			if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
			{
				gm.pause();
			}
			if(e.getKeyCode() ==  KeyEvent.VK_SPACE)
			{
				if(gm.getGameState().equals(GameState.SpaceInvaders))
				{
					if(l == null || !l.isAlive())
					{
						l = new Laser(gm);
						l.setLocation(new Location(x,y-32));
						l.fire();
						gm.addEntity(l);
					}
				}
			}
		}
	}

	public void MorphIntoSI()
	{
		morph = "SpaceInvader";
		dotick = true;
	}
	
	public void MorphBackPong()
	{
		morph = "Pong";
		dotick = true;
	}

	public void setSpeed(int s)
	{
		speed = s;
	}
}
