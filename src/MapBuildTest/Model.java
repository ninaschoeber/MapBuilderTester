package MapBuildTest;

import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.UndoManager;

/**
 *
 * @author nina
 */
public class Model{
    private final CopyOnWriteArrayList<ChangeListener> listeners;
    protected List<MapPoint> points;
    protected UndoManager actions;
    private File bg;
    private int total;
    private int correct;
    private int incorrect;
    private int savedHeight;
        
    public Model(){
        this.listeners = new CopyOnWriteArrayList<>();
        points = Collections.synchronizedList(new ArrayList());
        actions = new UndoManager();
        actions.setLimit(-1);
        total = 0;
        correct = 0;
        incorrect = 0;
        savedHeight = 0;
    }
    
    public Model(File model) throws IOException {
        this();
        this.load(model);
    }
    
    public void edit(AbstractUndoableEdit edit) {
        this.actions.addEdit(edit);
        this.change();
    }
    
    public void undo() {
        if(actions.canUndo())
        {
             actions.undo();
             this.change();
        }
    }
        
    public void redo() {
        if(actions.canRedo())
        {
             actions.redo();
             this.change();
        }
    }
    
    public int getSavedHeight(){
        return savedHeight;
    }
    
    public void setSavedHeight(int h){
        savedHeight = h;
    }
    
    public int getTotal(){
        return total;
    }
    
    public int getCorrect(){
        return correct;
    }
    
    public int getIncorrect(){
        return incorrect;
    }
    
    protected void change(){
        this.fireChangeEvent();
    }
    
    public void addPoint(MapPoint gv){
        points.add(gv);
        this.change();
    }   
    
    public void addPoint(String name, Rectangle position){
        Model.this.addPoint(new MapPoint(name, position));
    }
    
    
    public void setScore(int t, int c, int i){
        total = t;
        correct = c;
        incorrect = i;
    }
    
    public void addCorrect(){
        total += 1;
        correct += 1;
    }
    
    public void addIncorrect(){
        total += 1;
        incorrect += 1;
    }
    
    public List<MapPoint> getPoints(){
        return this.points;
    }
    
    public File getBG(){
        return bg;
    }
    
    public void setBG(File f){
        bg = f;
    }
    public void save(File f) throws IOException{
        
        if(!f.exists()) f.createNewFile();
        
        PrintWriter writer = new PrintWriter(f);
        if(bg==null){
            throw new IllegalArgumentException("Cannot save without image");
        }
        writer.println(savedHeight);
        writer.println(bg.getPath());
        writer.println(points.size());
        for(MapPoint gv : points) writer.println(gv.saveString());
        
        writer.flush();
        writer.close();
    }
       
    
    public void renamePoint(MapPoint gv, String s){
        gv.setName(s);
        this.change();
    }
    
    public void removePoint(int gvi){
        if(gvi >= points.size()) throw new IllegalArgumentException("Index out of bounds");
        removePoint(points.get(gvi));
    }
    
    public void removePoint(MapPoint gv){
        if(!points.contains(gv)) throw new IllegalArgumentException("Vertex not member of this graph");
                
        points.remove(gv);
        this.change();
    }

    public void load(File input) throws IOException {
        String buffer;
        
        //empty model
        actions.discardAllEdits();
        points.clear();
        
        BufferedReader in = new BufferedReader(new FileReader(input));
        buffer = in.readLine();
        Scanner s = new Scanner(buffer);
        savedHeight = Integer.parseInt(s.next());
        bg = new File(s.next());
        buffer = in.readLine();
        s = new Scanner(buffer);
        int numVertices = s.nextInt();
        for (int i = 0; i<numVertices; i++){
            buffer = in.readLine();
            s = new Scanner(buffer);
            int x = s.nextInt();
            int y = s.nextInt();
            int width = s.nextInt();
            int height = s.nextInt();
            String name = s.next();
            while (s.hasNext())
            {
                name = name + " " + s.next(); //For name with spaces in it
            }
            addPoint(name, new Rectangle(x,y,width,height));
        }
        
        this.change();
    }

    
    
    //Listening behaviour. Allows the frame to pick up changes in the model
    public void addChangeListener(ChangeListener l){
        this.listeners.add(l);
    }    
    
    public void removeChangeListener(ChangeListener l){
        this.listeners.remove(l);
    }
    
    public void removeAllListeners(){
        this.listeners.clear();
    }
    
    protected void fireChangeEvent(){
        ChangeEvent evt = new ChangeEvent(this);
        for(ChangeListener l : listeners){
            l.changeEventReceived(evt);
        }
    }


    
}
