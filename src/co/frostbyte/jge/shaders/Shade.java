package co.frostbyte.jge.shaders;

import co.frostbyte.jge.Point;
import co.frostbyte.jge.components.Component;

import java.util.Map;

public abstract class Shade extends Component{
    public abstract void draw(int[] pixels, Map<Integer, Double> lights, Point point);
}
