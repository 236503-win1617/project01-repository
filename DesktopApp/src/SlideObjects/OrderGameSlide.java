package SlideObjects;

import java.io.File;

/**
 * Created by dan on 16/05/2016.
 */
public class OrderGameSlide extends AbstractSlide {
    private int max_num;

    public OrderGameSlide(){
        super();
    }

    public OrderGameSlide(int max_num){
        this.max_num = max_num;
    }

    public int getMaxNum(){ return this.max_num;}

    public void setMaxNum(int max_num){
        this.max_num = max_num;
    }

    @Override
    public SlideType getType() {
        return SlideType.OrderGame;
    }

    @Override
    public void setSlideFile(File file) {

    }

    @Override
    protected String[] getSupportedFormats() {
        return new String[0];
    }
}
