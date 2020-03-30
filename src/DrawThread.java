import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class DrawThread extends Thread{


	public Socket socket;
	//线程名字
	public String name;
	//输入输出流
	public DataInputStream dis;
	public DataOutputStream dos;
	
	public DrawThread(Socket a, String username) {
		socket = a;
		System.out.println("draw success!");
	}
	
	public void run() {
//		
//		try {
//			//获取输入输出流
//			InputStream ins=socket.getInputStream();
//			OutputStream ous =socket.getOutputStream();
//			//开启线程以后，从数据库中取一条消息
//			dis =new DataInputStream(ins);
//			dos = new DataOutputStream(ous);
//		//如果是画的客户端
//		if("draw".equals(name)){
//				//发送画的消息给客户端
//				sendMsg(socket.getOutputStream(), "draw");
////				System.out.println("已发送draw");
//				//发送要画的信息给画的客户端
//				//dos.writeUTF( Myserver.infos[0]);
//				//循环收客户端画的消息
//				while(true){
//						int x1=dis.readInt();
//						int y1=dis.readInt();
//						int x2=dis.readInt();
//						int y2=dis.readInt();
//						int color =dis.readInt();
//						int width=dis.readInt();
//						//将该信息转发给猜的客户端
//						//for (int i = 0; i <Myserver.list.size(); i++) {
//							//ServerThread st =Myserver.list.get(i);
//							//if(st!=this){
//								//发送头部数据信息
////								sendMsg(st.socket.getOutputStream(), "data");
////								//发送坐标信息
////								sendMsg1(st.socket.getOutputStream(), x1, y1, x2, y2,color,width);
////							}
//						}
//				}
			}
//		

	// 发送消息的函数
	public void sendMsg(OutputStream os, String s) throws IOException {
 
		// 向客户端输出信息
		// System.out.println(s);
		byte[] bytes = s.getBytes();
		os.write(bytes);
		os.write(13);
		os.write(10);
		os.flush();
 
	}
 
	// 读取客户端输入数据的函数
	public String readMsg(InputStream ins) throws Exception {
		// 读取客户端的信息
		int value = ins.read();
		// 读取整行 读取到回车（13）换行（10）时停止读
		String str = "";
		while (value != 10) {
			// 点击关闭客户端时会返回-1值
			if (value == -1) {
				throw new Exception();
			}
			str = str + ((char) value);
			value = ins.read();
		}
		str = str.trim();
		return str;
	}
	
	// 发送坐标的函数
		public void sendMsg1(OutputStream os, int x1, int y1, int x2, int y2,int color ,int width) throws IOException {

			DataOutputStream dos = new DataOutputStream(os);
			dos.writeInt(x1);
			dos.writeInt(y1);
			dos.writeInt(x2);
			dos.writeInt(y2);
			dos.writeInt(color);
			dos.writeInt(width);
			dos.flush();
		}
	
}
