package screens;

import buttons.MenuButton;
import buttons.PlayButton;
import cards.Card;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.pasjans.Pasjans;

import java.util.ArrayList;

public class PauseScreen implements Screen {
    final Pasjans game;

    OrthographicCamera camera;
    Stage stage;
    PlayButton playButton;
    MenuButton menuButton;
    private ArrayList<Card> deck; // ArrayList of spades
    private ArrayList<ArrayList<Card>> stacks;
    private ArrayList<Rectangle> goodStacks; // ułożone stacki
    private ArrayList<Texture> goodStacksTextures; // karty dobrych stacków
    private ArrayList<Rectangle> deckRectangle; // zakryte karty
    private ArrayList<Rectangle> emptyStacks;

    public PauseScreen(final Pasjans game, ArrayList<Card> deck, ArrayList<ArrayList<Card>> stacks, ArrayList<Rectangle> goodStacks,
                       ArrayList<Texture> goodStacksTextures, ArrayList<Rectangle> deckRectangle, ArrayList<Rectangle> emptyStacks){
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, game.SCREEN_WIDTH, game.SCREEN_HEIGHT);

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        createGameObjects(deck, stacks, goodStacks, goodStacksTextures, deckRectangle, emptyStacks);
        createPlayButton();
        createMenuButton();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0.2f, 0, 1);
        camera.update();
        game.spriteBatch.setProjectionMatrix(camera.combined);
        stage.draw();

        playButton.getButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game, true, deck, stacks, goodStacks, goodStacksTextures, deckRectangle, emptyStacks));
                dispose();
            }
        });

        menuButton.getButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
                dispose();
            }
        });
    }

    public void createPlayButton(){
        playButton = new PlayButton();
        playButton.getButton().setText("Dalej");
        playButton.getButton().setX(game.SCREEN_WIDTH/2 - playButton.getButton().getWidth()/2);
        playButton.getButton().setY(game.SCREEN_HEIGHT - 500);
        stage.addActor(playButton.getButton());
    }

    public void createMenuButton(){
        menuButton = new MenuButton();
        menuButton.getButton().setX(game.SCREEN_WIDTH/2 - menuButton.getButton().getWidth()/2);
        menuButton.getButton().setY(playButton.getButton().getY() - menuButton.getButton().getHeight() - 50);
        stage.addActor(menuButton.getButton());
    }

    public void createGameObjects(ArrayList<Card> deck, ArrayList<ArrayList<Card>> stacks, ArrayList<Rectangle> goodStacks,
                                  ArrayList<Texture> goodStacksTextures, ArrayList<Rectangle> deckRectangle, ArrayList<Rectangle> emptyStacks){
        this.deck = deck;
        this.stacks = stacks;
        if(goodStacks != null)
            this.goodStacks = goodStacks;
        if(goodStacksTextures!= null)
            this.goodStacksTextures = goodStacksTextures;
        if(deckRectangle != null)
            this.deckRectangle = deckRectangle;
        this.emptyStacks = emptyStacks;
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
