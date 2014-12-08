package com.turkey.LD31.Screen;

import java.awt.event.KeyEvent;

import VolatiliaAPI.main.APIMain;
import VolatiliaAPI.screen.Screen;

import com.turkey.LD31.Game.GameMain;

public class GameScreen extends Screen
{
	private GameMain game;
	
	public GameScreen()
	{
		super("Screen");
		game = new GameMain(this);
	}

	public void render()
	{
		game.render();
		this.pixels = game.getPixels();
		super.render();
	}
	
	public void update()
	{
		game.update();
		APIMain.getAPI().setTitle("Game Evolution: " + APIMain.getAPI().getFPS());
		super.update();
	}
	
	public void OnKeyEvent(KeyEvent e, boolean pressed)
	{
		game.OnKeyEvent(e, pressed);
	}
}