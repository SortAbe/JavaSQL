public class Bench{	

	public static void main(String[] args){
		Thread[] threads = new Thread[20];
		for(int i = 0; i < 20; i++) threads[i] = new Thread(new BenchThread(), "Threadsss");
		//Thread thread1 = new Thread(new BenchThread(), "Threading 1");
		for(int j = 0; j < 20; j++) threads[j].start();
		//thread1.start();
	}
}
