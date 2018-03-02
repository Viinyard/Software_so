package Vue.Component;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class JMultiField extends JPanel {
	
	private static final long serialVersionUID = -2716797035988131709L;

	private GridBagConstraints gbc;
	private TitledBorder border;
	
	public JMultiField(String title) {
		this.setLayout(new GridBagLayout());
		
		this.border = BorderFactory.createTitledBorder(BorderFactory
		        .createEtchedBorder(EtchedBorder.LOWERED), title);
		
		this.setBorder(this.border);
		
		this.gbc = new GridBagConstraints();
		this.initGBC(this.gbc);
	}
	
	public void setTitlePosition(int pos) {
		this.border.setTitleJustification(pos);
	}
	
	public void addComponents(Component[] comp) {
		this.gbc.gridy++;
		switch(comp.length) {
		case 1 :
			if(comp[0] instanceof JSeparator || comp[0] instanceof JMultiField) {
				this.gbc.gridwidth = GridBagConstraints.REMAINDER;
				this.add(comp[0], this.gbc);
			} else {
				this.gbc.gridx = 1;
				this.gbc.gridwidth = 4;
				this.add(comp[0], this.gbc);
			}
			break;
		case 2 :
			this.add(comp[0], this.gbc);
			this.gbc.gridx++;
			this.gbc.gridwidth = 4;
			this.gbc.weightx = 500;
			if(comp[1] instanceof ColorPicker) {
				gbc.insets = new Insets(0, 3, 0, 2);
			}
			this.add(comp[1], this.gbc);
			break;
		case 3:
			for(int x = 0; x < comp.length; x++) {
				this.gbc.gridx = (x < 2) ? x : x+1;
				this.gbc.gridwidth = (x == 0) ?  1 : 2;
				this.add(comp[x], this.gbc);
				this.gbc.gridx += this.gbc.gridwidth;
			}
			break;
		case 4:
			for(int x = 0; x < comp.length; x++) {
				this.gbc.fill = (x == 1) ? GridBagConstraints.NONE : GridBagConstraints.BOTH;
				this.gbc.gridwidth = (x == 1) ? 2 : 1;
				
				this.add(comp[x], this.gbc);
				
				this.gbc.gridx += this.gbc.gridwidth;
			}
		};
		
		this.initGBC(this.gbc);
	}
	
	private void initGBC(GridBagConstraints gbc) {
		gbc.gridheight = gbc.gridwidth = 1;
		gbc.weightx = gbc.weighty = 1;
		gbc.gridx = 0;
		gbc.ipadx = gbc.ipady = 0;
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
	}
}
