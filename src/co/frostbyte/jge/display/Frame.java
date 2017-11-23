package co.frostbyte.jge.display;

public class Frame {
    private ImageAsset image;
    private short timespan;

    public Frame (String location, short timespan){
        this.image = new ImageAsset(location);
        this.timespan = timespan;
    }

    public ImageAsset getImage() {
        return image;
    }

    public short getTimespan() {
        return timespan;
    }

    public int[] getPixels(){
        return image.getPixels();
    }

    public int getWidth(){
        return image.getWidth();
    }

    public int getHeight(){
        return image.getHeight();
    }
}
