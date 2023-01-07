package benke.ladislau.attila.chat;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatClient {
    //region Listener Interface(s)
    public interface InitializedListener {
        void Initialized(ChatClient source);
    }
    public interface ConnectedListener {
        void Connected(ChatClient source, IpV4address remoteAddress, int remotePort);
    }
    public interface ConnectFailedListener {
        void ConnectFailed(ChatClient source, IpV4address remoteAddress, int remotePort, String failMessage);
    }
    public interface MessageReceivedListener {
        void MessageReceived(ChatClient source, String message, String msgFrom);
    }
    public interface DisconnectedListener {
        void Disconnected(ChatClient source);
    }
    public interface DisconnectFailedListener {
        void DisconnectFailed(ChatClient source, String failMessage);
    }
    //endregion Listener Interface(s)

    //region Instance Variable(s)
    private Thread listenForMsgThread;
    private Socket socket;
    IpV4address connectAddress;
    private int connectPort;
    //endregion Instance Variable(s)

    //region Constructor(s)
    public ChatClient() {
        this(new IpV4address("127.0.0.1"), 50000, null);
    }

    public ChatClient(InitializedListener listener) {
        this(new IpV4address("127.0.0.1"), 50000, listener);
    }

    public ChatClient(int port) {
        this(new IpV4address("127.0.0.1"), port, null);
    }

    public ChatClient(int port, InitializedListener listener) {
        this(new IpV4address("127.0.0.1"), port, listener);
    }

    public ChatClient(IpV4address connectAddress, int port) {
        this(connectAddress, port, null);
    }

    public ChatClient(IpV4address connectAddress, int port, InitializedListener listener) {
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
    List<InitializedListener> InitializedListeners = new ArrayList<InitializedListener>();
    List<ConnectedListener> ConnectedListeners = new ArrayList<ConnectedListener>();
    List<ConnectFailedListener> ConnectFailedListeners = new ArrayList<ConnectFailedListener>();
    List<MessageReceivedListener> MessageReceivedListeners = new ArrayList<MessageReceivedListener>();
    List<DisconnectedListener> DisconnectedListeners = new ArrayList<DisconnectedListener>();
    List<DisconnectFailedListener> DisconnectFailedListeners = new ArrayList<DisconnectFailedListener>();

    public synchronized void addInitializedListener(InitializedListener listener) {
        if (!InitializedListeners.contains(listener)) {
            InitializedListeners.add(listener);
        }
    }
    public synchronized void addConnectedListener(ConnectedListener listener) {
        if (!ConnectedListeners.contains(listener)) {
            ConnectedListeners.add(listener);
        }
    }
    public synchronized void addConnectFailedListener(ConnectFailedListener listener) {
        if (!ConnectFailedListeners.contains(listener)) {
            ConnectFailedListeners.add(listener);
        }
    }
    public synchronized void addMessageReceivedListener(MessageReceivedListener listener) {
        if (!MessageReceivedListeners.contains(listener)) {
            MessageReceivedListeners.add(listener);
        }
    }
    public synchronized void addDisconnectedListener(DisconnectedListener listener) {
        if (!DisconnectedListeners.contains(listener)) {
            DisconnectedListeners.add(listener);
        }
    }
    public synchronized void addDisconnectFailedListener(DisconnectFailedListener listener) {
        if (!DisconnectFailedListeners.contains(listener)) {
            DisconnectFailedListeners.add(listener);
        }
    }
    //endregion Event(s)

    //region Event Raiser(s)
    private void raiseInitializedEvent() {
        for (InitializedListener listener : InitializedListeners) {
            listener.Initialized(this);
        }
    }
    private void raiseConnectedEvent(IpV4address remoteAddress, int remotePort) {
        for (ConnectedListener listener : ConnectedListeners) {
            listener.Connected(this, remoteAddress, remotePort);
        }
    }
    private void raiseConnectFailedEvent(IpV4address remoteAddress, int remotePort, String failMessage) {
        for (ConnectFailedListener listener : ConnectFailedListeners) {
            listener.ConnectFailed(this, remoteAddress, remotePort, failMessage);
        }
    }
    private void raiseMessageReceivedEvent(String message, String msgFrom) {
        for (MessageReceivedListener listener : MessageReceivedListeners) {
            listener.MessageReceived(this, message, msgFrom);
        }
    }
    private void raiseDisconnectedEvent() {
        for (DisconnectedListener listener : DisconnectedListeners) {
            listener.Disconnected(this);
        }
    }
    private void raiseDisconnectFailed(String failMessage) {
        for (DisconnectFailedListener listener : DisconnectFailedListeners) {
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
