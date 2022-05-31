package com.badlogic.mygame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class RulesScreen extends ScreenAdapter {
    private GameRunner game;
    private OrthographicCamera camera;
    private TextButton back;
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
        back = new TextButton("Back", backStyle);
        stage.addActor(back);
        img = new Texture("rules.png");
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
