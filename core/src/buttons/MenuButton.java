package buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class MenuButton {

    private TextButton button;

    public MenuButton(){
        TextureAtlas buttonAtlas = new TextureAtlas("win_menu_button/win_menu_button.pack");
        Skin skin = new Skin(buttonAtlas);
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();

        textButtonStyle.up = skin.getDrawable("up");
        textButtonStyle.down = skin.getDrawable("down");
        textButtonStyle.pressedOffsetX = 1;
        textButtonStyle.pressedOffsetY = -1;
        textButtonStyle.font = new BitmapFont(Gdx.files.internal("fonts/menu_font.fnt"));

        button = new TextButton("Menu", textButtonStyle);
    }

    public TextButton getButton() {
        return button;
    }

    public void setButton(TextButton button) {
        this.button = button;
    }
}
