package AdditionalClasses;

import javax.swing.*;
import java.util.UUID;

/**
 * Created by Evgeniy on 12/17/2015.
 */
public class UniqueTextPane extends JTextPane
{
    private final UUID id;

    public UniqueTextPane(UUID id)
    {
        super();
        this.id = id;
    }

    public UUID getId()
    {
        return id;
    }
}
