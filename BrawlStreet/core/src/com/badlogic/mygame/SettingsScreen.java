package com.badlogic.mygame;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class SettingsScreen extends ScreenAdapter {
    private GameRunner game;
    private Texture musicScreen;
    private Stage stage;
    private TextButton soundUp;
    private TextButton soundDown;
    private TextButton.TextButtonStyle up;
    private TextButton.TextButtonStyle down;
    public SettingsScreen(GameRunner game){
        this.game = game;
    }

    public void render(float delta){
        game.batch.begin();
    }
}
