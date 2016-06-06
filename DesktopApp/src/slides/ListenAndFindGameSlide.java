package slides;

import javax.swing.*;
import java.io.File;

/**
 * Created by dan on 16/05/2016.
 */
public class ListenAndFindGameSlide extends AbstractSlide {
    private GameType type;

    public ListenAndFindGameSlide() {
        String[] gameTypes = {"Animals", "Colors", "Numbers"};
        String choice = (String) JOptionPane.showInputDialog(null, "What game do you want?",
                "Choose Type of Game", JOptionPane.QUESTION_MESSAGE, null, // Use
                gameTypes, // Array of choices
                gameTypes[0]); // Initial choice
        if (choice == null) return;
        if (choice.equals("Animals")) {
            type = ListenAndFindGameSlide.GameType.Animals;
        } else if (choice.equals("Colors")) {
            type = ListenAndFindGameSlide.GameType.Colors;
        } else if (choice.equals("Numbers")) {
            type = ListenAndFindGameSlide.GameType.Numbers;
        }
    }

    public ListenAndFindGameSlide(GameType type) {
        this.type = type;
    }

    public GameType getGameType() {
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
