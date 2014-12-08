package com.turkey.LD31.Entity;

import java.awt.Rectangle;

import VolatiliaAPI.entity.Entity;

import com.turkey.LD31.Game.GameMain;
import com.turkey.LD31.Game.SpaceInvadersGame;
import com.turkey.LD31.Resources.Resources;

public class AlienLaser extends Entity
{
	public int speed = 5;
	private boolean move = false;
	
	private GameMain gm;
	private SpaceInvadersGame sigame;
	
	public AlienLaser(GameMain g, SpaceInvadersGame sig)
	{
		super(Resources.alienLaser1, 32, g);
		gm = g;
		sigame = sig;
		isAlive = true;
	}
	
	public void update()
	{
		if(!move)
			return;
		y+=speed;
		
		if(y > 600)
		{
			move = false;
			isAlive = false;
		}
		
		for(Entity ent : gm.getEntities())
		{
			if(ent instanceof Player)
			{
				Rectangle pr = new Rectangle(ent.getLocation().getX(), ent.getLocation().getY(), ent.getWidth(), ent.getHeight());
				Rectangle lr = new Rectangle(x+15,y-7,width - 30,height - 24);
				if(pr.intersects(lr))
				{
					isAlive = false;
					sigame.playerHit();
					return;
				}
			}
			else if(ent instanceof Barrier)
			{
				Rectangle br = new Rectangle(ent.getLocation().getX() + 1 , ent.getLocation().getY() - 5, ent.getWidth() - 2, ent.getHeight() - 10);
				Rectangle lr = new Rectangle(x+15,y-7,width - 30,height - 24);
				if(br.intersects(lr))
				{
					isAlive = false;
					((Barrier)ent).hit();
					return;
				}
			}
		}
	}
	
	public void fire()
	{
		move = true;
	}
}
