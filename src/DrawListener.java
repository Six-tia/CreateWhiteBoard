
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

import sun.rmi.runtime.Log;

import java.awt.AWTException;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

public class DrawListener implements ActionListener, MouseListener, MouseMotionListener {

	private Graphics2D g;
	private JFrame frame;
	private int x1;
	private int y1;
	private int x2, preX2;
	private int y2, preY2;
	private int css = 0;
	private Object type = "Line";

	private String record;
	private ArrayList<String> recordList = new ArrayList<String>();
	private int thickness = 1;

	static Color color = Color.BLACK;
	private String colorRecord = "0 0 0";
	
	public DrawListener(JFrame frame) {
		this.frame = frame;
	}
	
	public DrawListener() {

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// If click tools button
		if (e.getActionCommand().equals("")) {
			JButton button = (JButton) e.getSource();
			// get color
			color = button.getBackground();

		} else if (e.getActionCommand().equals("More")) {
			final JFrame jf = new JFrame("Color Penal");
			jf.setSize(300, 300);
			jf.setLocationRelativeTo(null);
			jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			Color curColor = JColorChooser.showDialog(jf, "Choose your own color", null);
			if (curColor != null) {
				color = curColor;
			}

		} else {
			// set tools type
			this.type = e.getActionCommand();
			if (type.equals("Free")) {
				
				Toolkit tk = Toolkit.getDefaultToolkit();
				Image image = new ImageIcon(getClass().getResource("/Icon/free1.png")).getImage();
				Cursor cursor = tk.createCustomCursor(image, new Point(0, 40), "norm"); 
				frame.setCursor(cursor);

			} else if (type.equals("Eraser")) {
				
				Toolkit tk = Toolkit.getDefaultToolkit();
				Image image = new ImageIcon(getClass().getResource("/Icon/eraser.png")).getImage();
				Cursor cursor = tk.createCustomCursor(image, new Point(0, 40), "norm"); 
				frame.setCursor(cursor);

			} else if (type.equals("Oval")) {
				
				Cursor cur=new Cursor(Cursor.CROSSHAIR_CURSOR);//这一句就是设置了一个十字形的鼠标样式
				frame.setCursor(cur);

			} else if (type.equals("ShotScreen")) {
				
				Cursor cur=new Cursor(Cursor.HAND_CURSOR);//这一句就是设置了一个十字形的鼠标样式
				frame.setCursor(cur);

			} else if (type.equals("T")) {
				
				Cursor cur=new Cursor(Cursor.DEFAULT_CURSOR);//这一句就是设置了一个十字形的鼠标样式
				frame.setCursor(cur);

			} else if (type.equals("Circle")) {
				
				Cursor cur=new Cursor(Cursor.CROSSHAIR_CURSOR);//这一句就是设置了一个十字形的鼠标样式
				frame.setCursor(cur);

			} else if (type.equals("Rect")) {
				
				Cursor cur=new Cursor(Cursor.CROSSHAIR_CURSOR);
				frame.setCursor(cur);

			} else if (type.equals("Line")) {
				
				Cursor cur=new Cursor(Cursor.CROSSHAIR_CURSOR);
				frame.setCursor(cur);

			}
		}
	}

	@Override
	// press button
	public void mousePressed(MouseEvent e) {
//		System.out.println(this.type.toString());
		x1 = e.getX();
		y1 = e.getY();
		preX2 = x1;
		preY2 = y1;
		// System.out.println("x1:"+x1+" y1:"+y1);
		if (!g.getColor().equals(color)) {
			g.setColor(color);
		}

		if (type.equals("Free")) {
			colorRecord = getColor(color);
			g.setStroke(new BasicStroke(thickness));
			g.drawLine(x1, y1, x1, y1);

			record = "Line " + this.thickness + " " + colorRecord + " " + x1 + " " + y1 + " " + x1 + " " + y1 + " !";
			recordList.add(record);

		} else if (type.equals("Eraser")) {

			g.setColor(Color.white);
			colorRecord = getColor(Color.white);

			g.setStroke(new BasicStroke(thickness * 2));
			g.drawLine(x1, y1, x1, y1);

			record = "Line " + this.thickness * 2 + " " + colorRecord + " " + x1 + " " + y1 + " " + x1 + " " + y1
					+ " !";
		}
		// System.out.println("press color"+color.toString());\

	}

