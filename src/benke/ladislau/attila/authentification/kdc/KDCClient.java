package benke.ladislau.attila.authentification.kdc;

import benke.ladislau.attila.chat.IpV4address;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class KDCClient {
    //region Listener Interface(s)
    public interface InitializedListener {
        void Initialized(KDCClient source);
    }
    public interface ConnectedListener {
        void Connected(KDCClient source, IpV4address remoteAddress, int remotePort);
    }
    public interface ConnectFailedListener {
        void ConnectFailed(KDCClient source, IpV4address remoteAddress, int remotePort, String failMessage);
    }
    public interface MessageReceivedListener {
        void MessageReceived(KDCClient source, String message, String msgFrom);
    }
    public interface DisconnectedListener {
        void Disconnected(KDCClient source);
    }
    public interface DisconnectFailedListener {
        void DisconnectFailed(KDCClient source, String failMessage);
    }
    //endregion Listener Interface(s)

    //region Instance Variable(s)
    private Thread listenForMsgThread;
    private Socket socket;
    IpV4address connectAddress;
    private int connectPort;
    //endregion Instance Variable(s)

    //region Constructor(s)
    public KDCClient() {
        this(new IpV4address("127.0.0.1"), 50000, null);
    }

    public KDCClient(KDCClient.InitializedListener listener) {
        this(new IpV4address("127.0.0.1"), 50000, listener);
    }

    public KDCClient(int port) {
        this(new IpV4address("127.0.0.1"), port, null);
    }

    public KDCClient(int port, KDCClient.InitializedListener listener) {
        this(new IpV4address("127.0.0.1"), port, listener);
    }

    public KDCClient(IpV4address connectAddress, int port) {
        this(connectAddress, port, null);
    }

    public KDCClient(IpV4address connectAddress, int port, KDCClient.InitializedListener listener) {
        this.connectAddress = connectAddress;
        this.connectPort = port;
        if (listener != null) {
            addInitializedListener(listener);
            raiseInitializedEvent();
        }
        System.out.println( "[Client<" + this.connectPort + ">] - Client initialized." );
    }
    //endregion Constructor(s)

    //region Event(s)
    List<KDCClient.InitializedListener> InitializedListeners = new ArrayList<KDCClient.InitializedListener>();
    List<KDCClient.ConnectedListener> ConnectedListeners = new ArrayList<KDCClient.ConnectedListener>();
    List<KDCClient.ConnectFailedListener> ConnectFailedListeners = new ArrayList<KDCClient.ConnectFailedListener>();
    List<KDCClient.MessageReceivedListener> MessageReceivedListeners = new ArrayList<KDCClient.MessageReceivedListener>();
    List<KDCClient.DisconnectedListener> DisconnectedListeners = new ArrayList<KDCClient.DisconnectedListener>();
    List<KDCClient.DisconnectFailedListener> DisconnectFailedListeners = new ArrayList<KDCClient.DisconnectFailedListener>();

    public synchronized void addInitializedListener(KDCClient.InitializedListener listener) {
        if (!InitializedListeners.contains(listener)) {
            InitializedListeners.add(listener);
        }
    }
    public synchronized void addConnectedListener(KDCClient.ConnectedListener listener) {
        if (!ConnectedListeners.contains(listener)) {
            ConnectedListeners.add(listener);
        }
    }
    public synchronized void addConnectFailedListener(KDCClient.ConnectFailedListener listener) {
        if (!ConnectFailedListeners.contains(listener)) {
            ConnectFailedListeners.add(listener);
        }
    }
    public synchronized void addMessageReceivedListener(KDCClient.MessageReceivedListener listener) {
        if (!MessageReceivedListeners.contains(listener)) {
            MessageReceivedListeners.add(listener);
        }
    }
    public synchronized void addDisconnectedListener(KDCClient.DisconnectedListener listener) {
        if (!DisconnectedListeners.contains(listener)) {
            DisconnectedListeners.add(listener);
        }
    }
    public synchronized void addDisconnectFailedListener(KDCClient.DisconnectFailedListener listener) {
        if (!DisconnectFailedListeners.contains(listener)) {
            DisconnectFailedListeners.add(listener);
        }
    }
    //endregion Event(s)

    //region Event Raiser(s)
    private void raiseInitializedEvent() {
        for (KDCClient.InitializedListener listener : InitializedListeners) {
            listener.Initialized(this);
        }
    }
    private void raiseConnectedEvent(IpV4address remoteAddress, int remotePort) {
        for (KDCClient.ConnectedListener listener : ConnectedListeners) {
            listener.Connected(this, remoteAddress, remotePort);
        }
    }
    private void raiseConnectFailedEvent(IpV4address remoteAddress, int remotePort, String failMessage) {
        for (KDCClient.ConnectFailedListener listener : ConnectFailedListeners) {
            listener.ConnectFailed(this, remoteAddress, remotePort, failMessage);
        }
    }
    private void raiseMessageReceivedEvent(String message, String msgFrom) {
        for (KDCClient.MessageReceivedListener listener : MessageReceivedListeners) {
            listener.MessageReceived(this, message, msgFrom);
        }
    }
    private void raiseDisconnectedEvent() {
        for (KDCClient.DisconnectedListener listener : DisconnectedListeners) {
            listener.Disconnected(this);
        }
    }
    private void raiseDisconnectFailed(String failMessage) {
        for (KDCClient.DisconnectFailedListener listener : DisconnectFailedListeners) {
            listener.DisconnectFailed(this, failMessage);
        }
    }
    //endregion Event Raiser(s)

    //region Public Method(s)
    public void connect() {
        connect(this.connectAddress, this.connectPort);
    }

    public void connect(IpV4address address, int port) {
        try {
            this.connectAddress = address;
            this.connectPort = port;

            socket = new Socket(address.toString(), port);

            raiseConnectedEvent(address, port);

            listenForMsgThread = new Thread() {
                public void run() {listenForMessages();}
            };
            listenForMsgThread.start();
        } catch (IOException e) {
            raiseConnectFailedEvent(address, port, e.getMessage());
        }
    }

    public void disconnect() {
        try {
            listenForMsgThread.interrupt();
            socket.close();
            raiseDisconnectedEvent();
        } catch (IOException e) {
            raiseDisconnectFailed(e.getMessage());
        }
    }

    public String getLocalAddress() {
        return socket.getLocalAddress().toString();
    }

    public int getLocalPort() {
        return  socket.getLocalPort();
    }

    public void sendMessage(String message) {
        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream());

            out.println(message);
            out.flush();

            System.out.println("[Client<" + this.connectPort + ">] - Message sent: " + message);
        } catch (Exception e) {
            System.out.println("[Client<" + this.connectPort + ">] - Failed to send message: " + e.getMessage());
        }
    }
    //endregion Public Method(s)

    //region Private Method(s)
    private void listenForMessages() {
        try {
            BufferedReader in = new BufferedReader( new InputStreamReader(socket.getInputStream()));

            String message = in.readLine();
            while( message != null && message.length() > 0 ) {
                System.out.println("[Client<" + this.connectPort + ">] - Message received: " + message);
                raiseMessageReceivedEvent(message, socket.getRemoteSocketAddress().toString());

                message = in.readLine();
            }

            in.close();
            socket.close();

            System.out.println("[Client<" + this.connectPort + ">] - Connection closed" );
            raiseDisconnectedEvent();
        } catch( Exception e ) {
            if (e.getMessage().equals("Socket closed")) {
                System.out.println("[Client<" + this.connectPort + ">] - Connection closed" );
            } else {
                System.out.println("[Client<" + this.connectPort + ">] - Message receiving interrupted unexpectedly: " + e.getMessage());
            }
        }
    }
    //endregion Private Method(s)
}
