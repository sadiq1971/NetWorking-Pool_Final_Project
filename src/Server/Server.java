
package Server;

import util.*;

import java.net.ServerSocket;
import java.net.Socket;


/**
 * Created by Sadiqur Rahman on 5/28/2016.
 */
public class Server implements Runnable {

    String id;
    String opid;
    ServerSocket servSocket;
    Thread t;
    Controller server;
    Controller client;
    String s;
    Turn turn;
    StartGame startGame;
    public static   NetworkUtil nc;

    Server(Controller client, Controller server, Turn turn,StartGame str) {
        s = new String();
        this.server = server;
        this.client = client;
        this.turn=turn;
        t = new Thread(this);
        t.start();
        startGame=str;
    }

    @Override
    public void run() {

        try {
                servSocket = new ServerSocket(33333);
                Socket clientSock = servSocket.accept();
                nc = new NetworkUtil(clientSock);
            while(id==null) {
                id = startGame.getUserid();
                nc.write(id);
            }
            while(opid==null){
                opid=(String)nc.read();
                startGame.setOpImage(opid);
            }
                new ReadThread(nc, client,turn);
                new WriteThread(nc, "Server", server);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}


