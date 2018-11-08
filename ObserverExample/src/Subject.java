import java.util.ArrayList;

public abstract class Subject {
	protected ArrayList<IObserver> observers=new ArrayList<IObserver>();
	
	protected void notifyObservers() {
		for(IObserver observer:observers) {
			observer.actionDone(this);
		}
	}
	
	public void addObserver(IObserver observer) {
		observers.add(observer);
	}
	
	public void removeObserver(IObserver observer) {
		observers.remove(observer);
	}
}
