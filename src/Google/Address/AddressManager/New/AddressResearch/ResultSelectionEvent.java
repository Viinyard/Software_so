package Google.Address.AddressManager.New.AddressResearch;

import java.util.EventObject;

import Google.Address.AddressManager.Data.Result;

public class ResultSelectionEvent extends EventObject{

	@Override
	public String toString() {
		return "ResultSelectionEvent [result=" + result + "]";
	}

	private static final long serialVersionUID = 1L;
	
	private Result result;
	
	public ResultSelectionEvent(Object source, Result result) {
		super(source);
		this.result = result;
	}
	
	public Result getSelectedResult() {
		return this.result;
	}
}
