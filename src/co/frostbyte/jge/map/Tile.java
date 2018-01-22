package co.frostbyte.jge.map;

import co.frostbyte.jge.GameManager;
import co.frostbyte.jge.display.ImageAsset;

public class Tile {
    private ImageAsset image;

    public Tile(String name){
         image = new ImageAsset("/Blocks/"+ name);
    }

    public enum Type {
        FLUID,
        SOLID,
        SEMI_SOLID
    }

    public void draw(int[] pixels, int startX, int startY){
        for (int x = 0; x < 20; x++){
            if (x + startX < 0){
                continue;
            }

            for (int y = 0; y < 20; y++){
                if (y + startY < 0){
                    continue;
                }

                pixels[(x + startX) + (y + startY) * GameManager.WIDTH] = image.getPixels()[x + y * 20];
            }
        }
    }

    public ImageAsset getImage(){
        return image;
    }
}
