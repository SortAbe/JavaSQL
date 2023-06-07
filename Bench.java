//Abrahan Diaz
//Version 0.5
//All the things she said
public class Bench{	

	public static void main(String[] args){
		Thread[] threads = new Thread[20];
		for(int i = 0; i < 20; i++) threads[i] = new Thread(new BenchThread(), "Threadsss");
		for(int j = 0; j < 20; j++) threads[j].start();
	}
}
