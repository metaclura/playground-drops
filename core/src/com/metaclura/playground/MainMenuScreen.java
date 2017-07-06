package com.metaclura.playground;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

import sun.applet.Main;

/**
 * Created by Miguel on 02/07/2017.
 */

public class MainMenuScreen implements Screen {
    private final Playground game;
    private final OrthographicCamera camera;

    public MainMenuScreen(Playground game ) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho( false, 800, 480 );
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor( 0.2f, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        // Reposition camera
        camera.update();
        // Batch coordinate system
        game.batch.setProjectionMatrix( camera.combined );

        game.batch.begin();
        game.font.setColor(Color.CYAN);
        game.font.draw( game.batch, "Welcome to DROPLETS!!", 100, 150 );
        game.font.draw( game.batch, "Tap anywhere to Begin", 100, 100 );

        game.batch.end();

        if ( Gdx.input.isTouched() ) {
            game.setScreen( new GameScreen( this.game ));
        }
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

    }
}
