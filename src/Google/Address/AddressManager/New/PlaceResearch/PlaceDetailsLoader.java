package Google.Address.AddressManager.New.PlaceResearch;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.EventListenerList;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import org.codehaus.jackson.map.ObjectMapper;

import Google.Address.AddressManager.Component.StaticMap.DownloadImageEvent;
import Google.Address.AddressManager.Component.StaticMap.DownloadImageListener;
import Google.Address.AddressManager.Data.GooglePlaceResponse;
import Google.Address.AddressManager.Data.Result;

public class PlaceDetailsLoader implements ActionListener, DownloadImageListener, DocumentListener {
	private String language = "fr";
	private String place_id;
	
	private final String API_KEY = "AIzaSyBCV9qcodF69hmqiI4-k74Mw0WEqVVwJFs";
	
	private final String URL_SEARCH_PLACE = "https://maps.googleapis.com/maps/api/place/details/json";
	
	private PlaceDetailsModel placeDetailsModel;
	private PlaceDetailsContainer placeDetailsContainer;
	
	private String documentLabel = "";
	
	private EventListenerList listeners = new EventListenerList();
	
	private Thread requestThread;
	
	public PlaceDetailsLoader() {
		this.placeDetailsModel = new PlaceDetailsModel();
		this.placeDetailsContainer = new PlaceDetailsContainer();
		this.placeDetailsModel.addObserver(this.placeDetailsContainer);
		this.placeDetailsModel.addObserver(this.placeDetailsContainer.addressForm);
		this.placeDetailsModel.addObserver(this.placeDetailsContainer.staticMap);
		this.placeDetailsContainer.addressForm.addDocumentListener(this);
		this.placeDetailsContainer.staticMap.addImageDownloadListener(this);
		this.placeDetailsContainer.actionButton.addActionListener(this);
	}
	
	public void addSearchAddressListener(SearchAddressResponseListener listener) {
		this.listeners.add(SearchAddressResponseListener.class, listener);
	}
	
	public void removeSearchAddressListener(SearchAddressResponseListener listener) {
		this.listeners.add(SearchAddressResponseListener.class, listener);
	}
	
	public SearchAddressResponseListener[] getSearchAddressListener() {
		return this.listeners.getListeners(SearchAddressResponseListener.class);
	}
	
	private String getURL() {
		String url = this.URL_SEARCH_PLACE;
		url += "?placeid="+this.place_id;
		url += "&language="+this.language;
		url += "&key="+this.API_KEY;
		
		return url;
	}
	
	private void makeResearch() {
		if(this.requestThread != null) {
			this.requestThread.interrupt();
		}
		
		this.placeDetailsModel.search();
		
		this.requestThread = new Thread() {
			@Override
			public void run() {
				GooglePlaceResponse resp = null;
				try {
					URLConnection conn = new URL(getURL()).openConnection();
					conn.setRequestProperty("Accept-Charset", "UTF8");
					InputStream is = conn.getInputStream();
					InputStreamReader reader = new InputStreamReader(is);
					ObjectMapper mapper = new ObjectMapper();
					resp = (GooglePlaceResponse) mapper.readValue(reader, GooglePlaceResponse.class);
					is.close();
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				if(!this.isInterrupted()) {
					setResponse(resp);
				}
			}
		};
		
		this.requestThread.start();
	}
	
	protected void setResponse(GooglePlaceResponse resp) {
		System.out.println(resp);
		this.placeDetailsModel.setResponse(resp);
	}
	
	public void setPlaceId(String place_id) {
		this.place_id = place_id;
	}
	
	public void setResult(Result result) {
		this.placeDetailsModel.setResult(result);
		this.place_id = result.getPlace_id();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(this.placeDetailsModel.isLoaded()) {
			this.placeDetailsModel.getAddress().setLabel(this.documentLabel);
			for(SearchAddressResponseListener listener : this.getSearchAddressListener()) {
				listener.findAddress(new SearchAddressResponseEvent(this, this.placeDetailsModel.getAddress(), this.placeDetailsModel.getMapImage()));
			}
			this.placeDetailsContainer.dispose();
		} else {
			this.makeResearch();
		}
	}

	@Override
	public void downloadImage(DownloadImageEvent e) {
		if(e.getEtat() == DownloadImageEvent.FINISH) {
			this.placeDetailsModel.setMapImage(e.getImage());
		}
	}
	
	private void setDocument(Document d) {
		try {
			this.documentLabel = d.getText(0, d.getLength());
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		this.setDocument(e.getDocument());
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		this.setDocument(e.getDocument());
	}

	@Override
	public void changedUpdate(DocumentEvent e) {}
}
