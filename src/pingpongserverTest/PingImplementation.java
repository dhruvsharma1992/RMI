package pingpongserverTest;

import rmi.*;

public class PingImplementation implements PingInterface
{
    public String ping(int idNumber) throws RMIException
    {
        return "Pong " + idNumber;
    }
}
