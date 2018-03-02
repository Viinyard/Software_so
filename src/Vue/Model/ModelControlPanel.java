package Vue.Model;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.TitledBorder;

import Vue.Component.JMultiField;

public abstract class ModelControlPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;

	protected boolean editable = false;
	protected final int heightComponent = 30, widthJLabel = 80, tailleField = 30;
	protected ArrayList<Component[]> alLigneComp;

	protected JSeparator js;
	
	
	public ModelControlPanel() {
		this.alLigneComp = new ArrayList<Component[]>();
		this.js = new JSeparator();
	}
	
	protected TitledBorder getTitledBorder(String title) {
		TitledBorder border = BorderFactory.createTitledBorder(BorderFactory
		        .createLoweredBevelBorder(), title);
		border.setTitleJustification(TitledBorder.CENTER);
		return border;
	}
	
	protected abstract void setComponents();
	
	protected abstract void initGUI();
	
	protected void setGUI(JPanel pan) {
		pan.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		this.initGBC(gbc);
		
		for(Component[] tabComp : this.alLigneComp) {
			this.addComponents(tabComp, gbc, pan);
		}
	}
	
	private void addComponents(Component[] comp, GridBagConstraints gbc, JPanel pan) {
		gbc.gridy++;
		switch(comp.length) {
		case 1 :
			if(comp[0] instanceof JSeparator || comp[0] instanceof JMultiField || comp[0] instanceof JButton) {
				gbc.gridwidth = GridBagConstraints.REMAINDER;
				pan.add(comp[0], gbc);
			} else {
				gbc.gridx = 1;
				gbc.gridwidth = 4;
				pan.add(comp[0], gbc);
			}
			break;
		case 2 :
			pan.add(comp[0], gbc);
			gbc.gridx++;
			gbc.gridwidth = 4;
			pan.add(comp[1], gbc);
			break;
		case 3:
			for(int x = 0; x < comp.length; x++) {
				gbc.gridx = (x < 2) ? x : x+1;
				gbc.gridwidth = (x == 0) ?  1 : 2;
				pan.add(comp[x], gbc);
				gbc.gridx += gbc.gridwidth;
			}
			break;
		case 4:
			for(int x = 0; x < comp.length; x++) {
				gbc.fill = (x == 1) ? GridBagConstraints.NONE : GridBagConstraints.BOTH;
				gbc.anchor = (x == 1) ? GridBagConstraints.WEST : GridBagConstraints.CENTER;
				gbc.ipadx = (x == 1) ? 5 : 0;
				gbc.gridwidth = (x == 1) ? 2 : 1;
				
				pan.add(comp[x], gbc);
				
				gbc.gridx += gbc.gridwidth;
			}
		};
		
		this.initGBC(gbc);
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
