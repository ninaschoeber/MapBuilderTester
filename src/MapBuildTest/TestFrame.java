package MapBuildTest;


import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
public class TestFrame extends JFrame{
    private JMenuItem load;

    private JPanel buttons;
        private JButton start;
        private JButton restart;
        private JButton highlight;
        private JButton next;
    
    private Panel gp;
        
    public TestFrame(){
        super("Map Builder");
        
        JMenuBar bar = new JMenuBar();
        JMenu menu = new JMenu("File");
        menu.add(load = new JMenuItem("Load"));
        bar.add(menu);
        
        buttons = new JPanel(new FlowLayout());
        buttons.add(start = new JButton("Start"));
        buttons.add(restart = new JButton("Restart"));
        buttons.add(highlight = new JButton("Highlight correct answer"));
        buttons.add(next = new JButton("Next"));
        
        this.setJMenuBar(bar);
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
