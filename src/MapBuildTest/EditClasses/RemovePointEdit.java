package MapBuildTest.EditClasses;

import MapBuildTest.MapPoint;
import MapBuildTest.Model;
import javax.swing.undo.AbstractUndoableEdit;




public class RemovePointEdit extends AbstractUndoableEdit{
    private final Model model;
    private final MapPoint toRemove;
    
    public RemovePointEdit(Model model, MapPoint toRemove){
        this.model = model;
        this.toRemove = toRemove;
        model.removePoint(toRemove);
    }
    
    @Override
    public void undo(){
        super.undo();
        model.addPoint(toRemove);
        
    }
    
    public void redo(){
        super.redo();
        
        model.removePoint(toRemove);
    }

}
