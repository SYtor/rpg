package ua.sytor.rpg.system;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import ua.sytor.rpg.screenmanager.ScreenEnum;
import ua.sytor.rpg.screenmanager.ScreenManager;

public class SaveSystem {

    private static SaveSystem instance;

    private SaveSystem() {
        super();
    }

    public static SaveSystem getInstance() {
        if (instance == null) {
            instance = new SaveSystem();
        }
        return instance;
    }

    public void initialize() {

    }

    public boolean loadSave(int slot){
        return true;
    }

    public boolean makeSave(int slot){
        return true;
    }
}
