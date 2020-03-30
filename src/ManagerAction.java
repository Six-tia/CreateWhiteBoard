import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class ManagerAction {

	public static void addchat(String uname, String message) {

		String msg = uname + ": " + message + "\r\n";
		LoginManager.whiteBoard.ChatWindow.append(msg);
		LoginManager.whiteBoard.ChatWindow.setCaretPosition(LoginManager.whiteBoard.ChatWindow.getDocument().getLength());

	}

	public static synchronized int checkin(String name) {
		// LoginManager.whiteBoard.ChatWindow.setText("aaa");
		int ans = LoginManager.whiteBoard.showRequest(name);
		return ans;
	}

	public static void addUser(String[] clients) {

		LoginManager.whiteBoard.list.setListData(clients);

	}

	public static void clientOut(String clients) {

		String[] k = clients.split(" ", 2);
		JOptionPane.showMessageDialog(LoginManager.whiteBoard.frame, "user " + k[0] + " leaves!");
		String[] client = k[1].split(" ");
		LoginManager.whiteBoard.list.setListData(client);

	}

	public static void canvasRepaint(String drawRecord) {
		WhiteBoard.drawListener.update(drawRecord);
		WhiteBoard.canvas.repaint();
	}

	public static void broadcast(String message) throws IOException {
		for (int i = 0; i < LaunchManager.cList1.size(); i++) {
			ChatThread st = LaunchManager.cList1.get(i);

			// System.out.println(st.dis.readUTF());
			st.dos.writeUTF(message);
			st.dos.flush();
			// sendMsg(st.socket.getOutputStream(), "msg");
		}
	}

	public static void broadcast(String open, ArrayList<String> recordList) throws IOException {
		String[] recordArray = (String[]) recordList.toArray(new String[recordList.size()]);
		for (String message : recordArray) {
//			System.out.println("open " + message);
			for (int i = 0; i < LaunchManager.cList1.size(); i++) {
				ChatThread st = LaunchManager.cList1.get(i);
//				System.out.println(i+message);
				// System.out.println(st.dis.readUTF());
				st.dos.writeUTF("draw " + message);
				st.dos.flush();
				// sendMsg(st.socket.getOutputStream(), "msg");
			}
		}

	}

}
