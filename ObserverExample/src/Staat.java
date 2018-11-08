
public class Staat implements IObserver{

	@Override
	public void actionDone(Subject subject) {
		Heizung hzng=(Heizung)subject;
		System.out.println("Staat sagt zu viel Heizkosten");
	}
	
}
