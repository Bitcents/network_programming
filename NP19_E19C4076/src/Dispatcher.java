import java.net.*; // for ServerSocket

public interface Dispatcher {
  public void startDispatching(ServerSocket servSock, Logger logger,
                               ProtocolFactory protoFactory);
}
