import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class LoginManager {

	private JFrame frame;
	private JTextField textField;
	private static String ipAddr;
	private static int port;
	static String username;
	public static WhiteBoard whiteBoard;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		if (args.length >= 3) {
			try {
				ipAddr = args[0];
				port = Integer.parseInt(args[1]);
				username = args[2];
			} catch (Exception e) {
				System.out.println("Please enter the correct IP address and port.");
				System.exit(1);
			}
		} else {
			System.out.println("Use default setting: localhost 10000 User1");
			ipAddr = "localhost";
			port = 10000;
			username = "User1";
//			System.exit(1);
		}
		System.out.println("IP: " + ipAddr + "; port: "+ port + ".");

//		System.out.println(ipAddr);
//		System.out.println(port);
//		System.out.println(username);

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginManager window = new LoginManager();
					// window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		LaunchManager.begin(ipAddr, port, username);
	}

	/**
	 * Create the application.
	 */
	public LoginManager() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		DrawListener drawListener = new DrawListener();

		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JButton login = new JButton("OK");
		login.setBounds(158, 126, 111, 34);

		JLabel Username = new JLabel("Join in as");
		Username.setBounds(185, 33, 75, 24);
		frame.getContentPane().add(Username);

		textField = new JTextField();
		textField.setBounds(79, 69, 274, 45);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		textField.setText(username);
//		System.out.println(textField.getText());

		login.addActionListener(drawListener);
		login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("OK")) {
					System.out.println(" Login pressed");
					username = textField.getText();
					username = username.replace(" ", "");
					frame.dispose();
					try {
						whiteBoard = new WhiteBoard(username);
						whiteBoard.setFrame(whiteBoard);
						
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					// LaunchManager.begin(ipAddr, port, username);
				}
			}
		});

		frame.getContentPane().setLayout(null);
		frame.getContentPane().add(login);
		frame.setVisible(true);

	}

}
