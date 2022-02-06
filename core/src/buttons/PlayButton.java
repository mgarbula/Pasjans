package buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class PlayButton {

    private TextButton button;

    public PlayButton(){
        TextureAtlas buttonAtlas = new TextureAtlas("buttons/buttons.pack");
        Skin skin = new Skin(buttonAtlas);
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();

        textButtonStyle.up = skin.getDrawable("button_play_up1");
        textButtonStyle.down = skin.getDrawable("button_play_down1");
        textButtonStyle.pressedOffsetX = 1;
        textButtonStyle.pressedOffsetY = -1;
        textButtonStyle.font = new BitmapFont(Gdx.files.internal("fonts/menu_font.fnt"));

        this.button = new TextButton("Graj", textButtonStyle);
    }

    public TextButton getButton() {
        return button;
    }

    public void setButton(TextButton button) {
        this.button = button;
    }

}
