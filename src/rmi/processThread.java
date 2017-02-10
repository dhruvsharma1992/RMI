package rmi;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.*;

public class processThread<T> extends Thread {
    private Skeleton<?> skeleton;
    private Socket connection;
    private Class<T> sclass = null;

    public processThread (Skeleton<?> s, Socket cs, Class<T> c) {
        this.skeleton = s;
        this.connection = cs;
        this.sclass = c;
    }

    public void run() {
        try {
            SerializedObject serverMyObject = null;
            ObjectOutputStream out = new ObjectOutputStream(this.connection.getOutputStream());
            out.flush();
            ObjectInputStream in = new ObjectInputStream(this.connection.getInputStream());
            Message msg = (Message) in.readObject();
            String methodName = msg.getMethodName();
            Class<?>[] parameterTypes = msg.getParameterTypes();
            Class<?> returnType = msg.getReturnType();
            Object[] args = msg.getArgs();

            Method serverMethod = null;
            try {
                serverMethod = sclass.getMethod(methodName, parameterTypes);
            } catch(Exception e) {
                serverMyObject = new SerializedObject(new RMIException(e.getCause()), true);
            }


            if (serverMethod != null) {
                try {
                    Object serverObject = serverMethod.invoke(this.skeleton.getServer(), args);
                    serverMyObject = new SerializedObject(serverObject, false);
                } catch (Throwable e) {
                    serverMyObject = new SerializedObject(e.getCause(), true);
                }
            }
            out.writeObject(serverMyObject);
            connection.close();
        } catch (Throwable e) {
        	try {
				connection.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            this.skeleton.service_error(new RMIException(e.getCause()));
        }
    }
}
