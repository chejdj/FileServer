package entity;

import java.io.Serializable;

public class StoreNode  implements Serializable{
		private String ip;
	    private int  port;
	    private int volum;
	    private int r_volum;
	    private int rem_volum;
	    private int File_num;
	    private String name;
	    private boolean isAiviable;
      public StoreNode(String ip,int port,int volum,int r_volum,int rem_volum,int File_num,String name,boolean isAiviable)
      {
    	  this.ip=ip;
    	  this.port=port;
    	  this.volum=volum;
    	  this.r_volum=r_volum; //Êµ¼Ê´æ´¢Á¿
    	  this.rem_volum=rem_volum;
    	  this.File_num=File_num;
    	  this.name=name;
    	  this.isAiviable=isAiviable;
        }
        public StoreNode(){}
        public String getName(){
        	return name;
        }
        public void setRem_volum(int rem_volum)
        {
        	this.rem_volum = rem_volum;
        }
        public void  setIsAiviable(boolean state)
        {
        	this.isAiviable=state;
        }
        public String getIp(){
        	return ip;
        }
        public int getPort(){
        	return port;
        }
        public int getVolum(){
        	return volum;
        }
        public int getR_volum(){
        	return r_volum;
        }
        public int getRem_volum(){
        	return rem_volum;
        }
        public int getFile_num(){
        	return File_num;
        }
        public boolean getIsaviable(){
        	return isAiviable;
        }
        
}
