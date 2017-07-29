import java.util.*;
import java.awt.*;
import java.awt.List;
import java.awt.event.*;
import java.io.IOException;
import java.io.PrintWriter;

public class gui extends Frame implements ItemListener{
	CheckboxGroup orient = null;
	boolean scend;
	gui(){
		scend = true;
		int index = 0;
		setSize(500,125);
		orient = new CheckboxGroup();
		List limiter = new List(4, true);
		limiter.add("5");
		limiter.add("10");
		limiter.add("50");
		limiter.add("100");
		
		Checkbox ascend = new Checkbox("Ascend", orient, true);
		Checkbox descend = new Checkbox("Descend", orient, false);
		
		Label seedPrompt = new Label("Seed Value");
		Label maxPrompt = new Label("Maximum");
		Label reporter = new Label("");
		TextField seedIn = new TextField();
		Button commit = new Button("Commit");
		commit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				reporter.setText("");					// Resets reporter from possible error state
				commit.setLabel("Processing");
				try{
					System.out.print("Committing ");
					System.out.println(Integer.parseInt(seedIn.getText()));
					int seed = Integer.parseInt(seedIn.getText());			// Read in seed value
					int max = Integer.parseInt(limiter.getSelectedItem());	// Read in max iterations
					if(scend){
						ascensionTree(seed, max);
					}
					else{
						descentTree(seed, max);
					}
					commit.setLabel("Commit");
				}
				catch(NumberFormatException er)					// Catches user inputting multiple values
				{
					reporter.setText("Select one value");		// Report error in UI
				}
			}
				
		});
		Button cancel = new Button("Cancel");
		cancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				exit();
			}
		});
		add(ascend);
		add(seedPrompt);
		ascend.addItemListener(this);
		descend.addItemListener(this);
		add(maxPrompt);
		add(descend);				// Assembles all elements
		add(seedIn);
		add(limiter);
		add(commit);
		add(cancel);
		add(reporter);
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent windowEvent){
				exit();				// Ensures  proper closure
			}			
		});
		GridLayout grid = new GridLayout(3,2);
		setTitle("Collatz");
		setLayout(grid);
		setVisible(true);
	}
	public void itemStateChanged(ItemEvent ie){
		Checkbox selected = orient.getSelectedCheckbox();
		scend = true;
		if(selected.getLabel().charAt(0)=='D'){
			scend = false;
		}
		System.out.println(scend?"Ascend":"Descend");
	}
	public void descentTree(int seed, int max){
		PrintWriter file = null;
		try{
			file = new PrintWriter("collatz.csv");

			Vector<Integer> cont = new Vector<Integer>();
			cont.addElement(seed);
			for(int i = 0; i < max; i++){
				int item = cont.get(i);	// remove item from queue for processing
				file.write(String.valueOf(item));
				file.write(",\n");
				System.out.println(item);
				if(item==1){
					break;
				}
				if(item%2==0){					// Determine direction of tree
					cont.addElement(item / 2);
				}
				else {
					cont.addElement(item * 3 + 1);
				}
			}
		}
		catch(IOException e){
			System.out.println(e);
		}
		finally{
			file.flush();
		}
		return;
	}
	public void ascensionTree(int seed, int max){
		PrintWriter file = null;
		try{
			file = new PrintWriter("collatz.csv");
			
			Vector<Integer> cont = new Vector<Integer>();
			cont.addElement(seed);
			String csv = String.valueOf(seed)+",\n";
			for(int i = 0; i < max; i++){
				int item = cont.get(0);	// remove item from queue for processing
				cont.addElement(item * 2);			// all items have doubles
				String line = String.valueOf(item * 2) + ", ";
				if(item%3==1 && ((item - 1)/3)%2==1){					// not all items can have this transform applied
					cont.addElement((item - 1) / 3);
					line = line + String.valueOf((item - 1) / 3) + ", ";
				}
				cont.remove(0);
				Collections.sort(cont);
				line = line + "\n";
				csv = csv + line;
				// Attend to the present item
				System.out.println(item);
			}
			file.write(csv);
		}
		catch(IOException e){
			System.out.println(e);
		}
		finally{
			file.flush();
		}
		return;
	}
	public void exit(){
		System.exit(0);		// Handles end-of-operation procedures
	}
}
