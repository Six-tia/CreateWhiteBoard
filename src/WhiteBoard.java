import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.AWTException;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;

import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.border.LineBorder;
import javax.swing.JComboBox;
import java.awt.SystemColor;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;

public class WhiteBoard {

	static DrawListener drawListener;
	public JFrame frame;
	int x;
	int y;
	int width;
	int height;
	private String file = "./save/white_board.dat";
	static WhiteBoard whiteBoard;
	static MyPanel canvas;
	public JTextArea ChatWindow;
	public JList list;
	public static int curX, curY;

	ImageIcon line = new ImageIcon(WhiteBoard.class.getResource("/Icon/line.png"));
	ImageIcon circle = new ImageIcon(WhiteBoard.class.getResource("/Icon/circle.png"));
	ImageIcon rect = new ImageIcon(WhiteBoard.class.getResource("/Icon/rect.png"));
	ImageIcon oval = new ImageIcon(WhiteBoard.class.getResource("/Icon/oval.png"));
	ImageIcon free = new ImageIcon(WhiteBoard.class.getResource("/Icon/free1.png"));
	ImageIcon eraser = new ImageIcon(WhiteBoard.class.getResource("/Icon/eraser.png"));
	ImageIcon shotScreen = new ImageIcon(WhiteBoard.class.getResource("/Icon/screenshot.png"));

	ImageIcon more = new ImageIcon(WhiteBoard.class.getResource("/Icon/color.png"));
	ImageIcon[] icons = { line, circle, rect, oval, free, eraser,shotScreen};

	// Create the application.
	public WhiteBoard(String mName) {
		initialize(mName);
	}

	public WhiteBoard() {
	}

	public int showRequest(String name) {
		int option = JOptionPane.showConfirmDialog(null, name + " wants to share your white board", "Confirm",
				JOptionPane.INFORMATION_MESSAGE);
		return option;
	}

