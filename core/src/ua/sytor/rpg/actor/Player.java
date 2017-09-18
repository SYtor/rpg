package ua.sytor.rpg.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;
import ua.sytor.rpg.interfaces.PlayerInteractListener;

import java.util.*;

public class Player extends Actor implements InputProcessor{

    private int height = 24, width = 20;

    private float velocityX, velocityY;
    private float speed = 1f;

    private TextureAtlas atlas;
    private Animation animation;
    private float elapsedTime = 0;

    private Rectangle collision;

    private PlayerInteractListener interactListener;

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
    public boolean keyDown(int keycode) {
        switch (keycode){
            case Input.Keys.A:
                velocityX = -speed;
                break;
            case Input.Keys.D:
                velocityX = speed;
                break;
            case Input.Keys.W:
                velocityY = speed;
                break;
            case Input.Keys.S:
                velocityY = -speed;
                break;
            case Input.Keys.F:
            default:
                if(interactListener!=null)
                    interactListener.buttonPress(keycode);
                break;

        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        System.out.println(velocityX + " " + velocityY);

        switch (keycode){
            case Input.Keys.A:
            case Input.Keys.D:
                velocityX = 0;
                break;
            case Input.Keys.W:
            case Input.Keys.S:
                velocityY = 0;
                break;
        }

        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        /*
        Vector2 vector2 = new Vector2();
        getStage().getViewport().unproject(vector2.set(screenX,screenY));
        setPosition(vector2.x,vector2.y);
        */
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
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

    public void setInteractListener(PlayerInteractListener interactListener) {
        this.interactListener = interactListener;
    }
}

