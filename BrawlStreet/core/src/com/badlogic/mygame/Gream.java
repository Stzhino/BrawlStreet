package com.badlogic.mygame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;

public class Gream extends Sprite {
    protected World world;
    protected Body b2body;
    protected enum State {JUMPING, PUNCHING, KICKING, STANDING, DEAD, WALKING, SPECIAL, BLOCK};
    protected State currentState;
    protected State pastState;
    private Animation shuffle;
    private Animation punch;
    private Animation kick;
    private Animation jump;
    private Texture gream;
    private float stateTimer;
    private TextureRegion greamStand;
    private boolean isPunch;
    private boolean isKick;
    private boolean isBlock;
    private boolean isSpecial;
    private TextureRegion block;
    private TextureRegion special;

    private final double maxHp = 100;
    private double currentHp;
    private final double maxEnergy = 50;
    private double currentEnergy;
    private boolean specialReady;
    private final int fistDamage = 10;
    private final int kickDamage = 5;
    private final int specialDamage = 25;

    public Gream(World world){
        this.world = world;
        currentState = State.STANDING;
        pastState = State.STANDING;
        stateTimer = 0;
        gream = new Texture("gream.png");
        Array<TextureRegion> frames = new Array<TextureRegion>();
        // shuffling animation frames
        frames.add(new TextureRegion(gream, 0, 800, 200, 400));
        frames.add(new TextureRegion(gream, 200, 400, 200, 400));
        shuffle = new Animation(.2f, frames);
        frames.clear();
        // punch animation frames
        frames.add(new TextureRegion(gream, 400, 400, 200, 400));
        frames.add(new TextureRegion(gream, 600, 400, 200, 400));
        frames.add(new TextureRegion(gream, 800, 400, 200, 400));
        punch = new Animation(.05f, frames);
        frames.clear();
        // kick animation frames
        frames.add(new TextureRegion(gream, 600, 0, 200, 400));
        frames.add(new TextureRegion(gream, 800, 0, 200, 400));
        kick = new Animation(.25f, frames);
        frames.clear();
        // jump animation frames
        frames.add(new TextureRegion(gream, 200, 0, 200, 400));
        frames.add(new TextureRegion(gream, 400, 0, 200, 400));
        jump = new Animation(.05f, frames);
        frames.clear();
        // block
        block = new TextureRegion(gream,0,0,200,400);
        // special
        special = new TextureRegion(gream, 200, 800, 200, 400);

        defineGream();
        greamStand = new TextureRegion(gream, 0, 400, 200, 400);
        setRegion(greamStand);
        setBounds(0, 0, 200, 400);

        isKick = false;
        isPunch = false;
        isBlock = false;
        isSpecial = false;
        currentHp = 100;
        currentEnergy = 0;
        specialReady = false;
    }

    public void defineGream(){
        FixtureDef fdef = new FixtureDef();
        Rectangle rect = new Rectangle(100, 100, 200, 400);

        BodyDef bdef = new BodyDef();
        bdef.position.set(rect.getX()+rect.getWidth()/2, rect.getY()+rect.getHeight()/2);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(rect.getWidth()/2, rect.getHeight()/2);

        fdef.shape = shape;
        b2body.createFixture(fdef);
    }

    public void update(float dt){
        setPosition(b2body.getPosition().x-getWidth()/2, b2body.getPosition().y-getWidth());
        setRegion(getFrame(dt));
    }

    public TextureRegion getFrame(float dt){
        currentState = getState();

        TextureRegion region;
        switch(currentState){
            case JUMPING:
                region = (TextureRegion) jump.getKeyFrame(stateTimer);
                break;
            case WALKING:
                region = (TextureRegion) shuffle.getKeyFrame(stateTimer, true);
                break;
            case KICKING:
                region = (TextureRegion) kick.getKeyFrame(stateTimer);
                if(kick.isAnimationFinished(stateTimer)){
                    resetKick();
                }
                break;
            case PUNCHING:
                region = (TextureRegion) punch.getKeyFrame(stateTimer);
                if(kick.isAnimationFinished(stateTimer)){
                    resetPunch();
                }
                break;
            case BLOCK:
                region = block;
                break;
            case SPECIAL:
                region = special;
                resetSpecial();
                break;
            case STANDING:
            default:
                region = greamStand;
        }
        stateTimer = currentState == pastState ? stateTimer + dt : 0;
        pastState = currentState;

        return region;
    }
    public State getState(){
        if(b2body.getLinearVelocity().y>0 || (b2body.getLinearVelocity().y<0 && pastState == State.JUMPING)){
            return State.JUMPING;
        }
        else if(isPunch){
            return State.PUNCHING;
        }
        else if(isKick){
            return State.KICKING;
        }
        else if(b2body.getLinearVelocity().x!=0){
            return State.WALKING;
        }
        else if(isBlock){
            return State.BLOCK;
        }
        else if(isSpecial){
            return State.SPECIAL;
        }
        else{
            return State.STANDING;
        }
    }
    public void setKick(){
        isKick = true;
    }
    public void resetKick(){
        isKick = false;
    }
    public void setPunch(){
        isPunch = true;
    }
    public void resetPunch(){
        isPunch = false;
    }
    public void setBlock(){
        isBlock = true;
    }
    public void setSpecial(){
        isSpecial = true;
    }
    public void resetBlock(){
        isBlock = false;
    }
    public void resetSpecial(){
        isSpecial = false;
    }
    public void increaseEnergy(){
        if(currentEnergy!=maxEnergy){
            currentEnergy+=10;
        }
        else{
            specialReady = true;
        }
    }
    public double getCurrentEnergy(){
        return currentEnergy;
    }
    public double getFistDamage(){
        return fistDamage;
    }
    public double getKickDamage(){
        return kickDamage;
    }
    public double getSpecialDamage(){
        return specialDamage;
    }
    public void depleteHp(double d){
        currentHp-=d;
    }
    public double getCurrentHp(){
        return currentHp;
    }
    public boolean getSpecialReady(){
        return specialReady;
    }
}
