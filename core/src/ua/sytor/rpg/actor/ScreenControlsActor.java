package ua.sytor.rpg.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import javafx.scene.shape.Rectangle;

public class ScreenControlsActor extends Actor{

    private ShapeRenderer shapeRenderer;

    public final int size = 20;

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
        shapeRenderer.rect(40,0,20,20);
        //left
        shapeRenderer.rect(20,20,20,20);
        //right
        shapeRenderer.rect(60,20,20,20);
        //top
        shapeRenderer.rect(40,40,20,20);

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
