package com.badlogic.mygame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class SettingsScreen extends ScreenAdapter {
    private GameRunner game;
    private OrthographicCamera camera;
    private Texture musicScreen;
    private Stage stage;
    private TextButton soundUp;
    private TextButton soundDown;
    private TextButton backButton;
    private TextButton.TextButtonStyle up;
    private TextButton.TextButtonStyle down;
    private TextButton.TextButtonStyle backStyle;
    public SettingsScreen(GameRunner game){
        this.game = game;
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

    }

    public void render(float delta){
        game.batch.begin();
    }
}
