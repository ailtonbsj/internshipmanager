package io.github.ailtonbsj.internshipmanager;

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

	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);
		g.drawImage(img, (getWidth()/2) - (img.getWidth(null)/2), (getHeight()/2) - (img.getHeight(null)/2), null);

	}
}
