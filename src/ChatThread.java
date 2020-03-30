import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

//import Myserver;
//import server.ServerThread;

public class ChatThread extends Thread {

	public Socket socket;
	// 锟竭筹拷锟斤拷锟斤拷
	public String name;
	// 锟斤拷锟斤拷锟斤拷锟斤拷锟�
	public DataInputStream dis;
	public DataOutputStream dos;
	public boolean beKicked = false;

	public ChatThread(Socket a) {
		socket = a;
		// name = username;
		System.out.println("chat success!");
	}

	public void run() {

		try {

			InputStream ins = socket.getInputStream();
			OutputStream ous = socket.getOutputStream();
			dis = new DataInputStream(ins);
			dos = new DataOutputStream(ous);
//			LaunchManager.cList3.add(name);

			String str1;
			while ((str1 = dis.readUTF()) != null) {
//				System.out.println(this.name);
//				System.out.println(str1);
//				System.out.println("aaaa");
//				System.out.println(str1);
				String[] out = str1.split(" ", 2);
//				System.out.println(out[1]);
				if (out[0].equals("chat")) {
//					System.out.println(LaunchManager.cList1.size());
					ManagerAction.broadcast("chat " + name + ": " + out[1]);
					ManagerAction.addchat(name, out[1]);
				} else if (out[0].equals("begin")) {

					ArrayList<String> rl = LoginManager.whiteBoard.drawListener.getRecord();
					try {
						ManagerAction.broadcast("Open Board", rl);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					String str = "userlist";
					for (String userName : LaunchManager.cList3) {
						str += " " + userName;
					}
					String[] k = str.split(" ", 2);
					String[] clients = k[1].split(" ");
					ManagerAction.addUser(clients);

					ManagerAction.broadcast(str);
				} else if (out[0].equals("request")) {
//					System.out.println("in request");
//					System.out.println(out[1]);
//					System.out.println(str1);
					String curName = out[1];
					name = curName;
//					System.out.println(LaunchManager.cList3.size());
					if (LaunchManager.cList3.contains(curName)) {
						//LaunchManager.cList1.remove(this);
						dos.writeUTF("feedback no");
						dos.flush();
					} else {
						int ans = ManagerAction.checkin(out[1]);

						if (ans == JOptionPane.YES_OPTION) {
							if (LaunchManager.cList3.contains(curName)) {
								try{
									dos.writeUTF("feedback no");
									dos.flush();
									LaunchManager.cList1.remove(this);
									socket.close();
									break;
								}catch(Exception e1){
									LaunchManager.cList1.remove(this);
								}
								
							} else {
								LaunchManager.cList3.add(curName);
								dos.writeUTF("feedback yes");
								dos.flush();
							}
						} else if (ans == JOptionPane.CANCEL_OPTION || ans == JOptionPane.CLOSED_OPTION
								|| ans == JOptionPane.NO_OPTION) {
							
							dos.writeUTF("feedback rejected");
							dos.flush();
							
//							System.out.println("reject");
							LaunchManager.cList1.remove(this);
//							socket.close();
						}
					}
				} else if (out[0].equals("draw")) {
					ManagerAction.broadcast(str1);
					ManagerAction.canvasRepaint(out[1]);
				} else if (out[0].equals("OJBK"))
				{
					socket.close();
					break;
				}
				

			}

		} catch (SocketException e) {
			System.out.println("User " + this.name + " Connection interruption.");
			if (!this.beKicked) {
				clientout();
			}
//			e.printStackTrace();
		} catch (Exception w) {
			System.out.println("User " + this.name + " Connection interruption.");
//			w.printStackTrace();
		}

	}

	public void clientout() {

		LaunchManager.cList1.remove(this);
		LaunchManager.cList3.remove(name);
		String str = "clientout " + name;
		for (String userName : LaunchManager.cList3) {
			str += " " + userName;
		}
		for (int i = 0; i < LaunchManager.cList1.size(); i++) {

//			System.out.println(str);
			ChatThread st = LaunchManager.cList1.get(i);
			// System.out.println(st.dis.readUTF());
			try {
				st.dos.writeUTF(str);
				dos.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		String k = str.split(" ", 2)[1];
//		System.out.println(k);
		ManagerAction.clientOut(k);
	}

}