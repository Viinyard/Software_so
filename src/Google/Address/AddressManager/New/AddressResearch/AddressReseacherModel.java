package Google.Address.AddressManager.New.AddressResearch;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import Google.Address.AddressManager.Data.GoogleAddressResponse;
import Google.Address.AddressManager.Data.Result;

public class AddressReseacherModel extends Observable {

	protected static final int SEARCH = 0;
	protected static final int SEARCHING = 1;
	protected static final int RESULT = 2;
	protected static final int NO_RESULT = 3;
	protected static final int RESEAU_ERROR = 4;
	
	protected ImageIcon[] icons;
	protected int currentIcon;
	
	private Result[] results;
	
	private boolean documentChanged = false;
	
	public AddressReseacherModel() {
		this.currentIcon = AddressReseacherModel.SEARCH;
		this.icons = new ImageIcon[5];
		
		try {
			String path = "Images/Tag/";
			String ext = ".png";
			URL url = this.getClass().getClassLoader().getResource(path+"tag_search32"+ext);
			this.icons[AddressReseacherModel.SEARCH] = new ImageIcon(ImageIO.read(new File(url.getFile())));
			
			url = this.getClass().getClassLoader().getResource(path+"tag_refresh32"+ext);
			this.icons[AddressReseacherModel.SEARCHING] = new ImageIcon(ImageIO.read(new File(url.getFile())));
			
			url = this.getClass().getClassLoader().getResource(path+"tag_accept32"+ext);
			this.icons[AddressReseacherModel.RESULT] = new ImageIcon(ImageIO.read(new File(url.getFile())));
			
			url = this.getClass().getClassLoader().getResource(path+"tag_deny32"+ext);
			this.icons[AddressReseacherModel.NO_RESULT] = new ImageIcon(ImageIO.read(new File(url.getFile())));
			
			url = this.getClass().getClassLoader().getResource(path+"reseau_error"+ext);
			this.icons[AddressReseacherModel.RESEAU_ERROR] = new ImageIcon(ImageIO.read(new File(url.getFile())));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.results = new Result[0];
	}

	@Override
	public void addObserver(Observer o) {
		super.addObserver(o);
		o.update(this, null);
	}
	
	public void setResponse(GoogleAddressResponse gr) {
		if(gr != null) {
			this.setStatus(gr.getStatus());
			this.setResults(gr.getPredictions());
		} else {
			this.currentIcon = AddressReseacherModel.RESEAU_ERROR;
			this.documentChanged = true;
		}
		
		this.setChanged();
		this.notifyObservers();
	}
	
	protected void setStatus(final String status) {
		if(status.equals(GoogleAddressResponse.OK)) {
			this.currentIcon = AddressReseacherModel.RESULT;
		} else if(status.equals(GoogleAddressResponse.NO_RESULT)) {
			this.currentIcon = AddressReseacherModel.NO_RESULT;
		}
	}
	
	public void search() {
		this.documentChanged = false;
		this.currentIcon = AddressReseacherModel.SEARCHING;
		
		this.setChanged();
		this.notifyObservers();
	}
	
	protected void setDocumentChanged() {
		if(!this.documentChanged) {
			this.setChanged();
		}
		
		this.documentChanged = true;
		this.currentIcon = AddressReseacherModel.SEARCH;
		
		this.setChanged();
		this.notifyObservers();
	}
	
	protected boolean hasDocumentChanged() {
		return this.documentChanged;
	}
	
	protected Result[] getResults() {
		return this.results;
	}

	private void setResults(Result[] results) {
		ArrayList<Result> alTemp = new ArrayList<Result>();
		for(Result r : results) {
			if(r.getId() != null) {
				alTemp.add(r);
			}
		}
		
		this.results = alTemp.toArray(new Result[alTemp.size()]);
		if(this.results.length == 0) {
			this.currentIcon = AddressReseacherModel.NO_RESULT;
		}
	}
	
	public ImageIcon getIcon() {
		return this.icons[this.currentIcon];
	}
}
