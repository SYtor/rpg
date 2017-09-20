package ua.sytor.rpg.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;
import ua.sytor.rpg.stage.GameStage;
import ua.sytor.rpg.stage.UIStage;

import static com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.DEFAULT_CHARS;

public class DialogActor extends Actor implements InputProcessor {

    private InputMultiplexer inputMultiplexer;
    private UIStage uiStage;
    private GameStage gameStage;

    private GlyphLayout layout;
    private BitmapFont font;
    private static String alphabet = "ゲームスタト" + DEFAULT_CHARS;

    private float textSpeed = 10f;
    private float stringCompleteness = 0;

    private String text = "Wrapped Wrapped Wrapped Wrapped dfugsi dfoigjoifa odsjgdaoij;sdoif  osiajfsodifdubg sdhfbadihbsdiuf dsiaufglsidufh fdi haui hsdliusdhf ugadiuhfsdiu ausihflasidufas";

    private ShapeRenderer shapeRenderer;

    public DialogActor(InputMultiplexer inputMultiplexer, UIStage uiStage, GameStage gameStage){
        this.inputMultiplexer = inputMultiplexer;
        this.uiStage = uiStage;
        this.gameStage = gameStage;

        inputMultiplexer.addProcessor(this);
        inputMultiplexer.removeProcessor(uiStage);
        inputMultiplexer.removeProcessor(gameStage);

        setWidth(uiStage.getWidth());
        setHeight(uiStage.getHeight()/2);

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("jackeyfont.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.characters = alphabet;
        parameter.size = 22;
        font = generator.generateFont(parameter);
        generator.dispose();

        layout = new GlyphLayout();

        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        stringCompleteness += textSpeed * delta;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        float x = getX()+20;
        float y = getY()+20;
        float width = uiStage.getWidth()-40;
        float height = uiStage.getHeight()/2-20;

        batch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0,0,0,0);
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.rect(0,0,uiStage.getWidth(),uiStage.getHeight()/2);
        shapeRenderer.end();

        batch.begin();

        layout.setText(font, text, Color.WHITE, width, Align.left, true);
        y += height / 2 + layout.height / 2;

        int charToDraw = (int)stringCompleteness;
        font.getData().setScale(uiStage.getHeight()/300);

        if(charToDraw>text.length())
            font.draw(batch, text, x, y, width, Align.left, true);
        else
            font.draw(batch, text.substring(0,charToDraw), x, y, width, Align.left, true);
    }



    @Override
    public boolean remove() {
        inputMultiplexer.removeProcessor(this);
        inputMultiplexer.addProcessor(uiStage);
        inputMultiplexer.addProcessor(gameStage);
        return super.remove();
    }

    @Override
    public boolean keyDown(int keycode) {
        System.out.println("Dialog");
        if(keycode==Input.Keys.F){
            if(stringCompleteness < text.length()){
                stringCompleteness = text.length();
            }
            else {
                remove();
            }
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
