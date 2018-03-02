package Vue.Component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class ColorPicker extends JPanel implements MouseListener {
	
	private static final long serialVersionUID = -1195052291604058620L;

	private JLabel colorLabel;
	private JCheckBox jcb;
	private boolean editable;
	private int couleur;

	public ColorPicker() {
		this.setLayout(new BorderLayout());
		
		this.editable = false;
		this.jcb = new JCheckBox();
		this.jcb.setEnabled(false);
		
		this.colorLabel = new JLabel();
		this.colorLabel.setBorder(BorderFactory.createEtchedBorder());
		this.colorLabel.setText("< undefined >");
		this.colorLabel.setHorizontalAlignment(SwingConstants.CENTER);
		this.colorLabel.setOpaque(true);
		this.colorLabel.addMouseListener(this);
		
		this.add(this.colorLabel);
	}
	
	public void setEditable(boolean b) {
		this.remove(this.jcb);
		if(b) {
			this.add(this.jcb, BorderLayout.EAST);
			this.colorLabel.setText("< cliquez pour choisir une couleur >");
		}
		this.editable = b;
	}
	
	public void setCouleur(Color couleur) {
		this.setCouleur(couleur.getRGB());
	}
	
	public void setCouleur(int couleur) {
		this.couleur = couleur;
		this.colorLabel.setText("0x"+Integer.toHexString(this.couleur));
		this.colorLabel.setBackground(new Color(this.couleur));
		this.jcb.setSelected(true);
	}
	
	public int getCouleur() {
		return this.colorLabel.getBackground().getRGB();
	}

	public boolean isChecked() {
		return this.jcb.isSelected();
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if(this.editable) {
			Color c = JColorChooser.showDialog(null, "Attribuer une couleur", this.colorLabel.getForeground());
			if (c != null) {
				this.setCouleur(c);
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}
}