package com.badlogic.mygame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameRunner extends Game {
	SpriteBatch batch;
	BitmapFont font;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont();
		font.getData().setScale(2f);
		font.setColor(255,178,0f, 1);
		setScreen(new TitleScreen(this));
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