	// Initialize the contents of the frame.
	private void initialize(String name) {

		frame = new JFrame();
		String mName = name.replace(" ", "");
		frame.setTitle(mName + " Whiteboard (Manager)");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(0, 0, 1205, 840);
		
		
		
		// tool Panel
		drawListener = new DrawListener(frame);
		JPanel toolPanel = new JPanel();
		toolPanel.setBackground(Color.lightGray);
		toolPanel.setBounds(0, 0, 1500, 40);
		toolPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

		JComboBox menu = new JComboBox();
		menu.setModel(new DefaultComboBoxModel(new String[] { "New", "Save", "Save as", "Open", "Exit" }));
		menu.addActionListener(new ActionListener() {

			// check the commnd from coboBox value
			public void actionPerformed(ActionEvent e) {

				if (menu.getSelectedItem().toString().equals("New")) {
					canvas.removeAll();
					canvas.updateUI();
					drawListener.clearRecord();
					try {
						ManagerAction.broadcast("new");
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					System.out.println("New White board");
				} else if (menu.getSelectedItem().toString().equals("Save")) {
					// save as a text file.
					saveFile();

				} else if (menu.getSelectedItem().toString().equals("Save as")) {
					// save as a img file.
					SaveAs saveAs = new SaveAs(whiteBoard);
					saveAs.frmSaveAs.setVisible(true);
				} else if (menu.getSelectedItem().toString().equals("Open")) {
					Open open = new Open(whiteBoard);
					open.frmOpen.setVisible(true);
				} else if (menu.getSelectedItem().toString().equals("Exit")) {

					System.exit(1);
				}
			}
		});

		toolPanel.add(menu);

		// Tools Button
		String[] tools = { "Line", "Circle", "Rect", "Oval", "Free", "Eraser" , "ShotScreen"};
		for (int i = 0; i < tools.length; i++) {
			// JButton button1 = new JButton(tools[i]);
			JButton button1 = new JButton("");
			button1.setActionCommand(tools[i]);
			// button1.setPreferredSize(new Dimension(75, 25));
			button1.setPreferredSize(new Dimension(60, 30));
			Image temp = icons[i].getImage().getScaledInstance(21, 21, icons[i].getImage().SCALE_DEFAULT);
			icons[i] = new ImageIcon(temp);
			button1.setIcon(icons[i]);
			button1.addActionListener(drawListener);
			toolPanel.add(button1);
		}

		// Text
		JButton text = new JButton("T");
		text.setFont(new Font(null, 0, 20));
		text.setPreferredSize(new Dimension(50, 30));
		text.addActionListener(drawListener);
		if (text != null) {
			toolPanel.add(text);
		}

		// thickness menu
		JLabel labelT = new JLabel("Size: ");
		toolPanel.add(labelT);

		int lenThickMenu = 30;
		String[] number = new String[lenThickMenu];
		for (int i = 0; i < lenThickMenu; i++) {
			number[i] = ((Integer) (i + 1)).toString();
		}
		JComboBox thickMenu = new JComboBox();
		thickMenu.setModel(new DefaultComboBoxModel(number));
		toolPanel.add(thickMenu);
		thickMenu.addActionListener(new ActionListener() {

			// check the commnd from coboBox value
			public void actionPerformed(ActionEvent e) {
				int preThick = drawListener.getThickness();
				int curThick = Integer.parseInt(thickMenu.getSelectedItem().toString());
				if (preThick != curThick) {
					drawListener.setThickness(curThick);
				} else {
//					System.out.println(preThick);
				}
			}
		});

		// Color Button
		Color[] color_array = { Color.WHITE, Color.GRAY, Color.BLACK, Color.RED, Color.RED.darker(), Color.ORANGE,
				Color.ORANGE.darker(), Color.YELLOW, Color.GREEN, Color.BLUE, Color.BLUE.darker(), Color.CYAN,
				Color.CYAN.darker(), Color.PINK, Color.PINK.darker(), Color.MAGENTA, Color.MAGENTA.darker() };
		for (int i = 0; i < color_array.length; i++) {
			JButton button2 = new JButton();
			button2.setBackground(color_array[i]);
			button2.setOpaque(true);
			button2.setBorderPainted(false);
			button2.setPreferredSize(new Dimension(20, 20));
			toolPanel.add(button2);
			DrawListener drawListener = new DrawListener();
			button2.addActionListener(drawListener);
		}
		frame.getContentPane().setLayout(null);

		// add tool panel to window
		frame.getContentPane().add(toolPanel);

		// canvas
		canvas = new MyPanel();
		canvas.setBorder(null);
		canvas.setBounds(106, 40, 950, 740);
		width = canvas.getWidth();
		height = canvas.getHeight();
		canvas.setBackground(Color.WHITE);
		canvas.setList(drawListener.getRecord());
		frame.getContentPane().add(canvas);
		canvas.setLayout(null);
		
		frame.addComponentListener(new ComponentAdapter() {
			public void componentMoved(ComponentEvent e) {
				Component comp = e.getComponent();
				curX = comp.getX()+116;
				curY = comp.getY()+80;
//				System.out.println(curX);
//				System.out.println(curY);
			}
		});
		
		frame.setVisible(true);
		frame.setResizable(false);
		

		// canvas

		canvas.addMouseListener(drawListener);
		canvas.addMouseMotionListener(drawListener);

		// get canvas and put it into draw listener
		drawListener.setG(canvas.getGraphics());

		// Chat Window
		ChatWindow = new JTextArea();
		// ChatWindow.setBounds(1060, 80, 140, 500);
		ChatWindow.setBackground(SystemColor.text);
		frame.getContentPane().add(ChatWindow);

		JScrollPane scrollPaneC = new JScrollPane(ChatWindow);
		scrollPaneC.setBounds(1060, 80, 140, 500);
		scrollPaneC.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		frame.getContentPane().add(scrollPaneC);

		// Color panel
		JButton btnMore = new JButton("");
		btnMore.setActionCommand("More");
		btnMore.setPreferredSize(new Dimension(27, 27));
		Image temp = more.getImage().getScaledInstance(22, 22, more.getImage().SCALE_DEFAULT);
		more = new ImageIcon(temp);
		btnMore.setIcon(more);

		toolPanel.add(btnMore);
		btnMore.addActionListener(drawListener);

		JTextArea textArea_1 = new JTextArea();
		textArea_1.setBackground(SystemColor.text);
		textArea_1.setBounds(1060, 80, 140, 500);
		frame.getContentPane().add(textArea_1);

		JLabel lblNewLabel = new JLabel("Chat Window");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(1060, 40, 140, 40);
		frame.getContentPane().add(lblNewLabel);

		JLabel label = new JLabel("Input Text");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(1060, 580, 140, 40);
		frame.getContentPane().add(label);

		// InputWindow
		JTextArea InputWindow = new JTextArea();
		// InputWindow.setBounds(1060, 620, 140, 130);
		frame.getContentPane().add(InputWindow);

		JScrollPane ScrollPaneI = new JScrollPane(InputWindow);
		ScrollPaneI.setBounds(1060, 620, 140, 130);
		ScrollPaneI.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		frame.getContentPane().add(ScrollPaneI);

		JTextArea textArea_2 = new JTextArea();
		textArea_2.setBounds(1060, 620, 140, 130);
		frame.getContentPane().add(textArea_2);

		JButton btnNewButton = new JButton("Send");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String a = InputWindow.getText();
//				System.out.println(LaunchManager.cList1.size());
//				System.out.println(LaunchManager.cList3.size());

				if (!a.trim().equals("")) {
					String b = mName + ": " + a;
					for (int i = 0; i < LaunchManager.cList1.size(); i++) {
						ChatThread tt = LaunchManager.cList1.get(i);
						try {
							tt.dos.writeUTF("chat " + b);
							tt.dos.flush();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					InputWindow.setText("");
					ChatWindow.append(b + "\r\n");
					ChatWindow.setCaretPosition(ChatWindow.getDocument().getLength());
				} else {
					JOptionPane.showMessageDialog(frame, "sending content cannot be null!");
				}
			}
		});
		btnNewButton.setBounds(1060, 750, 140, 30);
		frame.getContentPane().add(btnNewButton);

		JLabel lblNewLabel_1 = new JLabel("Online");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(5, 40, 98, 40);
		frame.getContentPane().add(lblNewLabel_1);

		// jlist
		list = new JList();
		String uname = mName;
		// list.setBounds(2, 80, 100, 670);
		frame.getContentPane().add(list);
		String[] mname = { uname };
		list.setListData(mname);

		JScrollPane ScrollList = new JScrollPane(list);
		ScrollList.setBounds(2, 80, 100, 670);
		ScrollList.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		frame.getContentPane().add(ScrollList);

		JButton btnKick = new JButton("Kick Out");
		btnKick.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String user = list.getSelectedValue().toString();
				if (mName.equals(user)) {
					return;
				}
				for (int i = 0; i < LaunchManager.cList1.size(); i++) {
					ChatThread tt = LaunchManager.cList1.get(i);
					if (user.equals(tt.name)) {
//						System.out.println("tt name");

						tt.beKicked = true;
						try {
							tt.dos.writeUTF("kick " + tt.name);
							tt.dos.flush();
						} catch (IOException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}

						try {
							tt.socket.close();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						LaunchManager.cList1.remove(i);
						LaunchManager.cList3.remove(user);
						JOptionPane.showMessageDialog(frame, user + " is kicked out!");

					}
				}
				for (String userName : LaunchManager.cList3) {
					user += " " + userName;
				}
				for (int i = 0; i < LaunchManager.cList1.size(); i++) {
					ChatThread tt = LaunchManager.cList1.get(i);
					try {
						tt.dos.writeUTF("delete " + user);
						tt.dos.flush();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				String[] k1 = user.split(" ", 2);
				String[] kkk = k1[1].split(" ");
				list.setListData(kkk);
			}
		});
		btnKick.setBounds(0, 750, 100, 30);
		frame.getContentPane().add(btnKick);

	}

