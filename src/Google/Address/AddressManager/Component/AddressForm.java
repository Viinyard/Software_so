package Google.Address.AddressManager.Component;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentListener;

import Component.JIntegerField;
import Entity.Address;

public class AddressForm extends JPanel implements Observer {
	
	private static final long serialVersionUID = 1L;
	
	private JIntegerField jifZipCode;
	private JTextField jtfStreet, jtfCity, jtfCountry, jtfState, jtfLabel, jtfStreetNumber, jtfFormattedAddress;
	private JLabel jlStreet, jlCity, jlCountry, jlState, jlZipCode, jlLabel, jlFormattedAddress;
	
	public AddressForm(boolean editable) {
		this.initGUI(editable);
	}
	
	public void setAddress(Address addr) {
		if(addr != null) {
			this.displayInfo(addr);
		} else {
			this.clearInfo();
		}
	}
	
	public void addDocumentListener(DocumentListener listener) {
		this.jtfLabel.getDocument().addDocumentListener(listener);
	}
	
	private void clearInfo() {
		this.jtfFormattedAddress.setText("");
		this.jtfStreetNumber.setText("");
		this.jtfStreet.setText("");
		this.jtfCity.setText("");
		this.jtfState.setText("");
		this.jifZipCode.setText("");
		this.jtfCountry.setText("");
		this.jtfLabel.setText("");
	}
	
	private void displayInfo(Address addr) {
		this.jtfFormattedAddress.setText(addr.getFormatted_address());
		this.jtfStreetNumber.setText(addr.getStreetNumber());
		this.jtfStreet.setText(addr.getStreet());
		this.jtfCity.setText(addr.getCity());
		this.jtfState.setText(addr.getState());
		this.jifZipCode.setText(Integer.toString(addr.getZipCode()));
		this.jtfCountry.setText(addr.getCountry());
		this.jtfLabel.setText(addr.getLabel());
		
		this.revalidate();
	}
	
	private void initGUI(boolean editable) {
		this.setLayout(new GridBagLayout());
		
		this.jtfFormattedAddress = new JTextField();
		this.jtfFormattedAddress.setEnabled(false);
		
		this.jtfStreetNumber = new JTextField();
		this.jtfStreetNumber.setEnabled(false);
		this.jifZipCode = new JIntegerField();
		this.jifZipCode.setEnabled(false);
		
		this.jtfStreet = new JTextField();
		this.jtfStreet.setEnabled(false);
		this.jtfCity = new JTextField();
		this.jtfCity.setEnabled(false);
		this.jtfCountry = new JTextField();
		this.jtfCountry.setEnabled(false);
		this.jtfState = new JTextField();
		this.jtfState.setEnabled(false);
		this.jtfLabel = new JTextField();
		this.jtfLabel.setEnabled(editable);
		
		this.jlFormattedAddress = new JLabel("Address brut");
		this.jlFormattedAddress.setHorizontalAlignment(SwingConstants.RIGHT);
		this.jlStreet = new JLabel("Adresse");
		this.jlStreet.setHorizontalAlignment(SwingConstants.RIGHT);
		this.jlCity = new JLabel("Ville");
		this.jlCity.setHorizontalAlignment(SwingConstants.RIGHT);
		this.jlCountry = new JLabel("Pays");
		this.jlCountry.setHorizontalAlignment(SwingConstants.RIGHT);
		this.jlState = new JLabel("Région");
		this.jlState.setHorizontalAlignment(SwingConstants.RIGHT);
		this.jlZipCode = new JLabel("Code Postal");
		this.jlZipCode.setHorizontalAlignment(SwingConstants.RIGHT);
		this.jlLabel = new JLabel("Label");
		this.jlLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JPanel panAddr = new JPanel(new BorderLayout());
		panAddr.add(this.jtfStreetNumber, BorderLayout.WEST);
		panAddr.add(this.jtfStreet, BorderLayout.CENTER);
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridheight = gbc.gridwidth = 1;
		gbc.weightx = gbc.weighty = 1;
		gbc.gridx = gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.fill = GridBagConstraints.BOTH;
		
		gbc.weightx = 0.1;
		this.add(this.jlFormattedAddress, gbc);
		gbc.gridx++;
		gbc.weightx = 1;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		this.add(this.jtfFormattedAddress, gbc);
		gbc.gridwidth = 1;
		
		gbc.gridx = 0;
		gbc.gridy++;
		
		gbc.weightx = 0.1;
		this.add(this.jlLabel, gbc);
		gbc.gridx++;
		gbc.weightx = 1;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		this.add(this.jtfLabel, gbc);
		gbc.gridwidth = 1;
		
		gbc.gridx = 0;
		gbc.gridy++;
		
		gbc.weightx = 0.1;
		this.add(this.jlStreet, gbc);
		gbc.weightx = 1;
		gbc.gridx++;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		this.add(panAddr, gbc);
		
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy++;
		
		gbc.weightx = 0.1;
		this.add(this.jlCity, gbc);
		gbc.weightx = 1;
		gbc.gridx++;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		this.add(this.jtfCity, gbc);

		gbc.gridwidth = 1;
		gbc.gridy++;
		gbc.gridx = 0;
		
		gbc.weightx = 0.1;
		this.add(this.jlState, gbc);
		gbc.weightx = 1;
		gbc.gridx++;
		this.add(this.jtfState, gbc);
		gbc.gridx++;
		gbc.weightx = 0.1;
		this.add(this.jlZipCode, gbc);
		gbc.gridx++;
		this.add(this.jifZipCode, gbc);
		
		gbc.gridy++;
		gbc.gridx = 0;
		
		gbc.weightx = 0.1;
		this.add(this.jlCountry, gbc);
		gbc.weightx = 1;
		gbc.gridx++;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		this.add(this.jtfCountry, gbc);
	}

	@Override
	public void update(Observable o, Object arg) {
		if(arg instanceof Address) {
			this.setAddress((Address) arg); 
		}
	}
}