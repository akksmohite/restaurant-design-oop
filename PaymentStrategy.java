package restaurantsystem;

public interface PaymentStrategy {

	void pay(int amount);
}

class CardStrategy implements PaymentStrategy {

	int cardId;
	String cardOtp;

	CardStrategy(int cardId, String otp) {
		this.cardId = cardId;
		this.cardOtp = otp;
	}

	@Override
	public void pay(int amount) {
		System.out.println("Paid " + amount + " using card");
	}
}

class CashStrategy implements PaymentStrategy {

	@Override
	public void pay(int amount) {
		System.out.println("Paid " + amount + " using cash");
	}
}
