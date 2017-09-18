package ua.sytor.rpg.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;
import ua.sytor.rpg.stage.UIStage;

import static com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.DEFAULT_CHARS;

public class DialogActor extends Actor implements InputProcessor {

    private Player player;
    private UIStage uiStage;
    private GlyphLayout layout;
    BitmapFont font;
    public static String alphabet = "ゲームスタト" + DEFAULT_CHARS;

    private float textSpeed = 10f;
    private float stringCompleteness = 0;

    String text = "Wrapped Wrapped Wrapped Wrapped dfugsi dfoigjoifa odsjgdaoij;sdoif  osiajfsodifdubg sdhfbadihbsdiuf dsiaufglsidufh fdi haui hsdliusdhf ugadiuhfsdiu ausihflasidufas";

    public DialogActor(Player player, UIStage uiStage){
        this.player = player;
        this.uiStage = uiStage;

        setWidth(uiStage.SCREEN_WIDTH);
        setHeight(uiStage.SCREEN_HEIGHT/2);

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("jackeyfont.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.characters = alphabet;
        parameter.size = 12;
        font = generator.generateFont(parameter);
        generator.dispose();

        layout = new GlyphLayout();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        stringCompleteness += textSpeed * delta;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        float x = getX();
        float y = getY();
        float width = getWidth();
        float height = getHeight();

        layout.setText(font, text, Color.WHITE, width, Align.left, true);
        y += height / 2 + layout.height / 2;

        int charToDraw = (int)stringCompleteness;
        if(charToDraw>text.length())
            font.draw(batch, text, x, y, width, Align.left, true);
        else
            font.draw(batch, text.substring(0,charToDraw), x, y, width, Align.left, true);
    }

    @Override
    public boolean keyDown(int keycode) {
        System.out.println("Dialog");
        if(keycode==Input.Keys.F){
            if(stringCompleteness<text.length()){
                stringCompleteness=text.length();
            }
            else {
                Gdx.input.setInputProcessor(player);
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
