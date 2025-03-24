package mycode.main.Test;

import org.java_websocket.server.WebSocketServer;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ChatServerTest extends WebSocketServer {
    private Set<WebSocket> clients = Collections.synchronizedSet(new HashSet<>());

    private List<String> chatHistory = new ArrayList<>(); // 保存聊天記錄

    public ChatServerTest(String ip, Integer port) {
        super(new InetSocketAddress(ip, port));
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        System.out.println("New connection: " + conn.getRemoteSocketAddress());
        clients.add(conn);
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        System.out.println("Received: " + message);
        
        chatHistory.add(message); // 保存聊天記錄
        // 廣播給所有客戶端
        this.broadcast(message);


        if (message.equals("hello")) {
            conn.send("Hi there! How can I help you?");
        } else if (message.equals("bye")) {
            conn.send("Goodbye! See you next time.");
        } else {
            conn.send("You said: " + message);
        }
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        System.out.println("Connection closed: " + conn.getRemoteSocketAddress());
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        ex.printStackTrace();
    }

    @Override
    public void onStart() {
        System.out.println("Chat server has started...");
    }

    public static void main(String[] args) throws Exception {
        ChatServerTest server = new ChatServerTest("localhost", 8080);        
        server.start();        
    }
}
