package co.frostbyte.jge.map;

import co.frostbyte.jge.GameManager;
import co.frostbyte.jge.components.CollisionDetection;
import co.frostbyte.jge.entities.Entity;
import co.frostbyte.jge.entities.Sprite;
import co.frostbyte.jge.shaders.ShaderManager;
import co.frostbyte.jge.shaders.Square;

import java.util.ArrayList;
import java.util.List;

public class GameMap {
    private List<Entity> entities = new ArrayList<>();
    private ViewPort viewPort = new ViewPort();

    public Entity entity = new Entity(new String[]{"/Animation/IDLE_1.png", "/Animation/IDLE_2.png",
            "/Animation/IDLE_3.png", "/Animation/IDLE_4.png"}, (short) 15, this);
    private Entity entity2 = new Entity(new String[]{"/Animation/IDLE_1.png", "/Animation/IDLE_2.png",
            "/Animation/IDLE_3.png", "/Animation/IDLE_4.png"}, (short) 15, this);
    private Sprite trump = new Sprite("/TEST.jpg");

    public ShaderManager shaderManager = new ShaderManager(this);

    public Tile[][] tiles = new Tile[10][10];
    public Tile testTile = new Tile("Test.png");

    public GameMap() {
        entities.add(entity);
        entities.add(entity2);

        entity.getComponents().add(new Square());
        entity.getComponents().add(new CollisionDetection());
        entity2.getComponents().add(new CollisionDetection());

//        shaderManager.STATIC_SHADES.add(mouse);

        entity2.getPoint().setX(100);
        entity2.getPoint().setY(100);

        tiles[0][2] = testTile;
        tiles[1][1] = testTile;
     }

    public void update() {

    }

    public void draw(int[] pixels) {
        trump.draw(pixels, GameManager.WIDTH, GameManager.HEIGHT);

        final int DIAMETER = 20;
        int startXTile = (int) Math.floor(viewPort.getPoint().getX() / DIAMETER);
        int startYTile = (int) Math.floor(viewPort.getPoint().getY() / DIAMETER);

        for (int x = startXTile; x < 10; x++){
            for (int y = startYTile; y < 10; y++){
                Tile tile = tiles[y][x];

                if (tile == null){
                    continue;
                }

                tile.draw(pixels, x * 20 - (int) viewPort.getPoint().getX(), y * 20 - (int) viewPort.getPoint().getY());
            }
        }


        for (Entity entity : entities) {
            entity.draw(pixels);
        }

        shaderManager.draw(pixels, viewPort);
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public ViewPort getViewPort() {
        return viewPort;
    }
}
