package screens;

import cards.Card;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.pasjans.Pasjans;

import java.util.ArrayList;
import java.util.Collections;

public class GameScreen implements Screen {
    final Pasjans game;

    private final int SIZE_OF_DECK = 104;
    private final int CARD_HEIGHT = 230;
    private final int CARD_WIDTH = 160;
    private final int SPACE_BETWEEN_CARDS = 35;
    private final int HOW_MANY_CARDS_TO_ROLL_STACK = 13;
    private final OrthographicCamera camera;
    private final SpriteBatch batch;
    private final Sound cardOnTable;
    private final String[] names = {"ace", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten",
            "jack", "queen", "king"}; // names of cards

    private ArrayList<Card> deck; // ArrayList of spades
    private ArrayList<ArrayList<Card>> stacks;
    private ArrayList<Rectangle> goodStacks; // ułożone stacki
    private ArrayList<Texture> goodStacksTextures; // karty dobrych stacków
    private ArrayList<Rectangle> deckRectangle; // zakryte karty
    private ArrayList<Rectangle> emptyStacks;
    private int whichStack, whichCard; // do poruszania kartami
    private int howManyCardsToMove; // zmienna przechowująca ilość przenoszonych kart
    private boolean wasClicked, moveOne, moveMultiple; // zmienne do przesuwania, żeby się nie przesuwały inne karty
    boolean oneCard, multipleCards, breakLoop; // zmienne do przesuwania jednej i wielu kart
    private Texture cardBack;
    private int howManyColors;
    private int howManyMoves;
    private long startTime; // czas gry

    public GameScreen(final Pasjans game, int howManyColors) {
        this.game = game;
        this.howManyColors = howManyColors;

        // ustawienie kamery (zawsze pokazuje obszar 1920x1080)
        camera = new OrthographicCamera();
        camera.setToOrtho(false, game.SCREEN_WIDTH, game.SCREEN_HEIGHT);

        batch = new SpriteBatch();

        makeDeck();
        makeStacks();
        makePlaceForStacks();
        makePlaceForHiddenCards();
        makePlaceForEmptyStacks();

        cardOnTable = Gdx.audio.newSound(Gdx.files.internal("card_on_table_2.mp3"));
        cardBack = new Texture(Gdx.files.internal("rewers.png"));
        startTime = TimeUtils.millis();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0.4f, 0, 1);
        camera.update();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        displayDeck();
        displayGoodStacks();
        displayCards();
        //displayEmptyStacks();
        inputProcesses();
        drawMovingCard();
        batch.end();
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
        for (int i = 0; i < deck.size(); i++)
            deck.get(i).getImage().dispose();

