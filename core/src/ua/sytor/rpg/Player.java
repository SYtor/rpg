package ua.sytor.rpg;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

public class Player extends Actor {

    TextureAtlas atlas;
    Animation animation;
    private float elapsedTime = 0;
    
    float speed = 500f;

    public Player(){
        init(0,0);
    }

    public Player(float posX, float posY){
        init(posX,posY);
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        
        elapsedTime += Gdx.graphics.getDeltaTime();
        //System.out.println(elapsedTime);
        batch.draw((TextureRegion) animation.getKeyFrame((elapsedTime),true), getX(),getY(),getOriginX(), getOriginY(),
                getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }

    private void init(float x, float y){
        atlas = new TextureAtlas(Gdx.files.internal("character/Character.atlas"));
        Array<TextureAtlas.AtlasRegion> atlasRegions = new Array<TextureAtlas.AtlasRegion>();
        atlasRegions.add(atlas.findRegion("walk1"));
        atlasRegions.add(atlas.findRegion("walk2"));
        animation = new Animation(1/2f,atlasRegions);
        //setHeight(24);
        //setWidth(20);
        setScale(1f);
        setX(x);
        setY(y);
    }
}
