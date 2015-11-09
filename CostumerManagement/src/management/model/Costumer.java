package management.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Model class for a Costumer.
 */
public class Costumer {

	private int costumerNumber;
    private String name;
    private String city;
    private String manager;
    private String address;
    private String email;
    private int ucnUic;
    private String birthday;
    private ObservableList<Credit> creditsData = FXCollections.observableArrayList();




    /**
     * Default constructor.
     */
    public Costumer () {
    	this.name = "ZP Ivan Traktorista";
    	this.city = "Trapoklovo";
        this.manager = "Bai Ivan";
        this.address = "Gorno nadolnishte 1";
        this.email = "traktorista@abv.bg";
        this.ucnUic = 123456789;
        this.birthday = "01.01.1980";
        this.costumerNumber = 0;
        // Adds some random credits
//        Random random = new Random();
//        int randomCredits = random.nextInt(5);
//        for (int i = 0; i < randomCredits; i++) {
//        	creditsData.add(new Credit(0001, "01.01.2015", "01.01.2025", 10, 9.5, i*50));
//        }
    }

    /**
     * Constructor with some initial data.
     *
     * @param name
     * @param city
     */
    public Costumer(String name, String city) {
        this.name = name;
        this.city = city;

        // some initial data
        this.manager = "Bai Ivan";
        this.address = "Gorno nadolnishte 1";
        this.email = "traktorista@abv.bg";
        this.ucnUic = 123456789;
        this.birthday = "01.01.1980";
        this.costumerNumber = 0;
        creditsData.add(new Credit(0001, "01.01.2015", "01.01.2025", 10, 9.5, 10000.0));
    	creditsData.add(new Credit(0002, "01.02.2015", "01.02.2025", 10, 9.5, 9500.0));
    	creditsData.add(new Credit(0003, "01.04.2015", "01.04.2025", 10, 9.5, 1345.50));
    	creditsData.add(new Credit(0004, "01.06.2015", "01.04.2025", 10, 9.5, 4689.48));
    	creditsData.add(new Credit(0005, "01.09.2015", "01.09.2025", 10, 9.5, 2737.95));
    }

    /**
     * Returns the data as an observable list of Credits.
     * @return
     */
    public ObservableList<Credit> getCreditsData() {
        return creditsData;
    }

	public int getNextNumber() {
		int nextNumber = creditsData.size() + 1;
		return nextNumber;
	}

	public int getCostumerNumber() {
		return costumerNumber;
	}

	public void setCostumerNumber(int costumerNumber) {
		this.costumerNumber = costumerNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getUcnUic() {
		return ucnUic;
	}

	public void setUcnUic(int ucnUic) {
		this.ucnUic = ucnUic;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

}
