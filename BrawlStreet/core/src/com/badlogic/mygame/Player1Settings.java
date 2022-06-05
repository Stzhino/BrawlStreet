package com.badlogic.mygame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class Player1Settings extends ScreenAdapter {
    private Texture background;
    private TextButton back;
    private TextButton.TextButtonStyle backStyle;
    private TextButton next;
    private TextButton.TextButtonStyle nextStyle;
    private GameRunner game;
    private Skin skin;
    private Stage stage;
    private TextureAtlas textureAtlas;

    public Player1Settings(GameRunner game){
        this.game = game;
        background = new Texture("Player1Settings.png");
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        textureAtlas = new TextureAtlas("BackButton.txt");
        skin = new Skin();
        skin.addRegions(textureAtlas);
        backStyle = new TextButton.TextButtonStyle();
        backStyle.down = skin.getDrawable("backagain");
        backStyle.up = skin.getDrawable("back");
    }
}
