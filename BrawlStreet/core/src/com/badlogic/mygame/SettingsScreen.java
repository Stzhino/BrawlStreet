package com.badlogic.mygame;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;

public class SettingsScreen extends ScreenAdapter {
    private GameRunner game;
    private Texture bg;
    public SettingsScreen(GameRunner game){
        this.game = game;
    }
}
