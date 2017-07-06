package com.metaclura.playground;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Playground extends Game {
	protected SpriteBatch batch;
    protected BitmapFont font;


    @Override
	public void create () {
		batch = new SpriteBatch();
        font = new BitmapFont( Gdx.files.internal("Ravie.fnt"));

        this.setScreen( new MainMenuScreen(this) );

	}



	@Override
	public void render () {
        super.render();

	}
	
	@Override
	public void dispose () {
		batch.dispose();
        font.dispose();
	}
}
