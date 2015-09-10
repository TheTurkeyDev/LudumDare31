package com.turkey.LD31.Entity;

import VolatiliaAPI.entity.Entity;
import VolatiliaAPI.game.GameBase;

import com.turkey.LD31.Resources.Resources;

public class SILives extends Entity
{

	public SILives(GameBase g)
	{
		super(32, 16, g);
		super.setImage(Resources.player_SIMorph7);
	}

}
