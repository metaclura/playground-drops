package com.metaclura.playground;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

/**
 * Created by Miguel on 02/07/2017.
 */

public class GameScreen implements Screen {
    OrthographicCamera camera;
    Texture bucketImage;
    private Rectangle bucket;
    private Texture raindropImage;
    private Array<Rectangle> raindrops;
    private long lastDropTime;
    private Music rainMusic;
    private Sound dropSound;
    private Playground game;
    private int dropsGathered;

    public GameScreen( Playground game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho( false, 800, 480 );

        rainMusic = Gdx.audio.newMusic( Gdx.files.internal( "rain.mp3" ));
        dropSound = Gdx.audio.newSound( Gdx.files.internal( "drop.wav"));

        rainMusic.setLooping( true );
        rainMusic.play();

        raindropImage = new Texture(Gdx.files.internal("droplet.png"));
        bucketImage = new Texture(Gdx.files.internal("bucket.png"));

        bucket = new Rectangle();
        bucket.x = 800 / 2 - 64 / 2;
        bucket.y = 20;
        bucket.width = 64;
        bucket.height = 64;

        raindrops = new Array<Rectangle>();
        spawmRaindrop();

        dropsGathered = 0;
    }

    public void spawmRaindrop() {
        Rectangle raindrop = new Rectangle();
        raindrop.x = MathUtils.random( 0, 800 - 64 );
        raindrop.y = 480;
        raindrop.width = 64;
        raindrop.height = 64;

        raindrops.add( raindrop );
        lastDropTime = TimeUtils.millis();
    }

    @Override
    public void render ( float delta ) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        interact();

        camera.update();
        game.batch.setProjectionMatrix( camera.combined );
        game.batch.begin();

        game.batch.draw(bucketImage, bucket.x, bucket.y);

        Iterator<Rectangle> it = raindrops.iterator();
        while (it.hasNext()) {
            Rectangle raindrop = it.next();
            raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
            game.batch.draw( raindropImage, raindrop.x, raindrop.y);
            if ( raindrop.y + 64 < 0 ) {
                it.remove();
            }

            if ( raindrop.overlaps( bucket ) ) {
                dropSound.play();
                dropsGathered++;
                it.remove();
            }
        }

        game.font.draw( game.batch, "droplets " + dropsGathered, 10, 470 );
        game.batch.end();

        if ( TimeUtils.millis() - lastDropTime > 1 * 1000 ) {
            spawmRaindrop();
        }
    }

    protected void interact() {
        if ( Gdx.input.isTouched() ) {
            Vector3 touchPos = new Vector3();
            touchPos.set( Gdx.input.getX(), Gdx.input.getY(), 0 );
            camera.unproject( touchPos );
            bucket.x = touchPos.x - ( 64 / 2 );
        }
        if ( bucket.x > 0 && Gdx.input.isKeyPressed(Input.Keys.LEFT ) ) {
            bucket.x -= 200 * Gdx.graphics.getDeltaTime();
        }
        if ( bucket.x < (800 - 64) && Gdx.input.isKeyPressed(Input.Keys.RIGHT) ) {
            bucket.x += 200 * Gdx.graphics.getDeltaTime();
        }
    }

    @Override
    public void dispose () {
        bucketImage.dispose();
        raindropImage.dispose();
        rainMusic.dispose();
        dropSound.dispose();
    }
    @Override
    public void show() {

    }


    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
        rainMusic.stop();
    }

    @Override
    public void resume() {
        rainMusic.play();
    }

    @Override
    public void hide() {

    }

}
