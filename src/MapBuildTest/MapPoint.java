package MapBuildTest;


import java.awt.Rectangle;

public class MapPoint
{
    protected String name;
    protected Rectangle position;
    private float x;
    private float y;
    
    public MapPoint(){
        this("Unnamed Point", new Rectangle(50, 50, 10, 10));
    }
    
    public MapPoint(String name){
        this(name, new Rectangle(50, 50, 10, 10));
        x = 50;
        y = 50;
    }
    
    public MapPoint(Rectangle position){
        this("Unnamed Point", position);
    }
    
    public MapPoint(String name, Rectangle position){
        this.name = name;
        this.position = position;
    }
    
    public String getName(){
        return this.name;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public Rectangle getPosition(){
        return position;
    }
    
    public void scalePoint(int oldH, int newH){
        float scale = (float)newH/oldH;
        x = x*scale;
        y = (y-60)*scale + 60;
        this.position = new Rectangle((int)x, (int)y, 10, 10);
    }
    
    public void setPosition(int x, int y){
        //System.out.println("x new: " + newX + " old " + x + ", y new: " + newY + " old: " + y + " scaler: " + scaler);
        this.position = new Rectangle(x, y, 10, 10);
        this.x = x;
        this.y = y;
    }
    
    public String saveString(){
        return position.x + " " + position.y + " " + 
               position.width + " " + position.height + " " + name;
    }
}
