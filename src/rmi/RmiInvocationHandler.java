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
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
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
			s = new Socket(hostname, port);
			ObjectOutputStream outToServer = new ObjectOutputStream(s.getOutputStream());
			outToServer.flush();
	        ObjectInputStream inFromServer = new ObjectInputStream(s.getInputStream());
	        Message msg = new Message(method,args);
	        if (method.equals(Object.class.getMethod("equals", Object.class))) {
                if (args[0] instanceof Proxy) {
                	RmiInvocationHandler handler = (RmiInvocationHandler) Proxy.getInvocationHandler((Proxy) args[0]);
                    return (className.equals(handler.className) && hostname.equals(handler.hostname) && handler.port == port);
                }
                return false;
                // alternately can match tostrings of both
            }
	        if(method.getName().equals("hashCode"))
	        	return Objects.hashCode(proxy);
	        
	        if (method.equals(Object.class.getMethod("toString"))) {
                return className.getCanonicalName() + " " + hostname + " " + port;
            }
	        	
	        
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
	
	public RmiInvocationHandler(Class c,String hostname, int port)  {
		this.className = c;
		this.hostname = hostname;
		this.port = port;
		
//		throw new UnknownHostException();
		
	}
	
	

}
