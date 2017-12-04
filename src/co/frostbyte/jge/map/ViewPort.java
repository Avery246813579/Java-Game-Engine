package co.frostbyte.jge.map;

import co.frostbyte.jge.entities.Entity;
import co.frostbyte.jge.math.Point;

public class ViewPort {
    private Point point = new Point();
    private Entity attached;

    public Point getPoint(){
        return point;
    }

    public void moveX(int x){
        if (point.getX() + x < 0){
            point.setX(0);
            return;
        }

        point.moveX(x);
    }

    public void moveY(int y){
        if (point.getY() + y < 0){
            point.setY(0);
            return;
        }

        point.moveY(y);
    }
}
