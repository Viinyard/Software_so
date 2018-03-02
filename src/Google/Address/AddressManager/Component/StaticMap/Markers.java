package Google.Address.AddressManager.Component.StaticMap;

import java.awt.Color;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import Google.Address.AddressManager.Data.Location;

public class Markers {
	private final String markers = "&markers=";
	
	private final String separator = "|";
	private String color = "blue";
	private char label = new Character(Character.MIN_VALUE);
	private int size = -1;
	
	private ArrayList<Location> locations;
	
	/**
	 * SIZES
	 */
	private final String[] SIZES = new String[] {
			"tiny",
			"mid",
			"small"
	};
	
	public static final int DEFAULT = -1;
	public static final int TINY = 0;
	public static final int MID = 1;
	public static final int SMALL = 2;
	
	/**
	 *  COLORS
	 */
	private final String[] COLORS = new String[] {
			"black", // 0
			"brown", // 1
			"green", // 2
			"purple", // 3
			"yellow", // 4
			"blue", // 5
			"gray", // 6
			"orange", // 7
			"red", // 8
			"white" // 9
	};
	
	public static final int BLACK = 0;
	public static final int BROWN = 1;
	public static final int GREEN = 2;
	public static final int PURPLE = 3;
	public static final int YELLOW = 4;
	public static final int BLUE = 5;
	public static final int GRAY = 6;
	public static final int ORANGE = 7;
	public static final int RED = 8;
	public static final int WHITE = 9;
	
	public Markers(Location location) {
		this.locations = new ArrayList<Location>();
		this.locations.add(location);
	}
	
	public void setColor(int color) {
		if(color >= 0 && color < this.COLORS.length) {
			this.color = this.COLORS[color];
		} else {
			this.color = this.COLORS[Markers.BLUE];
		}
	}
	
	public ArrayList<Location> getLocations() {
		return this.locations;
	}
	
	public void addLocation(Location location) {
		this.locations.add(location);
	}
	
	public void setColor(Color color) {
		if(color != null) {
			this.color = Integer.toHexString(color.getRGB());
		}
	}
	
	public void setSize(int size) {
		if(size >= 0 && size < this.SIZES.length) {
			this.size = size;
		} else {
			this.size = Markers.DEFAULT;
		}
	}
	
	public void setLabel(char label) {
		/* needs to be A-Z or 0-9 and in uppercase
		 * needs to have a markers size equals to default (normal) or mid
		 */
		this.label = new Character(Character.MIN_VALUE);
		if(Character.isLetterOrDigit(label)) {
			if(Character.isUpperCase(label)) {
				if(this.size == Markers.DEFAULT || this.size == Markers.MID) {
					this.label = label;
				}
			}
		}
	}
	
	private String getSeparator() {
		try {
			return URLEncoder.encode(this.separator, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return this.separator;
	}
	
	public char getLabel() {
		return this.label;
	}
	
	public String toString() {
		String mark = "";
		if(this.locations.size() > 0) {
			mark = this.markers;
			mark += "color:"+this.color;
			if(this.label != new Character(Character.MIN_VALUE)) {
				mark += this.getSeparator();
				mark += "label:"+this.label;
			}
			if(this.size != Markers.DEFAULT) {
				mark += this.getSeparator();
				mark += "size:"+this.SIZES[this.size];
			}
			for(Location l : this.locations) {
				mark += this.getSeparator();
				mark += l.getLat()+","+l.getLng();
			}
		}
		return mark;
	}
}
