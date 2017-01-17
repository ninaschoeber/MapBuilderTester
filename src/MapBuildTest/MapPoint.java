package MapBuildTest;


import java.awt.Color;
import java.awt.Rectangle;

public class MapPoint
{
    protected String name;
    protected Rectangle position;
    private float scale;
    private Color colour;
    
    public MapPoint(){
        this("Unnamed Point", new Rectangle(50, 50, 10, 10), 1);
    }
    
    public MapPoint(String name, float scale){
        this(name, new Rectangle(50, 50, 10, 10), scale);
    }
    
    public MapPoint(Rectangle position, float scale){
        this("Unnamed Point", position, scale);
    }
    
    public MapPoint(String name, Rectangle position, float scale){
        this(name,position,scale,Color.red);
    }
    
    public MapPoint(String name, Rectangle position, float scale, Color colour){
        this.name = name;
        this.position = position;
        this.scale = scale;
        this.colour = colour;
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
    
    public Color getColour(){
        return colour;
    }
    
    public void setColour(Color c){
        this.colour = c;
    }
    
    public void scalePoint(float s){
        int x = (int) (this.position.x*(s/scale));
        int y = (int) ((this.position.y-60)*(s/scale)) + 60;
        this.position = new Rectangle(x, y, 10, 10);
        //System.out.println("aanpassing: " + s/scale);
        scale = s;
    }
    
    public void setPosition(int x, int y){
        //System.out.println("x new: " + newX + " old " + x + ", y new: " + newY + " old: " + y + " scaler: " + scaler);
        this.position = new Rectangle(x, y, 10, 10);
    }
    
    public String saveString(){
        return position.x + " " + position.y + " " + 
               position.width + " " + position.height + " " + Integer.toString(colour.getRGB()) + " " + name;
    }
}
