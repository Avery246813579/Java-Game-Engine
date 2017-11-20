package co.frostbyte.jge.entities;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class ImageAsset {
    private String name;
    private int[] pixels;
    private int width, height;

    public ImageAsset(String location) {
        try{
            BufferedImage image = ImageIO.read(getClass().getResourceAsStream(location));

            width = image.getWidth();
            height = image.getHeight();
            pixels = new int[width * height];
            image.getRGB(0, 0, width, height, pixels, 0, width);
        }catch(Exception ex){
            System.out.println("Error loading frame at: " + location);
        }
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public int[] getPixels(){
        return pixels;
    }

    public String getName(){
        return name;
    }
}
