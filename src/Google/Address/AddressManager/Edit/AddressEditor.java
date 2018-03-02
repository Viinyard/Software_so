package Google.Address.AddressManager.Edit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.EventListenerList;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import Entity.Address;

public class AddressEditor implements ActionListener, DocumentListener {
	
	private AddressEditorContainer addressEditorContainer;
	private AddressEditorModel addressEditorModel;
	
	private EventListenerList listeners = new EventListenerList();
	
	private String documentLabel = "";
	
	public AddressEditor(Address address) {
		
		this.addressEditorContainer = new AddressEditorContainer();
		
		this.addressEditorModel = new AddressEditorModel();
		this.addressEditorModel.addObserver(this.addressEditorContainer);
		this.addressEditorModel.addObserver(this.addressEditorContainer.addressForm);
		
		this.addressEditorModel.setAddress(address);
		
		this.addressEditorContainer.addressForm.addDocumentListener(this);
		
		this.addressEditorContainer.jbCancel.addActionListener(this);
		this.addressEditorContainer.jbValidate.addActionListener(this);
	}
	
	public void addEditedAddressListener(EditedAddressListener listener) {
		this.listeners.add(EditedAddressListener.class, listener);
	}
	
	public void removeEditedAddressListener(EditedAddressListener listener) {
		this.listeners.add(EditedAddressListener.class, listener);
	}
	
	public EditedAddressListener[] getEditedAddressListener() {
		return this.listeners.getListeners(EditedAddressListener.class);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(this.addressEditorContainer.jbCancel)) {
			this.addressEditorContainer.dispose();
		}
		
		if(e.getSource().equals(this.addressEditorContainer.jbValidate)) {
			this.addressEditorModel.getAddress().setLabel(this.documentLabel);
			for(EditedAddressListener listener : this.getEditedAddressListener()) {
				listener.addressEdited(new EditedAddressEvent(this, this.addressEditorModel.getAddress()));
			}
			this.addressEditorContainer.dispose();
		}
	}
	
	private void setDocument(Document d) {
		try {
			this.documentLabel = d.getText(0, d.getLength());
			this.addressEditorModel.setDocumentChanged();
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}
		
		System.out.println(this.documentLabel);
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		this.setDocument(e.getDocument());
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		this.setDocument(e.getDocument());
	}

	@Override
	public void changedUpdate(DocumentEvent e) {}

}
