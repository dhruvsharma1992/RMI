package unit.rmi;

import rmi.RMIException;

public interface Server  {
	
	Number triple(int x) throws RMIException;
	

}
