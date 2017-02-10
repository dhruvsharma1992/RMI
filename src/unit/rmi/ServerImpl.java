package unit.rmi;

import rmi.RMIException;

public class ServerImpl implements Server {
	
	public Number triple(int x) throws RMIException{
		return new Number(3*x);
	}

	@Override
	public int triple(Number x) throws RMIException {
		// TODO Auto-generated method stub
		return x.num*3;
	}
	

}
