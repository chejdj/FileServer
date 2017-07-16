package service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import entity.MyFile;
import entity.StoreNode;
import thread.display_table;

public class main_service {
      private  static Map<String,StoreNode>Node_map;
      private static  Map<String, MyFile>file_map;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//加载Watcher的ip地址，和端口号
		        Properties p = new Properties();
		          try {
					p.load(new FileInputStream("watch.properties"));
				      } catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				    } catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                Node_map = new HashMap<String,StoreNode>();
                //读取序列化对象
                readFile();
                System.out.println(file_map.size());
               new  StoreNode_service(Node_map).start();
               new Fileclient_service(Node_map,file_map).start();
               new display_table(p.getProperty("Watcherip"), p.getProperty("Node_port"), p.getProperty("Watcherip"), p.getProperty("File_port"), Node_map, file_map).start(); //具体参数待定
	}
      public static void readFile()
      {
    	    try {
    	    	  if(new File("File_information.txt").length()>0){
				     ObjectInputStream obj = new ObjectInputStream(new FileInputStream("File_information.txt"));
					file_map = (Map<String, MyFile>)obj.readObject();
					  obj.close();
    	    	  }else{
    	    		  file_map=new HashMap<String,MyFile>();
    	    	  }
			 } 
    	    catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
      }
}
