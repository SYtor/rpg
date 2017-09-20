package ua.sytor.rpg.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import ua.sytor.rpg.stage.GameStage;
import ua.sytor.rpg.stage.UIStage;

public class GameScreen implements Screen {

    InputMultiplexer inputMultiplexer;

    GameStage gameStage;
    UIStage uiStage;

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
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameStage.getViewport().apply();
        gameStage.act(delta);
        gameStage.draw();

        uiStage.getViewport().apply();
        uiStage.getCamera().update();
        uiStage.act(delta);
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
