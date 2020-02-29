package restaurantsystem;

import java.util.ArrayList;
import java.util.List;

public class Table implements TableSubject {

	List<Observer> observers;
	int id;
	boolean isOccupied;
	int numberOfSeats;
	Customer currentCustomer;

	public Table(int id, int numberOfSeats) {
		super();
		this.id = id;
		this.isOccupied = false;
		this.numberOfSeats = numberOfSeats;
		observers = new ArrayList<Observer>();
	}

	void bookTable(Customer cus) {
		isOccupied = true;
		this.currentCustomer = cus;
	}

	void releaseTable() {
		isOccupied = false;
		this.currentCustomer = null;
	}

	void clearTable() {
		releaseTable();
	}

	@Override
	public void notifyObservers() {
		for (Observer o : observers) {
			TableObserver to = (TableObserver) o;
			to.getTableUpdate();
		}
	}

	@Override
	public void register(Observer o) {
		this.observers.add(o);
	}

	@Override
	public void unRegister(Observer o) {
		this.observers.remove(o);

	}

	@Override
	public Customer update(Observer o) {
		return this.currentCustomer;
	}

	@Override
	public void customerDone(Observer o) {
		System.out.println("Customer has done eating and paid the bill. Clearing table and making available");
		Restaurant.getRestaurant().releaseTableByTableId(id);
	}

}
