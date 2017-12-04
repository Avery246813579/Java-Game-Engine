package co.frostbyte.jge.shaders;

import co.frostbyte.jge.map.ViewPort;
import co.frostbyte.jge.math.Point;
import co.frostbyte.jge.GameManager;

import java.util.Map;

public class Square extends Shade {
    @Override
    public void draw(int[] pixels, Map<Integer, Double> lights, Point point, ViewPort viewPort) {
        if (point.getX() + viewPort.getPoint().getX() > GameManager.WIDTH + 75 || point.getY() + viewPort.getPoint().getY() > GameManager.HEIGHT + 75) {
            return;
        }

        for (int x = (int) point.getX() - 75; x < point.getX() + 75; x++) {
            if (x < viewPort.getPoint().getX() || x > viewPort.getPoint().getX() + GameManager.WIDTH){
                continue;
            }

            for (int y = (int) point.getY() - 75; y < point.getY() + 75; y++) {
                if (y < viewPort.getPoint().getY() || y > viewPort.getPoint().getY() + GameManager.HEIGHT){
                    continue;
                }

                lights.put(x - (int) viewPort.getPoint().getX() + (int) (y - viewPort.getPoint().getY()) * GameManager.WIDTH, (double) 1);
            }
        }

    }
}
