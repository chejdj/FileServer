package service;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

import entity.MyFile;
import entity.StoreNode;
import thread.File_thread;

public class Fileclient_service {
	private Map<String, MyFile>file_map;  //通过一个Map集合存储文件信息
	 private   Map<String,StoreNode>Node_map;
	private ServerSocket serverSocket;
         public Fileclient_service(Map<String,StoreNode>node_map,Map<String, MyFile>map)
         {
        	 this.file_map=map;
        	 this.Node_map = node_map;
         }
	   public void start()
	   {
		     new Thread(runnable).start();
	   }
	  Runnable runnable = new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try{
			    serverSocket = new ServerSocket(7777);             //每传一次断开连接
			   while(true)
			   {
				    Socket socket = serverSocket.accept();   //每接受一个开启一个线程！！
				      System.out.println("客户端连接成功！！");
				     new File_thread(file_map,Node_map,socket).start();              //开启线程
			   }
			    
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		        	
		   }
	};
}
