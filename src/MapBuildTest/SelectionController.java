package MapBuildTest;


import MapBuildTest.EditClasses.MovePointEdit;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 *
 * @author jos
 */
public class SelectionController implements MouseListener, MouseMotionListener{
    private Model model;
    private Panel panel;
    
    //used for dragging
    private boolean isDragging;
    private Point lastPos;
    private Point startPos;
    private MapPoint tracked;
    private MapPoint selected; //used for adding/removing
    
    public SelectionController(Model model, Panel gp){
        this.model = model;
        selected = null;
        this.panel = gp;
        
      
        lastPos = new Point(0, 0);
        tracked = null;
        
        gp.addMouseListener(this);
        gp.addMouseMotionListener(this);
    }
    
    public boolean isSelected(MapPoint gv){
        return selected == gv;
    }
    
    public MapPoint getSelection(){
        return this.selected;
    }
    
    public void setSelection(MapPoint gv){
        selected = gv;
        panel.repaint();
    }
    
    
    public Point getMousePosition(){
        return this.lastPos;
    }
    
    @Override
    public void mouseClicked(MouseEvent e){
        if(e.getButton() == MouseEvent.BUTTON3){
            selected = null;
            panel.repaint();
            return;
        }
        
        for(MapPoint gv : model.getPoints()){
            if(gv.getPosition().intersects(new Rectangle(e.getX(), e.getY(), 1, 1)))
            {   
                selected = gv;
                if(panel.getType()==1) panel.newSel();
                break;
            }
        }
        panel.repaint();
    }

    @Override
    public void mousePressed(MouseEvent e){
        if(panel.getType()==1) return; //No dragging in test mode
        for(MapPoint gv : model.getPoints()){
            if(gv.getPosition().intersects(new Rectangle(e.getX(), e.getY(), 1, 1))){
                tracked = gv;
                lastPos = e.getPoint();
                startPos = new Point();
                startPos.x = tracked.getPosition().x;
                startPos.y = tracked.getPosition().y;
                              
                break;
                
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e){
        if(startPos != null && tracked != null){
            Point endPos = new Point();
            endPos.x = tracked.getPosition().x;
            endPos.y = tracked.getPosition().y;
            
            model.edit(new MovePointEdit(model, tracked, startPos, endPos));
        }
        
        startPos = null;
        tracked = null;
    }

    @Override
    public void mouseEntered(MouseEvent e){
    }

    @Override
    public void mouseExited(MouseEvent e){
    }

    @Override
    public void mouseDragged(MouseEvent e){   
        if(tracked != null){
            if(startPos == null){
                startPos.x = tracked.getPosition().x;
                startPos.y = tracked.getPosition().y;
            }
            
            int newX = lastPos.x - e.getX();
            int newY = lastPos.y - e.getY();
            
            tracked.getPosition().x -= newX;
            tracked.getPosition().y -= newY;
            panel.repaint();
        }
        lastPos = e.getPoint();
    }

    @Override
    public void mouseMoved(MouseEvent e){
        lastPos = e.getPoint();
    }
        
}
