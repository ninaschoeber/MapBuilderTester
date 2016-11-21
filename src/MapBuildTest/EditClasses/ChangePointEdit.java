package MapBuildTest.EditClasses;


import MapBuildTest.MapPoint;
import MapBuildTest.Model;
import javax.swing.undo.AbstractUndoableEdit;



public class ChangePointEdit extends AbstractUndoableEdit{
    
    private final Model model;
    private final MapPoint changed;
    private final String oldName;
    private final String newName;
    
    public ChangePointEdit(Model model, MapPoint changed, String newName){
        this.model = model;
        this.changed = changed;
        this.newName = newName;
        this.oldName = changed.getName();
        
        changed.setName(newName);
    }
    
    @Override
    public void undo(){
        super.undo();
        changed.setName(oldName);
    }
    
    @Override
    public void redo(){
        super.redo();
        changed.setName(newName);
    }
    
}
