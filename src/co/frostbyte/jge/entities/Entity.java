package co.frostbyte.jge.entities;

import co.frostbyte.jge.GameManager;
import co.frostbyte.jge.components.CollisionDetection;
import co.frostbyte.jge.components.Component;
import co.frostbyte.jge.display.Animation;
import co.frostbyte.jge.display.Frame;
import co.frostbyte.jge.map.GameMap;
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
    private GameMap gameMap;

    public Entity(Frame[] frames) {
        animation = new Animation(frames);
    }

    public Entity(String[] locations, short delay, GameMap gameMap) {
        Frame[] frames = new Frame[locations.length];

        for (int i = 0; i < locations.length; i++) {
            frames[i] = new Frame(locations[i], delay);
        }

        animation = new Animation(frames);
        this.gameMap = gameMap;
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

        for (Entity entity : gameMap.getEntities()) {
            if (entity == this || !entity.hasComponent(CollisionDetection.class)) {
                continue;
            }

            if (getMoveHitbox().intersects(entity.getHitbox())) {
                velocity.stop();

                // New Two If/Elif blocks checks if we can move any closer before we intersect!

                // Check the X Axis
                if (entity.getPoint().getX() - (getPoint().getX() + getWidth()) > 1) {
                    getVelocity().setX(entity.getPoint().getX() - (getPoint().getX() + getWidth()));
                } else if (getPoint().getX() - (entity.getPoint().getX() + entity.getWidth()) > 0) {
                    getVelocity().setX(getPoint().getX() - (entity.getPoint().getX() + entity.getWidth()) - 1);
                }

                // Checks the Y Axis
                if (entity.getPoint().getY() - (getPoint().getY() + getHeight()) > 1) {
                    getVelocity().setY(entity.getPoint().getY() - (getPoint().getY() + getHeight()));
                } else if (getPoint().getY() - (entity.getPoint().getY() + entity.getHeight()) > 0) {
                    getVelocity().setY(getPoint().getY() - (entity.getPoint().getY() + entity.getHeight()) - 1);
                }

            }
        }
    }

    public Rectangle2D getHitbox() {
        return new Rectangle((int) point.getX(), (int) point.getY(), getWidth(), getHeight());
    }

    public Rectangle2D getMoveHitbox() {
        return new Rectangle((int) (point.getX() + velocity.getX()), (int) (point.getY() + velocity.getY()), getWidth(), getHeight());
    }

    public void draw(int[] pixels) {
        int maxWidth = (int) point.getX() + animation.getCurrentFrame().getWidth();
        int maxHeight = (int) point.getY() + animation.getCurrentFrame().getHeight();

        for (int x = (int) point.getX(); x < maxWidth; x++) {
            if (x < gameMap.getViewPort().getPoint().getX() || x > gameMap.getViewPort().getPoint().getX() + GameManager.WIDTH - 1)
                break;

            for (int y = (int) point.getY(); y < maxHeight; y++) {
                if (y < gameMap.getViewPort().getPoint().getY() || y > gameMap.getViewPort().getPoint().getY() + GameManager.HEIGHT - 1)
                    continue;

                int pixel = animation.getCurrentFrame().getPixels()[(x - (int) point.getX()) + (y - (int) point.getY()) * animation.getCurrentFrame().getWidth()];

                if (pixel == 0) {
                    continue;
                }

                pixels[x - (int) gameMap.getViewPort().getPoint().getX() + (int) (y - gameMap.getViewPort().getPoint().getY()) * GameManager.WIDTH] = pixel;
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

    public GameMap getGameMap() {
        return gameMap;
    }

    public List<Component> getComponents() {
        return components;
    }
}
