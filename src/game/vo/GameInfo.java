package game.vo;

import java.io.Serializable;
import java.util.ArrayList;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class GameInfo implements Serializable {

	private double x_point;
	private double y_point;
	private String color;//색 
	private double slider; //선굵기 
	
	
	
	public double getSlider() {
		return slider;
	}
	public void setSlider(double slider) {
		this.slider = slider;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}



	private ArrayList<double[]> geographicInfo = new ArrayList<double[]>();
	
	public GameInfo (){
		
	}
	public ArrayList<double[]> getGeographicInfo() {
		return geographicInfo;
	}



	public void setGeographicInfo(ArrayList<double[]> geographicInfo) {
		this.geographicInfo = geographicInfo;
	}



	
	
	public GameInfo(double x, double y) {
		this.x_point =x;
		this.y_point=y;
	}



	public double getX_point() {
		return x_point;
	}



	public void setX_point(double x_point) {
		this.x_point = x_point;
	}



	public double getY_point() {
		return y_point;
	}



	public void setY_point(double y_point) {
		this.y_point = y_point;
	}



	@Override
	public String toString() {
		return "GameInfo [x_point=" + x_point + ", y_point=" + y_point + "]";
	}

	
}
