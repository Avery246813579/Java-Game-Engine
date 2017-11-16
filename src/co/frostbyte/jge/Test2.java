package co.frostbyte.jge;

import co.frostbyte.jge.shaders.ShaderManager;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.*;

import javax.swing.JFrame;

public class Test2 extends Canvas implements Runnable, MouseListener, KeyListener {
    private static final long serialVersionUID = 1L;

    public final static int WIDTH = 800;
    public final static int HEIGHT = WIDTH / 16 * 10;
    public final static String TITLE = "Genesis";
    public final static int SCALE = 1;

    private List<Point> toAdd = new ArrayList<Point>();
    private Sprite bird = new Sprite("/bird.png");
    private Sprite trump = new Sprite("/TEST.jpg");
    private Map<Integer, Integer> lights = new HashMap<>();
    private final float max = 64F, min = 1 / max;
    private List<Integer> keyDown = new ArrayList<>();

    public static List<Sprite> sprites = new ArrayList<>();

    private Thread thread;
    private boolean running = false;
    private boolean paused = false;
    private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
    private JFrame frame;

    public Test2() {
        Dimension size = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        addMouseListener(this);
        addKeyListener(this);


//        screen = new Screen(WIDTH, HEIGHT);
//        new SpriteSheet("/sprites.png");
//        input = new InputHandler();
//        setMenuLevel();
//        menu = new MainMenu(input);
//        map = new Map();
//        addKeyListener(input);
//        addFocusListener(input);
    }

    public static void setMenuLevel() {
//		Sound.menutheme.play(true);
//        level = new Level("/levels/test.png");
//        player = new Player(input);
//        for (int i = 0; i < 20; i++) {
//            level.add(new MenuMob());
//        }
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
        bird.draw(pixels, WIDTH, HEIGHT);

        lights.clear();

        ShaderManager.draw(pixels);

//        toAdd.add(bird.getPoint());
//        for (Point point : toAdd) {
//            for (int x = point.getX() - 75; x < point.getX() + 75; x++) {
//                for (int y = point.getY() - 75; y < point.getY() + 75; y++) {
//                    if (lights.get(x + y * WIDTH) != null) {
//                        lights.put(x + y * WIDTH, lights.get(x + y * WIDTH) + 1);
//                    } else {
//                        lights.put(x + y * WIDTH, 3);
//                    }
//                }
//            }
//        }
//
//        toAdd.clear();
//
//        for (java.util.Map.Entry<Integer, Integer> entry : lights.entrySet()) {
//            if (entry.getKey() >= WIDTH * HEIGHT || entry.getKey() < 0) {
//                continue;
//            }
//            int color = unharmed[entry.getKey()];
//            int r = (color & 0xff0000) >> 16;
//            int g = (color & 0xff00) >> 8;
//            int b = (color & 0xff);
//
//            r *= (1.0 / entry.getValue());
//            g *= (1.0 / entry.getValue());
//            b *= (1.0 / entry.getValue());
//
//            if (r > 255)
//                r = 255;
//            if (g > 255)
//                g = 255;
//            if (b > 255)
//                b = 255;
//
//            pixels[entry.getKey()] = r << 16 | g << 8 | b;
//        }

    }

    public static void main(String[] args) {
        Test2 game = new Test2();
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

}