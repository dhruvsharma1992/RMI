package rmi;

import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Method;

public class Message implements Serializable{	
	

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public Class<?>[] getParameterTypes() {
		return parameterTypes;
	}

	public void setParameterTypes(Class<?>[] parameterTypes) {
		this.parameterTypes = parameterTypes;
	}

	public Class<?> getReturnType() {
		return returnType;
	}

	public void setReturnType(Class<?> returnType) {
		this.returnType = returnType;
	}

	public Object[] getArgs() {
		return args;
	}

	public void setArgs(Object[] args) {
		this.args = args;
	}


	private static final long serialVersionUID = 51872356804250821L;
	String methodName;
	Class<?>[] parameterTypes;
	Class<?> returnType;
	Object[] args;
	
	
	public Message(Method method, Object[] args){
		this.methodName = method.getName();
		this.parameterTypes = method.getParameterTypes();
		this.returnType = method.getReturnType();
		this.args = args; 
	}
}
