package com.badlogic.mygame;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class TitleScreen extends ScreenAdapter {
    private GameRunner game;
    private OrthographicCamera camera;
    private Texture img;
    private TextButton button;
    private TextButton button2;
    private TextButton button3;
    private TextButton.TextButtonStyle buttonStyle;
    private Stage stage;
    private Skin skin;
    private TextureAtlas textureAtlas;

    public TitleScreen(GameRunner game){
        this.game = game;
        img = new Texture("Menu.png");
        camera = new OrthographicCamera();
		camera.setToOrtho(false, 1000, 600);
        stage = new Stage();
        buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = game.font;
        button = new TextButton("Play", buttonStyle);
        button.setPosition(Gdx.graphics.getWidth() * .45f, Gdx.graphics.getHeight() * .45f);
        stage.addActor(button);
        button2 = new TextButton("Settings", buttonStyle);
        button2.setPosition(Gdx.graphics.getWidth() * .45f, Gdx.graphics.getHeight() * .3f);
        stage.addActor(button2);
        button3 = new TextButton("Rules", buttonStyle);
        button3.setPosition(Gdx.graphics.getWidth() * .45f, Gdx.graphics.getHeight() * .15f);
        stage.addActor(button3);
    }

    public void show(){
        button.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                System.out.println("Button Pressed");
            }
        });
        button2.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                System.out.println("Button2 Pressed");
            }
        });
        button3.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                System.out.println("Button3 Pressed");
            }
        });
    }

    public void render(float delta){
        camera.update();
		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();
		game.batch.draw(img,0,0, camera.viewportWidth, camera.viewportHeight);
		game.batch.end();
        stage.draw();
    }

    public void hide(){
        Gdx.input.setInputProcessor(null);
        img.dispose();
    }
}
