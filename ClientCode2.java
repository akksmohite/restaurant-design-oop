package restaurantsystem;

import java.util.Scanner;

public class ClientCode2 {

	public static void main(String[] args) {

		Restaurant restaurant = Restaurant.getRestaurant();

		System.out.println("\t\t\tWelcome");
		Scanner sc = new Scanner(System.in);
		System.out.println("\nPlease enter your name");
		String name = sc.nextLine();
		// party number is number of total people want to sit together
		// customer is the one who books table and orders food
		System.out.println("\nPlease enter party number");
		int partyNumber = sc.nextInt();
		restaurant.addCustomer(name, partyNumber);

	}

}
