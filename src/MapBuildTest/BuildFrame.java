package MapBuildTest;


import MapBuildTest.EditClasses.AddPointEdit;
import MapBuildTest.EditClasses.ChangePointEdit;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * Graphical interface showing map
 * 
 */
public class BuildFrame extends JFrame {
    private JMenuItem save;
    private JMenuItem load;
    private JMenuItem changeBG;

    private JPanel buttons;
        private JButton newNode;
        private JButton changeNode;
        private JButton deleteNode;
        private JButton undo;
        private JButton redo;
    
    private Panel gp;
        
    public BuildFrame(){
        super("Map Builder");
        
        JMenuBar bar = new JMenuBar();
        JMenu menu = new JMenu("File");
        menu.add(save = new JMenuItem("Save"));
        menu.add(load = new JMenuItem("Load"));
        menu.add(changeBG = new JMenuItem("Change background"));
        bar.add(menu);
        
        buttons = new JPanel(new FlowLayout());
        buttons.add(newNode = new JButton("New point"));
        buttons.add(changeNode = new JButton("Change name"));
        buttons.add(deleteNode = new JButton("Delete point"));
        buttons.add(undo = new JButton("Undo"));
        buttons.add(redo = new JButton("Redo"));
        
        this.setJMenuBar(bar);
        this.add(buttons, BorderLayout.NORTH);
        this.add(gp = new Panel(Panel.BUILD));
        this.setModel(new Model());
        
        addListeners();
        
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    
    public BuildFrame(Model model){
        this();
        gp.setModel(model);
    }
    

    private void addListeners(){
        //menubar buttons
        save.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                JFileChooser fc = new JFileChooser();
                
                int code = fc.showSaveDialog(BuildFrame.this);
                if(code == JFileChooser.APPROVE_OPTION){
                    File f = fc.getSelectedFile();
                    try{
                        BuildFrame.this.gp.getModel().save(f);
                    }catch(IOException ex){
                        JOptionPane.showMessageDialog(BuildFrame.this, "Error writing graph to file", "Warning!", JOptionPane.WARNING_MESSAGE);
                    }
                    
                }
            }
        });
        
        load.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                JFileChooser fc = new JFileChooser();
                int code = fc.showOpenDialog(BuildFrame.this);
                if(code == JFileChooser.APPROVE_OPTION){
                    try {
                        BuildFrame.this.gp.getModel().load(fc.getSelectedFile());
                    }catch(IOException ex){
                        JOptionPane.showMessageDialog(BuildFrame.this, "Error loading graph from file", "Warning!", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        });
        
        changeBG.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                JFileChooser fc = new JFileChooser();
                int code = fc.showOpenDialog(BuildFrame.this);
                if(code == JFileChooser.APPROVE_OPTION){
                    BuildFrame.this.gp.getModel().setBG(fc.getSelectedFile());
                }
            }
        });
        
        
        //other buttons
        newNode.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                String s = (String)JOptionPane.showInputDialog(BuildFrame.this, "Name: ", "New node", JOptionPane.QUESTION_MESSAGE, null, null, null);
                if(s == null) return;
                gp.getModel().edit(new AddPointEdit(gp.getModel(), new MapPoint(s)));
            }
        });
        
        changeNode.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(gp.getSelectionController().getSelection() == null) return;
                String s = (String)JOptionPane.showInputDialog(BuildFrame.this, "New name: ", "Rename", JOptionPane.QUESTION_MESSAGE, null, null, null);
                if(s == null) return;
                gp.getModel().edit(new ChangePointEdit(gp.getModel(), gp.getSelectionController().getSelection(), s));
            }
        });
        
        deleteNode.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                gp.removeSelection();
            }
        });
        
      
        undo.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                BuildFrame.this.gp.getModel().undo();
            }
        });
        
        redo.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                BuildFrame.this.gp.getModel().redo();
            }
        });
        
        //add hotkey listener
        KeyboardFocusManager mng = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        mng.addKeyEventDispatcher(new KeyEventDispatcher(){

            @Override
            public boolean dispatchKeyEvent(KeyEvent e){
                if(e.getID() == KeyEvent.KEY_PRESSED && e.isControlDown()){
                    switch(e.getKeyCode()){
                        case KeyEvent.VK_Z:
                            BuildFrame.this.undo.doClick();
                            break;
                        case KeyEvent.VK_Y:
                            BuildFrame.this.redo.doClick();
                            break;
                        case KeyEvent.VK_N:
                            BuildFrame.this.newNode.doClick();
                            break;
                        case KeyEvent.VK_R:
                            BuildFrame.this.changeNode.doClick();
                            break;
                        case KeyEvent.VK_S:
                            BuildFrame.this.save.doClick();
                            break;
                        case KeyEvent.VK_L:
                            BuildFrame.this.load.doClick();
                            break;
                    }
                }
                
                return false;
            }
            
        });
    }
    
    public void setModel(Model gm){
        gp.setModel(gm);
    }
    
}
