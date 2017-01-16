package MapBuildTest.EditClasses;


import MapBuildTest.MapPoint;
import MapBuildTest.Model;
import MapBuildTest.Panel;
import java.awt.Point;
import javax.swing.undo.AbstractUndoableEdit;



public class MovePointEdit extends AbstractUndoableEdit{
    
    private final Panel panel;
    private final MapPoint moved;
    private final Point start;
    private final Point end;
    
    public MovePointEdit(Model model, Panel panel, MapPoint moved, Point start, Point end){
        this.moved = moved;
        this.start = start;
        this.end = end;
        this.panel = panel;
        moved.setPosition(end.x,end.y);
    }
    
    @Override
    public void undo(){
        super.undo();
        moved.getPosition().x = start.x;
        moved.getPosition().y = start.y;
    }
    
    @Override
    public void redo(){
        super.redo();
        moved.getPosition().x = end.x;
        moved.getPosition().y = end.y;
    }
    
}
