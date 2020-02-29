package restaurantsystem;

import java.util.ArrayList;
import java.util.List;

public class Order {
	static int orderId;
	int id;
	List<ItemOrder> itemOrders;

	public Order() {
		this.id = ++orderId;
		itemOrders = new ArrayList<ItemOrder>();
	}

	void addItemToOrder(ItemOrder o) {
		this.itemOrders.add(o);
	}

}
