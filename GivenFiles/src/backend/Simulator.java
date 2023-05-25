package backend;


import windowInterface.MyInterface;
import backend.Cell;
//TODO : add imports you will need here
/*
 *  Note : if you use an import in another class you will need to add
 *  the import lines on top of the file of the other class.
 */
// Examples of useful imports :
// import java.util.LinkedList;
// import java.util.ArrayList;
// import java.util.Random;

/*/!\ A MODIFIER / PEUT ETRE A MODIFIER
 * Row = y et column = x!!!!
 * la classe cell : mettre les methodes
 *  pour changer son etat(togglecell), get son etat (getcell) dans la classe
 */

public class Simulator extends Thread {

	private MyInterface mjf;
	private boolean stopFlag;
	private boolean pauseFlag;
	private int loopDelay;
	private int width;
	private int height;
	private int x;
	private int y;
	private int cellState[][];
	private int neighbor;
	//TODO : add declaration of additional attributes here

	public Simulator(MyInterface mjfParam) {
		mjf = mjfParam;
		stopFlag=false;
		pauseFlag=false;
		loopDelay = 150;
		//TODO : add other attribute initialization here

	}
	
	/**
	 * getter of the width of the simulated world
	 * @return the number of columns in the grid composing the simulated world
	 */
	public int getWidth() {
		//TODO : correct return
		width = 50;
		return width;
	}

	/**
	 * getter of the height of the simulated world
	 * @return the number of rows in the grid composing the simulated world
	 */
	public int getHeight() {
		//TODO : correct return
		height = 50;
		return height;
	}
	
	public void run() {
		//WARNING : Do not modify this.
		/*Exception : 
		 *if everything you have to do works and you have a backup... 
		 *have fun editing this if you want to enhance it!
		 *But be sure to take into account that this class inherits from Thread
		 *You might want to check documentation online about the Thread class
		 *But do not hesitate to email me any questions 
		*/
		int stepCount=0;
		while(!stopFlag) {
			stepCount++;
			makeStep();
			mjf.update(stepCount);
			try {
				Thread.sleep(loopDelay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			while(pauseFlag && !stopFlag) {
				try {
					Thread.sleep(loopDelay);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}

	/**
	 * Individual step of the Simulation, modifying the world from
	 * its state at time t to its state at time t+1
	 */
	public int nbOfNeighbors(int x, int y) {
	// Calcul the number of neighbors of the cell
		int i = x;
		int j = y;
		for(i = i - 1; i <= i + 1; i++) {
			for(j = j - 1; j <= j + 1; j++) {
				if(getCell(i, j) == 1) {
					neighbor = neighbor + 1;
				}
			}
		}
		neighbor -= getCell(x, y);
		return neighbor;
	}
	
	public void makeStep() {
		//TODO : fill in for Simulator behavior.
		/*
		 * Do not Hesitate to write other (private) methods in this class 
		 * to use them here,
		 * or other classes from which you might use instances here, 
		 * be it as variables or as attributes you may add to the class Simulator,
		 * by using their (public) methods.
		 */
		for(x = 0; x < height; x++) {
			for(y = 0; y < width; y++) {
				/* FAIRE UNE METHOD POUR CHAQUE FONCTION
				 * 'if' pour savoir cellule est morte ou vivante
				 * vivante :  
				 * if 3 voisins -> reste vivante, sinon meurt
				 * morte :
				 * if pour exactly 3 voisins -> devient vivante sinon reste dead
				 */
				//creer un objet de la classe
				
				//Cell CellX = new Cell();
				if(getCell(x, y) == 0) {
					if(nbOfNeighbors(x, y) == 3) {
						toggleCell(y, x);
					}
				}else if(getCell(x, y) == 1) {
					if(nbOfNeighbors(x, y)<2 || nbOfNeighbors(x, y)>3) {
						toggleCell(y, x);
					}
				}
			}
		}
		
		
	}

	/**
	 * Stops simulation by raising the stop flag used in the run method
	 */
	public void stopSimu() {
		//TODO : set stopFlag to true
		stopFlag = true;
		
	}

	/**
	 * Toggles Pause of simulation 
	 * by raising or lowering the pause flag used in the run method
	 */
	public void togglePause() {
		//TODO : change value of boolean attribute pauseFlag
		// from false to true, or from true to false
		if(stopFlag == true) {
			stopFlag = false;
		}else {
			stopFlag = true;
		}
	}
	/**
	 * Changes content value of the Cell at the coordinates specified in arguments
	 * 
	 * 
	 * @param x coordinate on the x-axis (horizontal)
	 * @param y coordinate on the y-axis (vertical)
	 */
	public void toggleCell(int x, int y) { //pareil que setCell sauf que tu peux pas 
		//choisir quelle est el nouvel etat, et en plus est utilisé dans l'interface
		
		//TODO : change the value of the cell at coordinates (x,y)
		/*
		 * Note : the value of the cell is NOT a boolean, it is an integer.
		 * O means dead, 1 means alive...
		 * But the GUI can also print properly more values than that.
		 * You might want to use this for the going further section.
		 */
		if(cellState[x][y] == 1) {
			cellState[x][y] = 0;
		}else if (cellState[x][y] == 0){
			cellState[x][y] = 1;
		}
		
	}
	/**
	 * get the value of a cell at coordinates
	 * @param x coordinate
	 * @param y coordinate
	 * @return the value of the cell
	 */
	public int getCell(int x, int y) { //donne l'etat d'une cellule (morte ou vivante)
		//TODO implement proper return
		
		return cellState[x][y];
	}

	/**
	 * set the value of a cell at coordinates
	 * @param x coordinate
	 * @param y coordinate
	 * @param val the value to set inside the cell
	 */
	public void setCell(int x, int y, int val) { //permet de changer l'etat d'une cellule
		//TODO implement
		cellState[x][y] = val;
	}

	/**
	 * Each String in the returned array represents a [row/column]
	 * 
	 * @return an array of Strings representing the simulated world's state
	 */
	public String[] getFileRepresentation() { 
		// Method which create a csv file from the actual grid with all pixels
		//TODO : implement
		return new String[0];
	}
	/**
	 * Populates a [row/column] indicated by the given coordinate
	 * using its String representation
	 * 
	 * @param y the y coordinate of the row/column to populate
	 * @param fileLine the String line representing the row
	 */
	public void populateLine(int coord, String fileLine) {
		// Method which create a grid from a csv file with all pixels
		//TODO : implement and correct the comment
		// As you have to choose row OR column depending on your implementation
	}
	
	/**
	 * populates world with randomly living cells
	 * 
	 * @param chanceOfLife the probability, expressed between 0 and 1, 
	 * that any given cell will be living
	 */
	public void generateRandom(float chanceOfLife) {
		//TODO implement
	}
	
	/**
	 * Checks if the borders are looping
	 * 
	 * @return true if the borders are looping, false otherwise
	 */
	public boolean isLoopingBorder() {
		//TODO implement correct return
		return false;
	}
	
	/**
	 * Toggle the looping of borders, activating or deactivating it
	 * depending on the present state
	 */
	public void toggleLoopingBorder() {
		//TODO implement
	}
	
	/**
	 * Setter for the delay between steps of the simulation
	 * @param delay in milliseconds
	 */
	public void setLoopDelay(int delay) {
		//TODO : implement
	}
}
