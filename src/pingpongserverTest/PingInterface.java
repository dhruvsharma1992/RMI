package pingpongserverTest;

import rmi.RMIException;

public interface PingInterface
{
    public String ping(int idNumber) throws RMIException;
}
