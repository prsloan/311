
public class DFSA {

	boolean[]  finalStates;
	char[] alphabet;
	int[][] transitions;
	int numStates;
	
	
	/**
	 * Constructor method will populate the variable for the DFSA
	 * 
	 * 
	 * @param inputFinalStates
	 * @param inputAlphabet
	 * @param inputTransitions
	 */
	public DFSA(boolean[] inputFinalStates, char[] inputAlphabet, int[][] inputTransitions){
		finalStates = inputFinalStates;
		alphabet = inputAlphabet;
		transitions = inputTransitions;
		numStates = transitions.length;
	}
	
	
	/**
	 * This method will test if a particular string will be accepted by 
	 * this machine.  If it is accepted, then the method will return true.
	 * @param input
	 * @return
	 */
	public boolean testString(String input){
		int state = 0; //set state to initial state
		char symbol;
		
		//loop for the machine for the length of the input string
		//at the end of the loop the state variable will be in it's final 
		//value. 
		for(int i=0; i< input.length();i++){
			symbol = input.charAt(i);
			if (isInAlphabet(symbol)){
				//update state
				state = this.transitions[state][alphabetIndex(symbol)];
				//if the state equals the length of transistions, then it is a trap state
				//so the string is rejected
				if (state == transitions.length){
					return false;
				}		
			}
			else if(symbol!=' ')//symbol is not in the alphabet, so reject
				return false;
		}
			
		//check if the current state is final
		if (finalStates[state])
			return true;
		else
			return false;
			
		}//end testString
		
	
	
	
	
	/**
	 * Returns the index of the symbol in the alphabet
	 * 
	 * @param symbol
	 * @return
	 */
	private int	alphabetIndex (char symbol){
		
		for(int i=0;i<alphabet.length;i++){
			if (symbol == alphabet[i]){
				return i;
			}
		}	
			return -1;
		
	}
	
	
	
	
	/**
	 * This method will return true if the symbol is in the alphabet for 
	 * this machine
	 * 
	 * @param symbol
	 * @return
	 */
	private boolean isInAlphabet(char symbol) {
		boolean isInAlphabet = false;
		
		for(int i=0;i<alphabet.length;i++){
			if (symbol == alphabet[i]){
				isInAlphabet = true;
			}
		}
		
		
		return isInAlphabet;
	}

	public String toString(){
		return  "1) Number of States : " + numStates + 
				"\n2) List of Final States: " + this.printFinalStates() +
				"\n3) Alphabet : " + this.printAlphabet() +
				"\n4) Transitions: \n" + this.printTransitions();
				
	}


	private String printTransitions() {
		StringBuilder message = new StringBuilder();
		
		for(int i=0; i<transitions.length;i++){
			for(int j = 0; j< transitions[i].length;j++){
				message.append("\t" + i + " " + j + " " + transitions[i][j] + "\n");
			}
		}
		//remove last \n for formatting
		message.deleteCharAt(message.length()-1);
		return message.toString();
	}


	private String printAlphabet() {
		StringBuilder message = new StringBuilder();
		
		for(int i=0; i<alphabet.length;i++){
			message.append(alphabet[i] + ", ");
		}
		//remove extraneous comma at the end
				message.deleteCharAt(message.length()-2);
		return message.toString();
	}


	/**
	 * returns a formatted string that is a list of the final states
	 * of this machine.
	 * 
	 * @return
	 */
	private String printFinalStates() {
		StringBuilder message = new StringBuilder();
		//add all the final strings to the stringbuilder
		for (int i=0; i< numStates; i++){
			if (finalStates[i]){
				message.append(i +", ");
			}
		}
		//remove extraneous comma at the end
		message.deleteCharAt(message.length()-2);
		
		return message.toString();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
