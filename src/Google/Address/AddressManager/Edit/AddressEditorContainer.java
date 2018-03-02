package Google.Address.AddressManager.Edit;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import Google.Address.AddressManager.Component.AddressForm;
import Google.Address.AddressManager.Component.AddressFrame;

public class AddressEditorContainer extends AddressFrame implements Observer {

	private static final long serialVersionUID = 1L;
	
	protected JButton jbValidate, jbCancel;
	protected AddressForm addressForm;

	protected AddressEditorContainer() {
		super("LMG 3 - Address Editor", AddressFrame.EDIT);
		this.initGUI();
		this.pack();
		this.setSize(new Dimension(600, this.getHeight()));
		this.setMinimumSize(new Dimension(this.getSize()));
		this.setMaximumSize(new Dimension(2000, this.getHeight()));
		this.setVisible(true);
	}
	
	private void initGUI() {
		this.setLayout(new BorderLayout());
		
		this.jbValidate = new JButton("Valider");
		this.jbCancel = new JButton("Annuler");
		
		Font font = new Font("Courrier", Font.PLAIN, 18);
		
		this.jbValidate.setFont(font);
		this.jbCancel.setFont(font);
		
		this.jbValidate.setEnabled(false);
		
		this.addressForm = new AddressForm(true);
		
		try {
			String path = "Images/Tag/";
			String ext = ".png";
			URL url = this.getClass().getClassLoader().getResource(path+"yes"+ext);
			this.jbValidate.setIcon(new ImageIcon(ImageIO.read(new File(url.getFile())).getScaledInstance(48, 48, Image.SCALE_DEFAULT)));
			
			url = this.getClass().getClassLoader().getResource(path+"no"+ext);
			this.jbCancel.setIcon(new ImageIcon(ImageIO.read(new File(url.getFile())).getScaledInstance(48, 48, Image.SCALE_DEFAULT)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		JPanel panSouth = new JPanel(new GridLayout(1, 2));
		panSouth.add(this.jbCancel);
		panSouth.add(this.jbValidate);
		
		this.add(this.addressForm, BorderLayout.CENTER);
		this.add(panSouth, BorderLayout.SOUTH);
	}

	@Override
	public void update(Observable o, Object arg) {
		if(arg instanceof Boolean) {
			if((boolean) arg) {
				this.jbValidate.setEnabled(true);
			}
		}
	}
}
