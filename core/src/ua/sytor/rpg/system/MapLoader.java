package ua.sytor.rpg.system;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import ua.sytor.rpg.actor.NPCActor;
import ua.sytor.rpg.actor.Player;

public class MapLoader {

    public static void loadNPC(TiledMap tiledMap, Stage stage, Player player){
        MapObjects npcArray = tiledMap.getLayers().get("spawn").getObjects();
        for(MapObject mapObject : npcArray){
            //Place Player
            if(mapObject.getName().equals("player")){
                Rectangle spawnRectangle = ((RectangleMapObject)mapObject).getRectangle();
                player.setPosition(spawnRectangle.x+spawnRectangle.width/2,spawnRectangle.y+spawnRectangle.height/2);
                continue;
            }

            //Place NPC

            NPCActor npcActor;
            Animation animation;
            TextureAtlas atlas;
            Array<TextureAtlas.AtlasRegion> atlasRegions;

            switch (mapObject.getProperties().get("npcid",Integer.class)){
                case 1:
                    atlas = new TextureAtlas(Gdx.files.internal("character/Character.atlas"));
                    atlasRegions = new Array<TextureAtlas.AtlasRegion>();
                    atlasRegions.add(atlas.findRegion("idle"));
                    animation = new Animation(1/2f,atlasRegions);
                    break;
                default:
                    atlas = new TextureAtlas(Gdx.files.internal("character/Character.atlas"));
                    atlasRegions = new Array<TextureAtlas.AtlasRegion>();
                    atlasRegions.add(atlas.findRegion("idle"));
                    animation = new Animation(1/2f,atlasRegions);
                    break;

            }

            npcActor = new NPCActor(animation);

            Rectangle spawnRectangle = ((RectangleMapObject)mapObject).getRectangle();
            npcActor.setPosition(spawnRectangle.x+spawnRectangle.width/2-npcActor.getWidth()/2,spawnRectangle.y+spawnRectangle.height/2);
            stage.addActor(npcActor);

        }
    }

}
