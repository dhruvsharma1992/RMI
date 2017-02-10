package unit.rmi;

import java.io.Serializable;

import rmi.RMIException;

public class Number implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1253926727277875085L;
	public int num;
	public Number(int a){
		this.num = a;
	}
	

}
