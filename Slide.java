import java.util.ArrayList;


public class Slide {
	private ArrayList<Picture> picArr;
	Slide(){
		this.picArr = new ArrayList<Picture>();
	}
	Slide(ArrayList<Picture> pArr){
		this.picArr= pArr;
	}
	
	void addPicture(Picture pic){
		picArr.add(pic);
	}
	Picture getPicture(int id){
		for (Picture pic : picArr){
			if (pic.getID() == id){
				return pic;
			}
		}
		return null;
	}
	ArrayList<Picture> getPicArr(){
		return this.picArr;
	}
}
