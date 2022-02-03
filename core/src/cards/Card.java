package cards;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

// klasa karta - przechowuje wszystkie informacje o karcie
public class Card {

    private int value;
    private String name, color;
    private Texture image;
    private Texture back;
    private boolean isKnown;
    private Rectangle rectangle;

    public Card(int value, String name, String color, Texture image){
        this.value = value;
        this.name = name;
        this.color = color;
        this.image = image;
        this.back = new Texture(Gdx.files.internal("rewers.png"));
        this.isKnown = false;
        this.rectangle = new Rectangle();
        rectangle.height = 230;
        rectangle.width = 160;
    }

    public Card(){

    }

    public int getValue() {
        return value;
    }

    public String getColor() {
        return color;
    }

    public Texture getImage() {
        return image;
    }

    public Texture getBack() {
        return back;
    }

    public boolean isKnown() {
        return isKnown;
    }

    public void setKnown(boolean known) {
        isKnown = known;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

}
