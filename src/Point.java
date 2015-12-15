/**
 * 
 * @author xuanhaozhao
 *
 */
public class Point {
	
  private double X;
  private double Y;
  
  public Point(){
	  
  }
  
  public Point(double X,double Y){
	  this.X=X;
	  this.Y=Y;
  }
  
  public boolean equals(Point p){
	  return this.X==p.X&&this.Y==p.Y;
  }
  
  public double getX(){
   	 return X;
    }
    
    public void setX(double X){
   	 this.X=X;
    }
    
    public double getY(){
   	 return Y;
    }
    
    public void setY(double Y){
   	 this.Y=Y;
    }
}
