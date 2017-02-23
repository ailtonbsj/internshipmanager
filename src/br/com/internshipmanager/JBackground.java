package br.com.internshipmanager;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;

@SuppressWarnings("serial")
public class JBackground extends JDesktopPane {

	Image img;
	
	public JBackground() {
		
		img  = new ImageIcon(this.getClass().getResource("bg.jpg")).getImage();
	}
	
	protected void paintComponent(Graphics g) {
		
		super.paintComponent(g);		
		g.drawImage(img, (getWidth()/2) - (img.getWidth(null)/2), (getHeight()/2) - (img.getHeight(null)/2), null);
		
	}
}

//try {
//	Graphics2D g2d = (Graphics2D) g;
//	double x = img.getWidth(null);
//	double y = img.getHeight(null);
//	g2d.scale(getWidth() / x, getHeight() / y);
//	g2d.scale(getWidth()/x, getHeight()/ y);
	
//	g2d.scale(getWidth()/(2*x), getHeight()/(2*y));
//	super.paintComponent(g2d);
//	g2d.drawImage(img, 0, 0, this);
//} catch (Exception e) {
//	System.out.println("A imagem não pode ser inserido!");
//}
//super.paintComponent(g);
