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
        	 //����timerѭ�����
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
		},1000,6000);      // 5��ɨ��һ��
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
					   datagramSocket.receive(packet); //������
					   System.out.println("���յ���");
					   String str =new String(packet.getData(),0,packet.getLength());
					   System.out.println(str);
					   if(str.charAt(0)=='#')  //����# ������   ��ʱ�����ʲô������  Ӧ����ζ�λ        #+nodename+#
					   {
						     String[] data = str.split("#");
						     System.out.println("������"+data[1]);
						     check_node.put(data[1], true);
					   }else{  //ע��Node
						     ByteArrayInputStream bs=new ByteArrayInputStream(packet.getData());
				             ObjectInputStream os=new ObjectInputStream(bs);
				             StoreNode m = (StoreNode)os.readObject();
				             if(map.get(m.getName())==null){
						     map.put(m.getName(),m);
						     System.out.println(m.getName()+"ע��ɹ�����");
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
