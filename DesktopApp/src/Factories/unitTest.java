//package Factories;
///**
// * @author philip&avi
// *
// */
//
//import SlideObjects.*;
//import java.io.File;
//import java.io.IOException;
//import java.util.ArrayList;
//import Factories.*;
//import AdditionalClasses.*;
//public class unitTest {
//	public static void main(String[] args)  {
//		ArrayList<AbstractSlide> slides = new ArrayList<AbstractSlide>();
//		File catPic =new File("C://Users//Desktop//cat.jpg");
//		File sound= new File("C://Users//Desktop//Sound.wav");
//		PictureSlide p1= new PictureSlide();
//		GameSlide.GameType animalType = GameSlide.GameType.Animals;
//		GameSlide game = new GameSlide(animalType);
//		SoundElement[] soundArray = new SoundElement[4];
//		SoundElement s1= new SoundElement(sound, 0, 0, 100, 80);
//		SoundElement s2= new SoundElement(sound, 10, 10, 10, 20);
//		SoundElement s3= new SoundElement(sound, 2, 3, 30, 30);
//		SoundElement s4= new SoundElement(sound, 50, 1, 60, 7);
//		soundArray[0]=s1;
//		soundArray[1]=s2;
//		soundArray[2]=s3;
//		soundArray[3]=s4;
//		p1.setPictureFile(catPic);
//		p1.setSoundElements(soundArray);
//		slides.add(p1);
//		slides.add(p1);
//		slides.add(p1);
//		slides.add(game);
//		CreateXmlFactory.generate(slides, "blabla");
//	}
//}