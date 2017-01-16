package MapBuildTest;


import MapBuildTest.EditClasses.AddPointEdit;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * Graphical interface showing map
 * 
 */
public class BuildFrame extends JFrame {
    
    private JPanel buttons;
        private JButton newNode;
        private JButton changeNode;
        private JButton deleteNode;
        private JButton undo;
        private JButton redo;
        
        private JButton save;
        private JButton load;
        private JButton changeBG;
    
    private Panel gp;
        
    public BuildFrame(){
        super("Map Builder");
                
        buttons = new JPanel(new FlowLayout());

        //This should be made less bulky code (list?)
        try {
            Image buttonIcon = ImageIO.read(new File("resources/iconSave.png"));
            buttonIcon = buttonIcon.getScaledInstance(-1, 80, Image.SCALE_SMOOTH);
            save = new JButton(new ImageIcon(buttonIcon));
            save.setBorder(BorderFactory.createEmptyBorder());
            save.setContentAreaFilled(false);
            buttons.add(save);
        } catch (IOException ex) {
            System.out.println("Error loading button save");
        }
        
        try {
            Image buttonIcon = ImageIO.read(new File("resources/iconOpen.png"));
            buttonIcon = buttonIcon.getScaledInstance(-1, 80, Image.SCALE_SMOOTH);
            load = new JButton(new ImageIcon(buttonIcon));
            load.setBorder(BorderFactory.createEmptyBorder());
            load.setContentAreaFilled(false);
            buttons.add(load);
        } catch (IOException ex) {
            System.out.println("Error loading button load");
        }
        
        try {
            Image buttonIcon = ImageIO.read(new File("resources/iconImage.png"));
            buttonIcon = buttonIcon.getScaledInstance(-1, 80, Image.SCALE_SMOOTH);
            changeBG = new JButton(new ImageIcon(buttonIcon));
            changeBG.setBorder(BorderFactory.createEmptyBorder());
            changeBG.setContentAreaFilled(false);
            buttons.add(changeBG);
        } catch (IOException ex) {
            System.out.println("Error loading button changeBG");
        }
                
        try {
            Image buttonIcon = ImageIO.read(new File("resources/iconNewPin.png"));
            buttonIcon = buttonIcon.getScaledInstance(-1, 80, Image.SCALE_SMOOTH);
            newNode = new JButton(new ImageIcon(buttonIcon));
            newNode.setBorder(BorderFactory.createEmptyBorder());
            newNode.setContentAreaFilled(false);
            buttons.add(newNode);
        } catch (IOException ex) {
            System.out.println("Error loading button newNode");
        }

        try {
            Image buttonIcon = ImageIO.read(new File("resources/iconUndo.png"));
            buttonIcon = buttonIcon.getScaledInstance(-1, 80, Image.SCALE_SMOOTH);
            undo = new JButton(new ImageIcon(buttonIcon));
            undo.setBorder(BorderFactory.createEmptyBorder());
            undo.setContentAreaFilled(false);
            buttons.add(undo);
        } catch (IOException ex) {
            System.out.println("Error loading button undo");
        }
        
        try {
            Image buttonIcon = ImageIO.read(new File("resources/iconRedo.png"));
            buttonIcon = buttonIcon.getScaledInstance(-1, 80, Image.SCALE_SMOOTH);
            redo = new JButton(new ImageIcon(buttonIcon));
            redo.setBorder(BorderFactory.createEmptyBorder());
            redo.setContentAreaFilled(false);
            buttons.add(redo);
        } catch (IOException ex) {
            System.out.println("Error loading button redo");
        }
        
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);       
        this.add(buttons, BorderLayout.NORTH);
        this.add(gp = new Panel(Panel.BUILD));
        gp.setBackground(Color.white);
        buttons.setBackground(Color.white);
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
                        JOptionPane.showMessageDialog(BuildFrame.this, "Error writing file", "Warning!", JOptionPane.WARNING_MESSAGE);
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
                        JOptionPane.showMessageDialog(BuildFrame.this, "Error loading file", "Warning!", JOptionPane.WARNING_MESSAGE);
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
        /*
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
        */
      
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
