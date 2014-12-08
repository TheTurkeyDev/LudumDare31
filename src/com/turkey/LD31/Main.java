package com.turkey.LD31;

import VolatiliaAPI.main.APIMain;
import VolatiliaAPI.main.Display;
import VolatiliaAPI.screen.ScreenManager;

import com.turkey.LD31.Resources.Resources;
import com.turkey.LD31.Screen.GameScreen;

public class Main
{
	
	public static void main(String[] args)
	{
		APIMain api = new APIMain("Game Evolution");
		api.setDisplay(new Display(800, 600));
		
		loadResource();
		loadScreen();
		
		api.start();
	}

	private static void loadScreen()
	{
		ScreenManager sm = ScreenManager.getInstance();
		
		sm.setOmmitColor(-65316);
		
		GameScreen screen = new GameScreen();
		
		sm.addScreen(screen);
		sm.setCurrentScreen(screen);
		
	}

	private static void loadResource()
	{
		Resources.loadResources();
	}
}
