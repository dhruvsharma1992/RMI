package rmi;

import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Method;

public class Message implements Serializable{	
	private static final long serialVersionUID = 51872356804250821L;
	String methodName;
	Object[] args;
	
	
	public Message(Method method, Object[] args){
		this.methodName = method.getName();
		this.args = args; 
	}
}
