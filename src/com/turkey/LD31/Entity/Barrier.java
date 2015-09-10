package com.turkey.LD31.Entity;

import VolatiliaAPI.entity.Entity;

import com.turkey.LD31.Game.GameMain;
import com.turkey.LD31.Resources.Resources;

public class Barrier extends Entity
{

	@SuppressWarnings("unused")
	private GameMain gm;
	
	private int damage = 5;
	
	public Barrier(GameMain g)
	{
		super(32, g);
		super.setImage(Resources.barrier1);
		gm = g;
	}
	
	public void update()
	{
		
	}
	
	public void hit()
	{
		damage--;
		if(damage == 4)
			image = Resources.barrier2;
		else if(damage == 3)
			image = Resources.barrier3;
		else if(damage == 2)
			image = Resources.barrier4;
		else if(damage == 1)
			image = Resources.barrier5;
		else if(damage == 0)
			isAlive = false;
	}

}
