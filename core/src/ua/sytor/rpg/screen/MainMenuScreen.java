package ua.sytor.rpg.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import ua.sytor.rpg.screenmanager.ScreenEnum;
import ua.sytor.rpg.screenmanager.ScreenManager;

public class MainMenuScreen implements Screen {

    private OrthographicCamera camera;
    private Stage stage;
    private SpriteBatch batch;
    private Skin skin;

    private Table table;

    float density;

    public MainMenuScreen(){

        initCameraAndViewport();

        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

        density = Gdx.graphics.getDensity();

        table = new Table();
        //table.setDebug(true);
        table.setFillParent(true);


        Label label = new Label("Project X",skin);
        table.add(label).expandX();

        table.row();

        final TextButton newGameButton = new TextButton("New Game", skin, "default");

        newGameButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                ScreenManager.getInstance().showScreen(ScreenEnum.GAME);
            }
        });

        table.add(newGameButton).width(400f).top().expandX();

        table.row();

        final TextButton exitButton = new TextButton("Exit", skin, "default");

        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                int error = 1/0;
            }
        });

        table.add(exitButton).width(400f).bottom().expandX();

        stage.addActor(table);

        Gdx.input.setInputProcessor(stage);
    }

    private void initCameraAndViewport(){
        camera = new OrthographicCamera();
        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);
        camera.update();

        stage = new Stage(new ScreenViewport(camera));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height,true);
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
