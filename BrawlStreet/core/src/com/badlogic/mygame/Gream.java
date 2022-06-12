package com.badlogic.mygame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Gream extends Sprite {
    private enum State {FALLING, JUMPING, PUNCHING, KICKING, STANDING};
    private State currentState;
    private State pastState;
    private Animation passive;
    private Animation shuffle;
    private Animation punch;
    private Animation kick;
    private Animation jump;
    private Texture gream;
    private float stateTimer;
    public Gream(){
        currentState = State.STANDING;
        pastState = State.STANDING;
        stateTimer = 0;
        gream = new Texture("gream.png");
        Array<TextureRegion> frames = new Array<TextureRegion>();
        // passive animation frames
        frames.add(new TextureRegion(gream, 0, 400, 200, 400));
        frames.add(new TextureRegion(gream, 200, 400, 200, 400));
        passive = new Animation(.01f, frames);
        frames.clear();
        // shuffling animation frames
        frames.add(new TextureRegion(gream, 0, 800, 200, 400));
        frames.add(new TextureRegion(gream, 200, 400, 200, 400));
        shuffle = new Animation(.01f, frames);
        frames.clear();
        // punch animation frames
//        frames.add(new TextureRegion(gream, ));
    }

//    public void update(float dt){
//        setRegion(getFrame(dt));
//    }

//    public TextureRegion getFrame(float dt){
//        currentState = getState();
//    }
//    public State getState(){
//        if()
//    }

}
