package pingpongserverTest;

import rmi.RMIException;

public interface FactoryInterface{
    public PingInterface makePingServer(String serverIP) throws RMIException;
}
