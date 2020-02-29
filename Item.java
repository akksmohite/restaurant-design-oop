package restaurantsystem;

public enum Item {

	DALKHICHDI(100), PANEERMASALA(150), ICECREAM(100), POHE(20), SHIRA(30), RICE(100), RICEPLATE(50),
	CHICKENMASALA(300);

	int price;

	private Item(int price) {
		this.price = price;
	}

}
