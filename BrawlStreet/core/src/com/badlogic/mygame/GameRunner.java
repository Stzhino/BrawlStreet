package com.badlogic.mygame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameRunner extends Game {
	protected SpriteBatch batch;
	protected BitmapFont font;
	private static double volume = 5;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont();
		font.getData().setScale(2f);
		font.setColor(Color.BLACK);
		setScreen(new TitleScreen(this));
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
	public static void increase(){
		volume++;
	}
	public static void decrease(){
		volume--;
	}
	public static double getVolume(){
		return volume/10;
	}
}
