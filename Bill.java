package restaurantsystem;

public class Bill {

	int billId;
	int totalAmount;
	String billReceipt;
	boolean isPaid;

	public Bill(int billId, int totalAmount, String billReceipt) {
		super();
		this.billId = billId;
		this.totalAmount = totalAmount;
		this.billReceipt = billReceipt;
	}

	public boolean isPaid() {
		return isPaid;
	}

	public void setPaid(boolean isPaid) {
		this.isPaid = isPaid;
	}

}
