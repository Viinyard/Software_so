package Google.Address.AddressManager.New.AddressResearch;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import Google.Address.AddressManager.Data.Result;


public class ResultListCellRenderer implements ListCellRenderer<Result> {

	@Override
	public Component getListCellRendererComponent(JList<? extends Result> list, Result value, int index,
			boolean isSelected, boolean cellHasFocus) {
		DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();
		JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index,
		        isSelected, cellHasFocus);
		renderer.setText(value.getDescription());
		return renderer;
	}
}
