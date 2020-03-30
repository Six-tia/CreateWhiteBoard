import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

public class MyPanel extends JPanel {
	private ArrayList<String> recordList = new ArrayList<String>();

	public void setList(ArrayList<String> recordList) {
		this.recordList = recordList;
	}

	public void paint(Graphics gr) {
		super.paint(gr);// 调用父类的paint（）
		// 循环遍历shapeArray数组
		draw((Graphics2D) gr, this.recordList);

	}

	public void draw(Graphics2D g, ArrayList<String> recordList) {
		try {
			String[] recordArray = (String[])recordList.toArray(new String[recordList.size()]);  
			for (String line : recordArray) {
//			System.out.println(line);
				String[] record = line.split(" ");
				int x1, y1, x2, y2;
				int i;
				int thick;
				int red, green, blue;
				Color color, curColor;
				if (record[1].equals("!")) {
					continue;
				}
				switch (record[0]) {
				case "Color":

					break;
				case "Line":
					thick = Integer.parseInt(record[1]);
					g.setStroke(new BasicStroke(thick));

					red = Integer.parseInt(record[2]);
					green = Integer.parseInt(record[3]);
					blue = Integer.parseInt(record[4]);
					color = new Color(red, green, blue);
					g.setColor(color);

					x1 = Integer.parseInt(record[5]);
					y1 = Integer.parseInt(record[6]);
					x2 = Integer.parseInt(record[7]);
					y2 = Integer.parseInt(record[8]);

					g.drawLine(x1, y1, x2, y2);
					break;
				case "Oval":
					thick = Integer.parseInt(record[1]);
					g.setStroke(new BasicStroke(thick));

					red = Integer.parseInt(record[2]);
					green = Integer.parseInt(record[3]);
					blue = Integer.parseInt(record[4]);
					color = new Color(red, green, blue);
					g.setColor(color);

					x1 = Integer.parseInt(record[5]);
					y1 = Integer.parseInt(record[6]);
					x2 = Integer.parseInt(record[7]);
					y2 = Integer.parseInt(record[8]);

					g.drawOval(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1 - x2), Math.abs(y1 - y2));
					break;
				case "Circle":
					thick = Integer.parseInt(record[1]);
					g.setStroke(new BasicStroke(thick));

					red = Integer.parseInt(record[2]);
					green = Integer.parseInt(record[3]);
					blue = Integer.parseInt(record[4]);
					color = new Color(red, green, blue);
					g.setColor(color);

					x1 = Integer.parseInt(record[5]);
					y1 = Integer.parseInt(record[6]);
					x2 = Integer.parseInt(record[7]);
					y2 = Integer.parseInt(record[8]);

					int diameter = Math.min(Math.abs(x1 - x2), Math.abs(y1 - y2));
					g.drawOval(Math.min(x1, x2), Math.min(y1, y2), diameter, diameter);
					break;
				case "Rect":
					thick = Integer.parseInt(record[1]);
					g.setStroke(new BasicStroke(thick));

					red = Integer.parseInt(record[2]);
					green = Integer.parseInt(record[3]);
					blue = Integer.parseInt(record[4]);
					color = new Color(red, green, blue);
					g.setColor(color);

					x1 = Integer.parseInt(record[5]);
					y1 = Integer.parseInt(record[6]);
					x2 = Integer.parseInt(record[7]);
					y2 = Integer.parseInt(record[8]);

					g.drawRect(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1 - x2), Math.abs(y1 - y2));
					break;
				case "Text":
					thick = Integer.parseInt(record[1]);
					Font f = new Font(null, Font.PLAIN, thick + 10);
					g.setFont(f);

					red = Integer.parseInt(record[2]);
					green = Integer.parseInt(record[3]);
					blue = Integer.parseInt(record[4]);
					color = new Color(red, green, blue);
					g.setColor(color);

					x1 = Integer.parseInt(record[5]);
					y1 = Integer.parseInt(record[6]);
					String text = record[7];

					g.drawString(text, x1, y1);
					break;
				default:
					break;
				}
			}
		} catch (Exception e) {

		}
	}
}
