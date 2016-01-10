/**
 * @author philip&avi
 *
 */
package newProject;
import newProject.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
public class unitTest {
	public static void main(String[] args)  {
		ArrayList<AbstractSlide> pictureSlides = new ArrayList<AbstractSlide>();
		File catPic =new File("C:\\Users\\akozokin\\Desktop\\cat.jpg");
		File sound= new File("C:\\Users\\akozokin\\Desktop\\Sound.wav");
		PictureSlide p1= new PictureSlide();
		SoundElement[] soundArray = new SoundElement[4];
		SoundElement s1= new SoundElement(sound, 0, 0, 100, 80);
		SoundElement s2= new SoundElement(sound, 10, 10, 10, 20);
		SoundElement s3= new SoundElement(sound, 2, 3, 30, 30);
		SoundElement s4= new SoundElement(sound, 50, 1, 60, 7);
		soundArray[0]=s1;
		soundArray[1]=s2;
		soundArray[2]=s3;
		soundArray[3]=s4;
		p1.setPictureFile(catPic);
		p1.setSoundElements(soundArray);
		pictureSlides.add(p1);
		pictureSlides.add(p1);
		pictureSlides.add(p1);
		CreateXmlFactory.generate(pictureSlides, "blabla");
	}
}