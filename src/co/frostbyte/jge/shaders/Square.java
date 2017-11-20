package co.frostbyte.jge.shaders;

import co.frostbyte.jge.Point;
import co.frostbyte.jge.GameManager;

import java.util.Map;

public class Square extends Shade {
    @Override
    public void draw(int[] pixels, Map<Integer, Double> lights, Point point) {
        if (point.getX() > GameManager.WIDTH + 75 || point.getY() > GameManager.HEIGHT + 75) {
            return;
        }

        for (int x = point.getX() - 75; x < point.getX() + 75; x++) {
            for (int y = point.getY() - 75; y < point.getY() + 75; y++) {
                lights.put(x + y * GameManager.WIDTH, (double) 1);
            }
        }

    }
}
