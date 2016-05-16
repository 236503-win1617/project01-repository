package SlideObjects;

import java.io.File;

/**
 * Created by Evgeniy on 11/26/2015.
 */

public class GameSlide extends AbstractSlide
{
    public enum GameType {
        Numbers,
        Colors,
        Animals
    }

    private GameType type;

    public GameSlide(GameType type){
        this.type = type;
    }

    public GameType getGameType(){
        return this.type;
    }

    @Override
    public SlideType getType() {
        return SlideType.Game;
    }

    protected String[] getSupportedFormats(){
        return null;
    }

    public void setSlideFile(File file){}
}
