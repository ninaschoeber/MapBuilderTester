package MapBuildTest;

import MapBuildTest.EditClasses.ChangePointEdit;
import java.awt.Color;
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
    JMenuItem changeColour;
    
    Panel panel;
    public ContextMenu(Panel p){
        this.add(remove = new JMenuItem("Remove"));
        this.add(rename = new JMenuItem("Rename"));
        this.add(changeColour = new JMenuItem("Change Pin Colour"));
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
        changeColour.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(panel.getSelectionController().getSelection() == null) return;
                System.out.println("HALLOO?");
                String[] choices = { "Red", "Blue", "Green", "Yellow" };
                String colour = (String) JOptionPane.showInputDialog(null, "Select colour",
                "Change Pin Colour", JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]); 
                MapPoint toChange = panel.getSelectionController().getSelection();
                switch(colour){
                    case "Yellow":
                        toChange.setColour(Color.yellow);
                        break;
                    case "Blue":
                        toChange.setColour(Color.blue);
                        break;
                    case "Green":
                        toChange.setColour(Color.green);
                        break;
                    default:
                        toChange.setColour(Color.yellow);
                }
            }
        });
    }
}
