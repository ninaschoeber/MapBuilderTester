package MapBuildTest;


import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
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
public class TestFrame extends JFrame{

    private JPanel buttons;
        private JButton start;
        private JButton restart;
        private JButton highlight;
        private JButton next;
        private JButton load;
    
    private Panel gp;
        
    public TestFrame(){
        super("Map Builder");
        
        
        buttons = new JPanel(new FlowLayout());
        
        try {
            Image buttonIcon = ImageIO.read(new File("resources/iconOpen.png"));
            buttonIcon = buttonIcon.getScaledInstance(-1, 80, Image.SCALE_SMOOTH);
            load = new JButton(new ImageIcon(buttonIcon));
            load.setBorder(BorderFactory.createEmptyBorder());
            load.setContentAreaFilled(false);
            buttons.add(load);
        } catch (IOException ex) {
            System.out.println("Error loading button start");
        }
        
        try {
            Image buttonIcon = ImageIO.read(new File("resources/iconStart.png"));
            buttonIcon = buttonIcon.getScaledInstance(-1, 80, Image.SCALE_SMOOTH);
            start = new JButton(new ImageIcon(buttonIcon));
            start.setBorder(BorderFactory.createEmptyBorder());
            start.setContentAreaFilled(false);
        } catch (IOException ex) {
            System.out.println("Error loading button start");
        }
        
        try {
            Image buttonIcon = ImageIO.read(new File("resources/iconRestart.png"));
            buttonIcon = buttonIcon.getScaledInstance(-1, 80, Image.SCALE_SMOOTH);
            restart = new JButton(new ImageIcon(buttonIcon));
            restart.setBorder(BorderFactory.createEmptyBorder());
            restart.setContentAreaFilled(false);
            buttons.add(restart);
        } catch (IOException ex) {
            System.out.println("Error loading button restart");
        }
                
        try {
            Image buttonIcon = ImageIO.read(new File("resources/iconNext.png"));
            buttonIcon = buttonIcon.getScaledInstance(-1, 80, Image.SCALE_SMOOTH);
            next = new JButton(new ImageIcon(buttonIcon));
            next.setBorder(BorderFactory.createEmptyBorder());
            next.setContentAreaFilled(false);
            buttons.add(next);
        } catch (IOException ex) {
            System.out.println("Error loading button next");
        }
        
        try {
            Image buttonIcon = ImageIO.read(new File("resources/iconHighlight.png"));
            buttonIcon = buttonIcon.getScaledInstance(-1, 80, Image.SCALE_SMOOTH);
            highlight = new JButton(new ImageIcon(buttonIcon));
            highlight.setBorder(BorderFactory.createEmptyBorder());
            highlight.setContentAreaFilled(false);
            buttons.add(highlight);
        } catch (IOException ex) {
            System.out.println("Error loading button next");
        }
        
        
        this.add(buttons, BorderLayout.NORTH);
        this.add(gp = new Panel(Panel.TEST));
        setModel(new Model());
        
        addListeners();
        
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    
    public TestFrame(Model model){
        this();
        gp.setModel(model);

    }
    

    private void addListeners(){
        //menubar buttons
       
        load.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                JFileChooser fc = new JFileChooser();
                int code = fc.showOpenDialog(TestFrame.this);
                if(code == JFileChooser.APPROVE_OPTION){
                    try{
                        TestFrame.this.gp.getModel().load(fc.getSelectedFile());
                    }catch(IOException ex){
                        JOptionPane.showMessageDialog(TestFrame.this, "Error loading map from file", "Warning!", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        });
        
        //other buttons
        start.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                TestFrame.this.gp.start();
            }
        });

        restart.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                TestFrame.this.gp.start();
            }
        });
                        
        highlight.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                TestFrame.this.gp.getSelectionController().setSelection(gp.getCurrent());
            }
        });
        
        next.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                TestFrame.this.gp.nextQuestion();
            }
        });
    }
    
    public void setModel(Model gm){
        gp.setModel(gm);
    }
    
}
