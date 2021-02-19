import app.tcpclient.TCPClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class ClientTest {

    @Test
    void justAnExample() throws IOException {
        String msg = "TestMessage";
        String hostname = "java.lab.ssvl.kth.se";
        int port = 7;
        String response = TCPClient.askServer(	"java.lab.ssvl.kth.se",7);

        MockUp mockUp = new MockUp(hostname,port,msg,response);

        System.out.println(result(mockUp));

    }

    private String result(MockUp mockUp){

        return String.format(
                "Connection to : \n" +
                        "   hostname : %s\n" +
                        "   port : %s\n" +
                        "Data sent : %s\n" +
                        "Data received : %s"
                ,mockUp.hostname
                ,mockUp.port
                ,mockUp.msg
                ,mockUp.response);

    }

    private class MockUp{
        String hostname;
        int port;
        String msg;
        String response;

        MockUp(String hostname, int port, String msg, String response){
            this.hostname = hostname;
            this.port = port;
            this.msg = msg;
            this.response = response;
        }
    }
}
