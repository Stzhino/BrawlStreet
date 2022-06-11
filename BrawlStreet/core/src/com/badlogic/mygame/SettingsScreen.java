package com.badlogic.mygame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class SettingsScreen extends ScreenAdapter {
    private GameRunner game;
    private OrthographicCamera camera;
    private Texture musicScreen;
    private Stage stage;
    private TextButton soundUp;
    private TextButton soundDown;
    private TextButton backButton;
    private TextButton player1;
    private TextButton player2;
    private TextButton.TextButtonStyle up;
    private TextButton.TextButtonStyle down;
    private TextButton.TextButtonStyle backStyle;
    private TextButton.TextButtonStyle player1Style;
    private TextButton.TextButtonStyle player2Style;
    private TextureAtlas SoundAtlas;
    private TextureAtlas backAtlas;
    private Skin skin;
    private ShapeRenderer shapRend;
    private int volumeBar;


    public SettingsScreen(GameRunner game){
        this.game = game;
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1000, 600);
        backAtlas = new TextureAtlas("BackButton.txt");
        SoundAtlas = new TextureAtlas("musicButtons.txt");
        skin = new Skin();
        skin.addRegions(backAtlas);
        skin.addRegions(SoundAtlas);
        musicScreen = new Texture("musicVolume.png");
        shapRend = new ShapeRenderer();
        volumeBar = 550;
        // backButton
        backStyle = new TextButton.TextButtonStyle();
        backStyle.up = skin.getDrawable("backagain");
        backStyle.down = skin.getDrawable("back");
        backStyle.font = game.font;
        backButton = new TextButton("", backStyle);
        backButton.setPosition(Gdx.graphics.getWidth()*.005f, Gdx.graphics.getHeight()*.89f);
        stage.addActor(backButton);
        // sound up button
        up = new TextButton.TextButtonStyle();
        up.up = skin.getDrawable("plus1");
        up.down = skin.getDrawable("plus2");
        up.font = game.font;
        soundUp = new TextButton("", up);
        soundUp.setPosition(Gdx.graphics.getWidth()*.7f, Gdx.graphics.getHeight()*.5f);
        stage.addActor(soundUp);
        // sound down button
        down = new TextButton.TextButtonStyle();
        down.up = skin.getDrawable("minus1");
        down.down = skin.getDrawable("minus2");
        down.font = game.font;
        soundDown = new TextButton("", down);
        soundDown.setPosition(Gdx.graphics.getWidth()*.8f, Gdx.graphics.getHeight()*.5f);
        stage.addActor(soundDown);
    }
    public void show(){
        backButton.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor){
                System.out.println("BackButton pressed");
                game.setScreen(new TitleScreen(game));
            }
        });
        soundUp.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor){
                System.out.println("volume increased");
                if(GameRunner.getVolume()!=10)
                {
                    GameRunner.increase();
                }
            }
        });
        soundDown.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor){
                System.out.println("volume decreased");
                if(GameRunner.getVolume()!=0)
                {
                    GameRunner.decrease();
                }
            }
        });
    }
    public void render(float delta){
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(musicScreen, 0, 0);
        game.batch.end();
        stage.draw();
        shapRend.begin(ShapeRenderer.ShapeType.Filled);
        shapRend.setColor(Color.WHITE);
        shapRend.rect(Gdx.graphics.getWidth()*.1f, Gdx.graphics.getHeight()*.53f, volumeBar, 50);
        shapRend.rect(Gdx.graphics.getWidth()*.1f, Gdx.graphics.getHeight()*.53f, (float) (volumeBar*GameRunner.getVolume()), 50, Color.RED, Color.RED, Color.BLUE, Color.BLUE);
        shapRend.end();
    }
}
