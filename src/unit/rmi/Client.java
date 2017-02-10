package unit.rmi;

import java.net.InetSocketAddress;

import conformance.rmi.TestInterface;
import rmi.RMIException;
import rmi.Skeleton;
import rmi.Stub;

public class Client {
	
	public static void main(String[] args){
		ServerImpl server = new ServerImpl(); 

		InetSocketAddress address = new InetSocketAddress("127.0.0.1", 8000);
		Skeleton<Server> skeleton = new Skeleton<Server>(Server.class,server,address); 
		try {
			skeleton.start();
			Server stub = Stub.create(Server.class, skeleton);
			Server stub2 = Stub.create(Server.class, new InetSocketAddress(80));
			System.out.println(stub.triple(3).num);
			System.out.println(stub.triple(new Number(5)));
			System.out.println(stub.equals(null));
			System.out.println(stub.hashCode());
			System.out.println(stub2.hashCode());
//			System.out.println(server.triple(3));
//			System.out.println(server.triple(3));
//			System.out.println(server.triple(3));
			skeleton.stop();
			System.out.println("hi");
		} catch (RMIException e) {
			skeleton.stop();
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Throwable e) {
			skeleton.stop();
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}

}
