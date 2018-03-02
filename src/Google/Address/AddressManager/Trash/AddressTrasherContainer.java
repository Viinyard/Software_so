package Google.Address.AddressManager.Trash;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Entity.Address;
import Google.Address.AddressManager.Component.AddressFrame;
import Google.Address.AddressManager.Component.StaticMap.ImgPanel;
import Google.Address.AddressManager.Component.StaticMap.StaticMap;

public class AddressTrasherContainer extends AddressFrame implements Observer {

	private static final long serialVersionUID = 1L;
	
	private JLabel jlQuestion;
	protected JButton jbNo;
	private ImgPanel staticMapImagePanel, imagePanel;
	
	protected StaticMap staticMap;
	protected JList<Address> listAddress;
	protected JButton jbYes;
	
	private String questionFormat = "<html><p>Êtes-vous sûr(e) de vouloir supprimer %s addresse%s ?<br/>"
			+ "Cette action sera définitive !</p></html>";

	public AddressTrasherContainer() {
		super("Trash", AddressFrame.DELETE);
		
		this.initGUI();
		this.pack();
		this.setVisible(true);
	}
	
	private void initGUI() {
		this.setLayout(new BorderLayout());
		
		this.jlQuestion = new JLabel(this.questionFormat);
		this.imagePanel = new ImgPanel(new Dimension(48, 48));
		
		JPanel jpNorth = new JPanel(new BorderLayout());
		jpNorth.add(this.jlQuestion, BorderLayout.CENTER);
		jpNorth.add(this.imagePanel, BorderLayout.WEST);
		jpNorth.setBorder(new EmptyBorder(new Insets(0, 20, 0, 0)));
		
		Font font = new Font("Courrier", Font.PLAIN, 18);
		
		this.jbYes = new JButton("Yes");
		this.jbNo = new JButton("No");
		
		this.jbYes.setFont(font);
		this.jbNo.setFont(font);
		
		this.jlQuestion.setFont(font);
		this.jlQuestion.setBorder(new EmptyBorder(new Insets(20, 20, 20, 20)));
		
		JPanel jpButton = new JPanel(new GridLayout(1, 2));
		
		jpButton.add(this.jbYes);
		jpButton.add(this.jbNo);
		
		try {
			String path = "Images/Tag/";
			String ext = ".png";
			URL url = this.getClass().getClassLoader().getResource(path+"yes"+ext);
			this.jbYes.setIcon(new ImageIcon(ImageIO.read(new File(url.getFile())).getScaledInstance(48, 48, Image.SCALE_DEFAULT)));
			
			url = this.getClass().getClassLoader().getResource(path+"no"+ext);
			this.jbNo.setIcon(new ImageIcon(ImageIO.read(new File(url.getFile())).getScaledInstance(48, 48, Image.SCALE_DEFAULT)));
			
			url = this.getClass().getClassLoader().getResource(path+"caution"+ext);
			
			this.imagePanel.setImage(ImageIO.read(new File(url.getFile())), new Dimension(48, 48));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.staticMap = new StaticMap();
		
		this.staticMapImagePanel = new ImgPanel(new Dimension(240, 160));
		this.staticMapImagePanel.setBorder(BorderFactory.createLoweredBevelBorder());
		this.staticMap.addImageDownloadListener(this.staticMapImagePanel);
		
		this.listAddress = new JList<Address>();
		this.listAddress.setBorder(BorderFactory.createLoweredBevelBorder());
		this.listAddress.setCellRenderer(new AddressListCellRenderer());
		
		this.add(this.listAddress, BorderLayout.CENTER);
		this.add(this.staticMapImagePanel, BorderLayout.EAST);
		this.add(jpNorth, BorderLayout.NORTH);
		this.add(jpButton, BorderLayout.SOUTH);
	}

	@Override
	public void update(Observable o, Object arg) {
		if(arg instanceof ArrayList<?>) {
			int l = ((ArrayList<?>) arg).size();
			this.jlQuestion.setText(String.format(this.questionFormat, l > 1 ? "ces "+Integer.toString(l) : "cette", l > 1 ? "s" : ""));
		}
	}
}
