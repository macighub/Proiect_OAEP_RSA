package benke.ladislau.attila.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;


public class ChatServer {
    //region Listener Interface(s)
    public interface InitializedListener {
        void Initialized(ChatServer source);
    }
    public interface WaitingForConnectionListener {
        void WaitingForConnection(ChatServer source, int port);
    }
    public interface ConnectionReceivedListener {
        void ConnectionReceived(ChatServer source, String connectFrom);
    }
    public interface MessageReceivedListener {
        void MessageReceived(ChatServer source, String message, String msgFrom);
    }
    public interface ConnectionClosedListener {
        void ConnectionClosed(ChatServer source, boolean byClient);
    }
    public interface StoppedListener {
        void Stopped(ChatServer source);
    }
    //endregion Listener Interface(s)

    //region Instance Variable(s)
    private Thread runServer;
    private ServerSocket server;
    private Socket socket;
    private int port;
    //endregion Instance Variable(s)

    //region Constructor(s)
    public ChatServer() {
        this(50000, null);
    }

    public ChatServer(int port) {
        this(port,null);
    }

    public ChatServer(int port, InitializedListener listener) {
        this.port = port;
        if (listener != null) {
            addInitializedListener(listener);
            raiseInitializedEvent();
        }
        System.out.println( "[Server<" + this.port + ">] - Server initialized." );
    }
    //endregion Constructor(s)

    //region Event(s)
    private List<InitializedListener> InitializedListeners = new ArrayList<InitializedListener>();
    private List<WaitingForConnectionListener> WaitingForConnectionListeners = new ArrayList<WaitingForConnectionListener>();
    private List<ConnectionReceivedListener> ConnectionReceivedListeners = new ArrayList<ConnectionReceivedListener>();
    private List<MessageReceivedListener> MessageReceivedListeners = new ArrayList<MessageReceivedListener>();
    private List<ConnectionClosedListener> ConnectionClosedListeners = new ArrayList<ConnectionClosedListener>();
    private List<StoppedListener> StoppedListeners = new ArrayList<StoppedListener>();

    public synchronized void addInitializedListener(InitializedListener listener) {
        if (!InitializedListeners.contains(listener)) {
            InitializedListeners.add(listener);
        }
    }
    public synchronized void addWaitingForConnectionListener(WaitingForConnectionListener listener) {
        if (!WaitingForConnectionListeners.contains(listener)) {
            WaitingForConnectionListeners.add(listener);
        }
    }
    public synchronized void addConnectionReceivedListener(ConnectionReceivedListener listener) {
        if (!ConnectionReceivedListeners.contains(listener)) {
            ConnectionReceivedListeners.add(listener);
        }
    }
    public synchronized void addMessageReceivedListener(MessageReceivedListener listener) {
        if (!ConnectionReceivedListeners.contains(listener)) {
            MessageReceivedListeners.add(listener);
        }
    }
    public synchronized void addConnectionClosedListener(ConnectionClosedListener listener) {
        if (!ConnectionClosedListeners.contains(listener)) {
            ConnectionClosedListeners.add(listener);
        }
    }
    public synchronized void addStoppedListener(StoppedListener listener) {
        if (!StoppedListeners.contains(listener)) {
            StoppedListeners.add(listener);
        }
    }
    //endregion Event(s)

    //region Event Raiser(s)
    private void raiseInitializedEvent() {
        for (InitializedListener listener : InitializedListeners) {
            listener.Initialized(this);
        }
    }
    private void raiseWaitingForConnectionEvent(int port) {
        for (WaitingForConnectionListener listener : WaitingForConnectionListeners) {
            listener.WaitingForConnection(this, port);
        }
    }
    private void raiseConnectionReceivedEvent(String connectFrom) {
        for (ConnectionReceivedListener listener : ConnectionReceivedListeners) {
            listener.ConnectionReceived(this, connectFrom);
        }
    }
    private void raiseMessageReceivedEvent(String message, String msgFrom) {
        for (MessageReceivedListener listener : MessageReceivedListeners) {
            listener.MessageReceived(this, message, msgFrom);
        }
    }
    private void raiseConnectionClosedEvent(boolean byClient) {
        for (ConnectionClosedListener listener : ConnectionClosedListeners) {
            listener.ConnectionClosed(this, byClient);
        }
    }
    private void raiseStoppedEvent() {
        for (StoppedListener listener : StoppedListeners) {
            listener.Stopped(this);
        }
    }
    //endregion Event Raiser(s)

    //region Public Method(s)
    public void setPort(int port) {
        this.port = port;
    }

    public int getPort() {
        return this.port;
    }

    public void startServer(int port){
        this.port = port;
        startServer();
    }

    public void startServer(){
        try {
            if (server == null) {
                server = new ServerSocket(port);
            }
            runServer = new Thread() {
                public void run() {
                    runServer();
                }
            };
            runServer.start();
            System.out.println("[Server<" + this.port + ">] - Server started.");
        } catch (IOException e) {
            System.out.println("[Server<" + this.port + ">] - Failed to start server:");
            e.printStackTrace();
        }
    }

    public void stopServer(){
        if (runServer.isAlive()) {
            runServer.interrupt();
        }
        if (!server.isClosed()) {
            try {
                server.close();
                server = null;
                raiseStoppedEvent();
            } catch (Exception e) {
                System.out.println("[Server<" + this.port + ">] - Failed to stop server:");
                e.printStackTrace();
            }
        }
    }

    public void sendMessage(String message) {
        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream());

            out.println(message);
            out.flush();

            System.out.println("[Server<" + this.port + ">] - Message sent: " + message);
        } catch (Exception e) {
            System.out.println("[Server<" + this.port + ">] - Failed to send message: " + e.getMessage());
        }
    }
    //endregion Public Method(s)

    //region Private Method(s)
    private void runServer() {
        try {
            System.out.println("[Server<" + this.port + ">] - Server is waiting for connection");
            raiseWaitingForConnectionEvent(this.port);

            socket = server.accept();

            System.out.println("[Server<" + this.port + ">] - Received a connection" );
            raiseConnectionReceivedEvent(socket.getRemoteSocketAddress().toString());

            listenForMessages();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void listenForMessages() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String message = in.readLine();
            while( message != null && message.length() > 0 )
            {
                System.out.println("[Server<" + this.port + ">] - Message received: " + message);
                raiseMessageReceivedEvent(message, socket.getRemoteSocketAddress().toString());

                message = in.readLine();
            }

            in.close();
            socket.close();
            System.out.println("[Server<" + this.port + ">] - Connection closed by client.");
            raiseConnectionClosedEvent(true);

        } catch (Exception e) {
            System.out.println("[Server<" + this.port + ">] - Message receiving interrupted unexpectedly: " + e.getMessage());
            e.printStackTrace();
        } finally {
            stopServer();
            startServer();
        }
    }
    //endregion Private Method(s0
}
