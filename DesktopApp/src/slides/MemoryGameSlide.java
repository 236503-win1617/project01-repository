package slides;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;

/**
 * Created by dan on 16/05/2016.
 */
public class MemoryGameSlide extends AbstractSlide {
    private int boardSize;


    public MemoryGameSlide() {
        super();
        String[] boardSizes = {"4x4", "4x5", "4x6", "5x5","5x6","6x6"};
        String choice = (String) JOptionPane.showInputDialog(null, "Select board size",
                "Choose Maximal Number", JOptionPane.QUESTION_MESSAGE, null, // Use
                boardSizes, // Array of choices
                boardSizes[0]); // Initial choice

        if (choice == null) return;

        for(int i=0;i<6;i++){
            if(choice.equals(boardSizes[i])){
                this.boardSize = i+1;
            }
        }
    }

    public MemoryGameSlide(int boardSize) {
        this.boardSize = boardSize;
    }

    public int getBoardSize() {
        return this.boardSize;
    }

    public void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
    }

    @Override
    public SlideType getType() {
        return SlideType.MemoryGame;
    }

    @Override
    public void setSlideFile(File file) {

    }

    @Override
    protected String[] getSupportedFormats() {
        return new String[0];
    }
}

