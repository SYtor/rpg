package ua.sytor.rpg.stage;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class UIStage extends Stage {

    public static int SCREEN_WIDTH = 512;
    public static int SCREEN_HEIGHT = 297;

    //UI
    OrthographicCamera uiCamera;
    SpriteBatch uiSpriteBatch;

    public UIStage(){
        //UI Camera and Batch
        uiCamera = new OrthographicCamera(SCREEN_WIDTH,SCREEN_HEIGHT);
        uiCamera.setToOrtho(false,SCREEN_WIDTH,SCREEN_HEIGHT);

        uiSpriteBatch = new SpriteBatch();
        uiSpriteBatch.setProjectionMatrix(uiCamera.combined);

        uiCamera.update();

        //UI Stage
        setViewport(new ExtendViewport(SCREEN_WIDTH,SCREEN_HEIGHT,uiCamera));
    }

}
