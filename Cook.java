package restaurantsystem;

import java.util.ArrayList;
import java.util.List;

public class Cook extends Person implements CookSubject, Runnable {

	List<Observer> observers;
	Order currentOrder;

	public Cook() {
		super(1001, "Cook");
		observers = new ArrayList<Observer>();
	}

	@Override
	public void notifyObservers() {
		for (Observer o : observers) {
			Waiter w = (Waiter) o;
			if (w.isOccupied && w.currentCustomer.customerOrder.id == currentOrder.id) {
				System.out.println("COOK: Notifying waiter about food is ready for orderId : " + currentOrder.id);
				w.getCookingUpdate();
			}
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
	public void update(Observer o) {
		System.out.println("COOK: Food is ready for order " + currentOrder.id + "  You can serve the food");
	}

	@Override
	public void run() {
		while (true && !Restaurant.getRestaurant().isClosed()) {
			Order order = Restaurant.getRestaurant().getOrderForCook();
			this.currentOrder = order;
			try {
				Thread.sleep(2000);
				if (currentOrder == null)
					continue;
				System.out.println("COOK: Food making in progress. will be ready in 5 seconds");
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			notifyObservers();
			this.currentOrder = null;
		}
	}
}
