package ua.sytor.rpg;

import com.badlogic.gdx.*;
import ua.sytor.rpg.screenmanager.ScreenEnum;
import ua.sytor.rpg.screenmanager.ScreenManager;

public class GameManager extends Game{

	@Override
	public void create () {
		ua.sytor.rpg.screenmanager.ScreenManager.getInstance().initialize(this);
		ScreenManager.getInstance().showScreen(ScreenEnum.MAIN_MENU);
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width,height);
	}

	@Override
	public void dispose () {
		super.dispose();
	}
}
