package MapBuildTest;

import MapBuildTest.EditClasses.ChangePointEdit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

/**
 *
 * @author nina
 */
public class ContextMenu extends JPopupMenu{
    JMenuItem remove;
    JMenuItem rename;
    Panel panel;
    public ContextMenu(Panel p){
        this.add(remove = new JMenuItem("Remove"));
        this.add(rename = new JMenuItem("Rename"));
        panel = p;
        addListeners();
    }    
    
    private void addListeners(){
        remove.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                panel.removeSelection();
            }
        });
        rename.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(panel.getSelectionController().getSelection() == null) return;
                String s = (String)JOptionPane.showInputDialog(ContextMenu.this, "New name: ", "Rename", JOptionPane.QUESTION_MESSAGE, null, null, null);
                if(s == null) return;
                panel.getModel().edit(new ChangePointEdit(panel.getModel(), panel.getSelectionController().getSelection(), s));
            }
        });
    }
}
