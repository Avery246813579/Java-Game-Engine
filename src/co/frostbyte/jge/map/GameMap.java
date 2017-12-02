package co.frostbyte.jge.map;

import co.frostbyte.jge.components.CollisionDetection;
import co.frostbyte.jge.entities.Entity;
import co.frostbyte.jge.shaders.Square;
import co.frostbyte.jge.shaders.StaticShade;

import java.util.ArrayList;
import java.util.List;

public class Map {
    private List<Entity> entities = new ArrayList<>();
    private StaticShade mouse = new StaticShade(new co.frostbyte.jge.math.Point(0, 0), new Square());
    private Entity entity = new Entity(new String[]{"/Animation/IDLE_1.png", "/Animation/IDLE_2.png",
            "/Animation/IDLE_3.png", "/Animation/IDLE_4.png"}, (short) 15);
    private Entity entity2 = new Entity(new String[]{"/Animation/IDLE_1.png", "/Animation/IDLE_2.png",
            "/Animation/IDLE_3.png", "/Animation/IDLE_4.png"}, (short) 15);

    public Map(){
        entities.add(entity);
        entities.add(entity2);

        entity.getComponents().add(new Square());
        entity.getComponents().add(new CollisionDetection());
        entity2.getComponents().add(new CollisionDetection());

//        ShaderManager.STATIC_SHADES.add(mouse);

        entity2.getPoint().setX(100);
        entity2.getPoint().setY(100);
    }

    public void draw(int[] pixels) {
        for (Entity entity : entities) {
            entity.draw(pixels);
        }
    }
}
