package unit.rmi;

import java.net.InetSocketAddress;

import rmi.RMIException;
import rmi.Skeleton;
import rmi.Stub;

public class Client {
	
	public static void main(String[] args){
		ServerImpl server = new ServerImpl(); 

		InetSocketAddress address = new InetSocketAddress("127.0.0.1", 8001);
		Skeleton<Server> skeleton = new Skeleton<Server>(Server.class,server,address); 
		try {
			skeleton.start();
			Server stub = Stub.create(Server.class, skeleton);
			System.out.println(stub.triple(3).num);
			System.out.println(stub.triple(new Number(5)));
//			System.out.println(server.triple(3));
//			System.out.println(server.triple(3));
//			System.out.println(server.triple(3));
			skeleton.stop();
		} catch (RMIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}

}
