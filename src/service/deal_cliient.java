package service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import entity.MyFile;
import entity.StoreNode;

public class deal_cliient {
	  private  Map<String, MyFile>file_map;  //ͨ��һ��Map���ϴ洢�ļ���Ϣ
		 private   Map<String,StoreNode>Node_map;
		   private Socket socket;
		   public deal_cliient(Map<String, MyFile>file_map,Map<String, StoreNode>Node_map,Socket socket) {
			 this.file_map=file_map;
	    	 this.Node_map=Node_map;
	    	 this.socket=socket;
		 }
		 public void Remove(String filename)
		 {
			 file_map.remove(filename);
		 }
		 public void Download(String uuid)
		 {
			 MyFile file = file_map.get(uuid);
			 file.setName(null);
			 ObjectOutputStream outputStream;
			try {
				outputStream = new ObjectOutputStream(socket.getOutputStream());
 	    	       outputStream.writeObject(file);             //�ļ�ȫ����Ϣ,������Ӧ��ȥ��name,�����ȻҲ���ԣ�
			  } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
	 public void Upload(String filename,int size)   //�ϴ��ļ�, UUID�����ڵ㣬���洢���͸��洢���
	  {
		        System.out.println(filename+size);
		       String pathname = UUID.randomUUID().toString();
		       MyFile file1 = new MyFile();
		       file1.setID(pathname);
		       if(Node_map.size()<2){
		    	   try{
		    	   socket.close();
		    	   System.out.println("���߷�����������������");
		    	   }catch (Exception e) {
					// TODO: handle exception
		    		   e.printStackTrace();
				}
		       }
		       else{   
		    	   //Ѱ�����Ŀ����õģ�����ǰ��������
		    	   Set<String> set = Node_map.keySet();
		    	  Iterator< String> it = set.iterator();
		    	  StoreNode temp = new StoreNode();
		    	  StoreNode temp1= new StoreNode();
		    	  StoreNode temp2 = new StoreNode();
		    	  System.out.println(Node_map.size());
		    	    while(it.hasNext())
		    	    {
		    	    	String key = it.next();
		    	    	if(Node_map.get(key).getRem_volum() >temp.getRem_volum()&&Node_map.get(key).getIsaviable())
		    	    	{
		    	    		 temp=Node_map.get(key);
		    	    	}
		    	    }
		    	    Iterator<String> it1 = set.iterator();
		    	    while(it1.hasNext()){
		    	    	String key = it1.next();
		    	    	if(Node_map.get(key).getRem_volum() >temp1.getRem_volum()&&Node_map.get(key).getIsaviable()
		    	    			&&Node_map.get(key).getName()!=temp.getName())
		    	    	{
		    	    		 temp1=Node_map.get(key);
		    	    	}
		    	    }
		    	    Iterator<String> it2 = set.iterator();
		    	    while(it2.hasNext()){
		    	    	String key = it2.next();
		    	    	if(Node_map.get(key).getRem_volum() >temp2.getRem_volum()&&Node_map.get(key).getIsaviable()
		    	    			&&Node_map.get(key).getName()!=temp.getName()&&
		    	    				Node_map.get(key).getName()!=temp1.getName()
		    	    			)
		    	    	    {
		    	    		 temp2=Node_map.get(key);
		    	    	     }
		    	    }
		    	 //��������
		    	    try{
		    	    OutputStream outputStream =socket.getOutputStream();
		    	    ObjectOutputStream out = new ObjectOutputStream(outputStream);
		    	     file1.setM_node(temp);
	    	         file1.setB_node1(temp1);
	    	         file1.setB_node2(temp2);
	    	           out.writeObject(file1);
		    	    	  file1.setSize(size);
		  		         file1.setName(filename);
		    	    	InputStream  inputStream =socket.getInputStream();
		    	    	 int count = 0;  
				            while (count == 0) {  
				                count = inputStream.available();  
				          }  
		    	    	byte[] data = new byte[count];
		    	    	inputStream.read(data);     // ���ͳɹ�֮�� ������һ����Ϣ   s#123 ��ѡ�������з�����  ���� f#123;
		    	    	String value =new String(data,0,data.length);
				         String[] result =value.split("#");
				         System.out.println(result);
				         if(result[0]=="s")
				         {
				        	 file_map.put(pathname,file1);
			        	    //�ļ����£��������л�����д���ı���
			        	  System.out.println(pathname+"�ļ���ӳɹ�");  //�޸Ĵ洢�����Ϣ
			        	   if(result[1]=="1")
			        	   {
			        		   Node_map.get(temp.getName()).setRem_volum(temp.getRem_volum()-file1.getSize());
			        	   }
			        	   else if(result[1]=="2")
			        	   {
			        		   Node_map.get(temp1.getName()).setRem_volum(temp1.getRem_volum()-file1.getSize());
			        		    file_map.get(pathname).setM_node(temp1);
			        	   }
			        	   else if(result[1]=="3")
			        	   {
			        		   Node_map.get(temp2.getName()).setRem_volum(temp2.getRem_volum()-file1.getSize());
			        		    file_map.get(pathname).setM_node(temp2);
			        		    file_map.get(pathname).setB_node1(temp2);
			        	   } 
			        	     ObjectOutputStream obj = new ObjectOutputStream(new FileOutputStream("File_information.txt"));
				        	 obj.writeObject(file_map);
				        	 obj.close();
			         }else{
				           System.out.println("�ļ��ϴ�ʧ��"); 
				           
			              }
	    	    }catch (Exception e) {
					// TODO: handle exception
	    	    	e.printStackTrace();
				}
	       }
				        	
				      
				        	   
	  }
}
