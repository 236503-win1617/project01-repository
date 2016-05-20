package SlideObjects;

import java.io.File;

/**
 * Created by dan on 16/05/2016.
 */
public class ListenAndFindGameSlide extends AbstractSlide {
    private GameType type;

    public ListenAndFindGameSlide(){}

    public ListenAndFindGameSlide(GameType type){
        this.type = type;
    }

    public GameType getGameType(){
        return this.type;
    }

    public void setGameType(GameType type) {
        this.type = type;
    }

    @Override
    public SlideType getType() {
        return SlideType.ListenAndFindGame;
    }

    @Override
    public void setSlideFile(File file) {

    }

    @Override
    protected String[] getSupportedFormats() {
        return new String[0];
    }

    public enum GameType {
        Numbers,
        Colors,
        Animals
    }
}
