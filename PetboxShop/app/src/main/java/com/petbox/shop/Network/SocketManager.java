package com.petbox.shop.Network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by petbox on 2015-09-21.
 */
public class SocketManager {

    public final static String SERVER_HOST = "211.233.50.208";
    public final static int SERVER_PORT = 80;

    private static Socket socket;

    public static Socket getSocket() throws IOException{
        if(socket == null)
            socket = new Socket();

        if(!socket.isConnected())
            socket.connect(new InetSocketAddress(SERVER_HOST, SERVER_PORT));

        return socket;
    }

    public static void closeSocket() throws IOException{
        if(socket != null)
            socket.close();
    }
}
