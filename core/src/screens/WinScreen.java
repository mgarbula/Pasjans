package screens;

import buttons.CloseButton;
import buttons.MenuButton;
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
import com.mygdx.pasjans.Pasjans;

public class WinScreen implements Screen {
    final Pasjans game;

    private final int howManyMoves;
    private final OrthographicCamera camera;
    private long time;

    Label title, movesInfo, timeInfo;
    Label.LabelStyle style;
    Stage stage;
    PlayButton playButton;
    MenuButton menuButton;
    CloseButton closeButton;

    public WinScreen(final Pasjans game, int howManyMoves, long time){
        this.game = game;
        this.howManyMoves = howManyMoves;
        this.time = time;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, game.SCREEN_WIDTH, game.SCREEN_HEIGHT);

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        createTitle();
        createMovesInfo();
        createTimeInfo();
        createPlayAgainButton();
        createMenuButton();
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
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new HowManyColorsScreen(game));
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

        closeButton.getButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.dispose();
            }
        });
    }

    public void createTitle(){
        style = new Label.LabelStyle();
        style.font = game.titleFont;

        title = new Label("Wygrana!", style);
        title.setX(game.SCREEN_WIDTH/2 - title.getWidth()/2);
        title.setY(game.SCREEN_HEIGHT - title.getHeight() - 50);
        stage.addActor(title);
    }

    public void createMovesInfo(){
        style.font = new BitmapFont(Gdx.files.internal("fonts/checkbox_font.fnt"));

        movesInfo = new Label("Ilosc ruchow: " + howManyMoves, style);
        movesInfo.setX(game.SCREEN_WIDTH/2 - movesInfo.getWidth()/2);
        movesInfo.setY(title.getY() - movesInfo.getHeight() - 30);
        stage.addActor(movesInfo);
    }

    public void createTimeInfo(){
        int minutes = (int) (time/60000);
        time -= minutes * 60000;
        int seconds = (int) (time/1000);
        //if(minutes > 10 && seconds > 10)
            timeInfo = new Label("Czas gry: " + minutes + ":" + seconds, style);
       /* else if(minutes < 10 && seconds < 10)
            timeInfo = new Label("Czas gry: 0" + minutes + ":0" + seconds, style);
        else if(minutes < 10)
            timeInfo = new Label("Czas gry: 0" + minutes + ":" + seconds, style);
        else if(seconds < 10)
            timeInfo = new Label("Czas gry: " + minutes + ":0" + seconds, style);*/
        timeInfo.setX(game.SCREEN_WIDTH/2 - timeInfo.getWidth()/2);
        timeInfo.setY(movesInfo.getY() - timeInfo.getHeight() - 30);
        stage.addActor(timeInfo);
    }

    public void createPlayAgainButton(){
       playButton = new PlayButton();
        playButton.getButton().setX(game.SCREEN_WIDTH/2 - playButton.getButton().getWidth()/2);
        playButton.getButton().setY(timeInfo.getY() - playButton.getButton().getHeight() - 30);
        stage.addActor(playButton.getButton());
    }

    public void createMenuButton(){
        menuButton = new MenuButton();
        menuButton.getButton().setX(game.SCREEN_WIDTH/2 - menuButton.getButton().getWidth()/2);
        menuButton.getButton().setY(playButton.getButton().getY() - menuButton.getButton().getHeight() - 30);
        stage.addActor(menuButton.getButton());
    }

    public void createCloseButton(){
        closeButton = new CloseButton();
        closeButton.getButton().setX(game.SCREEN_WIDTH/2 - closeButton.getButton().getWidth()/2);
        closeButton.getButton().setY(menuButton.getButton().getY() - closeButton.getButton().getHeight() - 30);
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
