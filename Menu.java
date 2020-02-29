package restaurantsystem;

import java.util.ArrayList;
import java.util.List;

public class Menu {

	List<Item> menu = new ArrayList<Item>();

	public Menu() {
		for (Item i : Item.values()) {
			menu.add(i);
		}
	}
}
