package com.badlogic.mygame;

import com.badlogic.gdx.*;
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
    protected static Gream p1G;
    protected static Bream p2B;
    protected static AmongUsGreen aug;
    protected static AmongUsBlue aub;
    protected static HorseBlue hb;
    protected static HorseGreen hg;
    private InputAdapter greamI;
    private InputAdapter breamI;
    private InputAdapter amongUsGreenI;
    private InputAdapter amongUsBlueI;
    private InputAdapter horseGreen;
    private InputAdapter horseBlue;

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

        if(SelectionScreen.playerChoice.get(0).equals("gream")) {
            p1G = new Gream(world);
        }
        else if (SelectionScreen.playerChoice.get(0).equals("amongusGreen")){
            aug = new AmongUsGreen(world);
        }
        else if (SelectionScreen.playerChoice.get(0).equals("horseGreen")){
            hg = new HorseGreen(world);
        }

        if(SelectionScreen.player2Choice.get(0).equals("bream")) {
            p2B = new Bream(world);
        }
        else if (SelectionScreen.player2Choice.get(0).equals("amongusBlue")) {
            aub = new AmongUsBlue(world);
        }
        else if (SelectionScreen.player2Choice.get(0).equals("horseBlue")){
            hb = new HorseBlue(world);
        }

        InputMultiplexer inputMulti = new InputMultiplexer();
        if(SelectionScreen.playerChoice.get(0).equals("gream") && SelectionScreen.player2Choice.get(0).equals("bream")){
            greamI = new InputAdapter(){
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
            };
            breamI = new InputAdapter(){
                @Override
                public boolean keyDown(int keycode){
                    if(keycode == Input.Keys.DOWN){
                        p2B.setBlock();
                        return true;
                    }
                    return false;
                }
                @Override
                public boolean keyUp(int keycode){
                    if(keycode == Input.Keys.DOWN){
                        p2B.resetBlock();
                        return true;
                    }
                    return false;
                }
            };
            inputMulti.addProcessor(breamI);
            inputMulti.addProcessor(greamI);
        }
        if(SelectionScreen.playerChoice.get(0).equals("gream") && SelectionScreen.player2Choice.get(0).equals("amongusBlue")){
            greamI = new InputAdapter(){
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
            };
            amongUsBlueI = new InputAdapter(){
                @Override
                public boolean keyDown(int keycode){
                    if(keycode == Input.Keys.DOWN){
                        aub.setBlock();
                        return true;
                    }
                    return false;
                }
                @Override
                public boolean keyUp(int keycode){
                    if(keycode == Input.Keys.DOWN){
                        aub.resetBlock();
                        return true;
                    }
                    return false;
                }
            };
            inputMulti.addProcessor(amongUsBlueI);
            inputMulti.addProcessor(greamI);
        }
        if(SelectionScreen.playerChoice.get(0).equals("gream") && SelectionScreen.player2Choice.get(0).equals("horseBlue")){
            greamI = new InputAdapter(){
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
            };
            horseBlue = new InputAdapter(){
                @Override
                public boolean keyDown(int keycode){
                    if(keycode == Input.Keys.DOWN){
                        hb.setBlock();
                        return true;
                    }
                    return false;
                }
                @Override
                public boolean keyUp(int keycode){
                    if(keycode == Input.Keys.DOWN){
                        hb.resetBlock();
                        return true;
                    }
                    return false;
                }
            };
            inputMulti.addProcessor(horseBlue);
            inputMulti.addProcessor(greamI);
        }
        if(SelectionScreen.playerChoice.get(0).equals("amongusGreen") && SelectionScreen.player2Choice.get(0).equals("bream")){
            amongUsGreenI = new InputAdapter(){
                @Override
                public boolean keyDown(int keycode){
                    if(keycode == Input.Keys.S){
                        aug.setBlock();
                        return true;
                    }
                    return false;
                }
                @Override
                public boolean keyUp(int keycode){
                    if(keycode == Input.Keys.S){
                        aug.resetBlock();
                        return true;
                    }
                    return false;
                }
            };
            breamI = new InputAdapter(){
                @Override
                public boolean keyDown(int keycode){
                    if(keycode == Input.Keys.DOWN){
                        p2B.setBlock();
                        return true;
                    }
                    return false;
                }
                @Override
                public boolean keyUp(int keycode){
                    if(keycode == Input.Keys.DOWN){
                        p2B.resetBlock();
                        return true;
                    }
                    return false;
                }
            };
            inputMulti.addProcessor(breamI);
            inputMulti.addProcessor(amongUsGreenI);
        }
        if(SelectionScreen.playerChoice.get(0).equals("amongusGreen") && SelectionScreen.player2Choice.get(0).equals("amongusBlue")){
            amongUsGreenI = new InputAdapter(){
                @Override
                public boolean keyDown(int keycode){
                    if(keycode == Input.Keys.S){
                        aug.setBlock();
                        return true;
                    }
                    return false;
                }
                @Override
                public boolean keyUp(int keycode){
                    if(keycode == Input.Keys.S){
                        aug.resetBlock();
                        return true;
                    }
                    return false;
                }
            };
            amongUsBlueI = new InputAdapter(){
                @Override
                public boolean keyDown(int keycode){
                    if(keycode == Input.Keys.DOWN){
                        aub.setBlock();
                        return true;
                    }
                    return false;
                }
                @Override
                public boolean keyUp(int keycode){
                    if(keycode == Input.Keys.DOWN){
                        aub.resetBlock();
                        return true;
                    }
                    return false;
                }
            };
            inputMulti.addProcessor(amongUsBlueI);
            inputMulti.addProcessor(amongUsGreenI);
        }
        if(SelectionScreen.playerChoice.get(0).equals("amongusGreen") && SelectionScreen.player2Choice.get(0).equals("horseBlue")){
            amongUsGreenI = new InputAdapter(){
                @Override
                public boolean keyDown(int keycode){
                    if(keycode == Input.Keys.S){
                        aug.setBlock();
                        return true;
                    }
                    return false;
                }
                @Override
                public boolean keyUp(int keycode){
                    if(keycode == Input.Keys.S){
                        aug.resetBlock();
                        return true;
                    }
                    return false;
                }
            };
            horseBlue = new InputAdapter(){
                @Override
                public boolean keyDown(int keycode){
                    if(keycode == Input.Keys.DOWN){
                        hb.setBlock();
                        return true;
                    }
                    return false;
                }
                @Override
                public boolean keyUp(int keycode){
                    if(keycode == Input.Keys.DOWN){
                        hb.resetBlock();
                        return true;
                    }
                    return false;
                }
            };
            inputMulti.addProcessor(horseBlue);
            inputMulti.addProcessor(amongUsGreenI);
        }
        if(SelectionScreen.playerChoice.get(0).equals("horseGreen") && SelectionScreen.player2Choice.get(0).equals("bream")){
            horseGreen = new InputAdapter(){
                @Override
                public boolean keyDown(int keycode){
                    if(keycode == Input.Keys.S){
                        hg.setBlock();
                        return true;
                    }
                    return false;
                }
                @Override
                public boolean keyUp(int keycode){
                    if(keycode == Input.Keys.S){
                        hg.resetBlock();
                        return true;
                    }
                    return false;
                }
            };
            breamI = new InputAdapter(){
                @Override
                public boolean keyDown(int keycode){
                    if(keycode == Input.Keys.DOWN){
                        p2B.setBlock();
                        return true;
                    }
                    return false;
                }
                @Override
                public boolean keyUp(int keycode){
                    if(keycode == Input.Keys.DOWN){
                        p2B.resetBlock();
                        return true;
                    }
                    return false;
                }
            };
            inputMulti.addProcessor(breamI);
            inputMulti.addProcessor(horseGreen);
        }
        if(SelectionScreen.playerChoice.get(0).equals("horseGreen") && SelectionScreen.player2Choice.get(0).equals("amongusBlue")){
            horseGreen = new InputAdapter(){
                @Override
                public boolean keyDown(int keycode){
                    if(keycode == Input.Keys.S){
                        hg.setBlock();
                        return true;
                    }
                    return false;
                }
                @Override
                public boolean keyUp(int keycode){
                    if(keycode == Input.Keys.S){
                        hg.resetBlock();
                        return true;
                    }
                    return false;
                }
            };
            amongUsBlueI = new InputAdapter(){
                @Override
                public boolean keyDown(int keycode){
                    if(keycode == Input.Keys.DOWN){
                        aub.setBlock();
                        return true;
                    }
                    return false;
                }
                @Override
                public boolean keyUp(int keycode){
                    if(keycode == Input.Keys.DOWN){
                        aub.resetBlock();
                        return true;
                    }
                    return false;
                }
            };
            inputMulti.addProcessor(amongUsBlueI);
            inputMulti.addProcessor(horseGreen);
        }
        if(SelectionScreen.playerChoice.get(0).equals("horseGreen") && SelectionScreen.player2Choice.get(0).equals("horseBlue")){
            horseGreen = new InputAdapter(){
                @Override
                public boolean keyDown(int keycode){
                    if(keycode == Input.Keys.S){
                        hg.setBlock();
                        return true;
                    }
                    return false;
                }
                @Override
                public boolean keyUp(int keycode){
                    if(keycode == Input.Keys.S){
                        hg.resetBlock();
                        return true;
                    }
                    return false;
                }
            };
            horseBlue = new InputAdapter(){
                @Override
                public boolean keyDown(int keycode){
                    if(keycode == Input.Keys.DOWN){
                        hb.setBlock();
                        return true;
                    }
                    return false;
                }
                @Override
                public boolean keyUp(int keycode){
                    if(keycode == Input.Keys.DOWN){
                        hb.resetBlock();
                        return true;
                    }
                    return false;
                }
            };
            inputMulti.addProcessor(horseBlue);
            inputMulti.addProcessor(horseGreen);
        }

        Gdx.input.setInputProcessor(inputMulti);
    }

    public void handleInput(float delta){
        if(SelectionScreen.playerChoice.get(0).equals("gream") && p1G.currentState!=Gream.State.DEAD) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
                p1G.b2body.applyForceToCenter(0, 30000f, true);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                p1G.b2body.applyForceToCenter(-100f, 0, true);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                p1G.b2body.applyForceToCenter(100f, 0, true);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.T)) {
                p1G.setPunch();
            }
            if (Gdx.input.isKeyPressed(Input.Keys.Y)) {
                p1G.setKick();
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.U)){
                if(p1G.getSpecialReady()){
                    p1G.setSpecial();
                }
            }
        }
        if(SelectionScreen.playerChoice.get(0).equals("amongusGreen") && aug.currentState!=AmongUsGreen.State.DEAD) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
                aug.b2body.applyForceToCenter(0, 30000f, true);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                aug.b2body.applyForceToCenter(-100f, 0, true);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                aug.b2body.applyForceToCenter(100f, 0, true);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.T)) {
                aug.setPunch();
            }
            if (Gdx.input.isKeyPressed(Input.Keys.Y)) {
                aug.setKick();
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.U)){
                if(aug.getSpecialReady()){
                    aug.setSpecial();
                }
            }
        }
        if(SelectionScreen.playerChoice.get(0).equals("horseGreen") && hg.currentState!=HorseGreen.State.DEAD) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
                hg.b2body.applyForceToCenter(0, 30000f, true);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                hg.b2body.applyForceToCenter(-100f, 0, true);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                hg.b2body.applyForceToCenter(100f, 0, true);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.T)) {
                hg.setPunch();
            }
            if (Gdx.input.isKeyPressed(Input.Keys.Y)) {
                hg.setKick();
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.U)){
                if(hg.getSpecialReady()){
                    hg.setSpecial();
                }
            }
        }
        if(SelectionScreen.player2Choice.get(0).equals("bream") && p2B.currentState!=Bream.State.DEAD)
        {
            if(Gdx.input.isKeyJustPressed(Input.Keys.UP)){
                p2B.b2body.applyForceToCenter(0, 30000f, true);
            }
            if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
                p2B.b2body.applyForceToCenter(-100f, 0, true);
            }
            if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
                p2B.b2body.applyForceToCenter(100f, 0,true);
            }
            if(Gdx.input.isKeyPressed(Input.Keys.NUM_7)){
                p2B.setPunch();
            }
            if(Gdx.input.isKeyPressed(Input.Keys.NUM_8)){
                p2B.setKick();
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_9)){
                if(p2B.getSpecialReady()){
                    p2B.setSpecial();
                }
            }
        }
        if(SelectionScreen.player2Choice.get(0).equals("amongusBlue") && aub.currentState!=AmongUsBlue.State.DEAD)
        {
            if(Gdx.input.isKeyJustPressed(Input.Keys.UP)){
                aub.b2body.applyForceToCenter(0, 30000f, true);
            }
            if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
                aub.b2body.applyForceToCenter(-100f, 0, true);
            }
            if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
                aub.b2body.applyForceToCenter(100f, 0,true);
            }
            if(Gdx.input.isKeyPressed(Input.Keys.NUM_7)){
                aub.setPunch();
            }
            if(Gdx.input.isKeyPressed(Input.Keys.NUM_8)){
                aub.setKick();
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_9)){
                if(aub.getSpecialReady()){
                    aub.setSpecial();
                }
            }
        }
        if(SelectionScreen.player2Choice.get(0).equals("horseBlue") && hb.currentState!=HorseBlue.State.DEAD)
        {
            if(Gdx.input.isKeyJustPressed(Input.Keys.UP)){
                hb.b2body.applyForceToCenter(0, 30000f, true);
            }
            if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
                hb.b2body.applyForceToCenter(-100f, 0, true);
            }
            if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
                hb.b2body.applyForceToCenter(100f, 0,true);
            }
            if(Gdx.input.isKeyPressed(Input.Keys.NUM_7)){
                hb.setPunch();
            }
            if(Gdx.input.isKeyPressed(Input.Keys.NUM_8)){
                hb.setKick();
            }
            if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_9)){
                if(hb.getSpecialReady()){
                    hb.setSpecial();
                }
            }
        }
    }
    public void update(float delta){
        handleInput(delta);
        world.step(1/60f, 6,2);
        if(SelectionScreen.playerChoice.get(0).equals("gream"))
            p1G.update(delta);
        else if (SelectionScreen.playerChoice.get(0).equals("amongusGreen"))
            aug.update(delta);
        else
            hg.update(delta);
        if(SelectionScreen.player2Choice.get(0).equals("bream"))
            p2B.update(delta);
        else if (SelectionScreen.player2Choice.get(0).equals("amongusBlue"))
            aub.update(delta);
        else
            hb.update(delta);
    }
    public void render(float delta){
        update(delta);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(background, 0, 0);
        if(SelectionScreen.playerChoice.get(0).equals("gream"))
            p1G.draw(game.batch);
        else if (SelectionScreen.playerChoice.get(0).equals("amongusGreen"))
            aug.draw(game.batch);
        else
            hg.draw(game.batch);
        if(SelectionScreen.player2Choice.get(0).equals("bream"))
            p2B.draw(game.batch);
        else if (SelectionScreen.playerChoice.get(0).equals("amongusBlue"))
            aub.draw(game.batch);
        else
            hb.draw(game.batch);
        game.batch.end();
        b2dr.render(world, camera.combined);
    }
}
