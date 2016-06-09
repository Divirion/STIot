package com.example.prose.stiot.Network;

import java.io.OutputStream;
import java.net.Socket;
import java.net.ServerSocket;
public class ServeurSimple
{
    static final int PORT_DU_SERVEUR = 12345;

    public static void main (String[] args)
            throws Exception
    {
        ServerSocket socket_ecoute = new ServerSocket (PORT_DU_SERVEUR);

        Socket socket_dialogue = socket_ecoute.accept();
        socket_ecoute.close();
        String donnee = "TESTESTEST";

        socket_dialogue.close();
    }
}