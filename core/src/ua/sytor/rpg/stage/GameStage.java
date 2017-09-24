package ua.sytor.rpg.stage;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import ua.sytor.rpg.actor.DialogActor;
import ua.sytor.rpg.interfaces.TriggerListener;
import ua.sytor.rpg.actor.Player;
import ua.sytor.rpg.system.MapLoader;

import java.util.*;

public class GameStage extends Stage implements InputProcessor{

    private static int VIRTUAL_HEIGHT = 297, VIRTUAL_WIDTH = 512;

    private OrthographicCamera camera;
    private SpriteBatch spriteBatch;

    InputMultiplexer inputMultiplexer;
    UIStage uiStage;

    TiledMap tiledMap;
    OrthogonalTiledMapRenderer tiledMapRenderer;

    private Player player;

    //Collision stuff
    private MapObjects collisionObjects;
    private Set<Integer> previousTriggeredZones;
    private Set<Integer> currentlyTriggeredZones;
    private HashMap<Integer,TriggerListener> actions;
    //int currentActionSelected;


    //Debug render
    private boolean isDebug = false;
    private ShapeRenderer shapeRenderer;

    public GameStage(InputMultiplexer inputMultiplexer, UIStage uiStage){
        initCameraAndViewport();
        loadMap("maps/map.tmx");

        this.inputMultiplexer = inputMultiplexer;
        this.uiStage = uiStage;

        player = new Player();
        //Player spawn position
        MapLoader.loadNPC(tiledMap,this,player);
        addActor(player);

        previousTriggeredZones = new HashSet<Integer>();
        currentlyTriggeredZones = new HashSet<Integer>();
        actions = new LinkedHashMap<Integer, TriggerListener>();

        collisionObjects = tiledMap.getLayers().get("collision").getObjects();

        setDebugRender(true);
        player.setDebugRender(true);
    }

    private void initCameraAndViewport(){
        camera = new OrthographicCamera(VIRTUAL_WIDTH,VIRTUAL_HEIGHT);
        camera.setToOrtho(false,VIRTUAL_WIDTH,VIRTUAL_HEIGHT);
        spriteBatch = new SpriteBatch();
        spriteBatch.setProjectionMatrix(camera.combined);
        camera.update();
        setViewport(new ExtendViewport(VIRTUAL_WIDTH,VIRTUAL_HEIGHT,camera));
    }

    private void loadMap(String map){
        tiledMap = new TmxMapLoader().load(map);
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap,1f);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        resolveCollision();
        //Camera Follow player
        getCamera().position.set(player.getX(),player.getY(),0);
        getCamera().update();
    }

    @Override
    public void draw() {
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
        super.draw();
        if(isDebug)
            debugRender();
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
                    DialogActor dialogActor = new DialogActor(inputMultiplexer ,uiStage,GameStage.this);
                    uiStage.addActor(dialogActor);
                    player.setVelocity(0,0);
                }
            });
        actions.get(triggerZoneId).beginOverlap();
    }

    @Override
    public boolean keyDown(int keyCode) {
        System.out.println("GameStage");
        switch (keyCode){
            case Input.Keys.A:
                player.setVelocity(-1,0);
                break;
            case Input.Keys.D:
                player.setVelocity(1,0);
                break;
            case Input.Keys.W:
                player.setVelocity(0,1);
                break;
            case Input.Keys.S:
                player.setVelocity(0,-1);
                break;
            case Input.Keys.F:
                if(actions.size()!=0&&actions.entrySet().iterator().hasNext()){
                    actions.entrySet().iterator().next().getValue().customAction();
                }
        }
        return super.keyDown(keyCode);
    }

    @Override
    public boolean keyUp(int keyCode) {
        switch (keyCode){
            case Input.Keys.A:
            case Input.Keys.D:
                player.setVelocityX(0);
                break;
            case Input.Keys.W:
            case Input.Keys.S:
                player.setVelocityY(0);
                break;

        }
        return super.keyUp(keyCode);
    }

    private void setDebugRender(boolean debug){
        this.isDebug = debug;
        if (debug)
            shapeRenderer = new ShapeRenderer();
        else
            shapeRenderer = null;
    }

    private void debugRender(){
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setProjectionMatrix(getCamera().combined);
        shapeRenderer.setColor(Color.CYAN);
        for (MapObject mapObject : collisionObjects){
            Rectangle rectangle = ((RectangleMapObject)mapObject).getRectangle();
            shapeRenderer.rect(rectangle.x,rectangle.y, rectangle.width, rectangle.height);
        }
        shapeRenderer.end();
    }
}
