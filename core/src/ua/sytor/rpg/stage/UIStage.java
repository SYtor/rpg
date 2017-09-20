package ua.sytor.rpg.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import ua.sytor.rpg.actor.ScreenControlsActor;

public class UIStage extends Stage implements InputProcessor{

    public static int SCREEN_WIDTH = 512;
    public static int SCREEN_HEIGHT = 297;

    private OrthographicCamera uiCamera;
    private SpriteBatch uiSpriteBatch;

    InputMultiplexer inputManager;

    Skin skin;

    Table table;

    public UIStage(InputMultiplexer inputManager){
        this.inputManager = inputManager;

        setDebugAll(true);

        initCameraAndViewport();

        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        addActor(new ScreenControlsActor());

        table = new Table();
        table.setDebug(true);
        table.setFillParent(true);

        final TextButton button = new TextButton("Click me", skin, "default");
        button.setWidth(500f);
        button.setHeight(50f);

        table.row().fillX().fillY();
        table.left().bottom();
        table.add(button).size(button.getWidth(),button.getHeight());

        //addActor(table);
    }

    private void initCameraAndViewport(){
        uiCamera = new OrthographicCamera(SCREEN_WIDTH,SCREEN_HEIGHT);
        uiCamera.setToOrtho(false,SCREEN_WIDTH,SCREEN_HEIGHT);
        uiSpriteBatch = new SpriteBatch();
        uiSpriteBatch.setProjectionMatrix(uiCamera.combined);
        uiCamera.update();

        setViewport(new ScreenViewport(uiCamera));
    }

    @Override
    public boolean keyDown(int keycode) {
        System.out.println("UI Stage");
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {


        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        System.out.println("touchDown " + pointer);
        Vector2 vector2 = new Vector2();
        getViewport().unproject(vector2.set(screenX,screenY));
        //down
        if(vector2.x > 40 && vector2.x < 60 & vector2.y > 0 & vector2.y < 20 )
            inputManager.keyDown(Input.Keys.S);
        //top
        if(vector2.x > 40 && vector2.x < 60 & vector2.y > 40 & vector2.y < 60 )
            inputManager.keyDown(Input.Keys.W);
        //left
        if(vector2.x > 20 && vector2.x < 40 & vector2.y > 20 & vector2.y < 40 )
            inputManager.keyDown(Input.Keys.A);
        //right
        if(vector2.x > 60 && vector2.x < 80 & vector2.y > 20 & vector2.y < 40 )
            inputManager.keyDown(Input.Keys.D);
        //F use button
        float width = getWidth();
        float height = getHeight();
        if(vector2.x > width-40 && vector2.x < width-20 & vector2.y > 20 & vector2.y < 40 )
            inputManager.keyDown(Input.Keys.F);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        System.out.println("touchUp " + pointer);
        inputManager.keyUp(Input.Keys.A);
        inputManager.keyUp(Input.Keys.W);
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        System.out.println("touchDragged " + pointer);
        Vector2 vector2 = new Vector2();
        getViewport().unproject(vector2.set(screenX,screenY));
        //down
        if(vector2.x > 40 && vector2.x < 60 & vector2.y > 0 & vector2.y < 20 )
            inputManager.keyDown(Input.Keys.S);
        //top
        else if(vector2.x > 40 && vector2.x < 60 & vector2.y > 40 & vector2.y < 60 )
                inputManager.keyDown(Input.Keys.W);
        //left
        else if(vector2.x > 20 && vector2.x < 40 & vector2.y > 20 & vector2.y < 40 )
            inputManager.keyDown(Input.Keys.A);
        //right
        else if(vector2.x > 60 && vector2.x < 80 & vector2.y > 20 & vector2.y < 40 )
            inputManager.keyDown(Input.Keys.D);
        else {
            inputManager.keyUp(Input.Keys.A);
            inputManager.keyUp(Input.Keys.W);
        }
        return false;
    }
}
