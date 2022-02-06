package screens;

import buttons.CloseButton;
import buttons.PlayButton;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.pasjans.Pasjans;

import javax.swing.*;
import java.sql.Time;

public class MainMenuScreen implements Screen {

    final Pasjans game;

    private final int SCREEN_WIDTH = 1920;
    private final int SCREEN_HEIGHT = 1080;

    OrthographicCamera camera;
    Stage stage;
    Label title;
    Label.LabelStyle style;
    PlayButton playButton;
    CloseButton closeButton;

    public MainMenuScreen(final Pasjans game){
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        // napis tytułowy
        createTitle();
        // przycisk do grania
        createPlayButton();
        // przycisk do zamknięcia gry
        createCloseButton();
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
            public void clicked(InputEvent event, float x, float y){
                game.setScreen(new HowManyColorsScreen(game));
                //game.setScreen(new WinScreen(game, 293, 1352000));
                dispose();
            }
        });

        closeButton.getButton().addListener(new ClickListener(){
           public void clicked(InputEvent event, float x, float y){
               game.dispose();
           }
        });

    }

    public void createTitle(){
        style = new Label.LabelStyle();
        style.font = game.titleFont;

        title = new Label("Pasjans", style);
        title.setX(SCREEN_WIDTH/2 - title.getWidth()/2);
        title.setY(SCREEN_HEIGHT/2 + 300);
        stage.addActor(title);
    }

    public void createPlayButton(){
        playButton = new PlayButton();
        playButton.getButton().setX(SCREEN_WIDTH/2 - playButton.getButton().getWidth()/2);
        playButton.getButton().setY(title.getY() - playButton.getButton().getHeight() - 150);
        stage.addActor(playButton.getButton());
    }

    public void createCloseButton(){
        closeButton = new CloseButton();
        closeButton.getButton().setX(SCREEN_WIDTH/2 - closeButton.getButton().getWidth()/2);
        closeButton.getButton().setY(playButton.getButton().getY() - closeButton.getButton().getHeight() - 50);
        stage.addActor(closeButton.getButton());
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
