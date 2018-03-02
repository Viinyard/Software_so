package Vue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class Menu extends JMenuBar implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JMenu jmFile, jmConfig;
	private JMenu jmNew, jmPreference;
	private JMenuItem jmiAddress, jmiSaveDir;

	public Menu() {
		this.initGUI();
	}

	private void initGUI() {
		this.jmFile = new JMenu("File");
		this.jmConfig = new JMenu("Config");

		this.jmNew = new JMenu("new");
		this.jmPreference = new JMenu("Preference");

		this.jmiAddress = new JMenuItem("Address");
		this.jmiSaveDir = new JMenuItem("Change save directory");

		this.jmFile.add(this.jmNew);
		this.jmConfig.add(this.jmPreference);

		this.jmNew.add(this.jmiAddress);
		this.jmPreference.add(this.jmiSaveDir);

		this.jmiSaveDir.addActionListener(this);

		this.add(this.jmFile);
		this.add(this.jmConfig);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(this.jmiSaveDir)) {
			try {
				URL url = this.getClass().getClassLoader().getResource("config/directory.properties");
				File file = new File(url.toURI());
				InputStream in = new FileInputStream(file);
				Properties props = new Properties();
				props.load(in);
				in.close();

				JFileChooser fc = new JFileChooser();
				fc.setCurrentDirectory(new File(props.getProperty("save.url")));
				fc.setFileHidingEnabled(false);
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int ret = fc.showDialog(new JFrame(), "save dir");
				
				if (ret == JFileChooser.APPROVE_OPTION) {
					FileOutputStream out = new FileOutputStream(file);
					props.setProperty("save.url", fc.getCurrentDirectory().getAbsolutePath());
					props.store(out, "--- CONFIG ---");
						
					out.flush();
					out.close();
				}
			} catch (FileNotFoundException e2) {
				e2.printStackTrace();
			} catch (InvalidPropertiesFormatException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (URISyntaxException e1) {
				e1.printStackTrace();
			}
		}
	}
}
