package co.frostbyte.jge.entities;

public class Animation {
    private Frame[] frames;
    private short timespan = 0, currentFrame = 0;
    private boolean paused = false;

    public Animation (Frame[] frames){
        this.frames = frames;
    }

    public Frame getCurrentFrame(){
        return frames[currentFrame];
    }

    public void update(){
        if (paused){
            return;
        }

        timespan += 1;
        if (frames[currentFrame].getTimespan() + 1 < timespan){
            currentFrame += 1;
            timespan = 0;

            if (currentFrame + 1 > frames.length){
                currentFrame = 0;
            }
        }
    }
}
