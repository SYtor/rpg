package ua.sytor.rpg;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import static com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.DEFAULT_CHARS;

public class GameScreen implements Screen {

    TiledMap tiledMap;
    OrthographicCamera camera;
    TiledMapRenderer tiledMapRenderer;
    Player player;

    public static String japaneseAlphabet = "ゲームスタト" + DEFAULT_CHARS;

    SpriteBatch batch;

    BitmapFont font12;

    public GameScreen(){
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera(30,30 * (h/w));
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        //camera.position.x = 800;
        camera.update();

        tiledMap = new TmxMapLoader().load("map.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap,1f);

        player = new Player();

        //stage.addActor(player);
        //Gdx.input.setInputProcessor(stage);

        batch = new SpriteBatch();
        //Fonts
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("jackeyfont.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.characters = japaneseAlphabet;
        parameter.size = 12;
        font12 = generator.generateFont(parameter);
        generator.dispose();


    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        player.draw(batch,0f);
        batch.end();

        float speed = 100f;
        //camera.lookAt(player.getX(),player.getY(),0);
        camera.position.x = player.getX();
        camera.position.y = player.getY();
        camera.update();
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();

        System.out.println("Camera: " + camera.position.x + " " + camera.position.y +
            " Player: " + player.getX() + " " + player.getY());




        //font12.draw(batch,"ゲームスタートYa",200,200);
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = 30f;                 // Viewport of 30 units!
        camera.viewportHeight = 30f * height/width; // Lets keep things in proportion.
        camera.update();
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
