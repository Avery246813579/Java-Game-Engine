package co.frostbyte.jge.entities;

import co.frostbyte.jge.math.Point;
import co.frostbyte.jge.components.Component;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class Sprite {
    private int[] pixels;
    private int width, height;
    private Point point = new Point();
    private List<Component> components = new ArrayList<>();

    public Sprite(String location) {
        try {
            BufferedImage image = ImageIO.read(getClass().getResourceAsStream(location));

            width = image.getWidth();
            height = image.getHeight();
            pixels = new int[width * height];
            image.getRGB(0, 0, width, height, pixels, 0, width);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void draw(int[] pixels, int screenWidth, int screenHeight) {
        if (point.getX() > screenWidth || point.getY() > screenHeight){
            return;
        }

        for (int x = (int) point.getX(); x < point.getX() + width; x++) {
            if (x < 0 || x > screenWidth - 1)
                break;

            for (int y = (int) point.getY(); y < point.getY() + height; y++) {
                if (y < 0 || y > screenHeight - 1)
                    continue;

                int pixel = this.pixels[(x - (int) point.getX()) + (y - (int) point.getY()) * width];

                if (pixel == 0) {
                    continue;
                }

                pixels[x + y * screenWidth] = pixel;
            }
        }
    }

    public void move() {
        point.moveX(1);
        point.moveY(1);
    }

    public Component getComponent(Class<?> target){
        for(Component component : components){
            if (target.isAssignableFrom(component.getClass())){
                return component;
            }
        }

        return null;
    }

    public Point getPoint() {
        return point;
    }

    public List<Component> getComponents() {
        return components;
    }
}
