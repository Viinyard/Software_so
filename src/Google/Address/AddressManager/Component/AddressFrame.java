package Google.Address.AddressManager.Component;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class AddressFrame extends JFrame implements WindowListener {
	
	private static final long serialVersionUID = 1L;
	
	protected final static String SEARCH = "tag_search";
	protected final static String ADD = "tag_add";
	protected final static String DELETE = "tag_trash";
	protected final static String INFO = "tag_info";
	protected final static String CAUTION = "caution";
	protected final static String EDIT = "edit";
	protected final static String CONFIG = "settings";
	
	private static int nbInstance = 0;
	
	protected AddressFrame(String title, String logo) {
		this.setAlwaysOnTop(true);
		this.setTitle(title);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		
		try {
			String path = "Images/Tag/";
			String ext = ".png";
			URL url = this.getClass().getClassLoader().getResource(path+logo+ext);
			this.setIconImage(ImageIO.read(new File(url.getFile())));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.pack();
		this.addWindowListener(this);
	}
	
	public static boolean isAlreadyUsed() {
		System.out.println(AddressFrame.nbInstance);
		return AddressFrame.nbInstance > 0;
	}

	@Override
	public void windowOpened(WindowEvent e) {
		if(e.getSource().equals(this)) {
			AddressFrame.nbInstance++;
		}
	}

	@Override
	public void windowClosing(WindowEvent e) {}

	@Override
	public void windowClosed(WindowEvent e) {
		if(e.getSource().equals(this)) {
			AddressFrame.nbInstance--;
		}
	}

	@Override
	public void windowIconified(WindowEvent e) {}

	@Override
	public void windowDeiconified(WindowEvent e) {}

	@Override
	public void windowActivated(WindowEvent e) {}

	@Override
	public void windowDeactivated(WindowEvent e) {}
}
