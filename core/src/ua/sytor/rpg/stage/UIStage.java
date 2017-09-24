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

    private OrthographicCamera uiCamera;
    private SpriteBatch uiSpriteBatch;

    private InputMultiplexer inputManager;

    private Skin skin;

    private Table table;

    public float size = 30* Gdx.graphics.getDensity();

    public UIStage(InputMultiplexer inputManager){
        this.inputManager = inputManager;

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
        uiCamera = new OrthographicCamera();
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
        if(vector2.x > 2*size && vector2.x < 3*size & vector2.y > 0 & vector2.y < size )
            inputManager.keyDown(Input.Keys.S);
        //top
        if(vector2.x > 2*size && vector2.x < 3*size & vector2.y > 2*size & vector2.y < 3*size )
            inputManager.keyDown(Input.Keys.W);
        //left
        if(vector2.x > size && vector2.x < 2*size & vector2.y > size & vector2.y < 2*size )
            inputManager.keyDown(Input.Keys.A);
        //right
        if(vector2.x > 3*size && vector2.x < 4*size & vector2.y > size & vector2.y < 2*size )
            inputManager.keyDown(Input.Keys.D);
        //F use button
        float width = getWidth();
        float height = getHeight();
        if(vector2.x > width-2*size && vector2.x < width-size & vector2.y > size & vector2.y < 2*size )
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
        Vector2 vector2 = new Vector2();
        getViewport().unproject(vector2.set(screenX,screenY));

        float width = getWidth();
        float height = getHeight();

        //down
        if(vector2.x > 2*size && vector2.x < 3*size & vector2.y > 0 & vector2.y < size )
            inputManager.keyDown(Input.Keys.S);
        //top
        else if(vector2.x > 2*size && vector2.x < 3*size & vector2.y > 2*size & vector2.y < 3*size )
            inputManager.keyDown(Input.Keys.W);
        //left
        else if(vector2.x > size && vector2.x < 2*size & vector2.y > size & vector2.y < 2*size )
            inputManager.keyDown(Input.Keys.A);
        //right
        else if(vector2.x > 3*size && vector2.x < 4*size & vector2.y > size & vector2.y < 2*size )
            inputManager.keyDown(Input.Keys.D);
        //F
        else if(vector2.x > width-2*size && vector2.x < width-size & vector2.y > size & vector2.y < 2*size )
            inputManager.keyDown(Input.Keys.F);
        else {
            inputManager.keyUp(Input.Keys.A);
            inputManager.keyUp(Input.Keys.W);
        }
        return false;
    }
}
