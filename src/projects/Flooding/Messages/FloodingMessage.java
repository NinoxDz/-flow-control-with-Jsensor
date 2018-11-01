package projects.Flooding.Messages;

import java.util.ArrayList;

import jsensor.nodes.Node;
import jsensor.nodes.messages.Message;

/**
 *
 * @author Matheus
 */
public class FloodingMessage extends Message{
	
	private String msg;
    private Node sender;
    private Node destination;
    private int hops;
    short chunk;
   // int [] shortPath = new int[5000];
    ArrayList<Integer> shortPath = new ArrayList<Integer>();
    
    public FloodingMessage(Node sender, Node destination, int hops, String message, short chunk) {
    
    	//Call to create a new ID
    	super(chunk);
    	this.msg = message;
        this.sender = sender;
        this.destination = destination;
        this.hops = hops;
        this.chunk = chunk;
        
        
    	
    }

    private FloodingMessage(String msg, Node sender, Node destination, int hops, long ID, ArrayList<Integer> ar) {
    	//Call to set the ID
    	this.setID(ID);
    	this.msg = msg;
        this.sender = sender;
        this.destination = destination;
        this.hops = hops;
        this.shortPath=ar;
        
    	
    }
    
    public void setPath(int node){

    	this.shortPath.add(node);

	
    }
    
    public void getPath(){
    	
    	
    	
    	System.out.println("0="+this.shortPath);
    }

    public String getMsg(){
    	return this.msg;
    }
    
    public void setMsg(String msg){
    	this.msg = msg;
    }
    
    public Node getDestination() {
        return destination;
    }

    public void setDestination(Node destination) {
        this.destination = destination;
    }

    public int getHops() {
        return hops;
    }

    public void setHops(int hops) {
        this.hops = hops;
    }
    
    public short getChunk() {
        return chunk;
    }

    public Node getSender() {
        return sender;
    }

    public void setSender(Node sender) {
        this.sender = sender;
    }
       
    @Override
    public Message clone() {
        return new FloodingMessage(msg, sender, destination, hops+1,this.getID(),shortPath);
    }
}