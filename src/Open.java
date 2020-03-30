import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.AbstractListModel;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.SystemColor;
import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.SwingConstants;

public class Open {

	JFrame frmOpen;
	private JTextField txtFilename;
	private JTextField txtFileDoesNot;
	private WhiteBoard whiteBoard;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the application.
	 */
	public Open() {
		initialize();
	}

	public Open(WhiteBoard whiteBoard) {
		this.whiteBoard = whiteBoard;
		initialize();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmOpen = new JFrame();
		frmOpen.setTitle("Open");
		frmOpen.setBounds(100, 100, 450, 300);
		frmOpen.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmOpen.getContentPane().setLayout(null);

		JButton btnNewButton = new JButton("Open");
		btnNewButton.setBounds(103, 158, 93, 29);
		frmOpen.getContentPane().add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Cancel");
		btnNewButton_1.setBounds(226, 158, 86, 29);
		frmOpen.getContentPane().add(btnNewButton_1);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmOpen.dispose();
			}
		});

		txtFilename = new JTextField();
		txtFilename.setText("white_board");
		txtFilename.setBounds(77, 95, 168, 35);
		frmOpen.getContentPane().add(txtFilename);
		txtFilename.setColumns(10);

		JLabel lblOpenFile = new JLabel("Open File");
		lblOpenFile.setBounds(85, 66, 78, 16);
		frmOpen.getContentPane().add(lblOpenFile);

		txtFileDoesNot = new JTextField();
		txtFileDoesNot.setForeground(SystemColor.inactiveCaptionText);
		txtFileDoesNot.setHorizontalAlignment(SwingConstants.CENTER);
		txtFileDoesNot.setEditable(false);
		txtFileDoesNot.setBackground(SystemColor.control);
		txtFileDoesNot.setText("File does not exist!");
		txtFileDoesNot.setBounds(103, 218, 238, 26);
		txtFileDoesNot.setBorder(null);
		frmOpen.getContentPane().add(txtFileDoesNot);
		txtFileDoesNot.setColumns(10);
		txtFileDoesNot.setVisible(false);


		JLabel lbldat = new JLabel(".dat");
		lbldat.setBounds(248, 104, 61, 16);
		frmOpen.getContentPane().add(lbldat);

		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = txtFilename.getText();
				String file = "./save/"+name + ".dat";

				Scanner inputStream = null;
				try {
					inputStream = new Scanner(new FileInputStream(file));
				} catch (FileNotFoundException e1) {
					txtFileDoesNot.setVisible(true);
					JOptionPane.showMessageDialog(frmOpen, "file does not exist!");
					return;
				}
				whiteBoard.openFile(file);
				frmOpen.dispose();
			}
		});
	}
}
