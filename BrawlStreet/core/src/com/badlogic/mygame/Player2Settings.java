package com.badlogic.mygame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class Player2Settings extends ScreenAdapter {
    private GameRunner game;
    private Stage stage;
    private OrthographicCamera camera;
    private Texture background;
    private TextButton back;
    private TextButton.TextButtonStyle backStyle;
    private Texture player;
    private TextureAtlas backAtlas;
    private Skin skin;
    public Player2Settings(GameRunner game){
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1000, 600);
        background = new Texture("player2Settings.png");
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        backAtlas = new TextureAtlas("backButton.txt");
        skin = new Skin();
        skin.addRegions(backAtlas);
        backStyle = new TextButton.TextButtonStyle();
        backStyle.up = skin.getDrawable("backagain");
        backStyle.down = skin.getDrawable("back");
        backStyle.font = game.font;
        back = new TextButton("", backStyle);
        back.setPosition(Gdx.graphics.getWidth()*.005f, Gdx.graphics.getHeight()*.89f);
        stage.addActor(back);
        player = new Texture("bream/passive1p2.png");
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
        game.batch.draw(player, Gdx.graphics.getWidth()*.17f, Gdx.graphics.getHeight()*.17f);
        game.batch.end();
    }
}
