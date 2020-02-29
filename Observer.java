package restaurantsystem;

public interface Observer {
	void setSubject(Subject subject);
}

interface TableObserver extends Observer {
	void getTableUpdate();
}

interface CookObserver extends Observer {
	void getCookingUpdate();
}
