package AdditionalClasses;

import javax.swing.*;

/**
 * Created by Evgeniy on 12/3/2015.
 */
public class IndexedButton extends JButton
{
    private final int index;

    public IndexedButton(int index)
    {
        super();
        this.index = index;
    }

    public int getIndex()
    {
        return index;
    }
}
