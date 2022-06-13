package com.badlogic.mygame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;

public class Bream extends Sprite{
    protected World world;
    protected Body b2body;
    protected enum State {JUMPING, PUNCHING, KICKING, STANDING, DEAD, WALKING, SPECIAL, BLOCK};
    protected State currentState;
    protected State pastState;
    private Animation shuffle;
    private Animation punch;
    private Animation kick;
    private Animation jump;
    private Texture bream;
    private float stateTimer;
    private TextureRegion breamStand;
    private boolean isPunch;
    private boolean isKick;
    protected static boolean isBlock;
    private boolean isSpecial;
    private TextureRegion block;
    private TextureRegion special;

    private final double maxHp = 100;
    private static double currentHp;
    private static final double maxEnergy = 50;
    private static double currentEnergy;
    private static boolean specialReady;
    private final int fistDamage = 5;
    private final double kickDamage = 2.5;
    private final int specialDamage = 25;
    protected static Rectangle rect;

    public Bream(World world){
        this.world = world;
        currentState = State.STANDING;
        pastState = State.STANDING;
        stateTimer = 0;
        bream = new Texture("bream.png");
        Array<TextureRegion> frames = new Array<TextureRegion>();
        // shuffling animation frames
        frames.add(new TextureRegion(bream, 0, 800, 200, 400));
        frames.add(new TextureRegion(bream, 200, 400, 200, 400));
        shuffle = new Animation(.2f, frames);
        frames.clear();
        // punch animation frames
        frames.add(new TextureRegion(bream, 400, 400, 200, 400));
        frames.add(new TextureRegion(bream, 600, 400, 200, 400));
        frames.add(new TextureRegion(bream, 800, 400, 200, 400));
        punch = new Animation(.05f, frames);
        frames.clear();
        // kick animation frames
        frames.add(new TextureRegion(bream, 600, 0, 200, 400));
        frames.add(new TextureRegion(bream, 800, 0, 200, 400));
        kick = new Animation(.25f, frames);
        frames.clear();
        // jump animation frames
        frames.add(new TextureRegion(bream, 200, 0, 200, 400));
        frames.add(new TextureRegion(bream, 400, 0, 200, 400));
        jump = new Animation(.05f, frames);
        frames.clear();
        // block
        block = new TextureRegion(bream,0,0,200,400);
        // special
        special = new TextureRegion(bream, 200, 800, 200, 400);

        defineBream();
        breamStand = new TextureRegion(bream, 0, 400, 200, 400);
        setRegion(breamStand);
        setBounds(0, 0, 200, 400);

        isKick = false;
        isPunch = false;
        isBlock = false;
        isSpecial = false;
        currentHp = 100;
        currentEnergy = 0;
        specialReady = false;
    }

    public void defineBream(){
        FixtureDef fdef = new FixtureDef();
        rect = new Rectangle(700, 75, 100, 300);

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
        rect.setPosition(new Vector2(b2body.getPosition().x-getWidth()/2,b2body.getPosition().y-getWidth()));
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
                boolean doDamage = true;
                if(kick.isAnimationFinished(stateTimer)){
                    Rectangle r = new Rectangle(rect.getX(), rect.getY()+200, 200, 200);
                    if(r.overlaps(Gream.rect) && doDamage)
                    {
                        if(Gream.isBlock) {
                            Gream.depleteHp(kickDamage/2);
                        }
                        else{
                            Gream.depleteHp(kickDamage);
                        }
                        Bream.increaseEnergy();
                    }
                    doDamage = false;
                    resetKick();
                }
                break;
            case PUNCHING:
                region = (TextureRegion) punch.getKeyFrame(stateTimer);
                doDamage = true;
                if(punch.isAnimationFinished(stateTimer)){
                    Rectangle r = new Rectangle(rect.getX(), rect.getY()+200, 200, 200);
                    if(r.overlaps(Gream.rect) && doDamage)
                    {
                        if(Gream.isBlock) {
                            Gream.depleteHp(fistDamage/2);
                        }
                        else{
                            Gream.depleteHp(fistDamage);
                        }
                        Bream.increaseEnergy();
                    }
                    doDamage = false;
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
            case DEAD:
            default:
                region = breamStand;
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
        else if(currentHp == 0){
            return State.DEAD;
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
        specialReady = false;
    }
    public static void increaseEnergy(){
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
    public static void depleteHp(double d){
        currentHp-=d;
        System.out.println(currentHp);
    }
    public double getCurrentHp(){
        return currentHp;
    }
    public boolean getSpecialReady(){
        return specialReady;
    }
}
