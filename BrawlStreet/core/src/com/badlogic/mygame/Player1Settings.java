package com.badlogic.mygame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

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
    private OrthographicCamera camera;
    private Texture player;

    public Player1Settings(GameRunner game){
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1000, 600);
        background = new Texture("Player1Settings.png");
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        textureAtlas = new TextureAtlas("BackButton.txt");
        skin = new Skin();
        skin.addRegions(textureAtlas);
        backStyle = new TextButton.TextButtonStyle();
        backStyle.down = skin.getDrawable("backagain");
        backStyle.up = skin.getDrawable("back");
        backStyle.font = game.font;
        back = new TextButton("", backStyle);
        back.setPosition(Gdx.graphics.getWidth()*.005f, Gdx.graphics.getHeight()*.89f);
        stage.addActor(back);
        player = new Texture("gream/passive1.png");
    }
    public void show(){
        back.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor){
                System.out.println("Back pressed.");
                game.setScreen(new SettingsScreen(game));
            }
        });
    }
    public void render(float delta){
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(background, 0, 0);
        game.batch.end();
        stage.draw();
        game.batch.begin();
        game.batch.draw(player, Gdx.graphics.getWidth()*.15f, Gdx.graphics.getHeight()*.16f);
        game.batch.end();
    }
}
