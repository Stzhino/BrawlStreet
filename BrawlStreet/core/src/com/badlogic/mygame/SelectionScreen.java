package com.badlogic.mygame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import java.util.ArrayList;

public class SelectionScreen extends ScreenAdapter {
    private GameRunner game;
    private Stage stage;
    private TextButton playerOneRight;
    private TextButton playerOneLeft;
    private TextButton playerTwoRight;
    private TextButton playerTwoLeft;
    private TextButton.TextButtonStyle rightArrowStyle;
    private TextButton.TextButtonStyle leftArrowStyle;
    private Texture background;
    private OrthographicCamera camera;
    private Skin skin;
    private TextureAtlas buttonAtlas;
    protected static ArrayList<String> playerChoice;
    protected static ArrayList<String> player2Choice;
    private Texture gream;
    private Texture AmongUsBlue;
    private Texture horseGreen;
    private Texture bream;
    private Texture amongusBlue;
    private Texture horseBlue;
    private boolean player1ready;
    private boolean player2ready;

    public SelectionScreen(GameRunner game){
        this.game = game;
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1000, 600);
        background = new Texture("Selection.png");
        buttonAtlas = new TextureAtlas("selectionButton.txt");
        skin = new Skin();
        skin.addRegions(buttonAtlas);
        rightArrowStyle = new TextButton.TextButtonStyle();
        rightArrowStyle.up = skin.getDrawable("rightArrow1");
        rightArrowStyle.down = skin.getDrawable("rightArrow2");
        rightArrowStyle.font = game.font;
        leftArrowStyle = new TextButton.TextButtonStyle();
        leftArrowStyle.up = skin.getDrawable("leftArrow1");
        leftArrowStyle.down = skin.getDrawable("leftArrow2");
        leftArrowStyle.font = game.font;
        // buttons
        playerOneRight = new TextButton("", rightArrowStyle);
        playerOneLeft = new TextButton("", leftArrowStyle);
        playerTwoRight = new TextButton("", rightArrowStyle);
        playerTwoLeft = new TextButton("", leftArrowStyle);
        playerOneLeft.setPosition(Gdx.graphics.getWidth()*.005f, Gdx.graphics.getHeight()*.35f);
        playerOneRight.setPosition(Gdx.graphics.getWidth()*.4f, Gdx.graphics.getHeight()*.35f);
        playerTwoRight.setPosition(Gdx.graphics.getWidth()*.905f, Gdx.graphics.getHeight()*.35f);
        playerTwoLeft.setPosition(Gdx.graphics.getWidth()*.505f, Gdx.graphics.getHeight()*.35f);
        stage.addActor(playerOneLeft);
        stage.addActor(playerOneRight);
        stage.addActor(playerTwoLeft);
        stage.addActor(playerTwoRight);
        playerChoice = new ArrayList<>();
        player2Choice = new ArrayList<>();
        playerChoice.add("gream");
        playerChoice.add("AmongUsBlue");
        playerChoice.add("horseGreen");
        player2Choice.add("bream");
        player2Choice.add("amongusBlue");
        player2Choice.add("horseBlue");
        // texture of player1
        gream = new Texture("gream/passive1.png");
        AmongUsBlue = new Texture("amongGreen/AGstandp1.png");
        horseGreen = new Texture("HorseGreen/HStandp1.png");
        // texture of player2
        bream = new Texture("bream/passive1p2.png");
        amongusBlue = new Texture("amongBlue/AGstandp2.png");
        horseBlue = new Texture("HorseBlue/HStandp2.png");
        // booleans
        player1ready = false;
        player2ready = false;
    }

    public void show(){
        playerOneLeft.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor){
                System.out.println("PlayerLeft pressed.");
                if(!player1ready){
                    playerChoice = cycleLeftArrL(playerChoice);
                }
            }
        });
        playerOneRight.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor){
                System.out.println("PlayerRight pressed.");
                if(!player1ready){
                    playerChoice = cycleRightArrL(playerChoice);
                }
            }
        });
        playerTwoLeft.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor){
                System.out.println("PlayerLeft2 pressed.");
                if(!player2ready){
                    player2Choice = cycleLeftArrL(player2Choice);
                }
            }
        });
        playerTwoRight.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor){
                System.out.println("PlayerRight2 pressed.");
                if(!player2ready){
                    player2Choice = cycleRightArrL(player2Choice);
                }
            }
        });
    }

    public void render(float delta){
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(background, 0,0);
        game.batch.end();
        stage.draw();
        game.batch.begin();
        if(playerChoice.get(0).equals("gream")) {
            game.batch.draw(gream, Gdx.graphics.getWidth()*.15f, Gdx.graphics.getHeight()*.1f);
        }
        else if(playerChoice.get(0).equals("AmongUsBlue")) {
            game.batch.draw(AmongUsBlue, Gdx.graphics.getWidth()*.15f, Gdx.graphics.getHeight()*.1f);
        }
        else {
            game.batch.draw(horseGreen, Gdx.graphics.getWidth()*.15f, Gdx.graphics.getHeight()*.1f);
        }
        if(player2Choice.get(0).equals("bream")) {
            game.batch.draw(bream, Gdx.graphics.getWidth()*.65f, Gdx.graphics.getHeight()*.1f);
        }
        else if(player2Choice.get(0).equals("amongusBlue")) {
            game.batch.draw(amongusBlue, Gdx.graphics.getWidth()*.57f, Gdx.graphics.getHeight()*.1f);
        }
        else {
            game.batch.draw(horseBlue, Gdx.graphics.getWidth()*.55f, Gdx.graphics.getHeight()*.1f);
        }
        if(!player1ready){
            game.font.setColor(Color.WHITE);
            game.font.draw(game.batch, "Press c to lock choice.", Gdx.graphics.getWidth()*.06f, Gdx.graphics.getHeight()*.8f);
        }
        else{
            game.font.setColor(Color.GREEN);
            game.font.draw(game.batch, "Player 1 ready!", Gdx.graphics.getWidth()*.11f, Gdx.graphics.getHeight()*.8f);
        }
        if(!player2ready){
            game.font.setColor(Color.WHITE);
            game.font.draw(game.batch, "Press 4 to lock choice.", Gdx.graphics.getWidth()*.645f, Gdx.graphics.getHeight()*.8f);
        }
        else{
            game.font.setColor(Color.GREEN);
            game.font.draw(game.batch, "Player 2 ready!", Gdx.graphics.getWidth()*.7f, Gdx.graphics.getHeight()*.8f);
        }
        game.batch.end();
        if(Gdx.input.isKeyPressed(Input.Keys.C)){
            player1ready = true;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.NUM_4)){
            player2ready = true;
        }
        game.batch.begin();
        if(player1ready && player2ready){
            game.font.setColor(Color.GREEN);
            game.font.draw(game.batch, "Press enter to begin!", Gdx.graphics.getWidth()*.365f, Gdx.graphics.getHeight()*.1f);
        }
        game.batch.end();
        if(Gdx.input.isKeyPressed(Input.Keys.ENTER)){
            System.out.println("Enter Pressed!");
            if(player1ready && player2ready) {
                game.setScreen(new GameScreen(game));
            }
        }
    }

    public static ArrayList<String> cycleLeftArrL(ArrayList<String> arr){
        ArrayList<String> result = new ArrayList<>();
        for(int i = 1; i<arr.size(); i++)
        {
            result.add(i-1, arr.get(i));
        }
        result.add(arr.size()-1, arr.get(0));
        return result;
    }

    public static ArrayList<String> cycleRightArrL(ArrayList<String> arr){
        ArrayList<String> result = new ArrayList<>();
        result.add(0, arr.get(arr.size()-1));
        for(int i = 1; i<arr.size(); i++){
            result.add(i, arr.get(i-1));
        }
        return result;
    }
}
