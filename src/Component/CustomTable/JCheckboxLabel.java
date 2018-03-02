package Component.CustomTable;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class JCheckboxLabel extends JPanel implements ActionListener {
	
	private static final long serialVersionUID = 1L;

	private JLabel label;
	private JCheckBox checkbox;
	private ColumnObject columnObject;
	
	public JCheckboxLabel(ColumnObject columnObject) {
		this.columnObject = columnObject;
		this.initGUI(columnObject.isDisplayed(), columnObject.getColomn_name());
	}
	
	private void initGUI(boolean selected, String name) {
		this.setLayout(new BorderLayout());
		
		this.label = new JLabel(name);
		this.label.setHorizontalTextPosition(SwingConstants.LEFT);

		this.checkbox = new JCheckBox();
		this.checkbox.setEnabled(true);
		this.checkbox.setSelected(selected);
		this.checkbox.addActionListener(this);
		
		this.setBorder(BorderFactory.createLoweredBevelBorder());
		
		this.add(this.checkbox, BorderLayout.WEST);
		this.add(this.label, BorderLayout.CENTER);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.columnObject.setDisplayed(this.checkbox.isSelected());
	}
}
