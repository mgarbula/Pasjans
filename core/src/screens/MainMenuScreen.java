package screens;

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
import com.mygdx.pasjans.Pasjans;

import javax.swing.*;

public class MainMenuScreen implements Screen {

    final Pasjans game;

    private final int SCREEN_WIDTH = 1920;
    private final int SCREEN_HEIGHT = 1080;

    OrthographicCamera camera;
    Stage stage;
    TextButton playButton, closeButton;
    Label title;
    Label.LabelStyle style;

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

        playButton.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                game.setScreen(new HowManyColorsScreen(game));
                dispose();
            }
        });

        closeButton.addListener(new ClickListener(){
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
        title.setY(SCREEN_HEIGHT/2 + 200);
        stage.addActor(title);
    }

    public void createPlayButton(){
        TextureAtlas buttonAtlas = new TextureAtlas("buttons/buttons.pack");
        Skin skin = new Skin(buttonAtlas);
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();

        textButtonStyle.up = skin.getDrawable("button_play_up1");
        textButtonStyle.down = skin.getDrawable("button_play_down1");
        textButtonStyle.pressedOffsetX = 1;
        textButtonStyle.pressedOffsetY = -1;
        textButtonStyle.font = new BitmapFont(Gdx.files.internal("fonts/menu_font.fnt"));

        playButton = new TextButton("Graj", textButtonStyle);
        playButton.setX(SCREEN_WIDTH/2 - playButton.getWidth()/2);
        playButton.setY(title.getY() - playButton.getHeight() - 50);
        stage.addActor(playButton);
    }

    public void createCloseButton(){
        TextureAtlas atlas = new TextureAtlas("buttons/buttons.pack");
        Skin skin = new Skin(atlas);
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();

        textButtonStyle.up = skin.getDrawable("button_close_up1");
        textButtonStyle.down = skin.getDrawable("button_close_down1");
        textButtonStyle.pressedOffsetX = 1;
        textButtonStyle.pressedOffsetY = -1;
        textButtonStyle.font = new BitmapFont(Gdx.files.internal("fonts/menu_font.fnt"));

        closeButton = new TextButton("Zamknij", textButtonStyle);
        closeButton.setX(SCREEN_WIDTH/2 - closeButton.getWidth()/2);
        closeButton.setY(playButton.getY() - closeButton.getHeight() - 50);
        stage.addActor(closeButton);
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
