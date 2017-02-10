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
			ObjectOutputStream outToServer = new ObjectOutputStream(s.getOutputStream());
			outToServer.flush();
	        ObjectInputStream inFromServer = new ObjectInputStream(s.getInputStream());
	        Message msg = new Message(method,args);
	        
	        outToServer.writeObject(msg);
//	        outToServer.writeObject(method.getParameterTypes());
//	        outToServer.writeObject(method.getReturnType());
//	        outToServer.writeObject(args);
	        SerializedObject returned = ((SerializedObject)inFromServer.readObject());
	        if(returned.getExceptionStatus()){
	        	s.close();
	        	throw (Exception)returned.getObject();
	        }
	        else{
	        	s.close();
	        	return returned.getObject(); 
	        	
	        }
			
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
