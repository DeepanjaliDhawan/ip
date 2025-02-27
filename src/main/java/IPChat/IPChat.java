package IPChat;

import ipchatExceptions.IPChatExceptions;
import java.io.*;
import static IPChat.Parser.mySequence;
import static IPChat.Ui.start;

/**
 * Main class for activating and running the IPChat Box
 *
 * @author DeepanjaliDhawan
 */
public class IPChat {
    public static void main(String[] args) throws IPChatExceptions , IOException{
        System.out.println("Hi!! Welcome to IPChat! How can I help you");
        start();
        mySequence();
    }
}
