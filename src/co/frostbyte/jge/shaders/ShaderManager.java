package co.frostbyte.jge.shaders;

import co.frostbyte.jge.Test2;

public class ShaderManager {
    public static boolean ENABLED = true;

    // 24 = Dark and 1 - Light
    public static int ALPHA = 12;
    public static double POST_ALPHA = 1.0 / ALPHA;

    public void update() {

    }

    public static void draw(int[] pixels) {
        if (!ENABLED){
            return;
        }

        int[] preRender = pixels.clone();

        for (int x = 0; x < Test2.WIDTH; x++) {
            for (int y = 0; y < Test2.HEIGHT; y++) {
                int color = pixels[x + y * Test2.WIDTH];
                int r = (color & 0xff0000) >> 16;
                int g = (color & 0xff00) >> 8;
                int b = (color & 0xff);

                r *= POST_ALPHA;
                g *= POST_ALPHA;
                b *= POST_ALPHA;

                if (r > 255)
                    r = 255;
                if (g > 255)
                    g = 255;
                if (b > 255)
                    b = 255;

                pixels[x + y * Test2.WIDTH] = r << 16 | g << 8 | b;
            }
        }

    }
}
