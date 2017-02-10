package rmi;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

public class RmiInvocationHandler<T> implements InvocationHandler{

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
	        if(method.getName().equals("hashCode"))
	        	return className.hashCode()*hostname.hashCode()*port;
	        
	        if (method.equals(Object.class.getMethod("toString"))) {
                return className.getCanonicalName() + " " + hostname + " " + port;
            }
//        	new Socket(new InetSocketAddress(7000));
	        s = new Socket(hostname, port);
			ObjectOutputStream outToServer = new ObjectOutputStream(s.getOutputStream());
			outToServer.flush();
	        ObjectInputStream inFromServer = new ObjectInputStream(s.getInputStream());
	        Message msg = new Message(method,args);
	        
	        outToServer.writeObject(msg);
	        Object returned = inFromServer.readObject();
	        if(returned instanceof Exception){
	        	s.close();
	        	inFromServer.close();
	        	outToServer.close();
	        	throw (Exception) returned;
	        }
	        else{
	        	s.close();
//	        	outToServer.close();
	        	inFromServer.close();
	        	outToServer.close();
	        	return returned; 
	        	
	        }
			
		}catch(Exception e){
			 if (Arrays.asList(method.getExceptionTypes()).contains(e.getClass())){
                 throw e;
             }
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
