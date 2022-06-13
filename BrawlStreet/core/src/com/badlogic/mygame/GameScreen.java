package com.badlogic.mygame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class GameScreen extends ScreenAdapter {
    private World world;
    private Box2DDebugRenderer b2dr;
    private GameRunner game;
    private Texture background;
    private OrthographicCamera camera;
    private Gream p1G;

    public GameScreen(GameRunner game){
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1000, 600);
        background = new Texture("fightBG.png");

        // box2d shenanigans
        world = new World(new Vector2(0,-50), true);
        b2dr = new Box2DDebugRenderer();

        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        Rectangle r = new Rectangle(0,0, 1000, 75);
        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set(r.getX()+r.getWidth()/2, r.getY()+r.getHeight()/2);

        body = world.createBody(bdef);

        shape.setAsBox(r.getWidth()/2, r.getHeight()/2);
        fdef.shape = shape;
        body.createFixture(fdef);

        Rectangle r2 = new Rectangle(0, 0, 1, 600);
        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set(r2.getX()+r2.getWidth()/2, r2.getY()+r2.getHeight()/2);
        body = world.createBody(bdef);
        shape.setAsBox(r2.getWidth()/2, r2.getHeight()/2);
        fdef.shape = shape;
        body.createFixture(fdef);

        Rectangle r3 = new Rectangle(999, 0, 1, 600);
        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set(r3.getX()+r3.getWidth()/2, r3.getY()+r3.getHeight()/2);
        body = world.createBody(bdef);
        shape.setAsBox(r3.getWidth()/2, r3.getHeight()/2);
        fdef.shape = shape;
        body.createFixture(fdef);

        p1G = new Gream(world);
    }

    public void handleInput(float delta){
        if(p1G.currentState!=Gream.State.DEAD)
        {
            if(Gdx.input.isKeyJustPressed(Input.Keys.W)){
                p1G.b2body.applyForceToCenter(0, 30000f, true);
            }
            if(Gdx.input.isKeyPressed(Input.Keys.A)){
                p1G.b2body.applyForceToCenter(-100f, 0, true);
            }
            if(Gdx.input.isKeyPressed(Input.Keys.D)){
                p1G.b2body.applyForceToCenter(100f, 0,true);
            }
            if(Gdx.input.isKeyPressed(Input.Keys.T)){
                p1G.setPunch();
            }
            if(Gdx.input.isKeyPressed(Input.Keys.Y)){
                p1G.setKick();
            }
            Gdx.input.setInputProcessor(new InputAdapter(){
                @Override
                public boolean keyDown(int keycode){
                    if(keycode == Input.Keys.S){
                        p1G.setBlock();
                        return true;
                    }
                    return false;
                }
                @Override
                public boolean keyUp(int keycode){
                    if(keycode == Input.Keys.S){
                        p1G.resetBlock();
                        return true;
                    }
                    return false;
                }
            });
            if(Gdx.input.isKeyJustPressed(Input.Keys.U)){
                if(p1G.getSpecialReady()){

                }
                p1G.setSpecial();
            }
        }
    }
    public void update(float delta){
        handleInput(delta);
        world.step(1/60f, 6,2);
        p1G.update(delta);
    }
    public void render(float delta){
        update(delta);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(background, 0, 0);
        p1G.draw(game.batch);
        game.batch.end();
        b2dr.render(world, camera.combined);
    }
}
