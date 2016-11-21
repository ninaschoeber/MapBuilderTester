package MapBuildTest.EditClasses;


import MapBuildTest.MapPoint;
import MapBuildTest.Model;
import javax.swing.undo.AbstractUndoableEdit;


public class AddPointEdit extends AbstractUndoableEdit{
    private final Model model;
    private final MapPoint toAdd;
    
    public AddPointEdit(Model model, MapPoint toAdd){
        this.model = model;
        this.toAdd = toAdd;
        
        model.addPoint(toAdd);
    }
    
    
    @Override
    public void undo(){
        super.undo();
        model.removePoint(toAdd);
    }
    
    @Override
    public void redo(){
        super.redo();
        model.addPoint(toAdd);
    }
 
}
