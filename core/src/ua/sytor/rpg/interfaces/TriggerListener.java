package ua.sytor.rpg.interfaces;

public interface TriggerListener{
    public void onOverlap();
    public void beginOverlap();
    public void endOverlap();
    public void customAction();
}