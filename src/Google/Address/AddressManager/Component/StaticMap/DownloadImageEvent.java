package Google.Address.AddressManager.Component.StaticMap;

import java.awt.image.BufferedImage;
import java.util.EventObject;

public class DownloadImageEvent extends EventObject {

	private static final long serialVersionUID = 1L;
	
	public static final int START = 0;
	public static final int FINISH = 1;
	@Override
	public String toString() {
		return "DownloadImageEvent [time=" + time + ", image=" + image + ", etat=" + etat + "]";
	}

	public static final int FAIL = -1;

	private long time;
	private BufferedImage image;
	private int etat;
	
	public DownloadImageEvent(Object source, BufferedImage image, long time, int etat) {
		super(source);
		this.image = image;
		this.time = time;
		this.etat = etat;
	}
	
	public BufferedImage getImage() {
		return this.image;
	}

	public long getDownloadTime() {
		return this.time;
	}
	
	public int getEtat() {
		return this.etat;
	}
}
