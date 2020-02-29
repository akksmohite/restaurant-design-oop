package restaurantsystem;

public interface Subject {

	void notifyObservers();

	void register(Observer o);

	void unRegister(Observer o);

}

interface TableSubject extends Subject {
	Customer update(Observer o);

	void customerDone(Observer o);

}

interface CookSubject extends Subject {

	void update(Observer o);
}