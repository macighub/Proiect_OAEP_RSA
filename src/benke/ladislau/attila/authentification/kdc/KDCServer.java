package benke.ladislau.attila.authentification.kdc
        ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class KDCServer {
    //region Listener Interface(s)
    public interface InitializedListener {
        void Initialized(KDCServer source);
    }
    public interface WaitingForConnectionListener {
        void WaitingForConnection(KDCServer source, int port);
    }
    public interface ConnectionReceivedListener {
        void ConnectionReceived(KDCServer source, String connectFrom);
    }
    public interface MessageReceivedListener {
        void MessageReceived(KDCServer source, String message, String msgFrom);
    }
    public interface ConnectionClosedListener {
        void ConnectionClosed(KDCServer source, boolean byClient);
    }
    public interface StoppedListener {
        void Stopped(KDCServer source);
    }
    //endregion Listener Interface(s)

    //region Instance Variable(s)
    private Thread runServer;
    private ServerSocket server;
    private Socket socket;
    private int port;
    //endregion Instance Variable(s)

    //region Constructor(s)
    public KDCServer() {
        this(50000, null);
    }

    public KDCServer(int port) {
        this(port,null);
    }

    public KDCServer(int port, KDCServer.InitializedListener listener) {
        this.port = port;
        if (listener != null) {
            addInitializedListener(listener);
            raiseInitializedEvent();
        }
        System.out.println( "[Server<" + this.port + ">] - Server initialized." );
    }
    //endregion Constructor(s)

    //region Event(s)
    private List<KDCServer.InitializedListener> InitializedListeners = new ArrayList<InitializedListener>();
    private List<KDCServer.WaitingForConnectionListener> WaitingForConnectionListeners = new ArrayList<KDCServer.WaitingForConnectionListener>();
    private List<KDCServer.ConnectionReceivedListener> ConnectionReceivedListeners = new ArrayList<KDCServer.ConnectionReceivedListener>();
    private List<KDCServer.MessageReceivedListener> MessageReceivedListeners = new ArrayList<KDCServer.MessageReceivedListener>();
    private List<KDCServer.ConnectionClosedListener> ConnectionClosedListeners = new ArrayList<KDCServer.ConnectionClosedListener>();
    private List<KDCServer.StoppedListener> StoppedListeners = new ArrayList<KDCServer.StoppedListener>();

    public synchronized void addInitializedListener(KDCServer.InitializedListener listener) {
        if (!InitializedListeners.contains(listener)) {
            InitializedListeners.add(listener);
        }
    }
    public synchronized void addWaitingForConnectionListener(KDCServer.WaitingForConnectionListener listener) {
        if (!WaitingForConnectionListeners.contains(listener)) {
            WaitingForConnectionListeners.add(listener);
        }
    }
    public synchronized void addConnectionReceivedListener(KDCServer.ConnectionReceivedListener listener) {
        if (!ConnectionReceivedListeners.contains(listener)) {
            ConnectionReceivedListeners.add(listener);
        }
    }
    public synchronized void addMessageReceivedListener(KDCServer.MessageReceivedListener listener) {
        if (!ConnectionReceivedListeners.contains(listener)) {
            MessageReceivedListeners.add(listener);
        }
    }
    public synchronized void addConnectionClosedListener(KDCServer.ConnectionClosedListener listener) {
        if (!ConnectionClosedListeners.contains(listener)) {
            ConnectionClosedListeners.add(listener);
        }
    }
    public synchronized void addStoppedListener(KDCServer.StoppedListener listener) {
        if (!StoppedListeners.contains(listener)) {
            StoppedListeners.add(listener);
        }
    }
    //endregion Event(s)

    //region Event Raiser(s)
    private void raiseInitializedEvent() {
        for (KDCServer.InitializedListener listener : InitializedListeners) {
            listener.Initialized(this);
        }
    }
    private void raiseWaitingForConnectionEvent(int port) {
        for (KDCServer.WaitingForConnectionListener listener : WaitingForConnectionListeners) {
            listener.WaitingForConnection(this, port);
        }
    }
    private void raiseConnectionReceivedEvent(String connectFrom) {
        for (KDCServer.ConnectionReceivedListener listener : ConnectionReceivedListeners) {
            listener.ConnectionReceived(this, connectFrom);
        }
    }
    private void raiseMessageReceivedEvent(String message, String msgFrom) {
        for (KDCServer.MessageReceivedListener listener : MessageReceivedListeners) {
            listener.MessageReceived(this, message, msgFrom);
        }
    }
    private void raiseConnectionClosedEvent(boolean byClient) {
        for (KDCServer.ConnectionClosedListener listener : ConnectionClosedListeners) {
            listener.ConnectionClosed(this, byClient);
        }
    }
    private void raiseStoppedEvent() {
        for (KDCServer.StoppedListener listener : StoppedListeners) {
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
            System.out.println("[KDC<" + this.port + ">] - KDC started.");
        } catch (IOException e) {
            System.out.println("[KDC<" + this.port + ">] - Failed to start KDC:");
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
                System.out.println("[KDC<" + this.port + ">] - Failed to stop KDC:");
                e.printStackTrace();
            }
        }
    }

    public void sendMessage(String message) {
        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream());

            out.println(message);
            out.flush();

            System.out.println("[KDC<" + this.port + ">] - Message sent: " + message);
        } catch (Exception e) {
            System.out.println("[KDC<" + this.port + ">] - Failed to send message: " + e.getMessage());
        }
    }
    //endregion Public Method(s)

    //region Private Method(s)
    private void runServer() {
        try {
            System.out.println("[KDC<" + this.port + ">] - KDC is waiting for connection");
            raiseWaitingForConnectionEvent(this.port);

            socket = server.accept();

            System.out.println("[KDC<" + this.port + ">] - Received a connection" );
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
                System.out.println("[KDC<" + this.port + ">] - Message received: " + message);
                raiseMessageReceivedEvent(message, socket.getRemoteSocketAddress().toString());

                message = in.readLine();
            }

            in.close();
            socket.close();
            System.out.println("[KDC<" + this.port + ">] - Connection closed by client.");
            raiseConnectionClosedEvent(true);

        } catch (Exception e) {
            System.out.println("[KDC<" + this.port + ">] - Message receiving interrupted unexpectedly: " + e.getMessage());
            e.printStackTrace();
        } finally {
            stopServer();
            startServer();
        }
    }
    //endregion Private Method(s0
}
