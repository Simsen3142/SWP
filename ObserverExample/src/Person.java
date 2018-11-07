
public class Person implements IObserver{

	@Override
	public void actionDone() {
		System.out.println("Person sagt zu kalt");
	}

}
