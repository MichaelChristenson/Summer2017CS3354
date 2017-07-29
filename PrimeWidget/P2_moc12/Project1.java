import java.util.*;
import java.awt.*;
import java.io.PrintWriter;
import java.io.IOException;

public class Project1{
	public static void main(String[] args){
		gui view = new gui();
		/*
		Queue<Integer> collatz = new LinkedList<Integer>();
		int start = 8;
		collatz.add(start);
		String filename = "collatz.txt";
		PrintWriter file = null;
		try{
			file = new PrintWriter(filename);
			for(int i = 0; i < 20; i++){
				int item = collatz.remove();	// remove item from queue for processing
				collatz.add(item * 2);			// all items have doubles
				if(item%3==1){					// not all items can have this transform applied
					collatz.add((item - 1) / 3);
				}
				// Attend to the present item
				file.write(String.valueOf(item));
				file.write("\n");
			}
		}
		catch(IOException e){
			System.out.println(e);
			System.out.println("Failure");
		}
		finally{
			file.flush();
		}
		*/
	}
}
