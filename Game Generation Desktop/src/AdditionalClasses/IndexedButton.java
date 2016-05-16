package AdditionalClasses;

import javax.swing.*;

/**
 * Created by Evgeniy on 12/3/2015.
 */
public class IndexedButton extends JButton
{
    private final int _index;

    public IndexedButton(int index, String text)
    {
        super(text);

        _index = index;
    }

    public int getIndex()
    {
        return _index;
    }
}
