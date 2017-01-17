package MapBuildTest;


import MapBuildTest.EditClasses.RemovePointEdit;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * Drawing pane for graph
 * 
 */
public class Panel extends JPanel{
    private Model model;
    private SelectionController sc;
    private List<MapPoint> questionsLeft;
    private MapPoint currentQ;
    private Boolean incorrectFlag;
    private Boolean isLast;
    private int type;
    private float scale;
    
    public static final int BUILD = 0;
    public static final int TEST = 1;
    
    public Panel(int ty){
        //this.setPreferredSize(new Dimension(1280, 720));
        this.type = ty;
        isLast = false;
        scale = 1;
    }
    
    
    public int getType(){
        return type;
    }
    
    public void setModel(Model gm){
        if(this.model != null)
        {
            model.removeAllListeners();
        }
        
        this.model = gm;
        model.addChangeListener(new ChangeListener(){
            @Override
            public void changeEventReceived(ChangeEvent evt){
                Panel.this.repaint();
            }
        });
        sc = new SelectionController(gm, this);
        scale = gm.getScale();
        if(scale==0){
            scale = 1;
        }
        scaleAllPoints();
    }
    
    public void zoom(boolean in){ //if True zoom in, if False zoom out
        if(in){
            scale += 0.2;
        } else {
            if(scale>0.5){
                scale -= 0.2;
            }
        }
        model.setScale(scale); //only to be able to save the scale
        scaleAllPoints();
        //System.out.println(scale);
        repaint();
    }

    public SelectionController getSelectionController(){
        return this.sc;
    }
      
    
    public void removeSelection(){
        if(sc.getSelection() == null) return;
        model.edit(new RemovePointEdit(model, sc.getSelection()));
    }
    
    public Point getCenter(Rectangle r){
        return new Point(r.x + r.width/2, r.y + r.height/2);
    }
    
    public Model getModel(){
        return this.model;
    }
    
    
    public void start(){
        isLast = false;
        questionsLeft = new ArrayList<MapPoint>(model.getPoints()) {};
        if(questionsLeft.size()<1){
            return;
        }
        Collections.shuffle(questionsLeft);
        model.setScore(0,0,0);
        nextQuestion();
    }
    
    public void nextQuestion(){
        if(questionsLeft==null || questionsLeft.size()<1){
            isLast = true;
            return;
        }
        currentQ = questionsLeft.get(0);
        questionsLeft.remove(0);
        incorrectFlag = false;
        this.repaint();
    }
    
    public MapPoint getCurrent(){
        return currentQ;
    }
    
    public void newSel(){
        if((!isLast) && sc.getSelection() == null ? currentQ.getName() == null : sc.getSelection().getName().equals(currentQ.getName())){
            if(!incorrectFlag){
                model.addCorrect();
            }
            nextQuestion();
        } else if (!incorrectFlag){
            model.addIncorrect();
            incorrectFlag = true;
            questionsLeft.add(currentQ);
        }
    }
    
    public float getScale(){
        return scale;
    }
    
    private void scaleAllPoints(){
        for(MapPoint gv : model.getPoints()){
            gv.scalePoint(scale);
        }
    }
    
    protected void paintPoint(Graphics2D g, MapPoint gv){
        Rectangle pos = gv.getPosition();
        
        g.setStroke(new BasicStroke(2));
        g.setColor(Color.LIGHT_GRAY);
        g.drawLine(pos.x+(pos.width/2), pos.y+(pos.height/2), pos.x+(pos.width/2), pos.y+(pos.height/2)+10);
        
        g.setColor(gv.getColour());
        g.fillOval(pos.x, pos.y, pos.width, pos.height);
                
        if(sc.isSelected(gv)){
            g.setColor(Color.WHITE);
        }else g.setColor(Color.BLACK);
        g.drawOval(pos.x, pos.y, pos.width, pos.height);
    }
    

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        
        scale = model.getScale(); //because when the file is saved at a specific scale, the image does not load at that scale yet
        
        if(model.getBG()!=null){
            try {
                BufferedImage bgImage = ImageIO.read(model.getBG());
                float imgHeight = bgImage.getHeight();
                int newW = (int) (bgImage.getWidth()*scale);
                int newH = (int) (bgImage.getHeight()*scale);
                g.drawImage(bgImage, 0, 60, newW, newH, null);
            } catch (IOException ex) {
                Logger.getLogger(Panel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        MapPoint grv = sc.getSelection();
        for(MapPoint gv : model.getPoints()){
            paintPoint((Graphics2D)g, gv);
        }
        
        switch(type){
            case BUILD:
                if(sc.getSelection() != null){
                    g.drawString("Current selection: " + grv.getName(), 20, 20);
                }
                break;
            case TEST:
                if(currentQ != null){
                    g.drawString("Click " + currentQ.getName(), 20, 20);
                }       
                g.drawString((questionsLeft!=null ? "Questions left: " + questionsLeft.size() : " ") + " Total number of questions: " + model.getTotal() + "\n Correctly answered: " + model.getCorrect() + "\n Incorrectly answered " + model.getIncorrect(), 20, 35);
                if(isLast){
                    g.drawString("All questions have been answered", 20, 50);
                }
        }

    }
}
