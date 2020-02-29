package restaurantsystem;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Waiter extends Person implements TableObserver, CookObserver {

	boolean isOccupied;
	TableSubject tableSubject;
	Customer currentCustomer;
	CookSubject cookSubject;

	public Waiter(int id, String name) {
		super(id, name);
	}

	@Override
	public void setSubject(Subject subject) {
		if(subject instanceof CookSubject) {
			this.cookSubject = (CookSubject) subject;
		} else if(subject instanceof TableSubject) {
			this.tableSubject = (TableSubject) subject;	
		}
	}

	@Override
	public void getTableUpdate() {
		isOccupied = true;
		Customer curCustomer = this.tableSubject.update(this);
		this.currentCustomer = curCustomer;
		System.out
				.println("WAITER: Waiter is waiting to take order from customer " + curCustomer.name + "  for table ");
		Order order = new Order();
		while (true) {
			// Arrays.asList(Item.values()).stream().forEach((c) -> System.out.println(c));
			Restaurant.getRestaurant().displayMenu();
			System.out.println("WAITER: Please entery Item ");
			Scanner sc = new Scanner(System.in);
			String item = sc.nextLine();
			System.out.println("WAITER: Please enter number of plates");
			try {
				int numberOfPlates = sc.nextInt();
				Item itemSelected = Item.valueOf(item.toUpperCase());
				ItemOrder itemOrder = new ItemOrder(itemSelected, numberOfPlates);
				order.addItemToOrder(itemOrder);
			} catch (IllegalArgumentException | InputMismatchException o) {
				System.out.println("WAITER: Sorry wrong option. Select again");
				continue;
			}
			System.out.println("WAITER: Do you want to continue? Y or N");
			Character c = sc.next().charAt(0);
			if (c.equals('N') || c.equals('n'))
				break;
		}
		curCustomer.setCustomerOrder(order);
		Restaurant.getRestaurant().addOrder(order);
		System.out.println("WAITER: Order placed. Wait for some time. Food will be served");
	}

//	@Override
//	public void setSubject(Subject subject) {
//		this.cookSubject = (CookSubject) subject;
//	}

	@Override
	public void getCookingUpdate() {
		this.cookSubject.update(this);

		System.out.println("WAITER: Food served to customer " + this.currentCustomer.name + " customer is eating now");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Cashier.getCashierInstance().generateBillForCustomer(this.currentCustomer);
		System.out.println("WAITER: Bill generated for customer " + this.currentCustomer.name);
		currentCustomer.displayBill();

		while (true) {
			Scanner sc = new Scanner(System.in);
			System.out.println("WAITER: Please enter mode of payment. card or cash");
			String mode = sc.next();
			PaymentStrategy strategy;
			switch (mode) {
			case "card":
				strategy = new CardStrategy(1234, "123");
				break;
			case "cash":
				strategy = new CashStrategy();
				break;
			default:
				System.out.println("Unexpected value: Try again ");
				continue;
			}
			Cashier.getCashierInstance().acceptOrderBill(currentCustomer.getBill().billId, strategy);
			break;
		}
		// call to release table as customer is done and paid
		this.tableSubject.customerDone(this);
		isOccupied = false;
	}
}
