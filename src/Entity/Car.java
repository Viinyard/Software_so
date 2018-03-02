package Entity;

public class Car {
	
	private long id;
	private String model;
	private Integer fiscalHorsepower;
	private long registrationDate;
	private long idEmployee;
	
	public Car(long id, String model, int fiscalHorsepower, long registrationDate, long idEmployee) {
		super();
		this.id = id;
		this.model = model;
		this.fiscalHorsepower = fiscalHorsepower;
		this.registrationDate = registrationDate;
		this.idEmployee = idEmployee;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public int getFiscalHorsepower() {
		return fiscalHorsepower;
	}

	public void setFiscalHorsepower(int fiscalHorsepower) {
		this.fiscalHorsepower = fiscalHorsepower;
	}

	public long getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(long registrationDate) {
		this.registrationDate = registrationDate;
	}

	public long getIdEmployee() {
		return idEmployee;
	}

	public void setIdEmployee(long idEmployee) {
		this.idEmployee = idEmployee;
	}
}
