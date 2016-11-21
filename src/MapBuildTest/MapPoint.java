package MapBuildTest;


import java.awt.Rectangle;

/**
 *
 * Represents a named node.
 * 
 */
public class MapPoint
{
    protected String name;
    protected Rectangle position;
    
    public MapPoint(){
        this("Unnamed Point", new Rectangle(50, 50, 10, 10));
    }
    
    public MapPoint(String name){
        this(name, new Rectangle(50, 50, 10, 10));
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
        return this.position;
    }
    
    public String saveString(){
        return position.x + " " + position.y + " " + 
               position.width + " " + position.height + " " + name;
    }
}
