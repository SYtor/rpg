package ua.sytor.rpg.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import ua.sytor.rpg.actor.DialogActor;
import ua.sytor.rpg.interfaces.PlayerInteractListener;
import ua.sytor.rpg.interfaces.TriggerListener;
import ua.sytor.rpg.actor.Player;
import ua.sytor.rpg.system.MapLoader;

import java.util.*;

public class GameStage extends Stage implements PlayerInteractListener{

    private static int VIRTUAL_HEIGHT = 297, VIRTUAL_WIDTH = 512;

    private OrthographicCamera camera;
    private SpriteBatch spriteBatch;

    private UIStage uiStage;

    private Player player;

    //Collision stuff
    MapObjects collisionObjects;

    Set<Integer> previousTriggeredZones;
    Set<Integer> currentlyTriggeredZones;
    //List of actions that player can perform by click interaction button
    HashMap<Integer,TriggerListener> actions;
    //int currentActionSelected;

    public GameStage(TiledMap tiledMap,UIStage uiStage){
        camera = new OrthographicCamera(VIRTUAL_WIDTH,VIRTUAL_HEIGHT);
        camera.setToOrtho(false,VIRTUAL_WIDTH,VIRTUAL_HEIGHT);
        spriteBatch = new SpriteBatch();
        spriteBatch.setProjectionMatrix(camera.combined);
        camera.update();
        setViewport(new ExtendViewport(VIRTUAL_WIDTH,VIRTUAL_HEIGHT,camera));

        this.uiStage = uiStage;

        player = new Player();
        //Player spawn position
        MapLoader.loadNPC(tiledMap,this,player);
        player.setInteractListener(this);
        addActor(player);

        Gdx.input.setInputProcessor(player);

        previousTriggeredZones = new HashSet<Integer>();
        currentlyTriggeredZones = new HashSet<Integer>();
        actions = new LinkedHashMap<Integer, TriggerListener>();

        collisionObjects = tiledMap.getLayers().get("collision").getObjects();

    }

    @Override
    public void act(float delta) {
        super.act(delta);
        resolveCollision();
        //Camera Follow player
        getCamera().position.set(getPlayer().getX(),getPlayer().getY(),0);
        getCamera().update();
    }

    private void resolveCollision(){

        //Update actions
        previousTriggeredZones.clear();
        previousTriggeredZones.addAll(currentlyTriggeredZones);
        currentlyTriggeredZones.clear();

        //Handle collision

        boolean collision = false;

        Rectangle playerCollision = player.getCollision();
        float velocityX = player.getVelocityX(), velocityY = player.getVelocityY(), speed = player.getSpeed();

        Rectangle newCollision = new Rectangle(playerCollision.x+velocityX*speed,playerCollision.y+velocityY*speed,
                playerCollision.width,playerCollision.height);


        for (MapObject mapObject : collisionObjects){
            Rectangle rectangle = ((RectangleMapObject)mapObject).getRectangle();
            if(rectangle.overlaps(newCollision)){
                if(mapObject.getName().equals("block"))
                    collision = true;
                else{
                    int triggerZoneId = mapObject.getProperties().get("id",Integer.class);

                    if(currentlyTriggeredZones.add(triggerZoneId) && !previousTriggeredZones.contains(triggerZoneId))
                        registerAction(triggerZoneId,mapObject);
                    else
                        actions.get(triggerZoneId).onOverlap();
                }
                break;
            }

        }

        //Block player movement
        if(!collision){
            player.setPosition(player.getX()+velocityX*speed,player.getY()+velocityY*speed);
        }

        //Check if player exited some trigger zone
        previousTriggeredZones.removeAll(currentlyTriggeredZones);
        for(int zoneId : previousTriggeredZones){
            actions.get(zoneId).endOverlap();
            actions.remove(zoneId);
            System.out.println("Remove action "+zoneId);
        }

    }

    private void registerAction(final int triggerZoneId, final MapObject mapObject){
        System.out.println("add new Action");
        if(mapObject.getName().equals("read"))
            actions.put(triggerZoneId,new TriggerListener() {
                    @Override
                    public void onOverlap() {
                    }

                    @Override
                    public void beginOverlap() {
                        System.out.println("beginOverlap");
                    }

                    @Override
                    public void endOverlap() {
                        System.out.println("endOverlap");
                    }

                    @Override
                    public void customAction() {
                        System.out.println(mapObject.getProperties().get("text",String.class));
                        uiStage.labelActionNumber.setText(mapObject.getProperties().get("text",String.class));
                    }
                });
        if(mapObject.getName().equals("talk"))
            actions.put(triggerZoneId, new TriggerListener() {
                @Override
                public void onOverlap() {

                }

                @Override
                public void beginOverlap() {

                }

                @Override
                public void endOverlap() {

                }

                @Override
                public void customAction() {
                    DialogActor dialogActor = new DialogActor(player,uiStage);
                    uiStage.addActor(dialogActor);
                    player.setVelocityX(0);
                    player.setVelocityY(0);
                    Gdx.input.setInputProcessor(dialogActor);
                }
            });
        actions.get(triggerZoneId).beginOverlap();
    }


    public Player getPlayer() {
        return player;
    }

    @Override
    public void buttonPress(int keycode) {
        switch (keycode){
            case Input.Keys.F:
                if(actions.size()!=0){
                    actions.entrySet().iterator().next().getValue().customAction();
                }
                break;
            case Input.Keys.TAB:
                break;
        }
    }
}
