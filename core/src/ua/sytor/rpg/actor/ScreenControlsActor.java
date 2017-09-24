package ua.sytor.rpg.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import javafx.scene.shape.Rectangle;

public class ScreenControlsActor extends Actor{

    private ShapeRenderer shapeRenderer;

    public float size = 30* Gdx.graphics.getDensity();

    public ScreenControlsActor(){
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        //down
        shapeRenderer.rect(2*size,0, size, size);
        //left
        shapeRenderer.rect(size, size, size, size);
        //right
        shapeRenderer.rect(3*size , size, size, size);
        //top
        shapeRenderer.rect(2*size,2*size , size, size);

        float width = getStage().getWidth();
        float height = getStage().getHeight();
        //use button
        shapeRenderer.rect(width-size*2,size, size, size);
        //minimap
        shapeRenderer.rect(width-size*2,height-size*2, size*2, size*2);
        //menubutton/player
        shapeRenderer.rect(0,height, size, size);

        shapeRenderer.end();

        batch.begin();
    }
}
