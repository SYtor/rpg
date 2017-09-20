package ua.sytor.rpg.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

public class Player extends Actor{

    private int height = 24, width = 20;

    private float velocityX, velocityY;
    private float speed = 1f;

    private TextureAtlas atlas;
    private Animation animation;
    private float elapsedTime = 0;

    private Rectangle collision;

    public Player(){
        velocityX = 0;
        velocityY = 0;

        setHeight(height);
        setWidth(width);

        //Animation setup
        atlas = new TextureAtlas(Gdx.files.internal("character/Character.atlas"));
        Array<TextureAtlas.AtlasRegion> atlasRegions = new Array<TextureAtlas.AtlasRegion>();
        atlasRegions.add(atlas.findRegion("walk1"));
        atlasRegions.add(atlas.findRegion("walk2"));
        animation = new Animation(1/2f,atlasRegions);

        collision = new Rectangle(getX(),getY(),width,height/2);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        //Animation
        elapsedTime += Gdx.graphics.getDeltaTime();
        batch.draw((TextureRegion) animation.getKeyFrame((elapsedTime),true), getX(),getY(),getOriginX(), getOriginY(),
                getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        collision = new Rectangle(getX(),getY(),width,height/2);
    }

    public void setVelocityX(float velocityX) {
        this.velocityX = velocityX;
    }

    public void setVelocityY(float velocityY) {
        this.velocityY = velocityY;
    }

    public void setVelocity(float velocityX, float velocityY){
        setVelocityX(velocityX);
        setVelocityY(velocityY);
    }

    public float getVelocityX() {
        return velocityX;
    }

    public float getVelocityY() {
        return velocityY;
    }

    public Rectangle getCollision() {
        return collision;
    }

    public float getSpeed() {
        return speed;
    }
}

