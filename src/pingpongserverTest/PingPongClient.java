package pingpongserverTest;

import java.net.InetSocketAddress;

import rmi.RMIException;
import rmi.Stub;

public class PingPongClient
{

    public static void main(String[] args)
    {
        System.out.println("Program Started!");
        InetSocketAddress           address;
        boolean                     listening;
        if (args.length != 2) {
            System.out.println("Please provide the Server IP an then the idNumber!");
            return;
        }
        String serverIP = args[0];
        address = new InetSocketAddress(serverIP, 7000);
        FactoryInterface remoteFactory;
        PingInterface stub;
        // Create the stub.
        try
            {
                remoteFactory = Stub.create(FactoryInterface.class, address);
                stub = remoteFactory.makePingServer(serverIP);

            }
        catch(Throwable t)
            {
                System.out.println(t.toString());
                return;
            }
    	
        int correct = 0;
        int failed = 0;
        int total = 4;
        for (int i = 0; i < total; i++){
            try
                {
                    System.out.println(stub.ping(Integer.parseInt(args[1])));
                    correct += 1;
                }
            catch(RMIException e)
                {
                    System.out.println(e.toString());
                    failed += 1;
                }
            catch(Throwable t)
                {
                    System.out.println(t.toString());
                    failed += 1;
                }
        }
        
        System.out.println(total + " Tests Completed, " + correct + " passed, " + failed + " failed");


    }

}
