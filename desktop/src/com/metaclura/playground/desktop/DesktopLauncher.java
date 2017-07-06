package com.metaclura.playground.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.metaclura.playground.Playground;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Play";
		config.width = 800;
		config.height = 480;
		new LwjglApplication(new Playground(), config);
	}
}
