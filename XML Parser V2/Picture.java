import java.lang.String;
public class Picture {
	private String path;
	private int id;
	private int xSize;
	private int ySize;
	private int xCord;
	private int yCord;
	Picture(int id, String path,int xSize,int ySize,int xCord,int yCord){
		this.id = id;
		this.path=path;
		this.xSize=xSize;
		this.ySize=ySize;
		this.xCord=xCord;
		this.yCord=yCord;		
	}
	int getID(){
		return this.id;
	}
	String getPath(){
		return this.path;
	}
	int getXSize(){
		return this.xSize;
	}
	int getYSize(){
		return this.ySize;
	}
	int getXCord(){
		return this.xCord;
	}
	int getYCord(){
		return this.yCord;	
	}
	
	void setPath(String newP){
		this.path=newP;
	}
	void setxSize(int newXSize){
		this.xSize=newXSize;
	}
	void setYSize(int newYSize){
		this.ySize=newYSize;
	}
	void setXCord(int newXCord){
		this.xCord=newXCord;
	}
	void setYCord(int newYCord){
		this.yCord=newYCord;
	}
}
