//Abrahan Diaz
//Version 0.5
//All the things she said
public class Bench{	

	public static void main(String[] args){
		int count = Integer.parseInt(args[0]);
		Thread[] threads = new Thread[count];
		BenchThread bt = new BenchThread();
		for(int i = 0; i < count; i++) threads[i] = new Thread(bt, "Threadsss");
		for(int j = 0; j < count; j++) threads[j].start();
		try{
			for(int j = 0; j < count; j++) threads[j].join();
		}catch(InterruptedException e){
			System.out.println("Something went wrong!");
		}
	}
}
