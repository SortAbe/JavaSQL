public class Bench{	

	public static void main(String[] args){
		Thread thread1 = new Thread(new BenchThread(), "Threading 1");
		Thread thread2 = new Thread(new BenchThread(), "Threading 1");
		Thread thread3 = new Thread(new BenchThread(), "Threading 1");
		Thread thread4 = new Thread(new BenchThread(), "Threading 1");

		thread1.start();
		thread2.start();
		thread3.start();
		thread4.start();
	}
}
