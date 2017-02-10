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

//	InetSocketAddress address ;
	
	String hostname;
	int port;
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable{
		Socket s;
		try{
			s = new Socket(hostname, port);
			System.out.println("hi");
			ObjectOutputStream outToServer = new ObjectOutputStream(s.getOutputStream());
			outToServer.flush();
	        ObjectInputStream inFromServer = new ObjectInputStream(s.getInputStream());
	        Message msg = new Message(method, method.getParameterTypes(),args);
	        
	        outToServer.writeObject(method.getName());
	        outToServer.writeObject(method.getParameterTypes());
	        outToServer.writeObject(method.getReturnType());
	        outToServer.writeObject(args);
	        System.out.println("out");
	        Object returned = inFromServer.readObject();
	        System.out.println("returned " +returned);
	        s.close();
	        return returned;	        
			
		}catch(Exception e){
			throw e;
		}
	}
	
	public RmiInvocationHandler(String hostname, int port)  {
		this.hostname = hostname;
		this.port = port;
		
//		throw new UnknownHostException();
		
	}
	
	

}
