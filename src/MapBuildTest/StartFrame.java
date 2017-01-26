package MapBuildTest;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 *
 * @author nina
 */
public class StartFrame extends JFrame{
    private JButton open;
    private JButton build;
    private JPanel buttons;
    private JPanel label;
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(StartFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(StartFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(StartFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(StartFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        StartFrame sf = new StartFrame();
    }
    
    public StartFrame(){
        super("Start Menu");
        buttons = new JPanel();
        buttons.setLayout(new FlowLayout(FlowLayout.CENTER,20,20));
        label = new JPanel(new FlowLayout(FlowLayout.CENTER,0,0));
        buttons.add(new JLabel("           "));
        //this.add(open = new JButton("Open"),BorderLayout.NORTH);
        //this.add(build = new JButton("Create new map"),BorderLayout.SOUTH);
        try {
            Image buttonIcon = ImageIO.read(BuildFrame.class.getResourceAsStream("/resources/iconNew.png"));
            buttonIcon = buttonIcon.getScaledInstance(-1, 200, Image.SCALE_SMOOTH);
            build = new JButton(new ImageIcon(buttonIcon));
            build.setBorder(BorderFactory.createEmptyBorder());
            build.setContentAreaFilled(false);
            buttons.add(build);
        } catch (IOException ex) {
            System.out.println("Error loading button new");
        }
        try {
            Image buttonIcon = ImageIO.read(BuildFrame.class.getResourceAsStream("/resources/iconOpen.png"));
            buttonIcon = buttonIcon.getScaledInstance(-1, 200, Image.SCALE_SMOOTH);
            open = new JButton(new ImageIcon(buttonIcon));
            open.setBorder(BorderFactory.createEmptyBorder());
            open.setContentAreaFilled(false);
            buttons.add(open);
        } catch (IOException ex) {
            System.out.println("Error loading button open");
        }
        label.add(new JLabel("Create new map                                "));
        label.add(new JLabel("Open map           "));
        this.add(buttons,BorderLayout.NORTH);
        this.add(label,BorderLayout.CENTER);
        buttons.setBackground(Color.white);
        label.setBackground(Color.white);
        addListeners();
        
        //this.setPreferredSize(new Dimension(1280, 720));
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    
    private void addListeners(){
        open.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Model m = new Model();
                JFileChooser fc = new JFileChooser();
                int code = fc.showOpenDialog(StartFrame.this);
                if(code == JFileChooser.APPROVE_OPTION){
                    try {
                        m.loadModel(fc.getSelectedFile());
                    }catch(IOException ex){
                        JOptionPane.showMessageDialog(StartFrame.this, "Error loading file", "Warning!", JOptionPane.WARNING_MESSAGE);
                    }
                }
                Object[] choices = { "Edit Map", "Test Map" };
                int mode = JOptionPane.showOptionDialog(null, "Select Mode",
                "Mode", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,null, choices, choices[0]); 
                System.out.println(mode);
                if(mode==0){
                    new BuildFrame(m);
                } else if (mode==1) {
                    new TestFrame(m);
                }
            }
        });
        build.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                new BuildFrame();
            }
        });
    }
}
