package ua.sytor.rpg.screenmanager;

import com.badlogic.gdx.Screen;
import ua.sytor.rpg.screen.GameScreen;
import ua.sytor.rpg.screen.MainMenuScreen;

public enum ScreenEnum {

    MAIN_MENU {
        public Screen getScreen(Object... params) {
            return new MainMenuScreen();
        }
    },
    GAME {
        public Screen getScreen(Object... params) {
            return new GameScreen();
        }
    };

    public abstract Screen getScreen(Object... params);
}