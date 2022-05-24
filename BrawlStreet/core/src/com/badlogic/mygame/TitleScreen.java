package com.badlogic.mygame;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class TitleScreen extends ScreenAdapter {
    private GameRunner game;
    private OrthographicCamera camera;
    private Texture img;

    public TitleScreen(GameRunner game){
        this.game = game;
        img = new Texture("Menu.png");
        camera = new OrthographicCamera();
		camera.setToOrtho(false, 1000, 600);
    }

    public void show(){
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                if (screenX < 100) {

                }
                return true;
            }
        });
    }

    public void render(float delta){
        camera.update();
		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();
		game.batch.draw(img,0,0, camera.viewportWidth, camera.viewportHeight);
        game.font.draw(game.batch, "Play", Gdx.graphics.getWidth() * .5f, Gdx.graphics.getHeight() * .5f);
        game.font.draw(game.batch, "Settings", Gdx.graphics.getWidth() * .5f, Gdx.graphics.getHeight() * .35f);
        game.font.draw(game.batch, "Rules", Gdx.graphics.getWidth() * .5f, Gdx.graphics.getHeight() * .2f);
		game.batch.end();
    }

    public void hide(){
        Gdx.input.setInputProcessor(null);
        img.dispose();
    }
}
