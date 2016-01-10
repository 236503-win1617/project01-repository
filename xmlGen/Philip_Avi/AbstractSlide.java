package newProject;

/**
 * Created by Evgeniy on 11/26/2015.
 */
public abstract class AbstractSlide
{
    protected void validateNotNull(Object obj)
    {
        if (obj == null)
        {
            throw new NullPointerException();
        }
    }
}
