package Google.Address.AddressManager.New.PlaceResearch;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Observable;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import Entity.Address;
import Google.Address.AddressManager.Data.GoogleAddressResponse;
import Google.Address.AddressManager.Data.GooglePlaceResponse;
import Google.Address.AddressManager.Data.Result;

public class PlaceDetailsModel extends Observable {
	
	protected static final int SEARCH = 0;
	protected static final int SEARCHING = 1;
	protected static final int RESULT = 2;
	protected static final int NO_RESULT = 3;
	protected static final int RESEAU_ERROR = 4;
	
	private ImageIcon[] icons;
	private int currentIcon;
	
	private String buttonLabel;
	
	private String status = "";
	
	private BufferedImage mapImage;
	
	private boolean loaded = false;
	
	private Address address;
	
	private GooglePlaceResponse response;
	
	public PlaceDetailsModel() {
		this.currentIcon = PlaceDetailsModel.SEARCH;
		this.icons = new ImageIcon[5];
		
		try {
			String path = "Images/Tag/";
			String ext = ".png";
			URL url = this.getClass().getClassLoader().getResource(path+"tag_move"+ext);
			this.icons[PlaceDetailsModel.SEARCH] = new ImageIcon(ImageIO.read(new File(url.getFile())).getScaledInstance(48, 48, Image.SCALE_DEFAULT));
			
			url = this.getClass().getClassLoader().getResource(path+"tag_refresh"+ext);
			this.icons[PlaceDetailsModel.SEARCHING] = new ImageIcon(ImageIO.read(new File(url.getFile())).getScaledInstance(48, 48, Image.SCALE_DEFAULT));
			
			url = this.getClass().getClassLoader().getResource(path+"tag_accept"+ext);
			this.icons[PlaceDetailsModel.RESULT] = new ImageIcon(ImageIO.read(new File(url.getFile())).getScaledInstance(48, 48, Image.SCALE_DEFAULT));
			
			url = this.getClass().getClassLoader().getResource(path+"tag_deny"+ext);
			this.icons[PlaceDetailsModel.NO_RESULT] = new ImageIcon(ImageIO.read(new File(url.getFile())).getScaledInstance(48, 48, Image.SCALE_DEFAULT));
			
			url = this.getClass().getClassLoader().getResource(path+"reseau_error"+ext);
			this.icons[PlaceDetailsModel.RESEAU_ERROR] = new ImageIcon(ImageIO.read(new File(url.getFile())));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected boolean isLoaded() {
		return this.loaded;
	}
	
	protected void setMapImage(BufferedImage image) {
		System.out.println("IMAGE >>>>> " + image);
		this.mapImage = image;
		if(this.status != null && this.status.equals(GooglePlaceResponse.OK)) {
			this.currentIcon = PlaceDetailsModel.RESULT;
			this.buttonLabel = "Save Address";
			this.loaded = true;
		} else {
			this.currentIcon = PlaceDetailsModel.SEARCHING;
			this.buttonLabel = "Loading details ...";
		}
		
		this.setChanged();
		this.notifyObservers();
	}
	
	protected BufferedImage getMapImage() {
		return this.mapImage;
	}
	
	private void setStatus(final String status) {
		this.status = status;
		if(this.status.equals(GoogleAddressResponse.OK)) {
			if(this.mapImage == null) {
				this.currentIcon = PlaceDetailsModel.SEARCHING;
				this.buttonLabel = "Loading map ...";
			} else {
				this.currentIcon = PlaceDetailsModel.RESULT;
				this.buttonLabel = "Save Address";
				this.loaded = true;
			}
		} else if(this.status.equals(GoogleAddressResponse.NO_RESULT)) {
			this.currentIcon = PlaceDetailsModel.NO_RESULT;
			this.buttonLabel = "No result found";
		} else if(this.status.equals(GooglePlaceResponse.OVER_QUERY_LIMIT)) {
			this.currentIcon = PlaceDetailsModel.NO_RESULT;
			this.buttonLabel = "You are over yout quota";
		} else if(this.status.equals(GooglePlaceResponse.UNKNOWN_ERROR)) {
			this.currentIcon = PlaceDetailsModel.SEARCH;
			this.buttonLabel = "Error, please try again";
		} else if(this.status.equals(GooglePlaceResponse.INVALID_REQUEST)) {
			this.currentIcon = PlaceDetailsModel.NO_RESULT;
			this.buttonLabel = "Error, invalid request";
		} else if(this.status.equals(GooglePlaceResponse.REQUEST_DENIED)) {
			this.currentIcon = PlaceDetailsModel.NO_RESULT;
			this.buttonLabel = "Error, request denied";
		}
	}
	
	protected void search() {
		this.currentIcon = PlaceDetailsModel.SEARCHING;
		this.buttonLabel = "Searching ...";
		
		this.setChanged();
		this.notifyObservers();
	}
	
	protected void setResult(Result result) {
		this.currentIcon = PlaceDetailsModel.SEARCH;
		this.buttonLabel = "Chercher l'adresse";
		
		this.setChanged();
		this.notifyObservers(result);
	}
	
	protected void setResponse(GooglePlaceResponse gr) {
		this.response = gr;
		if(gr == null) {
			this.currentIcon = PlaceDetailsModel.RESEAU_ERROR;
			this.buttonLabel = "Error";
			this.setChanged();
			this.notifyObservers(null);
		} else {
			this.setStatus(gr.getStatus());
			this.setAddress(new Address(gr));
			this.setChanged();
			this.notifyObservers(gr.getResult());
		}
	}
	
	protected GooglePlaceResponse getResponse() {
		return this.response;
	}
	
	protected ImageIcon getIcon() {
		return this.icons[this.currentIcon];
	}
	
	protected String getButtonLabel() {
		return this.buttonLabel;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
		
		this.setChanged();
		this.notifyObservers(this.address);
	}
}
