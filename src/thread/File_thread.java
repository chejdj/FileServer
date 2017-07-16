package thread;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.omg.CORBA.DATA_CONVERSION;

import entity.MyFile;
import entity.StoreNode;
import service.deal_cliient;

public class File_thread {
    private Socket socket;
    private Map<String, MyFile>file_map;  //通过一个Map集合存储文件信息
	 private  static Map<String,StoreNode>Node_map;
      public File_thread(Map<String, MyFile>file_map,Map<String, StoreNode>Node_map,Socket socket){
    	 this.socket=socket;
    	 this.file_map=file_map;
    	 this.Node_map=Node_map;
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
			            InputStream  inputStream = socket.getInputStream();
			            int count = 0;  
			            while (count == 0) {  
			                count = inputStream.available();  
			            }  
			            byte[] data = new byte[count];
			            inputStream.read(data);
			            //获取命令  以 #隔开 ,只有文件的上传和删除需要告知服务器！！
			            String da = new String(data, 0, data.length);
			            System.out.println(da);
			            String[] command =da.split("#");
			            System.out.println(command[0]);
			            switch(command[0]){
			            case "Upload" :     //文件名字，文件大小
			            	new deal_cliient(file_map, Node_map, socket).Upload(command[1], Integer.parseInt( command[2]));
			            	break;
			            case "Download":  //文件名字 
			            	new deal_cliient(file_map, Node_map, socket).Download(command[2]);
			            break;
			            case "Remove":  //文件的UUID
			            	new deal_cliient(file_map, Node_map, socket).Remove(command[1]);
			            	break;
			            default:
			            	System.out.println(command);
			            }
			}catch (IOException e) {
				// TODO: handle exception
				e.printStackTrace();
			}          
		}
	};
	 
}
 