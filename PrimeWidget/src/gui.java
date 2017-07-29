import java.util.*;
import java.awt.*;
import java.io.*;
import java.awt.List;
import java.awt.event.*;

public class gui extends Frame implements ItemListener{
	Hashtable<Integer,Integer> table = null;
	FileWriter output = null;
	gui() throws IOException{
		output = new FileWriter("output.txt");
		establish();
		int index = 0;
		setSize(500,125);
		GridLayout grid = new GridLayout(3,3);
		setTitle("Twin Primes");
		setLayout(grid);
		
		Label primePrompt = new Label("# Prime");
		ArrayList<Label> reporter = new ArrayList<Label>();
		for(int i=0;i<3;i++)
			reporter.add(new Label(""));
		Label choice = new Label("");
		Label latter = new Label("");
		TextField primeIn = new TextField();
		Button commit = new Button("Commit");
		commit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				ArrayList<Integer> report = new ArrayList<Integer>();
				for(int i=0;i<3;i++)
					reporter.get(i).setText("");					// Resets reporter from possible error state
				
				commit.setLabel("Processing");
				System.out.print("Committing ");
				try{
					String line = "";
					System.out.println(Integer.parseInt(primeIn.getText()));
					int index = Integer.parseInt(primeIn.getText());			// Read in index value
					report = findPrimes(index);
					line += String.valueOf(index) + " : ";
					for(int i = 0; i < 3; i++){
						reporter.get(i).setText(report.get(i)==-1?"":toString().valueOf(report.get(i)));
						if(report.get(i)!=-1)
							line += reporter.get(i).getText()+" ";
					}
					try{
						output.write(line+"\n");
					}
					catch(IOException err){
						return;
					}
				}
				catch(NumberFormatException error){
					reporter.get(1).setText("Input a Number");
					
				}
				commit.setLabel("Commit");
				
			}
		});
		Button cancel = new Button("Cancel");
		cancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				exit();
			}
		});
		add(primePrompt, 0);				// Assembles all elements
		add(primeIn, 1);
		for(int i = 0; i < 3; i++)
			add(reporter.get(i), i);
		add(new Label(""));
		add(commit);
		add(cancel);
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent windowEvent){
				exit();				// Ensures  proper closure
			}			
		});
		setVisible(true);
	}
	public void itemStateChanged(ItemEvent ie){
		return;
	}
	private ArrayList<Integer> findPrimes(int index){
		// @param index the index of the prime to check 
		// @returns an array of double-primes, or -1 if a double-prime doesn't exist
		ArrayList<Integer> report = new ArrayList<Integer>();
		for(int i = 0; i < 3; i++){
			System.out.println(table.get(index-1+i));
			if((table.get(index - 1 + i) == table.get(index) - 2 + i * 2)
					&&table.get(index - 1 + i)!=null)
				report.add(table.get(index - 1 + i));
			else
				report.add(-1);
		}
		return report;
	}
	private void establish(){
		//@throws FileNotFound if there is no such "primes.csv"
		String line;
		Integer index, value;
		table = new Hashtable<Integer, Integer>(100, (float).75);
		try{
			FileReader primes = new FileReader("primes.csv");
			BufferedReader reader = new BufferedReader(primes);
			while((line = reader.readLine()) != null){
				if(line.charAt(0)==',')
					break;
				for(int i = 0; i < line.length(); i++){
					try{
						if(line.length() > 0 && line.charAt(i)==','){
							index = Integer.parseInt(line.substring(0, i));
							value = Integer.parseInt(line.substring(i + 1));
							System.out.print("Adding ");
							System.out.print(value);
							System.out.print(" at ");
							System.out.println(index);
							table.put(Integer.parseInt(line.substring(0, i)), 
									Integer.parseInt(line.substring(i + 1)));
							break;
						}
					}
					catch(NullPointerException err){
						return;
					}
				}
			}
		}
		catch(IOException ex){
			System.out.println(ex);
			System.exit(0);
		}
	}
	public void exit(){
		try{
			output.close();
		}
		catch(IOException err){}
		System.exit(0);		// Handles end-of-operation procedures
	}
}
