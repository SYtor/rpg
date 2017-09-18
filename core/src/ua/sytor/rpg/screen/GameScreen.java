package ua.sytor.rpg.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import ua.sytor.rpg.stage.GameStage;
import ua.sytor.rpg.stage.UIStage;

public class GameScreen implements Screen {

    GameStage gameStage;
    UIStage uiStage;

    TiledMap tiledMap;
    OrthogonalTiledMapRenderer tiledMapRenderer;

    TiledMapTileLayer layer;


    public GameScreen(){
        //Tiled Map
        tiledMap = new TmxMapLoader().load("map.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap,1f);

        layer = (TiledMapTileLayer) tiledMap.getLayers().get("ground");

        uiStage = new UIStage();
        gameStage = new GameStage(tiledMap,uiStage);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameStage.getViewport().apply();
        //Render tilemap
        tiledMapRenderer.setView((OrthographicCamera) gameStage.getCamera());
        tiledMapRenderer.render();


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
