package restaurantsystem;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Restaurant {
	static final Restaurant res = new Restaurant();
	private final BlockingQueue<Order> blockingQueue;
	boolean isClosed;

	static Restaurant getRestaurant() {
		return res;
	}

	static int customerId;
	Menu menu;
	Queue<Customer> customers = new ArrayDeque<Customer>();
	Table[] tables = new Table[10];
	boolean[] tableOcc = new boolean[10];

	Restaurant() {
		isClosed = false;
		this.blockingQueue = new ArrayBlockingQueue<Order>(10);
		menu = new Menu();
		Cook cook = new Cook();
		for (int i = 0; i < tables.length; i++) {
			if (i < 2) {
				tables[i] = new Table((i + 1), 2);
			} else if (i < 7) {
				tables[i] = new Table((i + 1), 4);
			} else {
				tables[i] = new Table((i + 1), 6);
			}
			Waiter w = new Waiter((i + 1), "waiter-" + (i + 1));
			tables[i].register(w);
			w.setSubject(tables[i]);

			cook.register(w);
			w.setSubject(cook);

			tableOcc[i] = false;
		}
		System.out.println("#################################################################");
		System.out.println("Printing table sizes : ");
		for (int i = 0; i < tables.length; i++) {
			Table t = tables[i];
			System.out.println("Table Number " + t.id + " seating capacity " + t.numberOfSeats);
		}
		System.out.println("#################################################################");

		Thread cookThread = new Thread(cook);
		cookThread.start();

	}

	void addCustomer(String name, int partyNumber) {
		Customer cus = new Customer(++customerId, name);
		cus.setPartyNumber(partyNumber);
		int book = bookTable(cus);
		if (book == -1) {
			System.out.println("Table not booked for customer " + cus.name + ". seats required " + cus.partyNumber
					+ " adding to queue");
			customers.add(cus);
		}

	}

	void displayMenu() {
		for (Item item : menu.menu) {
			System.out.println("\t\t\tItem: " + item.name() + " ::: Price:  " + item.price);
		}
	}

	int bookTable(Customer cus) {
		if (isAllTablesOccupied()) {
			System.out.println("Sorry All tables occupied");
			return -1;
		}
		int cusPartyNumber = cus.partyNumber;
		int index = -1;
		if (cusPartyNumber > 2) {
			index = 2;
		} else if (cusPartyNumber > 4) {
			index = 7;
		} else {
			index = 0;
		}
		for (int i = index; i < tableOcc.length; i++) {
			if (!tableOcc[i]) {
				Table t = tables[i];
				if (!t.isOccupied && t.numberOfSeats >= cus.partyNumber) {
					Customer waiting = customers.peek();
					if (waiting != null && waiting.partyNumber > cus.partyNumber
							&& t.numberOfSeats >= waiting.partyNumber) {
						System.out.println("Adding current customer " + cus.name + " to waiting state cause requires "
								+ cus.partyNumber + " seats and serving waiting customer " + waiting.name + " requires "
								+ waiting.partyNumber + " seats");
						customers.add(cus);
						cus = customers.poll();
					}
					t.clearTable();
					t.currentCustomer = cus;
					tableOcc[i] = true;
					System.out.println("Table booked for customer " + cus.name + " TableId: " + t.id + " partyNumber "
							+ cus.partyNumber);

					// comment this section for clientcode1 main run
					new Thread(new Runnable() {
						@Override
						public void run() {
							t.notifyObservers();
						}
					}).start();

					return t.id;
				}
			}
		}
//     	System.out.println("Seats not available");
		return -1;
	}

	boolean isAllTablesOccupied() {
		for (int i = 0; i < tableOcc.length; i++) {
			if (!tableOcc[i])
				return false;
		}
		return true;
	}

	boolean releaseTableByIndex(int tableIndex) {
		if (tableIndex > tables.length)
			return false;
		Table t = tables[tableIndex];
		t.clearTable();
		tableOcc[tableIndex] = false;
		System.out.println("Table released " + t.id);
		bookTableForQueuedCustomer();
		return true;
	}

	boolean releaseTableByTableId(int tableId) {
		for (int i = 0; i < tables.length; i++) {
			Table t = tables[i];
			if (t.id == tableId) {
				t.clearTable();
				tableOcc[i] = false;
				System.out.println("Table released " + tableId);
				bookTableForQueuedCustomer();
			}
		}
		return true;
	}

	boolean bookTableForQueuedCustomer() {
		Iterator<Customer> cusIt = customers.iterator();
		while (cusIt.hasNext()) {
			Customer cus = cusIt.next();
			int book = bookTable(cus);
			if (book != -1) {
				cusIt.remove();
				return true;
			}
		}
		return false;
	}

	String getEmptyTableIds() {
		String empty = "";
		for (int i = 0; i < tableOcc.length; i++) {
			if (!tableOcc[i])
				empty += (i + 1) + " ";
		}
		return empty;
	}

	String getWaitingCustomers() {
		String waiting = "";
		for (Customer cus : customers) {
			// bracket shows table with capacity required
			waiting += cus.name + "(" + cus.partyNumber + ")"  + ", ";
		}
		return waiting;
	}

	void addOrder(Order order) {
		this.blockingQueue.add(order);
	}

	Order getOrderForCook() {
		return blockingQueue.poll();
	}

	void closeRestaurant() {
		this.isClosed = true;
	}

	boolean isClosed() {
		return isClosed;
	}
}
