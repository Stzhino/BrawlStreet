package com.badlogic.mygame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
public class AmongUsGreen extends Sprite{
    protected World world;
    protected Body b2body;
    protected enum State {JUMPING, PUNCHING, KICKING, STANDING, DEAD, WALKING, SPECIAL, BLOCK};
    protected State currentState;
    protected State pastState;
    private Animation shuffle;
    private Animation punch;
    private Animation kick;
    private Animation jump;
    private Texture AUB;
    private float stateTimer;
    private TextureRegion AUBStand;
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

    public AmongUsGreen(World world){
        this.world = world;
        currentState = State.STANDING;
        pastState = State.STANDING;
        stateTimer = 0;
        AUB = new Texture("AmongUsGreen.png");
        Array<TextureRegion> frames = new Array<TextureRegion>();
        // shuffling animation frames
        frames.add(new TextureRegion(AUB, 588, 800, 300, 400));
        frames.add(new TextureRegion(AUB, 600, 400, 300, 400));
        shuffle = new Animation(.2f, frames);
        frames.clear();
        // atk animation frames
        frames.add(new TextureRegion(AUB, 0, 0, 300, 400));
        frames.add(new TextureRegion(AUB, 300, 0, 300, 400));
        punch = new Animation(.05f, frames);
        frames.clear();
        // kick animation frames
        frames.add(new TextureRegion(AUB, 0, 400, 300, 400));
        frames.add(new TextureRegion(AUB, 300, 400, 300, 400));
        kick = new Animation(.25f, frames);
        frames.clear();
        // jump animation frames
        frames.add(new TextureRegion(AUB, 600, 400, 300, 400));
        jump = new Animation(.05f, frames);
        frames.clear();
        // block
        block = new TextureRegion(AUB,600,0,300,400);
        // special
        special = new TextureRegion(AUB, 258, 800, 300, 400);

        defineAmong();
        AUBStand = new TextureRegion(AUB, 558, 800, 300, 400);
        setRegion(AUBStand);
        setBounds(0, 0, 300, 400);

        isKick = false;
        isPunch = false;
        isBlock = false;
        isSpecial = false;
        currentHp = 100;
        currentEnergy = 0;
        specialReady = false;
    }

    public void defineAmong(){
        FixtureDef fdef = new FixtureDef();
        rect = new Rectangle(100, 75, 100, 300);

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
                    if(SelectionScreen.player2Choice.get(0).equals("bream")){
                        if(r.overlaps(Bream.rect) && doDamage)
                        {
                            if(Bream.isBlock) {
                                Bream.depleteHp(kickDamage/2);
                            }
                            else{
                                Bream.depleteHp(kickDamage);
                            }
                            AmongUsGreen.increaseEnergy();
                        }
                    }
                    else if(SelectionScreen.player2Choice.get(0).equals("amongusBlue")){
                        if (r.overlaps(AmongUsBlue.rect) && doDamage)
                        {
                            if(AmongUsBlue.isBlock) {
                                AmongUsBlue.depleteHp(kickDamage/2);
                            }
                            else{
                                AmongUsBlue.depleteHp(kickDamage);
                            }
                            AmongUsGreen.increaseEnergy();
                        }
                    }
                    else {
                        if (r.overlaps(HorseBlue.rect) && doDamage)
                        {
                            if(HorseBlue.isBlock) {
                                HorseBlue.depleteHp(kickDamage/2);
                            }
                            else{
                                HorseBlue.depleteHp(kickDamage);
                            }
                            AmongUsGreen.increaseEnergy();
                        }
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
                    if(SelectionScreen.player2Choice.get(0).equals("bream")){
                        if(r.overlaps(Bream.rect) && doDamage)
                        {
                            if(Bream.isBlock) {
                                Bream.depleteHp(fistDamage/2);
                            }
                            else{
                                Bream.depleteHp(fistDamage);
                            }
                            AmongUsGreen.increaseEnergy();
                        }
                    }
                    else if(SelectionScreen.player2Choice.get(0).equals("amongusBlue")){
                        if (r.overlaps(AmongUsBlue.rect) && doDamage)
                        {
                            if(AmongUsBlue.isBlock) {
                                AmongUsBlue.depleteHp(fistDamage/2);
                            }
                            else{
                                AmongUsBlue.depleteHp(fistDamage);
                            }
                            AmongUsGreen.increaseEnergy();
                        }
                    }
                    else {
                        if (r.overlaps(HorseBlue.rect) && doDamage)
                        {
                            if(HorseBlue.isBlock) {
                                HorseBlue.depleteHp(fistDamage/2);
                            }
                            else{
                                HorseBlue.depleteHp(fistDamage);
                            }
                            AmongUsGreen.increaseEnergy();
                        }
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
                region = AUBStand;
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
        else if(currentHp==0){
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
    }
    public double getCurrentHp(){
        return currentHp;
    }
    public boolean getSpecialReady(){
        return specialReady;
    }
}
