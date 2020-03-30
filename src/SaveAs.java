import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SaveAs {

	JFrame frmSaveAs;
	private JTextField textField;
	private WhiteBoard whiteBoard;

	/**
	 * Create the application.
	 */
	public SaveAs() { 
		initialize();
	}

	public SaveAs(WhiteBoard whiteBoard) {
		this.whiteBoard = whiteBoard;
		initialize();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSaveAs = new JFrame();
		frmSaveAs.setTitle("Save as...");
		frmSaveAs.setBounds(100, 100, 403, 241);
		frmSaveAs.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmSaveAs.getContentPane().setLayout(null);
		
		JLabel lblSaveAsfile = new JLabel("File name:");
		lblSaveAsfile.setHorizontalAlignment(SwingConstants.CENTER);
		lblSaveAsfile.setBounds(81, 54, 82, 27);
		frmSaveAs.getContentPane().add(lblSaveAsfile);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {".jpg", ".dat"}));
		comboBox.setBounds(182, 93, 91, 27);
		frmSaveAs.getContentPane().add(comboBox);
		
		JButton btnSave = new JButton("OK");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name=textField.getText();
				String format=comboBox.getSelectedItem().toString();
				if (format.equals(".dat"))
				{
					whiteBoard.saveFile(name+format);
				}
				else if (format.equals(".jpg"))
				{
					whiteBoard.saveImg(name+format);
				}
				frmSaveAs.dispose();
			}
		});
		btnSave.setBounds(104, 147, 82, 29);
		frmSaveAs.getContentPane().add(btnSave);
		
		JButton btnNewButton = new JButton("Cancel");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmSaveAs.dispose();
			}
		});
		btnNewButton.setBounds(191, 147, 82, 29);
		frmSaveAs.getContentPane().add(btnNewButton);
		
		JLabel lblFileFormat = new JLabel("Format:");
		lblFileFormat.setBounds(91, 93, 61, 16);
		frmSaveAs.getContentPane().add(lblFileFormat);
		
		textField = new JTextField();
		textField.setBounds(175, 54, 130, 26);
		frmSaveAs.getContentPane().add(textField);
		textField.setColumns(10);
		textField.setText("white_board");
		
	}
}
