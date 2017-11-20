package co.frostbyte.jge;

import co.frostbyte.jge.entities.Entity;
import co.frostbyte.jge.entities.Sprite;
import co.frostbyte.jge.shaders.ShaderManager;
import co.frostbyte.jge.shaders.Square;
import co.frostbyte.jge.shaders.StaticShade;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.*;

import javax.swing.JFrame;

public class GameManager extends Canvas implements Runnable, MouseListener, KeyListener, MouseMotionListener {
    private static final long serialVersionUID = 1L;

    public final static int WIDTH = 800;
    public final static int HEIGHT = WIDTH / 16 * 10;
    public final static String TITLE = "Test Game";
    public final static int SCALE = 1;

    private List<Point> toAdd = new ArrayList<Point>();
    private Sprite bird = new Sprite("/bird.png");
    private Sprite bird2 = new Sprite("/bird.png");
    private Sprite trump = new Sprite("/TEST.jpg");
    private List<Integer> keyDown = new ArrayList<>();
    public static List<Sprite> sprites = new ArrayList<>();
    private StaticShade mouse = new StaticShade(new Point(0, 0), new Square());
    private Entity entity = new Entity(new String[]{"/Animation/IDLE_1.png", "/Animation/IDLE_2.png",
            "/Animation/IDLE_3.png", "/Animation/IDLE_4.png"}, (short) 15);

    private Thread thread;
    private boolean running = false;
    private boolean paused = false;
    private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
    private JFrame frame;


    public GameManager() {
        Dimension size = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        addMouseListener(this);
        addKeyListener(this);
        addMouseMotionListener(this);

        sprites.add(bird);
        sprites.add(bird2);
        bird.getComponents().add(new Square());
//        bird2.getComponents().add(new Square());
        ShaderManager.STATIC_SHADES.add(mouse);
    }

    public synchronized void start() {
        if (running) return;
        running = true;
        thread = new Thread(this, "GameThread");
        thread.start();
    }

    public synchronized void stop() {
        if (!running) return;
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        long lastTime = System.nanoTime();
        long lastTimer = System.currentTimeMillis();
        double ns = 1000000000.0 / 60.0;
        double delta = 0;
        int frames = 0;
        int updates = 0;
        requestFocus();

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            if (delta >= 1) {
                if (!paused) update();
                updates++;
                delta--;
            }

            render();
            frames++;

            while (System.currentTimeMillis() - lastTimer > 1000) {
                lastTimer += 1000;
                System.out.println(updates + " ups, " + frames + " fps");
                // frame.setTitle(TITLE + "  |  " + updates + " ups, " + frames + " fps");
                frames = 0;
                updates = 0;
            }
        }
    }

    public void update() {
        Iterator<Integer> iter = keyDown.iterator();

        while (iter.hasNext()) {
            int code = iter.next();

            if (code == KeyEvent.VK_RIGHT) {
                bird.getPoint().moveX(5);
            }

            if (code == KeyEvent.VK_LEFT) {
                bird.getPoint().moveX(-5);
            }

            if (code == KeyEvent.VK_UP) {
                bird.getPoint().moveY(-5);
            }

            if (code == KeyEvent.VK_DOWN) {
                bird.getPoint().moveY(5);
            }
        }

        entity.update();
        bird2.move();
    }

    public void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        draw();
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);

        g.dispose();
        bs.show();
    }

    public void draw() {
        trump.draw(pixels, WIDTH, HEIGHT);

        for (Sprite sprite : sprites) {
            sprite.draw(pixels, WIDTH, HEIGHT);
        }

        ShaderManager.draw(pixels);
        entity.draw(pixels);
    }

    public static void main(String[] args) {
        GameManager game = new GameManager();
        game.frame = new JFrame();
        game.frame.setResizable(false);
        game.frame.setTitle(game.TITLE);
        game.frame.add(game);
        game.frame.pack();
        game.frame.setLocationRelativeTo(null);
        game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.frame.setVisible(true);

        game.start();
    }

    @Override
    public void mouseClicked(MouseEvent event) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent event) {
        if (!keyDown.contains(event.getKeyCode())) {
            keyDown.add(event.getKeyCode());
        }
    }

    @Override
    public void keyReleased(KeyEvent event) {
        int code = keyDown.indexOf(event.getKeyCode());

        if (code == -1) {
            return;
        }

        keyDown.remove(code);
    }

    @Override
    public void keyTyped(KeyEvent event) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouse.point.setX(e.getX());
        mouse.point.setY(e.getY());
    }
}