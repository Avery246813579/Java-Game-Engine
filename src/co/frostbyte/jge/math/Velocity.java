package co.frostbyte.jge.math;

public class Velocity {
    private double x, y;

    public Velocity() {

    }

    public Velocity(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void moveX(double x) {
        this.x += x;
    }

    public void moveY(double y) {
        this.y += y;
    }

    public void stop() {
        x = 0;
        y = 0;
    }

    public String toString() {
        return x + " " + y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}