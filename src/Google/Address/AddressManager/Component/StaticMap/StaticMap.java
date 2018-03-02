package Google.Address.AddressManager.Component.StaticMap;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.event.EventListenerList;

import Entity.Address;
import Entity.Attachment;
import Google.Address.AddressManager.Data.Location;
import Google.Address.AddressManager.Data.Result;

public class StaticMap implements Observer {

	private final String URL_MAP = "http://maps.google.com/maps/api/staticmap";
	private final String map = "?center=";

	/**
	 * MAP TYPES
	 */
	private int mapType = 0;

	public static final String[] MAP_TYPES = { "roadmap", // 0
			"satellite", // 1
			"terrain", // 2
			"hybrid" // 3
	};

	public static final int ROADMAP = 0;
	public static final int SATELLITE = 1;
	public static final int TERRAIN = 2;
	public static final int HYBRID = 3;

	public static final int MIN_WIDTH = 0, MAX_WIDTH = 640;
	public static final int MIN_HEIGHT = 0, MAX_HEIGHT = 640;

	private Dimension size = new Dimension(StaticMap.MAX_WIDTH, StaticMap.MAX_HEIGHT);

	private int scale = 0;

	public static final Integer[] SCALES = new Integer[] { 1, 2 };

	private int zoom = 15;

	public static final int ZOOM_MIN = 0;
	public static final int ZOOM_MAX = 21;

	private Location location = null;

	private BufferedImage imageNotFound;
	private BufferedImage imageError;
	private BufferedImage imageLoading;

	private ArrayList<Markers> markers;

	private ImageThread imgThread;

	private EventListenerList listeners = new EventListenerList();

	public StaticMap(Dimension size) {
		super();
		this.size = size;
	}

	public StaticMap() {
		try {
			String path = "Images/File/";
			String ext = ".png";
			URL url = this.getClass().getClassLoader().getResource(path + "file_error" + ext);
			this.imageNotFound = ImageIO.read(new File(url.getFile()));

			url = this.getClass().getClassLoader().getResource(path + "file_refresh" + ext);
			this.imageLoading = ImageIO.read(new File(url.getFile()));

			url = this.getClass().getClassLoader().getResource(path + "file_error" + ext);
			this.imageError = ImageIO.read(new File(url.getFile()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void addImageDownloadListener(DownloadImageListener listener) {
		this.listeners.add(DownloadImageListener.class, listener);
	}

	public void removeImageDownloadListener(DownloadImageListener listener) {
		this.listeners.remove(DownloadImageListener.class, listener);
	}

	public DownloadImageListener[] getDownloadImageListener() {
		return this.listeners.getListeners(DownloadImageListener.class);
	}

	private void setLocation(Location location) {
		if (this.imgThread != null) {
			this.imgThread.interrupt();
		}
		if (location != null) {
			this.location = location;
			this.markers = new ArrayList<Markers>();
			this.markers.add(new Markers(this.location));

			for (DownloadImageListener listener : this.getDownloadImageListener()) {
				listener.downloadImage(new DownloadImageEvent(this, this.imageLoading, 0, DownloadImageEvent.START));
			}

			this.imgThread = new ImageThread(this, this.getURL(), this.imageNotFound, this.getDownloadImageListener());
			this.imgThread.start();
		} else {
			this.location = null;

			for (DownloadImageListener listener : this.getDownloadImageListener()) {
				listener.downloadImage(new DownloadImageEvent(this, this.imageError, 0, DownloadImageEvent.START));
			}
		}
	}

	private void setAddress(Address address) {
		if (this.imgThread != null) {
			this.imgThread.interrupt();
		}
		if (address != null) {
			this.location = address.getLocation();
			this.markers = new ArrayList<Markers>();
			this.markers.add(new Markers(this.location));

			for (DownloadImageListener listener : this.getDownloadImageListener()) {
				listener.downloadImage(new DownloadImageEvent(this, this.imageLoading, 0, DownloadImageEvent.START));
			}

			boolean cached = false;
			long startTime = System.currentTimeMillis();
			if (address.getAttachments().size() > 0) {
				for (Attachment attachment : address.getAttachments()) {
					if (attachment.getImage() == null) {
						cached = false;
					} else {
						long timeElapsed = System.currentTimeMillis() - startTime;
						for (DownloadImageListener listener : this.getDownloadImageListener()) {
							listener.downloadImage(new DownloadImageEvent(this, attachment.getImage(), timeElapsed, DownloadImageEvent.FINISH));
						}
						cached = true;
					}

				}
			}

			if (!cached) {
				this.imgThread = new ImageThread(this, this.getURL(), this.imageNotFound, this.getDownloadImageListener());
				this.imgThread.start();
			}
			
		} else { 
			this.location = null;

			for (DownloadImageListener listener : this.getDownloadImageListener()) {
				listener.downloadImage(new DownloadImageEvent(this, this.imageError, 0, DownloadImageEvent.START));
			}
		}

	}

	public void addMarker(Markers marker) {
		this.markers.add(marker);
	}

	public ArrayList<Markers> getMarkers() {
		return this.markers;
	}

	public Location getLocation() {
		return this.location;
	}

	public Dimension getSize() {
		return this.size;
	}

	public void setSize(Dimension size) {
		size.setSize(size.getWidth() > 640 ? 640 : size.getWidth(), size.getHeight() > 640 ? 640 : size.getHeight());
		this.size = size;
	}

	public String getSizeString() {
		return (int) this.size.getWidth() + "x" + (int) this.size.getHeight();
	}

	public void setScale(int scale) {
		if (scale <= 0) {
			this.scale = 0;
		} else if (scale >= StaticMap.SCALES.length) {
			this.scale = StaticMap.SCALES.length - 1;
		} else {
			this.scale = scale;
		}
	}

	public int getScale() {
		return this.scale;
	}

	public void setMapType(int type) {
		if (type >= 0 && type < StaticMap.MAP_TYPES.length) {
			this.mapType = type;
		}
	}

	public String getMapType() {
		return StaticMap.MAP_TYPES[this.mapType];
	}

	public void setZoom(int zoom) {
		this.zoom = zoom < StaticMap.ZOOM_MIN ? StaticMap.ZOOM_MIN
				: zoom > StaticMap.ZOOM_MAX ? StaticMap.ZOOM_MAX : zoom;
	}

	public int getZoom() {
		return this.zoom;
	}

	public String getURL() {
		String url = this.URL_MAP;
		url += this.map + this.getLocation().toString();
		url += "&size=" + this.getSizeString();
		url += "&maptype=" + this.getMapType();
		url += "&scale=" + this.getScale();
		url += "&zoom=" + this.getZoom();
		for (Markers m : this.markers) {
			url += m.toString();
		}
		return url;
	}

	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof Address) {
			this.setAddress((Address) arg);
		} else if (arg instanceof Result) {
			if (((Result) arg).getGeometry() != null) {
				this.setLocation(((Result) arg).getGeometry().getLocation());
			}
		}
	}
}