        for (int i = 0; i < stacks.size(); i++)
            for (int j = 0; j < stacks.get(i).size(); j++)
                stacks.get(i).get(j).getImage().dispose();
        batch.dispose();
    }

    public void makePlaceForHiddenCards() {
        // miejsce na zakryte karty
        deckRectangle = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            deckRectangle.add(new Rectangle());
            deckRectangle.get(i).x = 1698 - SPACE_BETWEEN_CARDS * i;
            deckRectangle.get(i).y = 65;
            deckRectangle.get(i).width = CARD_WIDTH;
            deckRectangle.get(i).height = CARD_HEIGHT;
        }
    }

    // metoda robiąca spades
    public void makeDeck() {
        deck = new ArrayList<>();
        switch (howManyColors){
            case 0: // jeden kolor
                for (int i = 0; i < SIZE_OF_DECK; i++) {
                    int value = i % 13 + 1;
                    String name = names[value - 1];
                    Texture image = new Texture(Gdx.files.internal("" + (value) + "_of_spades.png"));

                    Card card = new Card(value, name, "spades", image);
                    deck.add(card);
                }
                break;
            case 1: // dwa kolory
                // pierwszy kolor
                for (int i = 0; i < SIZE_OF_DECK/2; i++) {
                    int value = i % 13 + 1;
                    String name = names[value - 1];
                    Texture image = new Texture(Gdx.files.internal("" + (value) + "_of_spades.png"));

                    Card card = new Card(value, name, "spades", image);
                    deck.add(card);
                }
                // drugi kolor
                for (int i = 0; i < SIZE_OF_DECK/2; i++) {
                    int value = i % 13 + 1;
                    String name = names[value - 1];
                    Texture image = new Texture(Gdx.files.internal("" + (value) + "_of_hearts.png"));

                    Card card = new Card(value, name, "hearts", image);
                    deck.add(card);
                }
                break;
            case 2: // cztery kolory
                // pierwszy kolor
                for (int i = 0; i < SIZE_OF_DECK/4; i++) {
                    int value = i % 13 + 1;
                    String name = names[value - 1];
                    Texture image = new Texture(Gdx.files.internal("" + (value) + "_of_spades.png"));

                    Card card = new Card(value, name, "spades", image);
                    deck.add(card);
                }
                // drugi kolor
                for (int i = 0; i < SIZE_OF_DECK/4; i++) {
                    int value = i % 13 + 1;
                    String name = names[value - 1];
                    Texture image = new Texture(Gdx.files.internal("" + (value) + "_of_hearts.png"));

                    Card card = new Card(value, name, "hearts", image);
                    deck.add(card);
                }
                // trzeci kolor
                for (int i = 0; i < SIZE_OF_DECK/4; i++) {
                    int value = i % 13 + 1;
                    String name = names[value - 1];
                    Texture image = new Texture(Gdx.files.internal("" + (value) + "_of_clubs.png"));

                    Card card = new Card(value, name, "clubs", image);
                    deck.add(card);
                }
                // czwarty kolor
                for (int i = 0; i < SIZE_OF_DECK/4; i++) {
                    int value = i % 13 + 1;
                    String name = names[value - 1];
                    Texture image = new Texture(Gdx.files.internal("" + (value) + "_of_diamonds.png"));

                    Card card = new Card(value, name, "diamonds", image);
                    deck.add(card);
                }
                break;
        }
        Collections.shuffle(deck);
    }

    // metoda robiąca stosy początkowe
    public void makeStacks() {
        stacks = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            ArrayList<Card> bufor = new ArrayList<>(); // ArrayList of Card służąca do przechowywania. na końcu jest wrzucana do stacks
            if (i < 4) {
                for (int j = 0; j < 6; j++) {
                    bufor.add(deck.get(0)); // dodaje pierwszy z decku
                    deck.remove(0); // usuwa z decku
                    if (j == 5)
                        bufor.get(j).setKnown(true);
                }
            } else {
                for (int j = 0; j < 5; j++) {
                    bufor.add(deck.get(0));
                    deck.remove(0);
                    if (j == 4)
                        bufor.get(j).setKnown(true);
                }
            }
            stacks.add(bufor);
        }
    }

    public void makePlaceForStacks() {
        int addX = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < stacks.get(i).size(); j++)
                addX = makeRectangle(i, j, addX);
        }
    }

    public int makeRectangle(int i, int j, int addX) {
        addX = setX(i, j, addX);
        setY(i, j);

        return addX;
    }

    // metoda ustawiajaca współrzędne x
    public int setX(int i, int j, int addX) {
        if (i == 0) {
            stacks.get(i).get(j).getRectangle().x = 25;
            if (j == stacks.get(i).size() - 1) // tyle prostokątow na stack
                addX = 25;
        } else {
            stacks.get(i).get(j).getRectangle().x = addX + 30 + stacks.get(i).get(j).getRectangle().getWidth();
            if (j == stacks.get(i).size() - 1)
                addX += 30 + stacks.get(i).get(j).getRectangle().getWidth();
        }
        return addX; // addX pokazuje, o ile się przesunąć w prawo (czyli jak następny stack)
    }

    // metoda ustawiająca współrzędne y
    public void setY(int i, int j) {
        int mnoznik = j;
        stacks.get(i).get(j).getRectangle().y = 815 - SPACE_BETWEEN_CARDS * mnoznik;
    }

    public void makePlaceForEmptyStacks() {
        emptyStacks = new ArrayList<>();
        for (int i = 0; i < stacks.size(); i++) {
            Rectangle rectangle = new Rectangle();
            rectangle.setHeight(CARD_HEIGHT);
            rectangle.setWidth(CARD_WIDTH);
            rectangle.setX(stacks.get(i).get(0).getRectangle().getX());
            rectangle.setY(stacks.get(i).get(0).getRectangle().getY());
            emptyStacks.add(rectangle);
        }
    }

    // metoda wyświetlająca karty, które są dostępne do gry
    public void displayCards() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < stacks.get(i).size(); j++) {
                if (stacks.get(i).get(j).isKnown())
                    batch.draw(stacks.get(i).get(j).getImage(), stacks.get(i).get(j).getRectangle().x, stacks.get(i).get(j).getRectangle().y);
                else
                    batch.draw(stacks.get(i).get(j).getBack(), stacks.get(i).get(j).getRectangle().x, stacks.get(i).get(j).getRectangle().y);
            }
        }
    }

    public void inputProcesses() {
        Gdx.input.setInputProcessor(new InputProcessor() {
            @Override
            public boolean keyDown(int keycode) {
                return false;
            }

            @Override
            public boolean keyUp(int keycode) {
                return false;
            }

            @Override
            public boolean keyTyped(char character) {
                return false;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                multipleCards = true;
                if (button == Input.Buttons.LEFT) {
                    // czy myszka kliknięta (potrzebne do przesuwania, żeby nie brało innych kart na które najeżdżam)
                    for (int i = 0; i < stacks.size(); i++) {
                        for (int j = stacks.get(i).size() - 1; j >= 0; j--) {
                            if (stacks.get(i).get(j).getRectangle().contains(screenX, game.SCREEN_HEIGHT - screenY) && stacks.get(i).get(j).isKnown()) {
                                if (j == stacks.get(i).size() - 1) {
                                    // przesuwanie jednej karty
                                    wasClicked = true;
                                    oneCard = true;
                                    whichStack = i;
                                    whichCard = j;
                                    return true;
                                } else {
                                    howManyCardsToMove = 0;
                                    // przesuwanie wielu kart
                                    for (int k = j; k < stacks.get(i).size() - 1; k++) {
                                        howManyCardsToMove++;
                                        if (stacks.get(i).get(k).getValue() - stacks.get(i).get(k + 1).getValue() != 1) {
                                            multipleCards = false;
                                            return true;
                                        }
                                    }
                                    if (multipleCards) {
                                        howManyCardsToMove++; // zwiększam o jeden, bo w pętli wyżej nie dojeżdżam do ostatniej karty
                                        wasClicked = true;
                                        whichStack = i;
                                        whichCard = j;
                                        return true;
                                    }
                                }
                            }
                        }
                    }

                    // obsługa dorzucania kart z nieodkrytego stosu
                    if (deckRectangle.size() > 0) {
                        if (clickOnDeck(screenX, screenY)) {
                            boolean dontGiveCards = false; // nie mogę dawać kart, jeśli są puste pola
                            for (int i = 0; i < stacks.size(); i++)
                                if (stacks.get(i).size() == 0) {
                                    dontGiveCards = true;
                                    break;
                                }
                            if (!dontGiveCards) {
                                for (int i = 0; i < 10; i++) {
                                    addCards(i);
                                }
                                deckRectangle.remove(deckRectangle.size() - 1);
                            }
                            return true;
                        }
                    }

                    return true;
                }

                return false;
            }

            // warunki w touchDown
            public boolean clickOnDeck(int screenX, int screenY){
                if(screenX >= deckRectangle.get(deckRectangle.size() - 1).getX() && screenX <= deckRectangle.get(deckRectangle.size() - 1).getX() + deckRectangle.get(0).getWidth()
                        && screenY <= camera.viewportHeight - deckRectangle.get(0).getY() && screenY >= camera.viewportHeight - deckRectangle.get(0).getY() - deckRectangle.get(0).getHeight())
                    return true;
                else
                    return  false;
            }


            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                breakLoop = false;
                if (button == Input.Buttons.LEFT) {
                    // jedna karta lub wiele kart
                    if (moveOne || moveMultiple) {
                        for (int toStack = 0; toStack < stacks.size(); toStack++) {
                            // nie muszę sprawdzać stosu z którego biorę
                            if (toStack == whichStack)
                                toStack++;

                            // zabezpieczenie przed wyjściem poza ilość stacków
                            if (toStack == 10)
                                break;

                            // przeniesienie na pusty stos
                            if (overlapsEmpty(whichCard, whichStack, toStack) && !sizeBiggerThanZero(toStack)) {
                                move(toStack);
                                breakLoop = true;
                                break;
                            } else if (sizeBiggerThanZero(toStack) && overlapsNotEmpty(whichCard, whichStack, toStack) && cardKnown(toStack) && goodValue(whichCard, whichStack, toStack)) {
                                // przenieś kartę na nowy stos
                                move(toStack);
                                isAbleToRoll(toStack);
                                breakLoop = true;
                                break;
                            }
                        }
                    }
                    // karta wraca na swój stos
                    if (!breakLoop) {
                        if (moveOne) {
                            moveOne = false;
                            oneCardBackOnStack(whichStack, whichCard);
                        } else if (moveMultiple) {
                            moveMultiple = false;
                            multipleCardsBackOnStack(whichStack, whichCard);
                        }
                    }
                    return true;
                }
                return false;
            }

            // warunki w if'ach do touchUp()

            public boolean sizeBiggerThanZero(int toStack){
                if(stacks.get(toStack).size() == 0)
                    return false;
                else
                    return true;
            }

            public boolean overlapsEmpty(int whichCard, int whichStack, int toStack){
                if(stacks.get(whichStack).get(whichCard).getRectangle().overlaps(emptyStacks.get(toStack)))
                    return true;
                else
                    return false;
            }

            public boolean overlapsNotEmpty(int whichCard, int whichStack, int toStack){
                if(stacks.get(whichStack).get(whichCard).getRectangle().overlaps(stacks.get(toStack).get(stacks.get(toStack).size() - 1).getRectangle()))
                    return true;
                else
                    return false;
            }

            public boolean cardKnown(int toStack){
                if(stacks.get(toStack).get(stacks.get(toStack).size() - 1).isKnown())
                    return true;
                else
                    return false;
            }

            public boolean goodValue(int whichCard, int whichStack, int toStack){
                if(stacks.get(whichStack).get(whichCard).getValue() - stacks.get(toStack).get(stacks.get(toStack).size() - 1).getValue() == -1)
                    return true;
                else
                    return false;
            }

            // obsługa przesuwania kart
            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {

                    // poruszanie jedną kartą (ustawienie zmiennych boolean'owych, żeby przesuwanie było na zbocze narastające)
                    if (wasClicked && oneCard)
                        moveOne();

                    // poruszanie kartą
                    if (moveOne) {
                        stacks.get(whichStack).get(whichCard).getRectangle().setCenter(setScreenX(screenX), game.SCREEN_HEIGHT - setScreenY(screenY));
                        return true;
                    }

                    // poruszanie wieloma kartami (ustawienie zmiennych boolean'owych, żeby przesuwanie było na zbocze narastające)
                    if (wasClicked && multipleCards)
                        moveMultiple();

                    // poruszanie kartami
                    if (moveMultiple) {
                        int card = 0; // która karta zmienia współrzędne
                        for (int i = whichCard; i < stacks.get(whichStack).size(); i++) {
                            stacks.get(whichStack).get(i).getRectangle().setCenter(setScreenX(screenX), game.SCREEN_HEIGHT - setScreenY(screenY) - CARD_HEIGHT / 2 + 20 - card * SPACE_BETWEEN_CARDS); // 230/2 - połowa wys karty, 20 - odległosc od górnej krawędzi karty
                            card++;
                        }
                        return true;
                    }
                }
                return false;
            }

            @Override
            public boolean mouseMoved(int screenX, int screenY) {
                return false;
            }

            @Override
            public boolean scrolled(float amountX, float amountY) {
                return false;
            }
        });
    }

    // metoda dodająca karty
    public void addCards(int i) {
        cardOnTable.play();
        deck.get(0).setKnown(true);
        setCoordinates(deck.get(0), i);
        stacks.get(i).add(deck.get(0));
        deck.remove(0);
    }

    public void move(int i) {
        if (moveOne) {// jedna karta
            moveOneCard(i, whichStack, whichCard);
            moveOne = false;
            howManyMoves++;
        }
        if (moveMultiple) {
            // kilka kart
            moveMultipleCards(i, whichStack, whichCard);
            moveMultiple = false;
            howManyMoves++;
        }
    }

    // metoda przenosząca kartę na inny stos
    public void moveOneCard(int toStack, int fromStack, int whichCard) {
        // przenoszenie na pusty stos
        if (stacks.get(toStack).size() == 0) {
            stacks.get(fromStack).get(whichCard).getRectangle().setX(emptyStacks.get(toStack).getX());
            stacks.get(fromStack).get(whichCard).getRectangle().setY(emptyStacks.get(toStack).getY());
        } else {
            stacks.get(fromStack).get(whichCard).getRectangle().setX(stacks.get(toStack).get(stacks.get(toStack).size() - 1).getRectangle().getX());
            stacks.get(fromStack).get(whichCard).getRectangle().setY(stacks.get(toStack).get(stacks.get(toStack).size() - 1).getRectangle().getY() - SPACE_BETWEEN_CARDS);
        }
        // dodaję kartę na nowy stos
        stacks.get(toStack).add(stacks.get(fromStack).get(whichCard));
        //cardOnTable.play();
        // usuwam kartą stąd, skąd ją brałem
        stacks.get(fromStack).remove(whichCard);
        // poznaję nową kartę
        if (stacks.get(fromStack).size() > 0)
            stacks.get(fromStack).get(stacks.get(fromStack).size() - 1).setKnown(true);
    }

    // poruszanie wieloma kartami
    public void moveMultipleCards(int toStack, int fromStack, int whichCard) {
        for (int j = 1; j <= howManyCardsToMove; j++) {
            // przenoszenie na pusty stos
            if (stacks.get(toStack).size() == 0) {
                stacks.get(fromStack).get(whichCard).getRectangle().setX(emptyStacks.get(toStack).getX());
                stacks.get(fromStack).get(whichCard).getRectangle().setY(emptyStacks.get(toStack).getY());
            } else {
                stacks.get(fromStack).get(whichCard).getRectangle().setX(stacks.get(toStack).get(stacks.get(toStack).size() - 1).getRectangle().getX());
                stacks.get(fromStack).get(whichCard).getRectangle().setY(stacks.get(toStack).get(stacks.get(toStack).size() - 1).getRectangle().getY() - SPACE_BETWEEN_CARDS);

            }
            // dodaję kartę na nowy stos
            stacks.get(toStack).add(stacks.get(fromStack).get(whichCard));
            cardOnTable.play();
            // usuwam kartę stąd, skąd brałem
            stacks.get(fromStack).remove(whichCard);
            // poznaję nową kartę
            if (stacks.get(whichStack).size() > 0)
                stacks.get(fromStack).get(stacks.get(fromStack).size() - 1).setKnown(true);
        }
    }

    public void isAbleToRoll(int stack) {
        if (stacks.get(stack).size() >= HOW_MANY_CARDS_TO_ROLL_STACK) {
            int king = 0;
            boolean roll = false;
            int howManyToRoll = 0;
            for (int i = 0; i < stacks.get(stack).size(); i++) {
                if (stacks.get(stack).get(i).isKnown() && stacks.get(stack).get(i).getValue() == 13) {
                    king = i;
                    break;
                }
            }
            for (int i = king; i < stacks.get(stack).size() - 1; i++) {
                if (stacks.get(stack).get(i).getValue() - stacks.get(stack).get(i + 1).getValue() == 1 && stacks.get(stack).get(i).getColor().equals(stacks.get(stack).get(i + 1).getColor())) {
                    roll = true;
                    howManyToRoll++;
                } else {
                    roll = false;
                    break;
                }
            }
            if (roll && howManyToRoll == HOW_MANY_CARDS_TO_ROLL_STACK - 1) // -1 bo howManyToRoll może się zwiększyć tylko 12 razy
                oneStepForwardToEndTheGame(king, stack);
        }
    }

    // umożliwienie ruchu jednej karcie
    public void moveOne() {
        moveOne = true;
        wasClicked = false;
        oneCard = false;
    }

    // umożliwienie ruchu wielu kartom
    public void moveMultiple() {
        moveMultiple = true;
        wasClicked = false;
        multipleCards = false;
    }

    // jedna karta wraca na swój stos
    public void oneCardBackOnStack(int fromStack, int whichCard) {
        // powrót na pusty stos
        if (stacks.get(fromStack).size() == 1) {
            stacks.get(fromStack).get(whichCard).getRectangle().setX(emptyStacks.get(fromStack).getX());
            stacks.get(fromStack).get(whichCard).getRectangle().setY(emptyStacks.get(fromStack).getY());
        } else {
            stacks.get(fromStack).get(whichCard).getRectangle().setX(stacks.get(fromStack).get(whichCard - 1).getRectangle().getX());
            stacks.get(fromStack).get(whichCard).getRectangle().setY(stacks.get(fromStack).get(whichCard - 1).getRectangle().getY() - SPACE_BETWEEN_CARDS);
        }
    }

    // wiele kart wraca na swój stos
    public void multipleCardsBackOnStack(int fromStack, int whichCard) {
        for (int i = 1; i <= howManyCardsToMove; i++) {
            // zabezpieczenie przed braniem ostatniej karty ze stacku
            if (whichCard == 0) {
                stacks.get(fromStack).get(whichCard).getRectangle().setX(emptyStacks.get(fromStack).getX());
                stacks.get(fromStack).get(whichCard).getRectangle().setY(emptyStacks.get(fromStack).getY());
            } else {
                stacks.get(fromStack).get(whichCard).getRectangle().setX(stacks.get(fromStack).get(whichCard - 1).getRectangle().getX());
                stacks.get(fromStack).get(whichCard).getRectangle().setY(stacks.get(fromStack).get(whichCard - 1).getRectangle().getY() - SPACE_BETWEEN_CARDS);
            }
            whichCard++;
        }
    }

    public void oneStepForwardToEndTheGame(int firstCard, int stack) {
        // zrobienie miejsca na karty
        if (goodStacks == null) {
            goodStacks = new ArrayList<>();
            // zrobienie tablicy z kolorami ułożonych stacków
            goodStacksTextures = new ArrayList<>();
        }

        String color = stacks.get(stack).get(firstCard).getColor();
        Texture texture = new Texture(Gdx.files.internal("13_of_" + color + ".png"));
        goodStacksTextures.add(texture);

        Rectangle rectangle = new Rectangle();
        rectangle.setHeight(CARD_HEIGHT);
        rectangle.setWidth(CARD_WIDTH);
        rectangle.setY(65);
        rectangle.setX(65 + SPACE_BETWEEN_CARDS * goodStacks.size());
        goodStacks.add(rectangle);
        // usunięcie kart ze stołu
        for (int i = 1; i <= HOW_MANY_CARDS_TO_ROLL_STACK; i++)
            stacks.get(stack).remove(firstCard);
        // odsłonięcie ostatniej karty ze stosu który zrobiłem
        if (stacks.get(stack).size() > 0)
            stacks.get(stack).get(stacks.get(stack).size() - 1).setKnown(true);
    }

    // zabezpieczenia przed wyjściem karty poza ekran
    public int setScreenX(int screenX) {
        if (screenX < CARD_WIDTH / 2) // połowa szerokości karty
            return CARD_WIDTH / 2;
        else if (screenX > game.SCREEN_WIDTH - CARD_WIDTH / 2)
            return game.SCREEN_WIDTH - CARD_WIDTH / 2;
        else
            return screenX;
    }

    public int setScreenY(int screenY) {
        if (moveOne) {
            if (screenY < CARD_HEIGHT / 2 && screenY >= 0)
                screenY = CARD_HEIGHT / 2;
            else if (screenY > game.SCREEN_HEIGHT - CARD_HEIGHT / 2 && screenY <= game.SCREEN_HEIGHT)
                screenY = game.SCREEN_HEIGHT - CARD_HEIGHT / 2;
        } else if (moveMultiple) {
            for (int i = 0; i < howManyCardsToMove; i++) {
                if (screenY < 20 && screenY >= 0)
                    screenY = 20 * (i + 1) + SPACE_BETWEEN_CARDS * i;
            }
        }
        return screenY;
    }

    // metoda rysująca przesuwającą się kartę
    public void drawMovingCard() {
        if (moveOne)
            batch.draw(stacks.get(whichStack).get(whichCard).getImage(), stacks.get(whichStack).get(whichCard).getRectangle().x, stacks.get(whichStack).get(whichCard).getRectangle().y);
        else if (moveMultiple)
            for (int i = 0; i < howManyCardsToMove; i++)
                batch.draw(stacks.get(whichStack).get(whichCard + i).getImage(), stacks.get(whichStack).get(whichCard + i).getRectangle().x, stacks.get(whichStack).get(whichCard + i).getRectangle().y);

    }

    // wyświetla ułożone karty
    public void displayGoodStacks() {
        if (goodStacks != null) {
            for (int i = 0; i < goodStacks.size(); i++)
                batch.draw(goodStacksTextures.get(i), goodStacks.get(i).getX(), goodStacks.get(i).getY());
            if (goodStacks.size() == 8)
                game.setScreen(new WinScreen(game, howManyMoves, TimeUtils.timeSinceMillis(startTime)));
        }
    }

    public void setCoordinates(Card card, int i) {
        card.getRectangle().x = stacks.get(i).get(stacks.get(i).size() - 1).getRectangle().getX();
        card.getRectangle().y = stacks.get(i).get(stacks.get(i).size() - 1).getRectangle().getY() - SPACE_BETWEEN_CARDS;
    }

    // metoda wyświetlająca nieodkrytą talię
    public void displayDeck() {
        if (deckRectangle.size() <= 5) {
            for (int i = 0; i < deckRectangle.size(); i++)
                batch.draw(cardBack, deckRectangle.get(i).x, deckRectangle.get(i).y);
        }
    }

    // metoda do wyświetlania ustych stackow
    /*public void displayEmptyStacks() {
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.WHITE);
        for (int i = 0; i < stacks.size(); i++) {
            if (stacks.get(i).size() == 0) {
                float x = emptyStacks.get(i).getX();
                float y = emptyStacks.get(i).getY();
                float height = emptyStacks.get(i).getHeight();
                float width = emptyStacks.get(i).getWidth();
                shapeRenderer.rect(x, y, width, height);
                //}
            }
            shapeRenderer.end();
        }
    }*/
}
