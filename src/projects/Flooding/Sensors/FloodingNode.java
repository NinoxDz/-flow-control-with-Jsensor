package projects.Flooding.Sensors;

import java.util.LinkedList;

import jsensor.runtime.Jsensor;
import jsensor.nodes.Node;
import jsensor.nodes.messages.Inbox;
import jsensor.nodes.messages.Message;
import projects.Flooding.Messages.Ack;
import projects.Flooding.Messages.CTS;
import projects.Flooding.Messages.FloodingMessage;
import projects.Flooding.Messages.Pathmsg;
import projects.Flooding.Messages.RTS;
import projects.Flooding.Timers.FloodingTimer;


/**
 *
 * @author danniel & Matheus
 */
public class FloodingNode extends Node{
    public LinkedList<Long> messagesIDs; 

    @Override
    public void handleMessages(Inbox inbox) { 
    	
       while(inbox.hasMoreMessages())
       {
    	   
           Message message = inbox.getNextMessage();
           
        /////////////////////////////////////////////////////
           
           if(message instanceof FloodingMessage)
           {
        	   
        	   FloodingMessage floodingMessage = (FloodingMessage) message;
        	   
               if(this.messagesIDs.contains(floodingMessage.getID()))
               {
                   continue;
               }
               
               this.messagesIDs.add(floodingMessage.getID());
               
               if(floodingMessage.getDestination().equals(this))
               {
            	  
            	   String x =floodingMessage.getMsg().concat(this.ID+"");
            	   
            	   x=x.replace("Created by the sensor:", "");
            	   x=x.replace("Path:", "-");
            	   System.out.println(x);
            	   String[] array = x.split("-");
            	   Integer[] intarray=new Integer[array.length];
            	    int i=0;
            	    for(String str:array){
            	    	//System.out.println("str="+str);
            	    	intarray[i]=Integer.parseInt(str.trim());
            	    	//System.out.println("str="+intarray[i]);
            	        i++;
            	    } 
 
            	   
            	   Jsensor.log("time: "+ Jsensor.currentTime +
            			   "\t sensorID: " +this.ID+
            			   "\t receivedFrom: " +floodingMessage.getSender().getID()+
            			   "\t hops: "+ floodingMessage.getHops() +
            			   "\t msg: " +floodingMessage.getMsg().concat(this.ID+""));
            	   
            	   Pathmsg pt = new Pathmsg(intarray,intarray.length-1);
            	   int lengtht= intarray.length;
            	   
            	   Node dist = Jsensor.getNodeByID(intarray[lengtht-2]);
            	   
            		   //System.out.println("dist="+dist);
            		   System.out.println("sent"+dist);
            		   
            		  // this.multicast(pt);
            		   
            	   	this.unicast(pt,dist);
            	   
            		   
            	  
               }
               else
               {
				    int n = 999999;
				    int cont=0;
				    for (int i=1;i<=n;i++ ){
				    	if(n%i == 0)
				    		cont=cont+1;
				    }
				    
				    if (cont > 0){
				    	// floodingMessage.setPath(this.ID);
	            	   floodingMessage.setMsg(floodingMessage.getMsg().concat(this.ID+ " - "));
	                   this.multicast(message);
				    }
               }
           }
           
           
           ////////////////////////////recive RTS Msg////////////////////////////////
           if(message instanceof RTS)
           {
        	   RTS rtsmsg = (RTS) message;
        	   int destid = rtsmsg.getDestination().getID();
           
             
               
            
               
               if(destid==this.ID)
               {
            	   
            	   ///////////******************* wait SIFS and send data
            	   CTS ctsmsg = new CTS(this, rtsmsg.getSender(), 0, ""+this.ID);
            	   //this.multicast(ctsmsg);
            	   ////////**********************
               }
               else
               {////////////// wait the transimition time
				    int n = 999999;
				    int cont=0;
				    for (int i=1;i<=n;i++ ){
				    	if(n%i == 0)
				    		cont=cont+1;
				    }
				    
				    if (cont > 0){
				    //	rtsmsg.setMsg(rtsmsg.getMsg().concat(this.ID+ " - "));
	                   //this.multicast(message);
				    }
               }
           } 
           /////////////////////////////////////////////////
           if(message instanceof CTS)
           {
        	   CTS ctsmsg = (CTS) message;
        	   
        	   int destid = ctsmsg.getDestination().getID();
               
               
               if(destid==this.ID)
               {
            	   ///****************wait SIFS and send data
            	   
            	   
               }
               else
               {
            	   
            	 ///****************calculate wait time
				    int n = 999999;
				    int cont=0;
				    for (int i=1;i<=n;i++ ){
				    	if(n%i == 0)
				    		cont=cont+1;
				    }
				    
				    if (cont > 0){
				    	ctsmsg.setMsg(ctsmsg.getMsg().concat(this.ID+ " - "));
	                   this.multicast(message);
				    }
               }
           } 
           ///////////////////////////////////////
           if(message instanceof Ack)
           {
        	   Ack Ackmsg = (Ack) message;
        	   
               if(this.messagesIDs.contains(Ackmsg.getID()))
               {
                   continue;
               }
               
               this.messagesIDs.add(Ackmsg.getID());
               
               if(Ackmsg.getDestination().equals(this))
               {
            	   ////////////********* transmition done
            	   continue;
               }
               else
               {
            	   //////////*********** be able to send RTS
				    int n = 999999;
				    int cont=0;
				    for (int i=1;i<=n;i++ ){
				    	if(n%i == 0)
				    		cont=cont+1;
				    }
				    
				    if (cont > 0){
				    	Ackmsg.setMsg(Ackmsg.getMsg().concat(this.ID+ " - "));
	                   this.multicast(message);
				    }
               }
           } 
           //////////////////Pathmsg/////////////////////
           if(message instanceof Pathmsg)
           {
        	
        	   Pathmsg pathmsg = (Pathmsg) message;
        	   System.out.println("path"+pathmsg.getHops());
        	   Integer[] path = pathmsg.getpath();
        	   
               if(this.messagesIDs.contains(pathmsg.getID()))
               {
                   continue;
               }
               
               this.messagesIDs.add(pathmsg.getID());
               
               
               if(path[pathmsg.getHops()]==(int)this.ID)
               {
            	   if(pathmsg.getHops()-1==0){
            		   System.out.println("rtssssssssssssssssss");
            	   RTS rtsmsg = null;
            	   
            	    rtsmsg = new RTS(Jsensor.getNodeByID(this.ID), Jsensor.getNodeByID(path[pathmsg.getHops()-1]), 0, ""+path[0]);
            	    this.multicast(rtsmsg);
            	   }
            	   else{
            		   
            		   Pathmsg pt = new Pathmsg(path,pathmsg.getHops());
                	   
                	   
                	   Node dist = Jsensor.getNodeByID(path[pathmsg.getHops()-1]);
                	   
                		   //System.out.println("dist="+dist);
                		   System.out.println("sent1"+dist);
                		   
                		  // this.multicast(pt);
                		   
                	   	this.unicast(pt,dist);
            		   
            		   
            	   }
               }
               
           } 
           ////////////////////////////////
           
       }
    }

    @Override
    public void onCreation() 
    {
    	//initializes the list of messages received by the node.
        this.messagesIDs = new LinkedList<Long>();
 
        //sends the first messages if is one of the selected nodes
        if(this.ID < 2)
        {
        	int time = 10 + this.ID * 10;
        	FloodingTimer ft = new FloodingTimer();
            ft.startRelative(time, this);
        }
    }
}