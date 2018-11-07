
public class Main {
	public static void main(String[] args) {
		Heizung heizung=new Heizung();
		
		Person person=new Person();
		Staat staat=new Staat();
		
		heizung.addObserver(person);
		heizung.addObserver(staat);
		
		heizung.notifyObservers();
	}
}
