package newProject;

import java.util.HashMap;

public class Picture {
	String path;
	Integer xsize;
	Integer ysize;
	Integer xcord;
	Integer ycord;
	public Picture(String path,Integer xsize,Integer ysize,Integer xcord,Integer ycord) {
		this.path = path;
		this.xsize=xsize;
		this.ysize=ysize;
		this.xcord=xcord;
		this.ycord=ycord;
	}
	String getPath(){
		return this.path;
	}
	Integer getxsize(){
		return this.xsize;
	}
	Integer getysize(){
		return this.ysize;
	}
	Integer getycord(){
		return this.ycord;
	}
	Integer getxcord(){
		return this.xcord;
	}
}