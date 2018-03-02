package Google.Address.AddressManager.Trash;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import Entity.Address;

public class AddressListCellRenderer implements ListCellRenderer<Address> {

	@Override
	public Component getListCellRendererComponent(JList<? extends Address> list, Address value, int index,
			boolean isSelected, boolean cellHasFocus) {
		DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();
		JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		renderer.setText(value.getLabel()+" - "+value.getFormatted_address());
		return renderer;
	}

}
