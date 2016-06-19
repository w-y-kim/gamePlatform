package game.vo;

import java.io.Serializable;

public class GameInfo implements Serializable {

	double x_point;
	double y_point; 
	
	public GameInfo(double x, double y) {
		x_point = x;
		y_point = y; 
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
