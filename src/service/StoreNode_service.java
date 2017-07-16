package service;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import entity.StoreNode;

public class StoreNode_service {
         private Map<String,StoreNode> map;
         private  DatagramSocket datagramSocket;
         private Map<String, Boolean> check_node;
         private Timer timer;
         public StoreNode_service(Map<String,StoreNode> map)
         {
        	 this.map = map;
        	 check_node = new HashMap<String,Boolean>();
        	 timer = new Timer();
         }
         public void start(){
        	 //启动timer循环检测
           timer.schedule(new TimerTask() {
			
			@Override
			 public void run() {
				// TODO Auto-generated method stub
				   Set< String> set = map.keySet();
				   Iterator< String> it =set.iterator();
			          //  System.out.println(map.size());
				   while(it.hasNext())
				   {
					   String name = it.next();
					   if(check_node.get(name)==null)
					   {
						   map.get(name).setIsAiviable(false);
					   }else{
						   map.get(name).setIsAiviable(true);
					   }
				   }
				   check_node.clear();
			}
		},1000,6000);      // 5秒扫描一次
        	  new Thread(runnable).start();
         }
        Runnable runnable =new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub 
				 try{
					 datagramSocket  = new DatagramSocket(6666);
				  while(true)
				  {
					   DatagramPacket packet  =new DatagramPacket(new byte[1024], 1024);
					   datagramSocket.receive(packet); //会阻塞
					   System.out.println("接收到包");
					   String str =new String(packet.getData(),0,packet.getLength());
					   System.out.println(str);
					   if(str.charAt(0)=='#')  //等于# 代表复活   长时间代表什么？？？  应该如何定位        #+nodename+#
					   {
						     String[] data = str.split("#");
						     System.out.println("复活了"+data[1]);
						     check_node.put(data[1], true);
					   }else{  //注册Node
						     ByteArrayInputStream bs=new ByteArrayInputStream(packet.getData());
				             ObjectInputStream os=new ObjectInputStream(bs);
				             StoreNode m = (StoreNode)os.readObject();
				             if(map.get(m.getName())==null){
						     map.put(m.getName(),m);
						     System.out.println(m.getName()+"注册成功！！");
				             }
					   }
					}
				  }catch (Exception e) {
						// TODO: handle exception
						  e.printStackTrace();
				  }
			}
		};
}
