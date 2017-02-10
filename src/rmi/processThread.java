//package rmi;
//
//import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
//import java.lang.reflect.Method;
//import java.net.*;
//
//public class processThread<T> extends Thread {
//    private Skeleton<?> skeleton;
//    private Socket connection;
//    private Class<T> sclass = null;
//
//    public processThread (Skeleton<?> s, Socket cs, Class<T> c) {
//        this.skeleton = s;
//        this.connection = cs;
//        this.sclass = c;
//    }
//
//    public void run() {
//    	ObjectOutputStream out=null;
//    	ObjectInputStream in =null;
//        try {
//            SerializedObject serverMyObject = null;
//            out = new ObjectOutputStream(this.connection.getOutputStream());
//            out.flush();
//            in = new ObjectInputStream(this.connection.getInputStream());
//            Message msg = (Message) in.readObject();
//            String methodName = msg.getMethodName();
//            Class<?>[] parameterTypes = msg.getParameterTypes();
//            Class<?> returnType = msg.getReturnType();
//            Object[] args = msg.getArgs();
//
//            Method serverMethod = null;
//            serverMethod = sclass.getMethod(methodName, parameterTypes);
//            if (serverMethod != null) {
//                    Object serverObject = serverMethod.invoke(this.skeleton.getServer(), args);
//                    out.writeObject(serverObject);
//            } 
//            in.close();
//            out.close();            
//            connection.close();
//        } catch (Throwable e) {
//        	try {
//				connection.close();
//				if(out != null){
//					out.writeObject(e);
//	        		out.close();
//				}
//	        	if(in != null)
//	        		in.close();
//			} catch (IOException e1) {
//				e1.printStackTrace();
//			}
//            
//        }
//    }
//}
