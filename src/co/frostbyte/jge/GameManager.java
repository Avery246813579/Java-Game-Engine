package co.frostbyte.jge;

import co.frostbyte.jge.display.TextAsset;
import co.frostbyte.jge.entities.Entity;
import co.frostbyte.jge.map.GameMap;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.*;
import java.util.List;

import javax.swing.JFrame;

public class GameManager extends Canvas implements Runnable, MouseListener, KeyListener, MouseMotionListener {
    private static final long serialVersionUID = 1L;

    public final static int WIDTH = 800;
    public final static int HEIGHT = WIDTH / 16 * 10;
    public final static String TITLE = "Test Game";
    public final static double SCALE = 1;

    private List<Integer> keyDown = new ArrayList<>();

    public static List<TextAsset> textAssets = new ArrayList<>();

    private Thread thread;
    private boolean running = false;
    private boolean paused = false;
    private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
    private JFrame frame;

    private List<GameMap> gameMaps = new ArrayList<>(Arrays.asList(new GameMap()));
    private Entity entity = gameMaps.get(0).entity;

    private int currentMap = 0;


    public GameManager() {
        Dimension size = new Dimension((int) (WIDTH * SCALE), (int) (HEIGHT * SCALE));
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        addMouseListener(this);
        addKeyListener(this);
        addMouseMotionListener(this);
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

        if (!iter.hasNext()){
            entity.getVelocity().setX(0);
            entity.getVelocity().setY(0);
        }

        while (iter.hasNext()) {
            int code = iter.next();

            if (code == KeyEvent.VK_RIGHT) {
                entity.getVelocity().setX(2);
            }

            if (code == KeyEvent.VK_LEFT) {
                entity.getVelocity().setX(-2);
            }

            if (code == KeyEvent.VK_UP) {
                entity.getVelocity().setY(-2);
            }

            if (code == KeyEvent.VK_DOWN) {
                entity.getVelocity().setY(2);
            }
        }

        entity.update();
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

        for (TextAsset textAsset : textAssets) {
            g.setColor(textAsset.getColor());
            g.setFont(textAsset.getFont());
            g.drawString(textAsset.getText(), (int) textAsset.getPoint().getX(), (int) textAsset.getPoint().getY());
        }

        g.dispose();
        bs.show();
    }

    public void draw() {
        gameMaps.get(currentMap).draw(pixels);
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

        if (event.getKeyCode() ==  KeyEvent.VK_D){
            gameMaps.get(currentMap).getViewPort().moveX(5);
        }

        if (event.getKeyCode() ==  KeyEvent.VK_A){
            gameMaps.get(currentMap).getViewPort().moveX(-5);
        }

        if (event.getKeyCode() ==  KeyEvent.VK_W){
            gameMaps.get(currentMap).getViewPort().moveY(-5);
        }

        if (event.getKeyCode() ==  KeyEvent.VK_S){
            gameMaps.get(currentMap).getViewPort().moveY(5);
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

    }
}