	void setFrame(WhiteBoard whiteBoard) {
		this.whiteBoard = whiteBoard;
		// TODO Auto-generated method stub
	}

	void saveFile(String file) {
		this.file = "./save/" + file;
		this.saveFile();
	}

	void saveFile() {
		PrintWriter outputStream = null;
		try {
			outputStream = new PrintWriter(new FileOutputStream(file));

		} catch (IOException e1) {
			System.out.println("Error opening the file " + file + ".");
			return;
		}

		ArrayList<String> recordList = drawListener.getRecord();

		// *
		for (String record : recordList) {
			outputStream.println(record);
		}
		outputStream.flush();
		outputStream.close();
		System.out.println("File Saved");
	}

	void saveImg(String file) {
		BufferedImage targetImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);// 锟斤拷锟揭伙拷锟絠mage锟斤拷锟斤拷
		Graphics2D g = targetImg.createGraphics();// 锟斤拷锟揭伙拷锟酵硷拷锟斤拷锟�

		g.setColor(Color.white);
		g.fillRect(0, 0, width, height);
		ArrayList<String> recordList = drawListener.getRecord();
		canvas.draw(g, recordList);

		try {
			ImageIO.write(targetImg, "JPEG", new File("./save/" + file));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			System.out.println("Wrong file name.");
			e1.printStackTrace();
		} // 锟斤拷锟斤拷锟酵计�

		System.out.println("File Saved");
	}

	void openFile(String file) {
		//
		Scanner inputStream = null;
		try {
			inputStream = new Scanner(new FileInputStream(file));
		} catch (FileNotFoundException e1) {
			System.out.println("Problem opening files.");
			return;
		}
		drawListener.clearRecord();
		while (inputStream.hasNextLine()) {
			String line = inputStream.nextLine();
			drawListener.update(line);
		}

		try {
			ManagerAction.broadcast("new");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ArrayList<String> rl = drawListener.getRecord();
		try {
			ManagerAction.broadcast("Open Board", rl);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		canvas.repaint();
		inputStream.close();
	}
}
