package rmi;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.lang.IllegalStateException;

public class RmiInvocationHandler implements InvocationHandler{

	InetSocketAddress address ;
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable{
		Socket s;
		try{
			if(address.getAddress() == null)
				s = new Socket(address.getHostName(), address.getPort());
			else
				s = new Socket(address.getAddress(), address.getPort());
			
			ObjectOutputStream outToServer = new ObjectOutputStream(s.getOutputStream());
	        ObjectInputStream inFromServer = new ObjectInputStream(s.getInputStream());
	        Message msg = new Message(method, args);
	        
	        outToServer.writeObject(msg);
	        Object returned = inFromServer.readObject();
	        return returned;	        
			
		}catch(Exception e){
			throw e;
		}
	}
	
	public RmiInvocationHandler(InetSocketAddress address) throws UnknownHostException {
		this.address = address;
		if(address == null || address.getPort() == 0 || (address.getAddress() == null && address.getHostName() == null ))
			throw new IllegalStateException(); 
		throw new UnknownHostException();
		
	}
	
	

}
