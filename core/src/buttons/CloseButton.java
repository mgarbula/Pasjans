package buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class CloseButton{

    private TextButton button;

    public CloseButton(){
        TextureAtlas atlas = new TextureAtlas("buttons/buttons.pack");
        Skin skin = new Skin(atlas);
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();

        textButtonStyle.up = skin.getDrawable("button_close_up1");
        textButtonStyle.down = skin.getDrawable("button_close_down1");
        textButtonStyle.pressedOffsetX = 1;
        textButtonStyle.pressedOffsetY = -1;
        textButtonStyle.font = new BitmapFont(Gdx.files.internal("fonts/menu_font.fnt"));

        button = new TextButton("Zamknij", textButtonStyle);
    }

    public TextButton getButton() {
        return button;
    }

    public void setButton(TextButton button) {
        this.button = button;
    }

}
