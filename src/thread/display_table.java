package thread;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import entity.MyFile;
import entity.StoreNode;

public class display_table {
	private Map<String, StoreNode>Node_map;
	private Map<String,MyFile> file_map;
	private String ip1;
	private String port1;
	private String ip2;
	private String port2;
	private DatagramSocket socket;
	public display_table (String ip1,String port1,String ip2,String port2,Map<String,StoreNode>Node_map,Map<String, MyFile>file_map)
	{
		this.file_map=file_map;
	    this.Node_map=Node_map;
	    this.ip1 =ip1;
	    this.ip2=ip2;
	    this.port1=port1;
	    this.port2=port2;
	    try {
		socket = new DatagramSocket(9900);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void start()  //分两个端口传输信息
	{
		Timer timer= new Timer();
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try{
			byte[] by=new byte[516];
				 ByteArrayOutputStream bs=new ByteArrayOutputStream();
		            ObjectOutputStream bo=new ObjectOutputStream(bs);
		            bo.writeObject(Node_map);
		              by=bs.toByteArray(); 
		              byte[] by1=new byte[516];
						 ByteArrayOutputStream bs1=new ByteArrayOutputStream();
				            ObjectOutputStream bo1=new ObjectOutputStream(bs1);
				            bo1.writeObject(file_map);
				              by1=bs1.toByteArray(); 
			   DatagramPacket  packet = new DatagramPacket(by,by.length,InetAddress.getByName(ip1),Integer.parseInt(port1));
				  DatagramPacket  packet1 = new DatagramPacket(by1,by1.length,InetAddress.getByName(ip2),Integer.parseInt(port2));
				  socket.send(packet1);
				  socket.send(packet);
				}catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		}, 1000,5000);
	}
}
