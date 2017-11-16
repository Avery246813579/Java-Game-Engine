package co.frostbyte.jge.shaders;

import co.frostbyte.jge.Sprite;
import co.frostbyte.jge.components.Component;

import java.util.Map;

public abstract class Shade extends Component{
    public Shade(Sprite sprite) {
        super(sprite);
    }

    public abstract void draw(int[] pixels, Map<Integer, Integer> lights);
}
