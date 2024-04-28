package echo;

import java.util.*;
import java.io.*;
import java.net.*;

public class Server {

    protected ServerSocket mySocket;
    protected int myPort;
    public static boolean DEBUG = true;
    protected Class<?> handlerType;

    public Server(int port, String handlerTypeName) {
        try {
            myPort = port;
            mySocket = new ServerSocket(myPort);
            this.handlerType = Class.forName(handlerTypeName);
        } catch(Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        } // catch
    }


    public void listen() {
        while(true) {
            try {
                System.out.println("Server listening at port " + myPort);
                Socket clientSocket = mySocket.accept();
                System.out.println("Accepted connection from " + clientSocket.getInetAddress());
                RequestHandler handler = makeHandler(clientSocket)  ;
                Thread handlerThread = new Thread(handler);
                handlerThread.start();


            } catch (Exception e) {
                System.err.println(e);
            }
        }
//        while(true) {
//
//            // accept a connection
//            // make handler
//            // start handler in its own thread
//        } // while
    }

    public RequestHandler makeHandler(Socket s) {
        try {
            RequestHandler handler = (RequestHandler) handlerType.getDeclaredConstructor().newInstance();
            handler.setSocket(s);
            return handler;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }



    public static void main(String[] args) {
        int port = 5555;
        String service = "echo.RequestHandler";
        if (1 <= args.length) {
            service = args[0];
        }
        if (2 <= args.length) {
            port = Integer.parseInt(args[1]);
        }
        Server server = new Server(port, service);
        server.listen();
    }
}
