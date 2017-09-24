package ua.sytor.rpg.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.TimeUtils;
import ua.sytor.rpg.stage.GameStage;
import ua.sytor.rpg.stage.UIStage;

public class GameScreen implements Screen {

    InputMultiplexer inputMultiplexer;

    GameStage gameStage;
    UIStage uiStage;

    //fixed timestep
    private double accumulator = 0;
    private float step = 1.0f / 60.0f;

    public GameScreen(){
        inputMultiplexer = new InputMultiplexer();
        Gdx.input.setInputProcessor(inputMultiplexer);

        uiStage = new UIStage(inputMultiplexer);
        gameStage = new GameStage(inputMultiplexer, uiStage);

        inputMultiplexer.addProcessor(uiStage);
        inputMultiplexer.addProcessor(gameStage);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.109f,0.03f,0.176f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        accumulator += delta;

        while (accumulator>= step) { // The step is 1/10

            gameStage.act(delta);
            uiStage.act(delta);
            accumulator -= step;
        }

        gameStage.getViewport().apply();
        gameStage.draw();

        uiStage.getViewport().apply();
        uiStage.getCamera().update();
        uiStage.draw();
    }

    @Override
    public void resize(int width, int height) {
        gameStage.getViewport().update(width, height,false);
        uiStage.getViewport().update(width, height,true);
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
