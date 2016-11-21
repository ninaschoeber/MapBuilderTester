package MapBuildTest.EditClasses;


import MapBuildTest.MapPoint;
import MapBuildTest.Model;
import java.awt.Point;
import javax.swing.undo.AbstractUndoableEdit;



public class MovePointEdit extends AbstractUndoableEdit{
    
    private final Model model;
    private final MapPoint moved;
    private final Point start;
    private final Point end;
    
    public MovePointEdit(Model model, MapPoint moved, Point start, Point end){
        this.model = model;
        this.moved = moved;
        this.start = start;
        this.end = end;
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
