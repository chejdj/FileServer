package entity;

import java.io.Serializable;

public class MyFile  implements Serializable {
        private String ID;
        private String name;
        private int size;
        private StoreNode M_node;
        private StoreNode B_node1;
        private StoreNode B_node2;
        public MyFile(String ID,String name,int size,StoreNode M_node,StoreNode B_node1,StoreNode B_node2){
        	this.ID=ID;
        	this.name=name;
        	this.size=size;
        	this.M_node=M_node;
        	this.B_node1=B_node1;
        	this.B_node2 = B_node2;
        }
        public MyFile(){}
        public void setID(String ID)
        {
        	this.ID=ID;
        }
        public void setName(String name)
        {
        	this.name=name;
        }
        public void setSize(int size)
        {
        	this.size=size;
        }
        public void setM_node(StoreNode M_node)
        {
        	this.M_node=M_node;
        }
        public void setB_node1(StoreNode B_node1)
        {
        	this.B_node1=B_node1;
        }
        public void setB_node2(StoreNode B_node2)
        {
        	this.B_node2=B_node2;
        }
        public String getID()
        {
        	return ID;
        }
        public String getName()
        {
        	return name;
        }
        public int getSize()
        {
        	return size;
        }
        public StoreNode getM_node()
        {
        	return M_node;
        }
        public StoreNode getB_node1()
        {
        	return B_node1;
        }
        public StoreNode getB_node2()
        {
        	return B_node2;
        }
        
}  
