package screens;

import buttons.MenuButton;
import buttons.PlayButton;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.pasjans.Pasjans;

public class HowManyColorsScreen implements Screen {

    final Pasjans game;

    OrthographicCamera camera;
    Stage stage;
    Label.LabelStyle style;
    Label title, info, warning;
    CheckBox oneColor, twoColors, fourColors;
    Array<CheckBox> checkBoxes;
    PlayButton playButton;
    MenuButton menuButton;

    public HowManyColorsScreen(Pasjans game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, game.SCREEN_WIDTH, game.SCREEN_HEIGHT);
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        // tytul
        createTitle();
        // informacja na temat ilosci kart
        createInfo();
        // checkboxy do wyboru ilości kolorów
        createCheckBoxes();
        // przycisk graj
        playButton();
        // przycisk menu
        menuButton();
        // stworzenie ostrzeżenia przed ilością zaznaczonych checkboxów
        warning();
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
                int howManyChecked = 0;
                int whichChecked = -1;
                for(int i = 0 ; i < checkBoxes.size; i++){
                    if(checkBoxes.get(i).isChecked()) {
                        howManyChecked++;
                        whichChecked = i;
                    }
                }

                if(howManyChecked == 1){
                    game.setScreen(new GameScreen(game, whichChecked, false));
                    dispose();
                } else if(howManyChecked > 1 || howManyChecked == 0){
                    stage.addActor(warning);
                }
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

    public void createTitle(){
        style = new Label.LabelStyle();
        style.font = game.titleFont;

        title = new Label("Pasjans", style);
        title.setX(game.SCREEN_WIDTH/2 - title.getWidth()/2);
        title.setY(game.SCREEN_HEIGHT/2 + 300);
        stage.addActor(title);
    }

    public void createInfo(){
        style.font = new BitmapFont(Gdx.files.internal("fonts/menu_font.fnt"));

        info = new Label("Z ilu kolorow chcesz ukladac Pasjansa?", style);
        info.setX(game.SCREEN_WIDTH/2 - info.getWidth()/2);
        info.setY(title.getY() - info.getHeight());
        stage.addActor(info);
    }

    public void createCheckBoxes(){
        TextureAtlas atlas = new TextureAtlas("checkbox/checkbox.pack");
        Skin skin = new Skin(atlas);
        CheckBox.CheckBoxStyle style = new CheckBox.CheckBoxStyle();

        checkBoxes = new Array<>();

        style.checkboxOff = skin.getDrawable("checkbox_unchecked");
        style.checkboxOn = skin.getDrawable("checkbox_checked");
        style.font = new BitmapFont(Gdx.files.internal("fonts/checkbox_font.fnt"));

        oneColor = new CheckBox("   Jeden kolor", style);
        oneColor.setX(game.SCREEN_WIDTH/2 - oneColor.getWidth()/2);
        oneColor.setY(info.getY() - oneColor.getHeight() - 30);
        stage.addActor(oneColor);
        checkBoxes.add(oneColor);

        twoColors = new CheckBox("   Dwa kolory", style);
        twoColors.setX(game.SCREEN_WIDTH/2 - twoColors.getWidth()/2);
        twoColors.setY(oneColor.getY() - twoColors.getHeight() - 30);
        stage.addActor(twoColors);
        checkBoxes.add(twoColors);

        fourColors = new CheckBox("Cztery kolory", style);
        fourColors.setX(game.SCREEN_WIDTH/2 - fourColors.getWidth()/2);
        fourColors.setY(twoColors.getY() - fourColors.getHeight() - 30);
        stage.addActor(fourColors);
        checkBoxes.add(fourColors);
    }

    public void playButton(){
        playButton = new PlayButton();
        playButton.getButton().setX(game.SCREEN_WIDTH/2 - playButton.getButton().getWidth()/2);
        playButton.getButton().setY(fourColors.getY() - playButton.getButton().getHeight() - 30);
        stage.addActor(playButton.getButton());
    }

    public void menuButton(){
        menuButton = new MenuButton();
        menuButton.getButton().setX(game.SCREEN_WIDTH/2 - menuButton.getButton().getWidth()/2);
        menuButton.getButton().setY(playButton.getButton().getY() - menuButton.getButton().getHeight() - 30);
        stage.addActor(menuButton.getButton());
    }

    public void warning(){
        style.font = new BitmapFont(Gdx.files.internal("fonts/checkbox_font.fnt"));

        warning = new Label("Musisz zaznaczyc jedno pole!", style);
        warning.setX(game.SCREEN_WIDTH/2 - warning.getWidth()/2);
        warning.setY(20);
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
