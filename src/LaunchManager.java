import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class LaunchManager {
	
	    // Declare the port number
		//private static int cport;
//		public static LaunchManager master = new LaunchManager();
		// Identifies the user number connected
		
		public static List<ChatThread> cList1 = new ArrayList<ChatThread>();
		public static List<DrawThread> cList2 = new ArrayList<DrawThread>();
		public static List<String> cList3 = new ArrayList<String>();
		private static int clientNum = 0;
		public static String dicPath= null;
		
		private LaunchManager() {
//			cList1 = new ArrayList<>();
//			cList2 = new ArrayList<>();
//			cList3 = new ArrayList<>();
		}
		
		
		
//		public static LaunchManager getMaster(){
//			return master;
//		}

		protected static void begin(String path, int port, String username)
		{
			System.out.println("-----------");
			//cport = port;
			//dicPath = path;
			//System.out.println("Dictionary system is open!");
			//ServerSocketFactory factory = ServerSocketFactory.getDefault();
			//ThreadPoolExecutor pool = new ThreadPoolExecutor(0, 100, 60L, TimeUnit.MILLISECONDS, new SynchronousQueue<Runnable>());
			ChatThread t1 = null;
			DrawThread t2 = null;
			ServerSocket server = null;
			cList3.add(username);
			try
			{
				server = new ServerSocket(port);
				System.out.println("Waiting for client connection-----");
				//Socket Manager = new Socket(path, port);
				Socket client;
				//clientNum++;
				
//				t1 = new chatThread(client, username);
//				t1.start();
//				t2 = new drawThread(client, username);
//				t2.start();
				
//				getMaster().addChat(t1);
//				getMaster().addDraw(t2);
//				
				//System.out.println("Client " + clientNum + ": Applying for connection!");
				LaunchManager tt = new LaunchManager();
				
				// Wait for connections.
				while(true)
				{
					client = server.accept();
					clientNum++;
					System.out.println("Client " + clientNum + ": Applying for connection!");
					//System.out.println(pool.getActiveCount());	
					//System.out.println(pool.getPoolSize());
					// Start a new thread for a connection
					
					t1 = new ChatThread(client);
					
					//t2 = new drawThread(client, username);
					
					addChat(t1);
					//getMaster().addDraw(t2);
					t1.start();
					//t2.start();
					
					//t = new multiT(client);
					//pool.execute(t);
					//getMaster().conT(t);
					//System.out.println(cList);
					//System.out.println(tt.showAddr());
					//System.out.println(getMaster().getConT());
					//t.start();
				}
				
			} catch (SocketException e)
			{
				System.out.println("This port has been used.");
				System.exit(2);
//				if (t != null) {
//					
//					t.closeSocket();
//				}
				
//				e.printStackTrace();
			}
			catch (IOException e)
			{
				System.out.println("file operation error!");
			} 
			finally {
				if(server != null) {
					try {
						server.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			
			
		}
		
		private static synchronized void addChat(ChatThread client1) {
			cList1.add(client1);
		}
		
		private static synchronized void addDraw(DrawThread client2) {
			cList2.add(client2);
		}
		
		private static synchronized void deleteName(ChatThread client) {
			cList3.remove(client.name);
		}
	
}
