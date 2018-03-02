package Vue;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import Enterprise.EnterpriseManagerContainer;
import Google.Address.AddressManager.AddressManager;
import Google.Address.AddressManager.AddressManagerContainer;

public class DesktopFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private JTabbedPane jtbMenu;
	
	public static final MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
	
	public DesktopFrame() {
		this.setTitle("LMG 3");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		
		//this.setJMenuBar(new Menu());
		
		this.initGUI();
		
		getContentPane().add(new FooterComponent(), BorderLayout.SOUTH);
		
		this.pack();
		this.setVisible(true);
	}
	
	private void initGUI() {
		this.jtbMenu = new JTabbedPane(SwingConstants.TOP);
		BufferedImage mapIcon = null;
		BufferedImage enterpriseIcon = null;
		
		try {
			/*
			 * Image Address
			 */
			URL url = this.getClass().getClassLoader().getResource("Images/256x256.png");
			mapIcon = ImageIO.read(new File(url.getFile()));
			
			/*
			 * Image Entreprise
			 */
			url = this.getClass().getClassLoader().getResource("Images/user_starred.png");
			enterpriseIcon = ImageIO.read(new File(url.getFile()));
			
			/*
			 * Image fenêtre logiciel
			 */
			url = this.getClass().getClassLoader().getResource("Images/key_folder.png");
			this.setIconImage(ImageIO.read(new File(url.getFile())));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		/*
		 * Tab Addresses
		 */
		AddressManagerContainer addressManagerContainer = new AddressManagerContainer();
		new AddressManager(addressManagerContainer);
		this.jtbMenu.addTab("Address", new ImageIcon(mapIcon), addressManagerContainer);
		
		/*
		 * Tab Enterprises
		 */
		this.jtbMenu.addTab("Enterprise", new ImageIcon(enterpriseIcon), new EnterpriseManagerContainer());
		
		getContentPane().add(this.jtbMenu);
	}
	
	public static void main(String[] args) {
		new DesktopFrame();
	}
}
