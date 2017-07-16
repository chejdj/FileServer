package service;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

import entity.MyFile;
import entity.StoreNode;
import thread.File_thread;

public class Fileclient_service {
	private Map<String, MyFile>file_map;  //ͨ��һ��Map���ϴ洢�ļ���Ϣ
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
			    serverSocket = new ServerSocket(7777);             //ÿ��һ�ζϿ�����
			   while(true)
			   {
				    Socket socket = serverSocket.accept();   //ÿ����һ������һ���̣߳���
				      System.out.println("�ͻ������ӳɹ�����");
				     new File_thread(file_map,Node_map,socket).start();              //�����߳�
			   }
			    
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		        	
		   }
	};
}
