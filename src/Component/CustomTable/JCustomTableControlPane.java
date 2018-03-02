package Component.CustomTable;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class JCustomTableControlPane extends JPanel {

	private static final long serialVersionUID = 1L;

	protected JButton jbSettings, jbSearch;
	protected JTextField jtfSearchBar;
	protected JComboBox<String> jcbColumn;
	
	public JCustomTableControlPane() {
		this.initGUI();
	}
	
	private void initGUI() {
		this.setLayout(new BorderLayout());
		
		this.jbSettings = new JButton();
		this.jbSearch = new JButton();
		
		this.jcbColumn = new JComboBox<String>();
		
		this.jtfSearchBar = new JTextField();
		
		this.add(this.jtfSearchBar, BorderLayout.CENTER);
		
		try {
			String path = "Images/";
			String ext = ".png";
			URL url = this.getClass().getClassLoader().getResource(path+"zoom32"+ext);
			this.jbSearch.setIcon(new ImageIcon(ImageIO.read(new File(url.getFile()))));
			
			url = this.getClass().getClassLoader().getResource(path+"settings32"+ext);
			this.jbSettings.setIcon(new ImageIcon(ImageIO.read(new File(url.getFile()))));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		JPanel jpButton = new JPanel(new GridLayout(1, 2));
		jpButton.add(this.jbSearch);
		jpButton.add(this.jbSettings);
		
		this.add(jpButton, BorderLayout.EAST);
		this.add(this.jcbColumn, BorderLayout.WEST);
	}
}
