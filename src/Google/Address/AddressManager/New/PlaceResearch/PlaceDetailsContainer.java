package Google.Address.AddressManager.New.PlaceResearch;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import Google.Address.AddressManager.Component.AddressForm;
import Google.Address.AddressManager.Component.AddressFrame;
import Google.Address.AddressManager.Component.StaticMap.ImgPanel;
import Google.Address.AddressManager.Component.StaticMap.StaticMap;
import Google.Address.AddressManager.Data.Result;

public class PlaceDetailsContainer extends AddressFrame implements Observer {
	
	private static final long serialVersionUID = 1L;

	protected JButton actionButton;
	
	protected AddressForm addressForm;
	protected StaticMap staticMap;
	
	public PlaceDetailsContainer() {
		super("LMG 3 - Address Finder (details)", AddressFrame.ADD);
		this.initGUI();
		this.pack();
		this.setMinimumSize(new Dimension(this.getWidth(), 240));
	}
	
	private void initGUI() {
		this.setLayout(new BorderLayout());
		
		/*
		 * Address Formulaire
		 */
		this.add(this.addressForm = new AddressForm(true), BorderLayout.NORTH);
		this.addressForm.setBorder(BorderFactory.createLoweredBevelBorder());
		
		
		/*
		 * Image Panel
		 */
		ImgPanel imgPanel = new ImgPanel(new Dimension(640, 320));
		imgPanel.setBorder(BorderFactory.createLoweredBevelBorder());
		this.add(imgPanel, BorderLayout.CENTER);
		this.staticMap = new StaticMap();
		this.staticMap.addImageDownloadListener(imgPanel);
		
		
		/*
		 * Save Button
		 */
		Font font = new Font("Courrier", Font.PLAIN, 18);
		this.actionButton = new JButton();
		this.actionButton.setFont(font);
		this.actionButton.setHorizontalAlignment(SwingConstants.CENTER);
		
		this.add(this.actionButton, BorderLayout.SOUTH);
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof PlaceDetailsModel) {
			if(arg instanceof Result) {
				this.setVisible(true);
			}
			this.actionButton.setIcon(((PlaceDetailsModel) o).getIcon());
			this.actionButton.setText(((PlaceDetailsModel) o).getButtonLabel());
		}
	}
}
