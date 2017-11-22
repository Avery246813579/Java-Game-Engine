package co.frostbyte.jge.shaders;

import co.frostbyte.jge.math.Point;
import co.frostbyte.jge.GameManager;

import java.util.Map;

public class Square extends Shade {
    @Override
    public void draw(int[] pixels, Map<Integer, Double> lights, Point point) {
        if (point.getX() > GameManager.WIDTH + 75 || point.getY() > GameManager.HEIGHT + 75) {
            return;
        }

        for (int x = (int) point.getX() - 75; x < point.getX() + 75; x++) {
            if (x < 0 || x > GameManager.WIDTH){
                continue;
            }

            for (int y = (int) point.getY() - 75; y < point.getY() + 75; y++) {
                if (y < 0 || y > GameManager.HEIGHT){
                    continue;
                }

                lights.put(x + y * GameManager.WIDTH, (double) 1);
            }
        }

    }
}
