package co.frostbyte.jge.shaders;

import co.frostbyte.jge.Point;

public class StaticShade {
    public Point point;
    public Shade shade;

    public StaticShade(Point point, Shade shade){
        this.point = point;
        this.shade = shade;
    }
}
