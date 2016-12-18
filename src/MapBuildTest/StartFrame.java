package MapBuildTest;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author nina
 */
public class StartFrame extends JFrame{
    private JButton test;
    private JButton build;
    
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
        this.add(test = new JButton("Test"),BorderLayout.NORTH);
        this.add(build = new JButton("Build"),BorderLayout.SOUTH);
        
        addListeners();
        
        this.setPreferredSize(new Dimension(1280, 720));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    
    private void addListeners(){
        test.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                new TestFrame();
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