	private String getColor(Color color) {
		return color.getRed() + " " + color.getGreen() + " " + color.getBlue();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		x2 = e.getX();
		y2 = e.getY();
		//
		if (this.type.equals("Line")) {
			// draw line
			colorRecord = getColor(color);
			g.setStroke(new BasicStroke(thickness));
			g.drawLine(x1, y1, x2, y2);
			record = "Line " + this.thickness + " " + colorRecord + " " + x1 + " " + y1 + " " + x2 + " " + y2 + " !";
			recordList.add(record);

			try {
				ManagerAction.broadcast("draw " + record);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		} else if (type.equals("Oval")) {
			// draw Oval
			colorRecord = getColor(color);
			g.setStroke(new BasicStroke(thickness));
			g.drawOval(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1 - x2), Math.abs(y1 - y2));
			record = "Oval " + this.thickness + " " + colorRecord + " " + x1 + " " + y1 + " " + x2 + " " + y2 + " !";
			recordList.add(record);
		} else if (type.equals("Circle")) {
			// Draw circle
			colorRecord = getColor(color);
			g.setStroke(new BasicStroke(thickness));
			int diameter = Math.min(Math.abs(x1 - x2), Math.abs(y1 - y2));
			g.drawOval(Math.min(x1, x2), Math.min(y1, y2), diameter, diameter);
			record = "Circle " + this.thickness + " " + colorRecord + " " + x1 + " " + y1 + " " + x2 + " " + y2 + " !";
			recordList.add(record);
		} else if (type.equals("Rect")) {
			// draw rectangle
			colorRecord = getColor(color);
			g.setStroke(new BasicStroke(thickness));
			g.drawRect(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1 - x2), Math.abs(y1 - y2));
			record = "Rect " + this.thickness + " " + colorRecord + " " + x1 + " " + y1 + " " + x2 + " " + y2 + " !";
			recordList.add(record);
		} else if (type.equals("T")) {

			String text = JOptionPane.showInputDialog("Please Enter Input Text");

			if (text != null) {

//				System.out.println(text);

				Font f = new Font(null, Font.PLAIN, this.thickness + 10);
				g.setFont(f);

				g.drawString(text, x2, y2);
				colorRecord = getColor(color);
				record = "Text " + this.thickness + " " + colorRecord + " " + x2 + " " + y2 + " " + text + " !";
				recordList.add(record);
			}
		} else {
			if (type.equals("ShotScreen")) {
				WhiteBoard.canvas.repaint();

				BufferedImage myImage = null;
				try {
					css++;
					myImage = new Robot().createScreenCapture(new Rectangle(WhiteBoard.curX + Math.min(x1, x2),
							WhiteBoard.curY + Math.min(y1, y2), Math.abs(x1 - x2) - 4, Math.abs(y1 - y2) - 4));
					ImageIO.write(myImage, "jpg", new File(LoginManager.username + "_" + css + ".jpg"));
				} catch (AWTException e1) {
					e1.printStackTrace();
				} catch (IOException e2) {
					e2.printStackTrace();
				}
			}
			return;
		}

		try {
			ManagerAction.broadcast("draw " + record);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public void setG(Graphics g) {
		this.g = (Graphics2D) g;
	}

	public void mouseDragged(MouseEvent e) {
		x2 = e.getX();
		y2 = e.getY();
		if (type.equals("Free")) {
			// System.out.println("WTF");
			colorRecord = getColor(color);
			g.setStroke(new BasicStroke(thickness));

			g.drawLine(x1, y1, x2, y2);
			record = "Line " + this.thickness + " " + colorRecord + " " + x1 + " " + y1 + " " + x2 + " " + y2 + " !";
			recordList.add(record);

			x1 = x2;
			y1 = y2;
		} else if (type.equals("Eraser")) {
			g.setColor(Color.white);
			colorRecord = getColor(Color.white);
			g.setStroke(new BasicStroke(thickness));

			g.drawLine(x1, y1, x2, y2);
			record = "Line " + this.thickness * 2 + " " + colorRecord + " " + x1 + " " + y1 + " " + x2 + " " + y2
					+ " !";
			recordList.add(record);
			x1 = x2;
			y1 = y2;
		}

		else {
			if (type.equals("ShotScreen")) {
				if (Math.abs(preX2 - x2) + Math.abs(preY2 - y2) >= 4) {
					WhiteBoard.canvas.repaint();
					preX2 = x2;
					preY2 = y2;
				}

				g.setColor(Color.darkGray);
				g.setStroke(new BasicStroke(2));
				g.drawRect(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1 - x2), Math.abs(y1 - y2));
				g.setColor(color);
			}
			return;
		}

		try {
			ManagerAction.broadcast("draw " + record);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseClicked(MouseEvent e) {

		// TODO Auto-generated method stub
	}

	@Override
	public void mouseMoved(MouseEvent e) {

		// TODO Auto-generated method stub
	}

	public ArrayList<String> getRecord() {
		return recordList;
	}

	public void update(String line) {
		// TODO Auto-generated method stub
		recordList.add(line);

	}

	public void clearRecord() {
		// TODO Auto-generated method stub
		recordList.clear();
	}

	public void setThickness(int t) {
		this.thickness = t;
	}

	public int getThickness() {
		return this.thickness;
	}
}