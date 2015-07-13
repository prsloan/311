import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;


public class CS311Project {

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		String filename = "input.txt";
		File file = new File(filename);
		
		Scanner s = new Scanner(file);
		System.out.println(System.getProperty("user.dir"));
		int numberOfStates = 0;
		boolean[] final_states; //an array of the final states, if a state is final, the value at the index will be true
		char[]    alphabet;  //stores the alphabet, the index number will be used for the transitions
		int[][]   DFSAtransitions;  //will store the DFSA transition data for the machine [
		int[][][] NFSAtransitions;  // if it's an NFSA, so it goes to multiple states, it will be used to hold the initial data regardless, 
		ArrayList<String> testStrings =new ArrayList<String>();
		boolean isNFSA =false;  //to track if it is an nfsa or dfsa
		DFSA machine = null;  //the DFSA machine that will be used
		int count = 0;
		
		
		//main loop of the program, this will parse each machine in the file in order, then test the strings and print the output.
		while (s.hasNextLine()){
			String input = null;
			String[]  temp;
			//increment count for machine number
			count++;
			
			//parse number of states
			
			input= s.nextLine();
			//skip empty line until the begining of the next machine
			while (input.equals("")){
				input= s.nextLine();
			}
			numberOfStates = Integer.parseInt(input);
			final_states = new boolean [numberOfStates];
			
			//parse info about final state
			input= s.nextLine();
			temp = input.split(" ");
			for(int i=0;i<temp.length; i++){
				int n = Integer.parseInt(temp[i]);
				final_states[n] = true;
			}
			
			//parse alphabet
			input= s.nextLine();
			temp = input.split(" ");
			alphabet = new char[temp.length];
			for(int i=0; i<temp.length;i++){
				alphabet[i]=temp[i].charAt(0);
			}
			
			//initialize transition array
			DFSAtransitions = new int[numberOfStates][alphabet.length];
			NFSAtransitions = new int[numberOfStates][alphabet.length][];
			input = s.nextLine();
			//parse transistions, if it is an NFSA, the NFSA data will be built and subset construction will be called later on
			// 
			do{	
				temp = input.split("("+" "+ ")");
				if (temp.length >3)
					isNFSA = true;
				int state, symbol;
				temp[0] =temp[0].substring(1, temp[0].length());
				state = Integer.parseInt(temp[0]);
				symbol= Integer.parseInt(temp[1]);
				NFSAtransitions[state][symbol] = new int[temp.length-2];  //make the next array
				//populate next state array
				for (int i=0; i< temp.length-2; i++){
					//trim the end paren
					if(i == temp.length-3)
						temp[2+i] =temp[2+i].substring(0, temp[2+i].length()-1);
					
					NFSAtransitions[state][symbol][i] = Integer.parseInt(temp[2+i]);
				}		
				input = s.nextLine();
			}while(input.charAt(0)=='(');
			
			
			//Test to See if it is a DFSA or NFSA then create the machine
			
			if (!isNFSA){                          //if it is a DFSA
				//put the transitions in the DFSA variable
				for(int i=0;i< NFSAtransitions.length; i++){
					for (int j=0;j<NFSAtransitions[i].length;j++){
						DFSAtransitions[i][j] = NFSAtransitions[i][j][0];
					}
				}
				//make the machine
				machine = new DFSA(final_states, alphabet,DFSAtransitions);
				System.out.println("Finate State Automoton #" +count);
				System.out.println(machine.toString());
				
			}
			else{
		
				NFSA nfsaMachine = new NFSA(final_states, alphabet, NFSAtransitions);
				machine = nfsaMachine.getDFSA();
				System.out.println("Finate State Automoton #" +count);
				System.out.println(machine.toString());
				
			}
			
			//parse test strings
			while(!input.contains(".")){
				testStrings.add(input);
				input = s.nextLine();
			}	
			
			
			System.out.print("5) Strings : ");
			//test the strings and print the output
			for(int i= 0; i< testStrings.size(); i++){
				System.out.print("\n\t" + testStrings.get(i) + "\t\t\t");
				if (machine.testString(testStrings.get(i))){
					System.out.print(" Accept ");
					}
				else
					System.out.print(" Reject ");
				
			}
			//reset test Strings
			testStrings.clear();
			//add some lines for formatting
			System.out.println("\n\n");
			
			
		}// end while loop
	
	
	
	
	
	
	
	
	}

}
