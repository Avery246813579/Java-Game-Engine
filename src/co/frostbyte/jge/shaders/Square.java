package co.frostbyte.jge.shaders;

import co.frostbyte.jge.Point;
import co.frostbyte.jge.Sprite;
import co.frostbyte.jge.Test2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Square extends Shade {
    @Override
    public void draw(int[] pixels, Map<Integer, Integer> lights, Point point) {
        for (int x = point.getX() - 75; x < point.getX() + 75; x++) {
            for (int y = point.getY() - 75; y < point.getY() + 75; y++) {
                if (lights.get(x + y * Test2.WIDTH) != null) {
                    lights.put(x + y * Test2.WIDTH, lights.get(x + y * Test2.WIDTH) + 1);
                } else {
                    lights.put(x + y * Test2.WIDTH, 3);
                }
            }
        }

    }
}
