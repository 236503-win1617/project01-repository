package SlideObjects;

/**
 * Created by Evgeniy on 11/26/2015.
 */
public abstract class AbstractSlide
{
    public abstract SlideType getType();

    protected void validateNotNull(Object obj)
    {
        if (obj == null)
        {
            throw new NullPointerException();
        }
    }
}
