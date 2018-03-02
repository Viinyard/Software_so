package Google.Address.AddressManager.New.AddressResearch;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.EventListenerList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.BadLocationException;

import org.codehaus.jackson.map.ObjectMapper;

import Google.Address.AddressManager.Data.GoogleAddressResponse;
import Google.Address.AddressManager.Data.Location;

public class AddressReseacher implements DocumentListener, ActionListener, ListSelectionListener {
	private String language = "fr";
	private Location location;
	private int radius = 50000;
	private String input;
	
	private final String API_KEY = "AIzaSyBCV9qcodF69hmqiI4-k74Mw0WEqVVwJFs";
	
	private final String URL_AUTOCOMPLETE = "https://maps.googleapis.com/maps/api/place/queryautocomplete/json";
	
	private AddressReseacherModel addressReseacherModel;
	private AddressReseacherContainer addressReseacherContainer;
	
	private EventListenerList listeners = new EventListenerList();
	
	private String document;
	
	private Thread requestThread;
	
	public AddressReseacher() {
		this.location = new Location("49.141881,-0.332085");
		
		this.document = "";
		
		this.addressReseacherModel = new AddressReseacherModel();
		this.addressReseacherContainer = new AddressReseacherContainer();
		this.addressReseacherModel.addObserver(this.addressReseacherContainer);
		this.addressReseacherContainer.addDocumentListener(this);
		this.addressReseacherContainer.addListSelectionListener(this);
		this.addressReseacherContainer.addActionListener(this);
		this.addressReseacherContainer.setVisible(true);
	}
	
	public void setLocation(Location location) {
		this.location = location;
	}
	
	public void setRadius(int km) {
		this.radius = km * 1000;
	}
	
	private String getURL() {
		String url = this.URL_AUTOCOMPLETE;
		url += "?key="+this.API_KEY;
		url += "&location="+this.location.toString();
		url += "&radius="+this.radius;
		url += "&language="+this.language;
		url += "&input="+this.input;
		
		return url;
	}
	
	private void setDocument(DocumentEvent e) {
		try {
			this.document = e.getDocument().getText(0, e.getDocument().getLength());
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}
		
		if(this.addressReseacherModel != null) {
			this.addressReseacherModel.setDocumentChanged();
		}
	}
	
	@Override
	public void insertUpdate(DocumentEvent e) {
		this.setDocument(e);
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		this.setDocument(e);
	}

	private void makeResearch(String entry) {
		if(this.requestThread != null) {
			this.requestThread.interrupt();
		}
		this.input = entry;
		
		this.requestThread = new Thread() {
			@Override
			public void run() {
				GoogleAddressResponse resp = null;
				try {
					InputStream is = new URL(getURL()).openConnection().getInputStream();
					ObjectMapper mapper = new ObjectMapper();
					resp = (GoogleAddressResponse) mapper.readValue(is, GoogleAddressResponse.class);
					is.close();
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					setResponse(null);
					e.printStackTrace();
				}
				
				if(!this.isInterrupted()) {
					setResponse(resp);
				}
			}
		};
		
		this.requestThread.start();
	}
	
	protected void setResponse(GoogleAddressResponse resp) {
		this.addressReseacherModel.setResponse(resp);
	}
	
	public void addResultSelectionListener(ResultSelectionListener listener) {
		this.listeners.add(ResultSelectionListener.class, listener);
	}
	
	public void removeResultSelectionListener(ResultSelectionListener listener) {
		this.listeners.remove(ResultSelectionListener.class, listener);
	}
	
	public ResultSelectionListener[] getResultSelectionListener() {
		return this.listeners.getListeners(ResultSelectionListener.class);
	}
	
	@Override
	public void changedUpdate(DocumentEvent e) {}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(this.document.length() > 0) {
			if(this.addressReseacherModel != null && this.addressReseacherModel.hasDocumentChanged()) {
				this.addressReseacherModel.search();
				this.makeResearch(this.document.replaceAll(" ", "%20"));
			}
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if(!e.getValueIsAdjusting()) {
			for(ResultSelectionListener listener : this.getResultSelectionListener()) {
				listener.resultChange(new ResultSelectionEvent(this, this.addressReseacherModel.getResults()[e.getFirstIndex()]));
			}
			this.addressReseacherContainer.dispose();
		}
	}
}
