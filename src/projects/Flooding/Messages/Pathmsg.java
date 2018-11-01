
package projects.Flooding.Messages;

import java.util.ArrayList;
import jsensor.nodes.Node;
import jsensor.nodes.messages.Message;

   
     
    public class Pathmsg extends Message{
    	
    	private String msg;
        private Node sender;
        private Node destination;
        private int hops;
        short chunk;
        ArrayList<Integer> shortPath = new ArrayList<Integer>();

        private Integer[] path;
        
        public Pathmsg(Integer[] st,int hops) {
        

            this.path=st;
            this.hops = hops;
        	
        }
        
        public Pathmsg(Node sender, Node destination, int hops, String message, short chunk) {
        
        	//Call to create a new ID
        	super(chunk);
        	this.msg = message;
            this.sender = sender;
            this.destination = destination;
            this.hops = hops;
            this.chunk = chunk;
            
            
        	
        }

        private Pathmsg(String msg, Node sender, Node destination, int hops, long ID, ArrayList<Integer> ar) {
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
        
        

        public String getMsg(){
        	return this.msg;
        }
        public Integer[]  getpath(){
        	return this.path;
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
            return new Pathmsg(path,hops-1);
        }
    }
