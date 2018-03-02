package Google.Address.AddressManager.Component.StaticMap;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ImgPanel extends JPanel implements MouseListener, MouseMotionListener, DownloadImageListener {
	private static final long serialVersionUID = 1L;
	
	private BufferedImage img = null;
	
	private Point p;
	private Point p0 = null, m0 = null;
	
	private Dimension dimImg;
	
	public ImgPanel(Dimension preferredSize) {
		this.setPreferredSize(preferredSize);
		this.p = new Point(0, 0);
		
		try {
			String path = "Images/Tag/";
			String ext = ".png";
			URL url = this.getClass().getClassLoader().getResource(path+"tag_file"+ext);
			this.img = ImageIO.read(new File(url.getFile()));
			this.dimImg = new Dimension(this.img.getWidth(null), this.img.getHeight(null));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ImgPanel() {
		this.p = new Point(0, 0);
		
		try {
			String path = "Images/Tag/";
			String ext = ".png";
			URL url = this.getClass().getClassLoader().getResource(path+"tag_file"+ext);
			this.setImage(ImageIO.read(new File(url.getFile())));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setImage(BufferedImage img) {
		if(img != null) {
			this.img = img;
			this.dimImg = new Dimension(this.img.getWidth(null), this.img.getHeight(null));
			this.setPreferredSize(this.dimImg);
		}
		this.repaint();
	}
	
	public void setImage(BufferedImage img, Dimension dimImg) {
		if(img != null) {
			this.img = img;
			this.dimImg = dimImg;
			this.setPreferredSize(dimImg);
			this.setMinimumSize(dimImg);
		}
		this.repaint();
	}
	
	public void paintComponent(Graphics g) {
		int x = (this.getWidth() - this.dimImg.width) / 2;
	    int y = (this.getHeight() - this.dimImg.height) / 2;
	    g.drawImage(this.img, (int) this.p.getX() + x, (int) this.p.getY() + y, (int) this.dimImg.getWidth(), (int) this.dimImg.getHeight(), null);
	}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getPoint().getX() >= this.p.getX() && e.getPoint().getX() <= this.p.getX() + this.img.getWidth(null)
		&& e.getPoint().getY() >= this.p.getY() && e.getPoint().getY() <= this.p.getY() + this.img.getHeight(null)) {
			this.m0 = e.getPoint().getLocation();
			this.p0 = this.p.getLocation();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		this.m0 = null;
		this.p0 = null;
	}

	@Override
	public void mouseEntered(MouseEvent e) {	}

	@Override
	public void mouseExited(MouseEvent e) {	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if(this.m0 != null && this.p0 != null) {
			this.p.setLocation(e.getX() - (m0.getX() - p0.getX()), e.getY() - (m0.getY() - p0.getY()));
			this.repaint();
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {	}

	@Override
	public void downloadImage(DownloadImageEvent e) {
		this.setImage(e.getImage());
		if(e.getEtat() != DownloadImageEvent.START) {
			this.setImage(e.getImage());
		}
	}
}
