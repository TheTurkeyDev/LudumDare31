package com.turkey.LD31.Entity;

import java.util.Random;

import VolatiliaAPI.entity.Entity;
import VolatiliaAPI.graphics.Image;
import VolatiliaAPI.util.Location;

import com.turkey.LD31.Game.GameMain;
import com.turkey.LD31.Game.SpaceInvadersGame;

public class SIAlien extends Entity
{
	private AlienLaser l;

	private GameMain gm;
	private SpaceInvadersGame sigame;

	private Random r;

	private int delay = 0;

	private int correctX, correctY;
	private int speed = 2;
	private int xVel = 0;
	private int yVel = 0;

	public boolean moving = false;
	public boolean movingOn = false;

	public SIAlien(GameMain game, Image img, SpaceInvadersGame si, Location loc)
	{
		super(img, 32, game);
		gm = game;
		sigame = si;
		r = new Random();
		correctX = loc.getX();
		correctY = loc.getY();
	}

	public void fire()
	{
		l = new AlienLaser(gm, sigame);
		l.setLocation(new Location(x,y-32));
		l.fire();
		gm.addEntity(l);
	}

	public void update()
	{
		if(!moving)
		{
			delay++;
			if(delay%60 == 0)
			{
				int rand = r.nextInt(100);

				if(rand == 0)
					fire();
			}
		}
		else
		{
			if(!movingOn && (x+xVel < 0 || y+yVel <= 0 || x+xVel+width > 800 || y+yVel+height >= 600))
				moving = false;
			else
			{
				x+=xVel;
				y+=yVel;
			}

			if(movingOn && (x == correctX && y >= correctY))
				moving = false;
		}
	}

	public void moveOffScreen(int dir)
	{
		moving = true;
		movingOn = false;

		if(dir == 1)
			yVel = speed;
		else if(dir == 2)
			xVel = speed;
		else if(dir == 3)
			yVel = -speed;
		else if(dir == 4)
			xVel = -speed;
	}

	public void moveOnScreen(int dir)
	{
		moving = true;
		movingOn = true;

		if(dir == 1)
			yVel = speed;
		else if(dir == 2)
			xVel = speed;
		else if(dir == 3)
			yVel = -speed;
		else if(dir == 4)
			xVel = -speed;
	}
}
