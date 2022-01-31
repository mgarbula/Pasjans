package com.mygdx.pasjans.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.pasjans.Pasjans;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Pasjans";
		config.resizable = false;
		config.width = 1920;
		config.height = 1080;
		config.fullscreen = true;
		new LwjglApplication(new Pasjans(), config);
	}
}
