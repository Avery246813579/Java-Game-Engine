package co.frostbyte.jge.shaders;

import co.frostbyte.jge.entities.Sprite;
import co.frostbyte.jge.GameManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShaderManager {
    public static boolean ENABLED = true;
    public static int ALPHA = 3;
    public static double POST_ALPHA = 1.0 / ALPHA;
    public static List<StaticShade> STATIC_SHADES = new ArrayList<>();

    public static void draw(int[] pixels) {
        if (!ENABLED) {
            return;
        }

        int[] preRender = pixels.clone();

        for (int x = 0; x < GameManager.WIDTH; x++) {
            for (int y = 0; y < GameManager.HEIGHT; y++) {
                int color = pixels[x + y * GameManager.WIDTH];
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

                pixels[x + y * GameManager.WIDTH] = r << 16 | g << 8 | b;
            }
        }

        Map<Integer, Double> lighting = new HashMap<>();
        for (Sprite sprite : GameManager.sprites) {
            Shade shade = (Shade) sprite.getComponent(Shade.class);

            if (shade != null) {
                shade.draw(pixels, lighting, sprite.getPoint());
            }
        }

        for (StaticShade shade : STATIC_SHADES){
            shade.shade.draw(pixels, lighting, shade.point);
        }

        for (java.util.Map.Entry<Integer, Double> entry : lighting.entrySet()) {
            if (entry.getKey() >= GameManager.WIDTH * GameManager.HEIGHT || entry.getKey() < 0) {
                continue;
            }
            int color = preRender[entry.getKey()];
            int r = (color & 0xff0000) >> 16;
            int g = (color & 0xff00) >> 8;
            int b = (color & 0xff);

            r *= (1.0 / entry.getValue());
            g *= (1.0 / entry.getValue());
            b *= (1.0 / entry.getValue());

            if (r > 255)
                r = 255;
            if (g > 255)
                g = 255;
            if (b > 255)
                b = 255;

            pixels[entry.getKey()] = r << 16 | g << 8 | b;
        }
    }
}
