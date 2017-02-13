package pingpongserverTest;

import rmi.*;
import java.net.*;

public class PingServerFactory implements FactoryInterface
{
    public static void main(String[] args) {
        PingServerFactory factory = new PingServerFactory();
        InetSocketAddress            address;
        Skeleton<FactoryInterface>     skeleton;

        address = new InetSocketAddress(7000);
        skeleton = new Skeleton<FactoryInterface>(FactoryInterface.class, factory, address);

        try {
            skeleton.start();
        } catch (Throwable t) {
            System.out.println("skeleton start failed");
        }
    }

    public PingInterface makePingServer(String serverIP) throws RMIException
    {
        InetSocketAddress address = new InetSocketAddress(7100);
        PingImplementation server = new PingImplementation();
        Skeleton<PingInterface> skeleton1 = new Skeleton(PingInterface.class, server, address);
        skeleton1.start();
        PingInterface remote_server = Stub.create(PingInterface.class, skeleton1, serverIP);
        return remote_server;
    }
}
