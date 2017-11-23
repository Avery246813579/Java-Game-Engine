package co.frostbyte.jge.display;

import co.frostbyte.jge.math.Point;

import java.awt.*;

public class TextAsset {
    private Font font = new Font("serif", Font.PLAIN, 20);
    private Color color = Color.WHITE;
    private String text;
    private co.frostbyte.jge.math.Point point;

    public TextAsset(String text, co.frostbyte.jge.math.Point point){
        this.text = text;
        this.point = point;
    }

    public TextAsset(String text, co.frostbyte.jge.math.Point point, int size){
        this.text = text;
        this.point = point;

        this.font = new Font(font.getName(), font.getStyle(), size);
    }

    public TextAsset(String text, co.frostbyte.jge.math.Point point, Color color, int size){
        this.text = text;
        this.point = point;

        this.color = color;
        this.font = new Font(font.getName(), font.getStyle(), size);
    }

    public String getText(){
        return text;
    }

    public Point getPoint(){
        return point;
    }

    public Color getColor(){
        return color;
    }

    public Font getFont(){
        return font;
    }
}
