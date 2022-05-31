package com.badlogic.mygame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class RulesScreen extends ScreenAdapter {
    private GameRunner game;
    private OrthographicCamera camera;
    private TextButton backButton;
    private TextButton.TextButtonStyle backStyle;
    private Stage stage;
    private Texture img;

    public RulesScreen(GameRunner game){
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1000, 600);
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        backStyle = new TextButton.TextButtonStyle();
        backStyle.font = game.font;
        backButton = new TextButton("Back", backStyle);
        backButton.setPosition(Gdx.graphics.getWidth()*.1f, Gdx.graphics.getHeight()*.9f);
        stage.addActor(backButton);
        img = new Texture("rules.png");
    }

    public void show(){
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                System.out.println("BackButton Pressed");
                game.setScreen(new TitleScreen(game));
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
        img.dispose();
    }
}
