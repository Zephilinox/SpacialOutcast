package com.zenex.spacial_outcast.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.zenex.spacial_outcast.SpacialOutcast;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "OuterSpacial";
		config.width = 1600;
		config.height = 960;
		new LwjglApplication(new SpacialOutcast(), config);
	}
}
