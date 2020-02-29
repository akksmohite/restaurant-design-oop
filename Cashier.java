package restaurantsystem;

import java.util.HashMap;
import java.util.Map;

public class Cashier extends Person {

	private int totalAmount;
	private int billId;
	private Map<Integer, Bill> billings = new HashMap<Integer, Bill>();

	private Cashier() {
		super(1000, "Cachier");
		billId = 0;
		totalAmount = 0;

	}

	private static final Cashier cachier = new Cashier();

	static Cashier getCashierInstance() {
		return cachier;
	}

	void generateBillForCustomer(Customer customer) {
		int sum = 0;
		String billReceipt = "";
		billReceipt += "\n************************ Welcome to Akshay's Restaurant **********************************";
		billReceipt += "\n\t\t\tCustomerName: " + customer.name;
		for (ItemOrder io : customer.customerOrder.itemOrders) {
			int itemTotal = io.item.price * io.numberOfPlates;
			sum += itemTotal;
			billReceipt += "\n\t\t\tItem: " + io.item.name() + " ->  " + " Plates : " + io.numberOfPlates + " * Price "
					+ io.item.price + " Total: " + itemTotal;
		}
		billReceipt += "\n\t\t\tTotal Bill: " + sum;
		billReceipt += "\n******************************************************************************************";
		Bill bill = new Bill(++billId, sum, billReceipt);
		customer.setBill(bill);
		billings.put(billId, bill);
	}

	//
	void acceptOrderBill(int billId, PaymentStrategy strategy) {
		Bill bill = billings.get(billId);
		if (bill.isPaid())
			System.out.println("CACHIER: Already paid");
		else {
			strategy.pay(bill.totalAmount);
			System.out.println("CACHIER: Paid " + bill.totalAmount + " paid successfully by customer using "
					+ strategy.getClass().getSimpleName());
			totalAmount += bill.totalAmount;
			bill.setPaid(true);
		}
	}
}
