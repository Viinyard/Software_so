package Google.Address.AddressManager.Component.StaticMap;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

public class ImageThread extends Thread {
	
	private DownloadImageListener[] listeners;
	private String urlImage;
	private Object source;
	private BufferedImage defaultImage;
	
	public ImageThread(Object source, String urlImage, BufferedImage defaultImage, DownloadImageListener[] listeners) {
		this.source = source;
		this.urlImage = urlImage;
		this.listeners = listeners;
		this.defaultImage = defaultImage;
	}
	
	
	public void run() {
		long startTime = System.currentTimeMillis();
		
		BufferedImage bi = null;
		try {
			bi = ImageIO.read(new URL(this.urlImage));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		long timeElapsed = System.currentTimeMillis() - startTime;
		
		if(!this.isInterrupted()) {
			if(bi != null) {
				for(DownloadImageListener listener : listeners) {
					listener.downloadImage(new DownloadImageEvent(this.source, bi, timeElapsed, DownloadImageEvent.FINISH));
				}
			} else {
				for(DownloadImageListener listener : listeners) {
					listener.downloadImage(new DownloadImageEvent(this.source, this.defaultImage, timeElapsed, DownloadImageEvent.FAIL));
				}
			}
		}
	}
}
