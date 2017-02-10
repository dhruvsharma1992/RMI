package rmi;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Objects;
import java.lang.IllegalStateException;

public class RmiInvocationHandler<T> implements InvocationHandler, Serializable{

/**
	 * 
	 */
	private static final long serialVersionUID = -7948319869802020963L;
	//	InetSocketAddress address ;
	public Class<T> className;
	public String hostname;
	public int port;
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable{
		Socket s;
		try{
			
	        if (method.equals(Object.class.getMethod("equals", Object.class))) {
                if (args[0] instanceof Proxy) {
                	RmiInvocationHandler handler = (RmiInvocationHandler) Proxy.getInvocationHandler((Proxy) args[0]);
                    return (className.equals(handler.className) && hostname.equals(handler.hostname) && handler.port == port);
                }
                return false;
                // alternately can match tostrings of both
            }
	        if(method.equals(Object.class.getMethod("hashCode")))
	        	return className.hashCode()*hostname.hashCode()*port;
	        
	        if (method.equals(Object.class.getMethod("toString"))) {
                return className.getCanonicalName() + " " + hostname + " " + port;
            }
//        	new Socket(new InetSocketAddress(7000));
	        s = new Socket(hostname, port);
			ObjectOutputStream outToServer = new ObjectOutputStream(s.getOutputStream());
			outToServer.flush();
	        ObjectInputStream inFromServer = new ObjectInputStream(s.getInputStream());
	        Message msg = new Message(className,method,args);
	        
	        outToServer.writeObject(msg);
	        Object returned = null;
	        
	        String message = null;
	        try
	        {
	        	message = (String) inFromServer.readObject();
	        	returned = inFromServer.readObject();

//	        	System.out.println(message + " " + returned); 
	        }catch(EOFException e){}
//	        }
	        if(message != null){
	        	if(message.equals("RETURN")){
	        		inFromServer.close();
    	        	outToServer.close();
    	        	s.close();
    	        	return returned;
	        	}
	        	else if(message.equals("EXCEPTION")){
	    	        	inFromServer.close();
	    	        	outToServer.close();
	    	        	s.close();
	    	        	throw (Exception) returned;
	        	}
	        	
	        }
	        
	        return null;
	        
			
		}catch(Exception e){
			 if (Arrays.asList(method.getExceptionTypes()).contains(e.getClass())){
//				 System.out.println("e");
                 throw e;
             }
//			 System.out.println("rmi");
             throw new RMIException(e);
//			throw new RMIException(e.getMessage());
		}
	}
	
	public RmiInvocationHandler(Class c,String hostname, int port)  {
		this.className = c;
		this.hostname = hostname;
		this.port = port;
		
//		throw new UnknownHostException();
		
	}
	
	

}
