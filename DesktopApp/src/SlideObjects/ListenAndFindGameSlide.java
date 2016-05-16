package SlideObjects;

import java.io.File;

/**
 * Created by dan on 16/05/2016.
 */
public class ListenAndFindGameSlide extends GameSlide{
    public enum GameType {
        Numbers,
        Colors,
        Animals
    }

    private GameType type;

    public ListenAndFindGameSlide(){}

    public ListenAndFindGameSlide(GameType type){
        this.type = type;
    }

    public void setGameType(GameType type){this.type = type;}

    public GameType getGameType(){
        return this.type;
    }

    @Override
    public SlideType getType() {
        return SlideType.ListenAndFindGame;
    }
}
