package management.model;

public class Credit {
	private int creditNumber;
	private String startDate;
	private String maturityDate;
	private int term;
	private double interestRate;
	private double loanAmount;


	public Credit(){
		this.creditNumber = 0;
		this.startDate = "none";
		this.maturityDate = "none";
		this.term = 10;
		this.interestRate = 9.5;
		this.loanAmount = 0.0;
	}

	public Credit(int creditNumber, String startDate, String maturityDate, int term, double interestRate,
			double loanAmount) {
		super();
		this.creditNumber = creditNumber;
		this.startDate = startDate;
		this.maturityDate = maturityDate;
		this.term = term;
		this.interestRate = interestRate;
		this.loanAmount = loanAmount;
	}

	public int getCreditNumber() {
		return creditNumber;
	}

	public void setCreditNumber(int creditNumber) {
		this.creditNumber = creditNumber;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getMaturityDate() {
		return maturityDate;
	}

	public void setMaturityDate(String maturityDate) {
		this.maturityDate = maturityDate;
	}

	public int getTerm() {
		return term;
	}

	public void setTerm(int term) {
		this.term = term;
	}

	public double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}

	public double getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(double loanAmount) {
		this.loanAmount = loanAmount;
	}

}
