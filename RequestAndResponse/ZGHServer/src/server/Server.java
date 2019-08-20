package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public void startServer(){
        System.out.println("====启动服务器====");
        try {
            ServerSocket server = new ServerSocket(8888);
            while(true){
                Socket socket = server.accept();
                new ServerHandler(socket).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
