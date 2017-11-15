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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Test extends Canvas implements Runnable, MouseListener, KeyListener {
	JFrame frame;
	boolean running = true;
	public static final int WIDTH = 800, HEIGHT = 600;
	public static final double SCALE = 1;
	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	private Sprite bird = new Sprite("bird.png");
	Map<Integer, Integer> lights = new HashMap<>();
	private float max = 5F, min = 0.2F;
	List<Point> clicks = new ArrayList<>();
	List<Integer> keyDown = new ArrayList<>();

	private int[] steve;
	private int sW, sH;

	public static void main(String[] args) {
		new Test().start();
	}

	public Test() {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		addMouseListener(this);
		addKeyListener(this);

		try {
			BufferedImage image = ImageIO.read(getClass().getResourceAsStream("TEST.jpg"));

			int width = sW = image.getWidth();
			int height = sH = image.getHeight();
			steve = new int[width * height];
			image.getRGB(0, 0, width, height, steve, 0, width);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		init();
	}

	public void start() {
		running = true;
		new Thread(this).start();
	}

	public void pack() {
		frame = new JFrame("Test Frame");
		frame.setResizable(false);
		frame.add(this);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	@Override
	public void run() {
		pack();

		long lastTime = System.nanoTime();
		double delta = 0.0;
		double ns = 1000000000.0 / 30.0;
		long timer = System.currentTimeMillis();
		int updates = 0;
		int frames = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if (delta >= 1) {
				update();
				updates++;
				delta--;
			}

			render();
			frames++;
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println(lights.size());
				System.out.println(updates + " ups, " + frames + " fps");
				frames = 0;
				updates = 0;
			}
		}
	}

	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		draw();

		Graphics g = bs.getDrawGraphics();

		g.drawImage(image, 0, 0, (int) (WIDTH * SCALE), (int) (HEIGHT * SCALE), null);

		g.dispose();
		bs.show();
	}

	public void clear() {
		for (int x = 0; x < WIDTH * HEIGHT; x++) {
			pixels[x] = 0x000000;
		}
	}

	public void update() {
		for (int code : keyDown) {
			if (code == KeyEvent.VK_D) {
				bird.getPoint().moveX(2);
			}

			if (code == KeyEvent.VK_A) {
				bird.getPoint().moveX(-2);
			}

			if (code == KeyEvent.VK_W) {
				bird.getPoint().moveY(-2);
			}

			if (code == KeyEvent.VK_S) {
				bird.getPoint().moveY(2);
			}
		}
	}

	public void init() {
		bird.draw(pixels, WIDTH, HEIGHT);
	}

	public void draw() {
		for (int x = 0; x < sW; x++) {
			if (x < 0 || x > WIDTH)
				continue;

			for (int y = 0; y < sH; y++) {
				if (y < 0 || y > HEIGHT)
					continue;

				try {
					pixels[x + y * WIDTH] = steve[x + y * sW];
				} catch (Exception e) {
				}
			}
		}

		bird.draw(pixels, WIDTH, HEIGHT);

		for (int x = 0; x < WIDTH; x++) {
			for (int y = 0; y < HEIGHT; y++) {
				int color = pixels[x + y * WIDTH];
				int r = (color & 0xff0000) >> 16;
				int g = (color & 0xff00) >> 8;
				int b = (color & 0xff);

				r *= min;
				g *= min;
				b *= min;

				if (r > 255)
					r = 255;
				if (g > 255)
					g = 255;
				if (b > 255)
					b = 255;

				pixels[x + y * WIDTH] = r << 16 | g << 8 | b;
			}
		}

		lights.clear();

		toAdd.add(bird.getPoint());
		for (Point point : toAdd) {
			for (int x = point.getX() - 75; x < point.getX() + 75; x++) {
				for (int y = point.getY() - 75; y < point.getY() + 75; y++) {
					if (lights.get(x + y * WIDTH) != null) {
						lights.put(x + y * WIDTH, lights.get(x + y * WIDTH) + 1);
					} else {
						lights.put(x + y * WIDTH, 1);
					}
				}
			}
		}

		toAdd.clear();

		for (java.util.Map.Entry<Integer, Integer> entry : lights.entrySet()) {
			if (entry.getKey() >= WIDTH * HEIGHT || entry.getKey() < 0) {
				continue;
			}
			int color = pixels[entry.getKey()];
			int r = (color & 0xff0000) >> 16;
			int g = (color & 0xff00) >> 8;
			int b = (color & 0xff);

			int a = entry.getKey();
			if (a > max) {
				a = (int) max;
			}

			r *= a;
			g *= a;
			b *= a;

			if (r > 255)
				r = 255;
			if (g > 255)
				g = 255;
			if (b > 255)
				b = 255;

			pixels[entry.getKey()] = r << 16 | g << 8 | b;
		}
	}

	List<Point> toAdd = new ArrayList<Point>();

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
		if (event.getKeyCode() == KeyEvent.VK_Z) {
			if (clicks.size() > 0) {
				clicks.remove(clicks.size() - 1);
			}
		}

		keyDown.add(event.getKeyCode());
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