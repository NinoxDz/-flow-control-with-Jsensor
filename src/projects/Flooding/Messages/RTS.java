package projects.Flooding.Messages;

import jsensor.nodes.Node;
import jsensor.nodes.messages.Message;

public class RTS extends Message{
	
	private String msg;
    private Node sender;
    private Node destination;
    private int hops;
    
    
    public RTS(Node sender, Node destination, int hops, String message) {
    	//Call to create a new ID
    	
    	this.msg = message;
        this.sender = sender;
        this.destination = destination;
        this.hops = hops;
        
    }

    private RTS(String msg, Node sender, Node destination, int hops, long ID) {
    	//Call to set the ID
    	this.setID(ID);
    	this.msg = msg;
        this.sender = sender;
        this.destination = destination;
        this.hops = hops;
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
    
    

    public Node getSender() {
        return sender;
    }

    public void setSender(Node sender) {
        this.sender = sender;
    }

    @Override
    public Message clone() {
        return new RTS(msg, sender, destination, hops+1,this.getID());
    }
}