package com.mygdx.pasjans;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import screens.MainMenuScreen;

public class Pasjans extends Game {

	public SpriteBatch spriteBatch;
	public BitmapFont titleFont;

	@Override
	public void create() {
		spriteBatch = new SpriteBatch();
		// tytuł
		titleFont = new BitmapFont(Gdx.files.internal("fonts/title_font.fnt"));

		this.setScreen(new MainMenuScreen(this));
	}

	public void render(){
		super.render();
	}

	public void dispose(){
		spriteBatch.dispose();
		titleFont.dispose();
	}
}