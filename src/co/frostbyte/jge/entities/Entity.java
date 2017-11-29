package co.frostbyte.jge.entities;

import co.frostbyte.jge.GameManager;
import co.frostbyte.jge.components.CollisionDetection;
import co.frostbyte.jge.components.Component;
import co.frostbyte.jge.display.Animation;
import co.frostbyte.jge.display.Frame;
import co.frostbyte.jge.math.Point;
import co.frostbyte.jge.math.Velocity;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

public class Entity {
    private Animation animation;
    private Point point = new Point();
    private Velocity velocity = new Velocity();
    private List<Component> components = new ArrayList<>();

    public Entity(Frame[] frames) {
        animation = new Animation(frames);
    }

    public Entity(String[] locations, short delay) {
        Frame[] frames = new Frame[locations.length];

        for (int i = 0; i < locations.length; i++) {
            frames[i] = new Frame(locations[i], delay);
        }

        animation = new Animation(frames);
    }

    public void update() {
        updateMovement();

        animation.update();
    }

    public void updateMovement() {
        updateCollision();

        point.move(velocity.getX(), velocity.getY());
    }

    public void updateCollision() {
        if (!hasComponent(CollisionDetection.class)) {
            return;
        }

        for (Entity entity : GameManager.entities) {
            if (entity == this || !entity.hasComponent(CollisionDetection.class)) {
                continue;
            }

            if (getMoveHitbox().intersects(entity.getHitbox())) {
                velocity.stop();
            }
        }
    }

    public Rectangle2D getHitbox() {
        return new Rectangle((int) point.getX(), (int) point.getY(), getWidth(), getHeight());
    }

    public Rectangle2D getMoveHitbox(){
        return new Rectangle((int) (point.getX() + velocity.getX()), (int) (point.getY() + velocity.getY()), getWidth(), getHeight());
    }

    public void draw(int[] pixels) {
        int maxWidth = (int) point.getX() + animation.getCurrentFrame().getWidth();
        int maxHeight = (int) point.getY() + animation.getCurrentFrame().getHeight();

        for (int x = (int) point.getX(); x < maxWidth; x++) {
            if (x < 0 || x > GameManager.WIDTH - 1)
                break;

            for (int y = (int) point.getY(); y < maxHeight; y++) {
                if (y < 0 || y > GameManager.HEIGHT - 1)
                    continue;

                int pixel = animation.getCurrentFrame().getPixels()[(x - (int) point.getX()) + (y - (int) point.getY()) * animation.getCurrentFrame().getWidth()];

                if (pixel == 0) {
                    continue;
                }

                pixels[x + y * GameManager.WIDTH] = pixel;
            }
        }
    }

    public int getWidth() {
        return animation.getCurrentFrame().getWidth();
    }

    public int getHeight() {
        return animation.getCurrentFrame().getHeight();
    }

    public boolean hasComponent(Class<?> target) {
        return getComponent(target) != null;
    }

    public Component getComponent(Class<?> target) {
        for (Component component : components) {
            if (target.isAssignableFrom(component.getClass())) {
                return component;
            }
        }

        return null;
    }

    public Velocity getVelocity() {
        return velocity;
    }

    public Point getPoint() {
        return point;
    }

    public List<Component> getComponents() {
        return components;
    }
}
