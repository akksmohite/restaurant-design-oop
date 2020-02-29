package restaurantsystem;

public class ClientCode1 {

	public static void main(String[] args) {

		Restaurant restuarant = Restaurant.getRestaurant();

		for (int i = 1; i <= 14; i++) {
			restuarant.addCustomer("" + i, 5);
		}
		System.out.println("----------------------");
		System.out.println("Empty table Ids " + restuarant.getEmptyTableIds());
		System.out.println("Waiting customer Names : " + restuarant.getWaitingCustomers());

		for (int i = 0; i < 10; i++) {
			restuarant.releaseTableByIndex(i);
		}
		System.out.println("+++++++++++++++++++++++");
		System.out.println("Empty table Ids " + restuarant.getEmptyTableIds());
		System.out.println("Waiting customer Names : " + restuarant.getWaitingCustomers());
		for (int i = 20; i <= 25; i++) {
			restuarant.addCustomer("" + i, 2);
		}
		for (int i = 26; i <= 30; i++) {
			restuarant.addCustomer("" + i, 4);
		}
		for (int i = 0; i < 4; i++) {
			restuarant.releaseTableByIndex(i);
		}
		
		System.out.println("@@@@@@@@@@@@@@@@@@");
		System.out.println("Empty table Ids " + restuarant.getEmptyTableIds());
		System.out.println("Waiting customer Names : " + restuarant.getWaitingCustomers());
		restuarant.closeRestaurant();
	}

}
