package com.metaclura.playground;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;

/**
 * Created by Metaclura on 09/06/2017.
 */

public class AnimationActor extends Group {
    private static class AnimationDrawable extends BaseDrawable {
        public final Animation anim;
        private float stateTime = 0;

        public AnimationDrawable(Animation anim) {
            this.anim = anim;
            setMinWidth(((TextureRegion)anim.getKeyFrame(0)).getRegionWidth());
            setMinHeight(((TextureRegion)anim.getKeyFrame(0)).getRegionHeight());
        }

        public void act(float delta) {
            stateTime += delta;
        }

        public void draw(Batch batch, float x, float y, float originX, float originY, float width, float height, float scaleX, float scaleY, float rotation) {
            TextureRegion keyFrame = (TextureRegion) anim.getKeyFrame(stateTime, true);
            batch.draw(keyFrame, x, y, originX, originY, width, height, scaleX, scaleY, rotation);
        }
    }
    private final AnimationDrawable drawable;
    private Texture txt;

    public float getStateTime() {
        return drawable.stateTime;
    }

    public AnimationActor( String texture, int COLS, int ROWS, float width, float height ) {
        super();

        // Animated
        txt = new Texture( Gdx.files.internal( texture ) );
        TextureRegion[][] tmpSplit = TextureRegion.split(txt, txt.getWidth() / COLS, txt.getHeight() / ROWS);
        TextureRegion[] tmpArray = new TextureRegion[ COLS * ROWS];
        // Aplanar a 1 dimension
        int k = 0;
        for (int i = 0; i < tmpSplit.length; i++ ) {
            for ( int j = 0; j < tmpSplit[i].length; j++ ) {
                tmpArray[ k++ ] = tmpSplit[ i ][ j ];
            }
        }

        Animation<TextureRegion> animation = new Animation<TextureRegion>(0.125f, tmpArray);
        this.drawable = new AnimationDrawable( animation );
        this.setOrigin( width/2, height/2 );
        this.setWidth( width );
        this.setHeight( height );

    }

    @Override
    public void act(float delta) {
        super.act(delta);
        drawable.act(delta);
    }

    @Override
    public void draw (Batch batch, float parentAlpha) {
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        drawable.draw(batch, this.getX(), this.getY(), this.getOriginX(), this.getOriginY(), this.getWidth(), this.getHeight(), this.getScaleX(), this.getScaleY(), this.getRotation() );
        super.draw( batch, parentAlpha );
    }

    public void dispose() {
        txt.dispose();
    }

    @Override
    public void addActor( Actor actor ) {
        actor.setOrigin( actor.getWidth() / 2, actor.getHeight() / 2 );
        actor.setX( actor.getX() + this.getWidth() / 2 - actor.getWidth() / 2 );
        actor.setY( actor.getY() + this.getHeight() / 2 - actor.getHeight() / 2 );

        super.addActor( actor );
    }

}
