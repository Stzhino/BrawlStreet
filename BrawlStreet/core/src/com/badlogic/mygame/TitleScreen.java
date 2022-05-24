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
    private SpriteBatch batch;
    private Texture img;

    public TitleScreen(GameRunner game){
        this.game = game;
        img = new Texture("Menu.png");
        camera = new OrthographicCamera();
		camera.setToOrtho(false, 1000, 600);
    }

    public void show(){
//        Gdx.input.setInputProcessor(new InputAdapter(){
//            @Override
//            public boolean touchDown(int screenX, int screenY, int pointer, int button){
//            }
//        }
    }

    public void render(){
        ScreenUtils.clear(1, 0, 0, 1);
        camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(img,0,0, camera.viewportWidth, camera.viewportHeight);
		batch.end();
    }

    public void hide(){
        Gdx.input.setInputProcessor(null);
    }

    public void dispose(){
        batch.dispose();
        img.dispose();
    }
}
