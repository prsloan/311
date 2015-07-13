
import java.util.ArrayList;
import java.util.HashSet;


public class NFSA {

	boolean[]  finalStates;
	boolean[]  dfsaFinalStates;
	char[] alphabet;
	int[][][] transitions;
	int[][] dfsaTransitions;
	int numStates;
	DFSA equivalent ;
	ArrayList<HashSet<Integer>> dfsaStates = new ArrayList<HashSet<Integer>>();
	
	public NFSA(boolean[] inputFinalStates, char[] inputAlphabet, int[][][] inputTransitions){
		finalStates = inputFinalStates;
		alphabet = inputAlphabet;
		transitions = inputTransitions;
		numStates = transitions.length;
		//initialize dfsaStates
		HashSet<Integer> temp = new HashSet<Integer>();
		temp.add(0);
		dfsaStates.add(temp);
		equivalent = this.subSetConstruction();
	}

	
	public DFSA getDFSA(){
		return equivalent;
	}
		
	private DFSA subSetConstruction() {
		int dfsaNumStates = 0;
		int dfsaCurrentState = 0;
		HashSet<Integer> nextState;
		ArrayList<Integer[]> tranTable = new ArrayList<Integer[]>();
		
		while (dfsaCurrentState <= dfsaNumStates){
			
			for (int i=0; i< alphabet.length;i++){
				nextState = this.nextState(dfsaCurrentState, i);
				if (dfsaStates.contains(nextState)){
					int index = dfsaStates.indexOf(nextState);
					Integer[] temp = {dfsaCurrentState, i, index};
					tranTable.add(temp);
				}
				else{
					dfsaNumStates++;
					dfsaStates.add(nextState);
					Integer[] temp = {dfsaCurrentState, i, dfsaNumStates};
					tranTable.add(temp);
				}
			}
			dfsaCurrentState++;
		}//end while
		
		//now to make the dfsa	
		populateDFSAFinalStates();
		//populate DFSA transition array with values from Tran Table
		populateDFSATransitions(tranTable);
		//the alphabet is the same so return the DFSA
		return new DFSA(dfsaFinalStates, alphabet, dfsaTransitions);
		
		
	}

	private void populateDFSATransitions(ArrayList<Integer[]> tranTable) {
		dfsaTransitions = new int[dfsaStates.size()][alphabet.length];
		Integer[]  temp;
		//traverse the table
		for (int i= 0; i< tranTable.size();i++){
			temp = tranTable.get(i);
			dfsaTransitions[temp[0]][temp[1]] = temp [2]; 
		}
		
	}

	private void populateDFSAFinalStates() {
		dfsaFinalStates = new boolean[dfsaStates.size()];
		Integer[] temp;
		
		//iterate through each dfsaState and cross check if an nfsa final state is contained
		for (int i = 0; i<dfsaFinalStates.length;i++){
			temp = new Integer[dfsaStates.get(i).size()];
			temp = dfsaStates.get(i).toArray(temp);
			//iterate through temp
			for (int j = 0; j<temp.length;j++){
				if(finalStates[temp[j]]){
				dfsaFinalStates[i] = true;	
				}
			}
		}
		
	}

	private HashSet<Integer> nextState(int dfsaCurrentState, int symbol) {
		HashSet<Integer> states = new HashSet<Integer>();
		Integer[] nextStates;
		Integer[] nfsaStates = new Integer[dfsaStates.get(dfsaCurrentState).size()];
		nfsaStates	= dfsaStates.get(dfsaCurrentState).toArray(nfsaStates);
		
		for (int i = 0; i< nfsaStates.length;i++){
			if ( transitions[nfsaStates[i]][symbol]!=null){
				for (int j =0; j< transitions[nfsaStates[i]][symbol].length ; j++){
					states.add(transitions[nfsaStates[i]][symbol][j]);
				}
			}
		}
		nextStates = new Integer[states.size()];
		nextStates = states.toArray(nextStates);
		
		return states;
	}
	
	
	
}
