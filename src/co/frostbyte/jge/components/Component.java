package co.frostbyte.jge.components;

import co.frostbyte.jge.Sprite;

public abstract class Component {
    protected Sprite sprite;
    public Component(Sprite sprite){
        this.sprite = sprite;
    }
}
