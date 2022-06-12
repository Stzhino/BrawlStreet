package com.badlogic.mygame;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
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
    private TextButton.TextButtonStyle buttonStyle2;
    private TextButton.TextButtonStyle buttonStyle3;
    private Stage stage;
    private Skin skin;
    private TextureAtlas textureAtlas;
    private World world;
    private Box2DDebugRenderer b2dr;

    public TitleScreen(GameRunner game){
        this.game = game;
        img = new Texture("Menu.png");
        camera = new OrthographicCamera();
		camera.setToOrtho(false, 1000, 600);
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        textureAtlas = new TextureAtlas("PSR.txt");
        skin = new Skin();
        skin.addRegions(textureAtlas);
        buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.up = skin.getDrawable("play");
        buttonStyle.down = skin.getDrawable("play2");
        buttonStyle.font = game.font;
        buttonStyle2 = new TextButton.TextButtonStyle();
        buttonStyle2.up = skin.getDrawable("settings");
        buttonStyle2.down = skin.getDrawable("settings2");
        buttonStyle2.font = game.font;
        buttonStyle3 = new TextButton.TextButtonStyle();
        buttonStyle3.up = skin.getDrawable("rules");
        buttonStyle3.down = skin.getDrawable("rules2");
        buttonStyle3.font = game.font;
        button = new TextButton("", buttonStyle);
        button.setPosition(Gdx.graphics.getWidth() * .43f, Gdx.graphics.getHeight()*.4f);
        stage.addActor(button);
        button2 = new TextButton("", buttonStyle2);
        button2.setPosition(Gdx.graphics.getWidth() * .345f, Gdx.graphics.getHeight()*.25f);
        stage.addActor(button2);
        button3 = new TextButton("", buttonStyle3);
        button3.setPosition(Gdx.graphics.getWidth() * .41f, Gdx.graphics.getHeight()*.1f);
        stage.addActor(button3);
//        world = new World(new Vector2(0,0), true);
//        b2dr = new Box2DDebugRenderer();
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
                game.setScreen(new SettingsScreen(game));
            }
        });
        button3.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                System.out.println("Button3 Pressed");
                game.setScreen(new RulesScreen(game));
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
        textureAtlas.dispose();
    }
}
