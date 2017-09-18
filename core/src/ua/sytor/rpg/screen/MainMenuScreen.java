package ua.sytor.rpg.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import ua.sytor.rpg.screenmanager.ScreenEnum;
import ua.sytor.rpg.screenmanager.ScreenManager;

public class MainMenuScreen implements Screen {

    OrthographicCamera uiCamera;
    Viewport uiViewport;
    Stage uiStage;
    SpriteBatch uiSpriteBatch;

    Skin skin;




    public MainMenuScreen(){
        int SCREEN_WIDTH = 600;
        int SCREEN_HEIGHT = 480;
        uiCamera = new OrthographicCamera(SCREEN_WIDTH,SCREEN_HEIGHT);
        uiCamera.setToOrtho(false,SCREEN_WIDTH,SCREEN_HEIGHT);
        uiViewport = new ExtendViewport(SCREEN_WIDTH,SCREEN_HEIGHT,uiCamera);

        uiSpriteBatch = new SpriteBatch();
        uiSpriteBatch.setProjectionMatrix(uiCamera.combined);

        uiCamera.update();

        //UI Stage
        uiStage = new Stage(uiViewport,uiSpriteBatch);

        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

        final TextButton button = new TextButton("Click me", skin, "default");
        button.setWidth(500f);
        button.setHeight(50f);
        button.setPosition(0, 0);

        button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                ScreenManager.getInstance().showScreen(ScreenEnum.GAME);
            }
        });

        uiStage.addActor(button);


        Label label = new Label("Hello",skin);
        label.setPosition(SCREEN_WIDTH/2-label.getWidth(),SCREEN_HEIGHT/2-label.getHeight());
        uiStage.addActor(label);

        Gdx.input.setInputProcessor(uiStage);
    }



    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        uiViewport.apply();
        uiCamera.update();
        uiStage.act(delta);
        uiStage.draw();
    }

    @Override
    public void resize(int width, int height) {
        uiViewport.update(width, height,false);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
