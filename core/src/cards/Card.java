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
    /*private int xLeftDown, yLeftDown, xRightUp, yRightUp;*/

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

    public void setValue(int value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Texture getImage() {
        return image;
    }

    public void setImage(Texture image) {
        this.image = image;
    }

    public Texture getBack() {
        return back;
    }

    public void setBack(Texture back) {
        this.back = back;
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

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    public float getxLeftDown() { return rectangle.x; }
    public float getyLeftDown() { return rectangle.y; }
    public float getxRightUp() { return rectangle.x + rectangle.getWidth(); }
    public float getyRightUp() { return rectangle.y + rectangle.getHeight(); }
}
