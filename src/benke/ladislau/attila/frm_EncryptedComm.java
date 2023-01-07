package benke.ladislau.attila;

import benke.ladislau.attila.authentification.kdc.KDCClient;
import benke.ladislau.attila.authentification.packages.*;
import benke.ladislau.attila.chat.ChatClient;
import benke.ladislau.attila.chat.ChatServer;
import benke.ladislau.attila.chat.IpV4address;
import benke.ladislau.attila.crypto.AES;
import benke.ladislau.attila.crypto.OAEP;
import benke.ladislau.attila.crypto.cryptUtils.BinaryString;
import benke.ladislau.attila.crypto.cryptUtils.CryptConverter;
import benke.ladislau.attila.crypto.cryptUtils.CryptFormatter;
import benke.ladislau.attila.crypto.cryptUtils.HexString;
import benke.ladislau.attila.crypto.rsa.RSA;
import benke.ladislau.attila.crypto.rsa.RSAKey;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class frm_EncryptedComm extends JFrame  {
    //region Instance Variable(s)

        //region GUI Element(s)

            private JPopupMenu mnu_Messages;
                private JMenuItem mni_Clear;
            private JPanel pnl_Main;
                private JTabbedPane tab_Main;
                private JPanel pnl_Communication;
                    private JPanel pnl_Connect;
                        private JLabel lbl_Connect;
                        private JTextField txt_Connect;
                        private JLabel lbl_Port;
                        private JTextField txt_Port;
                        private JButton btn_Connect;
                    private JPanel pnl_Messages;
                        private JScrollPane scp_Messages;
                            private JTextPane txt_Messages;
                        private JPanel pnl_Message;
                            private JTextField txt_Message;
                            private JCheckBox chk_DisableRSA;
                            private JCheckBox chk_DisableOAEP;
                            private JButton btn_Send;
                    private JPanel pnl_Log;
                        private JScrollPane scr_Log;
                        private JTextArea txt_Log;
                private JScrollPane scp_Encryption;
                    private JPanel pnl_Encryption;
                        private JPanel pnl_Msg;
                            private JTabbedPane tab_Msg;
                                private JPanel pnl_Msg_Dec;
                                    private JScrollPane scp_Msg_Dec;
                                        private JTextArea txt_Msg_Dec;
                                private JPanel pnl_Msg_Byte;
                                    private JScrollPane scp_Msg_Byte;
                                        private JTextArea txt_Msg_Byte;
                                private JPanel pnl_Msg_Hex;
                                    private JScrollPane scp_Msg_Hex;
                                        private JTextArea txt_Msg_Hex;
                                private JPanel pnl_Msg_Bin;
                                    private JScrollPane scp_Msg_Bin;
                                        private JTextArea txt_Msg_Bin;
                        private JPanel pnl_OAEP_RSA;
                            private JPanel pnl_OAEP;
                                private JTabbedPane tab_OAEP;
                                    private JPanel pnl_OAEP_Dec;
                                        private JScrollPane scp_OAEP_Dec;
                                            private JTextArea txt_OAEP_Dec;
                                    private JPanel pnl_OAEP_Byte;
                                        private JScrollPane scp_OAEP_Byte;
                                            private JTextArea txt_OAEP_Byte;
                                    private JPanel pnl_OAEP_Hex;
                                        private JScrollPane scp_OAEP_Hex;
                                            private JTextArea txt_OAEP_Hex;
                                    private JPanel pnl_OAEP_Bin;
                                        private JScrollPane scp_OAEP_bin;
                                            private JTextArea txt_OAEP_Bin;
                            private JPanel pnl_RSA;
                                private JTabbedPane tab_RSA;
                                    private JPanel pnl_RSA_Dec;
                                        private JScrollPane scp_RSA_Dec;
                                            private JTextArea txt_RSA_Dec;
                                    private JPanel pnl_RSA_Byte;
                                        private JScrollPane scp_RSA_Byte;
                                            private JTextArea txt_RSA_Byte;
                                    private JPanel pnl_RSA_Hex;
                                        private JScrollPane scp_RSA_Hex;
                                            private JTextArea txt_RSA_Hex;
                                    private JPanel pnl_RSA_Bin;
                                        private JScrollPane scp_RSA_Bin;
                                            private JTextArea txt_RSA_Bin;
                        private JPanel pnl_Keys;
                            private JTabbedPane tab_Keys;
                                private JPanel pnl_Keys_Client;
                                    private JTabbedPane tab_Keys_Client;
                                        private JPanel pnl_Keys_Client_n;
                                            private JTabbedPane tab_Keys_Client_n;
                                                private JPanel pnl_Keys_Client_n_Dec;
                                                    private JScrollPane scp_Keys_Client_n_Dec;
                                                        private JTextArea txt_Keys_Client_n_Dec;
                                                private JPanel pnl_Keys_Client_n_Byte;
                                                    private JScrollPane scp_Keys_Client_n_Byte;
                                                        private JTextArea txt_Keys_Client_n_Byte;
                                                private JPanel pnl_Keys_Client_n_Hex;
                                                    private JScrollPane scp_Keys_Client_n_Hex;
                                                        private JTextArea txt_Keys_client_n_Hex;
                                                private JPanel pnl_Keys_Client_n_Bin;
                                                    private JScrollPane scp_Keys_Client_n_Bin;
                                                        private JTextArea txt_Keys_Client_n_Bin;
                                        private JPanel pnl_Keys_Client_e;
                                            private JTabbedPane tab_Keys_Client_e;
                                                private JPanel pnl_Keys_Client_e_Dec;
                                                    private JScrollPane scp_Keys_Client_e_Dec;
                                                        private JTextArea txt_Keys_Client_e_Dec;
                                                private JPanel pnl_Keys_Client_e_Byte;
                                                    private JScrollPane scp_Keys_Client_e_Byte;
                                                        private JTextArea txt_Keys_Client_e_Byte;
                                                private JPanel pnl_Keys_Client_e_Hex;
                                                    private JScrollPane scp_Keys_Client_e_Hex;
                                                        private JTextArea txt_Keys_Client_e_Hex;
                                                private JPanel pnl_Keys_Client_e_Bin;
                                                    private JScrollPane scp_Keys_Client_e_Bin;
                                                        private JTextArea txt_Keys_Client_e_Bin;
                                private JPanel pnl_Keys_Server;
                                    private JTabbedPane tab_Keys_Server;
                                        private JPanel pnl_Keys_Server_n;
                                            private JTabbedPane tab_Keys_Server_n;
                                                private JPanel pnl_Keys_Server_n_Dec;
                                                    private JScrollPane scp_Keys_Server_n_Dec;
                                                        private JTextArea txt_Keys_Server_n_Dec;
                                                private JPanel pnl_Keys_Server_n_Byte;
                                                    private JScrollPane scp_Keys_Server_n_Byte;
                                                        private JTextArea txt_Keys_Server_n_Byte;
                                                private JPanel pnl_Keys_Server_n_Hex;
                                                    private JScrollPane scp_Keys_Server_n_Hex;
                                                        private JTextArea txt_Keys_Server_n_Hex;
                                                private JPanel pnl_Keys_Server_n_Bin;
                                                    private JScrollPane scp_Keys_Server_n_Bin;
                                                        private JTextArea txt_Keys_Server_n_Bin;
                                        private JPanel pnl_Keys_Server_e;
                                            private JTabbedPane tab_Keys_Server_e;
                                                private JPanel pnl_Keys_Server_e_Dec;
                                                    private JScrollPane scp_Keys_Server_e_Dec;
                                                        private JTextArea txt_Keys_Server_e_Dec;
                                                private JPanel pnl_Keys_Server_e_Byte;
                                                    private JScrollPane scp_Keys_Server_e_Byte;
                                                        private JTextArea txt_Keys_Server_e_Hex;
                                                private JPanel pnl_Keys_Server_e_Hex;
                                                    private JScrollPane scp_Keys_Server_e_Hex;
                                                        private JTextArea txt_Keys_Server_e_Byte;
                                                private JPanel pnl_Keys_Server_e_Bin;
                                                    private JScrollPane scp_Keys_Server_e_Bin;
                                                        private JTextArea txt_Keys_Server_e_Bin;
                                        private JPanel pnl_Keys_Server_d;
                                            private JTabbedPane tab_Keys_Server_d;
                                                private JPanel pnl_Keys_Server_d_Dec;
                                                    private JScrollPane scp_Keys_Server_d_Dec;
                                                        private JTextArea txt_Keys_Server_d_Dec;
                                                private JPanel pnl_Keys_Server_d_Byte;
                                                    private JScrollPane scp_Keys_Server_d_Byte;
                                                        private JTextArea txt_Keys_Server_d_Byte;
                                                private JPanel pnl_Keys_Server_d_Hex;
                                                    private JScrollPane scp_Keys_Server_d_Hex;
                                                        private JTextArea txt_Keys_Server_d_Hex;
                                                private JPanel pnl_Keys_Server_d_Bin;
                                                    private JScrollPane scp_Keys_Server_d_Bin;
                                                        private JTextArea txt_Keys_Server_d_Bin;
    private JPanel pnl_Authentication;
    private JTabbedPane tab_Authentication;
    private JPanel pnl_Client;
    private JPanel pnl_Server;
    private JTabbedPane tabbedPane2;
    private JPanel pnl_KDC;
    private JLabel lbl_KDCAddress;
    private JTextField txt_KDCAddress;
    private JLabel lbl_KDCPort;
    private JTextField txt_KDCPort;
    private JPanel pnl_ServerAuthStatus;
    private JTextField txt_ServerAuthStatus;
    private JLabel lbl_ServerAuthStatus;
    private JPanel pnl_ClientAuthStatus;
    private JTextField txt_ClientAuthStatus;
    private JLabel lbl_ClientAuthStatus;
    private JPanel pnl_ServerAtuhConf;
    private JCheckBox chk_ServerAuthConf;
    private JPanel pnl_ServerComm;
    private JTextField txt_AUTH_REG_timestamp;
    private JPanel pnl_AS_REQ;
    private JTextField txt_AS_REQ_ts;
    private JTextArea txt_AS_REQ;
    private JPanel pnl_AUTH_ERR;
    private JTextArea txt_AUTH_ERR;
    private JPanel pnl_AUTH_REQ_Client;
    private JTextArea txt_AUTH_REQ_Client_Value;
    private JPanel pnl_AS_REP;
    private JTextArea txt_AS_REP;
    private JPanel pnl_AUTH_REQ_Server;
    private JPanel pnl_AUTH_REQ_Label_Server;
    private JPanel pnl_AUTH_REQ_Server_Value;
    private JTextArea lbl_AUTH_REQ_Server;
    private JTextArea txt_AUTH_REQ_Server;
    private JTextField txt_AS_TGT;
    private JPanel pnl_AS_TGT;
    private JPanel pnl_TGS_REQ;
    private JTextArea txt_TGS_REQ;
    private JPanel pnl_TGS_TGT;
    private JTextField txt_TGS_TGT;
    private JPanel pnl_TGS_REP;
    private JTextArea txt_TGS_REP;
    private JPanel pnl_TGS_ST;
    private JTextField txt_TGS_ST;
    private JPanel pnl_AUTH_REP_Client;
    private JTextArea txt_AUTH_REP_Client;
    private JPanel pnl_ST_Client;
    private JTextField txt_ST_Client;
    private JPanel pnl_ST_Server;
    private JPanel pnl_AUTH_REP_Server;
    private JTextArea txt_AUTH_REP_Server;
    private JTextArea txt_ST_Server;
    private JPanel pnl_KDCComm;
    private JPanel pnl_AUTH_REQ_Client_Label;
    private JTextArea lbl_AUTH_REQ_Client;
    private JPanel pnl_AUTH_REQ_Client_Value;
    private JPanel pnl_AS_REQ_Labels;
    private JLabel lbl_AS_REQ_ts;
    private JTextArea lbl_AS_REQ;
    private JPanel pnl_AS_REQ_Values;
    private JPanel pnl_AS_REP_Values;
    private JPanel pnl_AS_REP_Labels;
    private JTextArea lbl_AS_REP;
    private JPanel pnl_AUTH_ERR_Label;
    private JTextArea lbl_AUTH_ERR;
    private JPanel pnl_AUTH_ERR_Value;
    private JTextArea txt_AUTH_OK_Server;
    private JPanel pnl_AUTH_OK_Server;
    private JPanel pnl_AUTH_OK_Client;
    private JTextArea txt_AUTH_OK_Client;

    //endregion GUI Element(s)

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        private RSA chatRSA;
        private OAEP chatOAEP;
        private RSAKey dest_RSAPublicKey;
        private ChatServer chatServer;
        private ChatClient chatClient;
        private KDCClient kdcClient;

        private AS_REQ chat_AS_REQ = new AS_REQ();
        private TGS_REQ chat_TGS_REQ = new TGS_REQ();
        private String chat_TGT;
        private String chatClient_ST;
        private String chatServer_ST;
        private HexString clientKey;
        public HexString serverKey;
        private HexString shortTermKey;
        private HexString chatClient_SK;
        private HexString chatServer_SK;

    //endregion Instance Variable(s)

    //region Constructor(s)
    frm_EncryptedComm(String title, int serverPort) {
        this(title, serverPort, new IpV4address("127.0.0.1"), 0, false, 0);
    }

    frm_EncryptedComm(String title, int serverPort, IpV4address connectAddress, int connectPort) {
        this(title, serverPort, connectAddress, connectPort, false, 0);
    }

    frm_EncryptedComm(String title, int serverPort, IpV4address kdcAddress, int kdcPort, IpV4address connectAddress, int connectPort) {
        this(title, serverPort, connectAddress, connectPort, false, 0);

        this.txt_KDCAddress.setText(kdcAddress.toString());
        this.txt_KDCPort.setText(String.valueOf(kdcPort));

        chk_ServerAuthConf.setSelected(true);
    }

        frm_EncryptedComm(String title, int serverPort, IpV4address connectAddress, int connectPort, boolean autoConnect, int autoConnectDelay) {
        chatOAEP = new OAEP();

        dest_RSAPublicKey = new RSAKey(BigInteger.ZERO, BigInteger.ZERO);

        chatServer = new ChatServer(serverPort, new ChatServer.InitializedListener() {
            @Override
            public void Initialized(ChatServer source) {
                chatServer_Initialized(source);
            }
        });
        chatClient = new ChatClient(new ChatClient.InitializedListener() {
            @Override
            public void Initialized(ChatClient source) {
                chatClient_Initialized(source);
            }
        });
        kdcClient = new KDCClient(new KDCClient.InitializedListener() {
            @Override
            public void Initialized(KDCClient source) {
                kdcClient_Initialized(source);
            }
        });

        //region GUI Init

            super.setTitle(title);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setContentPane(pnl_Main);
            this.pack();

            //region GUI Listeners
                btn_Connect.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        btn_Connect_Clicked(e);
                    }
                });

                btn_Send.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        btn_Send_Clicked(e);
                    }
                });

                btn_Send.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (e.isPopupTrigger() || e.getButton() == 3) {
                            btn_Send_RightClicked(e);
                        }
                    }
                });

                chk_DisableOAEP.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        chk_DisableOAEP_ActionPerformed(e);
                    }
                });

                chk_DisableRSA.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        chk_DisableRSA_ActionPerformed(e);
                    }
                });

                txt_Connect.getDocument().addDocumentListener(new DocumentListener() {
                    public void changedUpdate(DocumentEvent e) {txt_Connect_Changed(e);}
                    public void removeUpdate(DocumentEvent e) {txt_Connect_Changed(e);}
                    public void insertUpdate(DocumentEvent e) {txt_Connect_Changed(e);}
                });

                txt_Message.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                        txt_Message_KeyTyped(e);
                    }
                });

                txt_Messages.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        txt_Messages_Clicked(e);
                    }
                });
            //endregion GUI Listeners

        //endregion GUI Init

        //region Listener(s)
            //region chatServer Listeners
            chatServer.addConnectionClosedListener(new ChatServer.ConnectionClosedListener() {
                @Override
                public void ConnectionClosed(ChatServer source, boolean byClient) {
                    chatServer_ConnectionClosed(source, byClient);
                }
            });
            chatServer.addConnectionReceivedListener(new ChatServer.ConnectionReceivedListener() {
                @Override
                public void ConnectionReceived(ChatServer source, String connectFrom) {
                    chatServer_ConnectionReceived(source, connectFrom);
                }
            });
            chatServer.addMessageReceivedListener(new ChatServer.MessageReceivedListener() {
                @Override
                public void MessageReceived(ChatServer source, String message, String msgFrom) {
                    chatServer_MessageReceived(source, message, msgFrom);
                }
            });
            chatServer.addStoppedListener(new ChatServer.StoppedListener() {
                @Override
                public void Stopped(ChatServer source) {
                    chatServer_Stopped(source);
                }
            });
            chatServer.addWaitingForConnectionListener(new ChatServer.WaitingForConnectionListener() {
                @Override
                public void WaitingForConnection(ChatServer source, int port) {
                    chatServer_WaitingForConnection(source, port);
                }
            });
            //endregion chatServer Listeners
            //region chatClient Listeners
            chatClient.addConnectedListener(new ChatClient.ConnectedListener() {
                @Override
                public void Connected(ChatClient source, IpV4address remoteAddress, int remotePort) {
                    chatClient_Connected(source, remoteAddress, remotePort);
                }
            });
            chatClient.addConnectFailedListener(new ChatClient.ConnectFailedListener() {
                @Override
                public void ConnectFailed(ChatClient source, IpV4address remoteAddress, int remotePort, String failMessage) {
                    chatClient_ConnectFailed(source, remoteAddress,remotePort,failMessage);
                }
            });
            chatClient.addDisconnectedListener(new ChatClient.DisconnectedListener() {
                @Override
                public void Disconnected(ChatClient source) {
                    chatClient_Disconnected();
                }
            });
            chatClient.addMessageReceivedListener(new ChatClient.MessageReceivedListener() {
                @Override
                public void MessageReceived(ChatClient source, String message, String msgFrom) {
                    chatClient_MessageReceived(message, msgFrom);
                }
            });
            chatClient.addDisconnectFailedListener(new ChatClient.DisconnectFailedListener() {
                @Override
                public void DisconnectFailed(ChatClient source, String failMessage) {
                    chatClient_DisconnectFailed(failMessage);
                }
            });
            //endregion chatClient Listeners
            //region kdcClient Listeners
            kdcClient.addConnectedListener(new KDCClient.ConnectedListener() {
                @Override
                public void Connected(KDCClient source, IpV4address remoteAddress, int remotePort) {
                    kdcClient_Connected(source, remoteAddress, remotePort);
                }
            });
            kdcClient.addConnectFailedListener(new KDCClient.ConnectFailedListener() {
                @Override
                public void ConnectFailed(KDCClient source, IpV4address remoteAddress, int remotePort, String failMessage) {
                    kdcClient_ConnectFailed(source, remoteAddress,remotePort,failMessage);
                }
            });
            kdcClient.addDisconnectedListener(new KDCClient.DisconnectedListener() {
                @Override
                public void Disconnected(KDCClient source) {
                    kdcClient_Disconnected();
                }
            });
            kdcClient.addMessageReceivedListener(new KDCClient.MessageReceivedListener() {
                @Override
                public void MessageReceived(KDCClient source, String message, String msgFrom) {
                    kdcClient_MessageReceived(message, msgFrom);
                }
            });
            kdcClient.addDisconnectFailedListener(new KDCClient.DisconnectFailedListener() {
                @Override
                public void DisconnectFailed(KDCClient source, String failMessage) {
                    kdcClient_DisconnectFailed(failMessage);
                }
            });
            //endregion kdcClient Listeners
        // endregion Listener(s)

        txt_Connect.setText(connectAddress.toString());
        txt_Port.setText(String.valueOf(connectPort));
        chatServer.startServer();

        if (autoConnect) {
            Timer timerAutoConnect = new Timer(autoConnectDelay, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    connectClientTo(connectAddress, connectPort);
                }
            });
            timerAutoConnect.setRepeats(false);
            timerAutoConnect.start();
        }
    }
    //endregion Constructor(s)

    //region Event handler(s)
        //region GUI Event handler(s)
        private void btn_Connect_Clicked(ActionEvent e) {
            if (btn_Connect.getText().equals("Connect")) {
                connectClientTo(new IpV4address(txt_Connect.getText()), Integer.parseInt(txt_Port.getText()));
            } else {
                disconnectClient();
                kdcClient.disconnect();
            }
        }

        private void btn_Send_Clicked(ActionEvent e) {
            sendMessage(txt_Message.getText());
            txt_Message.setText("");
        }

        private  void btn_Send_RightClicked(MouseEvent e) {
            //Toolkit toolkit = Toolkit.getDefaultToolkit();
            //Clipboard clipboard = toolkit.getSystemClipboard();

            try {
                //String message = (String) clipboard.getData(DataFlavor.stringFlavor);
                String message = txt_Message.getText();
                if (message.length() > 0) {
                    switch (message.split(":")[0]) {
                        case "BigInteger": {
                            message = CryptConverter.toHexString(new BigInteger(message.substring(12))).toString();
                            break;
                        }
                        case "HexString": {
                            message = message.substring(11).toString();
                            break;
                        }
                    }
                    chatClient.sendMessage(message);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        private void chk_DisableOAEP_ActionPerformed(ActionEvent e) {
            if (chk_DisableOAEP.isSelected()) {
                //region Disable OAEP UI
                    //Setting "flag" depending on original OAEP border's text
                    //"flag" = OAEP text fields Enabled/Disabled
                    if (pnl_OAEP.getBorder() instanceof TitledBorder) {
                        TitledBorder border = (TitledBorder) pnl_OAEP.getBorder();
                        if (border.getTitle().equals("After OAEP Encoding")) {
                            txt_OAEP_Dec.setEnabled(false);
                            txt_OAEP_Byte.setEnabled(false);
                            txt_OAEP_Hex.setEnabled(false);
                            txt_OAEP_Bin.setEnabled(false);
                        }
                    }
                    pnl_OAEP.setBorder((BorderFactory.createTitledBorder("OAEP")));
                    pnl_OAEP.setEnabled(false);
                        tab_OAEP.setEnabled(false);
                            pnl_OAEP_Dec.setEnabled(false);
                                scp_OAEP_Dec.setVisible(false);
                            pnl_OAEP_Byte.setEnabled(false);
                                scp_OAEP_Byte.setVisible(false);
                            pnl_OAEP_Hex.setEnabled(false);
                                scp_OAEP_Hex.setVisible(false);
                            pnl_OAEP_Bin.setEnabled(false);
                                scp_OAEP_bin.setVisible(false);
                //endregion Disable OAEP UI
            } else {
                //region Enable OAEP UI
                    pnl_OAEP.setEnabled(true);
                        tab_OAEP.setEnabled(true);
                            pnl_OAEP_Dec.setEnabled(true);
                                scp_OAEP_Dec.setVisible(true);
                            pnl_OAEP_Byte.setEnabled(true);
                                scp_OAEP_Byte.setVisible(true);
                            pnl_OAEP_Hex.setEnabled(true);
                                scp_OAEP_Hex.setVisible(true);
                            pnl_OAEP_Bin.setEnabled(true);
                                scp_OAEP_bin.setVisible(true);
                    //Restoring original OAEP border's text depending on "flag", if text fields are not empty
                    //"flag" = OAEP text fields Enabled/Disabled
                    if (txt_OAEP_Dec.getText().equals("") && txt_OAEP_Byte.getText().equals("") && txt_OAEP_Hex.getText().equals("")) {
                        pnl_OAEP.setBorder((BorderFactory.createTitledBorder("OAEP")));
                    } else if (txt_OAEP_Dec.isEnabled() && txt_OAEP_Byte.isEnabled() && txt_OAEP_Hex.isEnabled()) {
                        pnl_OAEP.setBorder((BorderFactory.createTitledBorder("Before OAEP Decoding")));
                    } else {
                        pnl_OAEP.setBorder((BorderFactory.createTitledBorder("After OAEP Encoding")));
                        txt_OAEP_Dec.setEnabled(true);
                        txt_OAEP_Byte.setEnabled(true);
                        txt_OAEP_Hex.setEnabled(true);
                        txt_OAEP_Bin.setEnabled(true);
                    }
                //endregion Enable OAEP UI
            }
        }

        private void chk_DisableRSA_ActionPerformed(ActionEvent e) {
            if (chk_DisableRSA.isSelected()) {
                //region Disable RSA UI
                    //Setting "flag" depending on original RSA border's text
                    //"flag" = RSA text fields Enabled/Disabled
                    if (pnl_RSA.getBorder() instanceof TitledBorder) {
                        TitledBorder border = (TitledBorder) pnl_RSA.getBorder();
                        if (border.getTitle().equals("After RSA Encryption")) {
                            txt_RSA_Dec.setEnabled(false);
                            txt_RSA_Byte.setEnabled(false);
                            txt_RSA_Hex.setEnabled(false);
                            txt_RSA_Bin.setEnabled(false);
                        }
                    }
                    pnl_RSA.setBorder((BorderFactory.createTitledBorder("RSA")));
                    pnl_RSA.setEnabled(false);
                        tab_RSA.setEnabled(false);
                            pnl_RSA_Dec.setEnabled(false);
                                scp_RSA_Dec.setVisible(false);
                            pnl_RSA_Byte.setEnabled(false);
                                scp_RSA_Byte.setVisible(false);
                            pnl_RSA_Hex.setEnabled(false);
                                scp_RSA_Hex.setVisible(false);
                            pnl_RSA_Bin.setEnabled(false);
                                scp_RSA_Bin.setVisible(false);
                //endregion Disable RSA UI
                //region Disable Keys UI
                    pnl_Keys.setEnabled(false);
                        tab_Keys.setEnabled(false);
                            //region Disable Client Keys UI
                                pnl_Keys_Client.setEnabled(false);
                                    tab_Keys_Client.setEnabled(false);
                                        //region Disable Client's PublicKey(n) UI
                                            pnl_Keys_Client_n.setEnabled(false);
                                                tab_Keys_Client_n.setEnabled(false);
                                                    pnl_Keys_Client_n_Dec.setEnabled(false);
                                                        scp_Keys_Client_n_Dec.setVisible(false);
                                                    pnl_Keys_Client_n_Byte.setEnabled(false);
                                                        scp_Keys_Client_n_Byte.setVisible(false);
                                                    pnl_Keys_Client_n_Hex.setEnabled(false);
                                                        scp_Keys_Client_n_Hex.setVisible(false);
                                                    pnl_Keys_Client_n_Bin.setEnabled(false);
                                                        scp_Keys_Client_n_Bin.setVisible(false);
                                        //endregion Disable Client's PublicKey(n) UI
                                        //region Disable Client's PublicKey(e) UI
                                            pnl_Keys_Client_e.setEnabled(false);
                                                tab_Keys_Client_e.setEnabled(false);
                                                    pnl_Keys_Client_e_Dec.setEnabled(false);
                                                        scp_Keys_Client_e_Dec.setVisible(false);
                                                    pnl_Keys_Client_e_Byte.setEnabled(false);
                                                        scp_Keys_Client_e_Byte.setVisible(false);
                                                    pnl_Keys_Client_e_Hex.setEnabled(false);
                                                        scp_Keys_Client_e_Hex.setVisible(false);
                                                    pnl_Keys_Client_e_Bin.setEnabled(false);
                                                        scp_Keys_Client_e_Bin.setVisible(false);
                                        //endregion Disable Client's PublicKey(e) UI
                            //endregion Disable Client Keys UI
                            //region Disable Server Keys UI
                                pnl_Keys_Server.setEnabled(false);
                                    tab_Keys_Server.setEnabled(false);
                                        //region Disable Server's PublicKey(n) UI
                                            pnl_Keys_Server_n.setEnabled(false);
                                                tab_Keys_Server_n.setEnabled(false);
                                                    pnl_Keys_Server_n_Dec.setEnabled(false);
                                                        scp_Keys_Server_n_Dec.setVisible(false);
                                                    pnl_Keys_Server_n_Byte.setEnabled(false);
                                                        scp_Keys_Server_n_Byte.setVisible(false);
                                                    pnl_Keys_Server_n_Hex.setEnabled(false);
                                                        scp_Keys_Server_n_Hex.setEnabled(false);
                                                    pnl_Keys_Server_n_Bin.setEnabled(false);
                                                        scp_Keys_Server_n_Bin.setEnabled(false);
                                        //endregion Disable Server's PublicKey(n) UI
                                        //region Disable Server's PublicKey(e) UI
                                            pnl_Keys_Server_e.setEnabled(false);
                                                tab_Keys_Server_e.setEnabled(false);
                                                    pnl_Keys_Server_e_Dec.setEnabled(false);
                                                        scp_Keys_Server_e_Dec.setVisible(false);
                                                    pnl_Keys_Server_e_Byte.setEnabled(false);
                                                        scp_Keys_Server_e_Byte.setVisible(false);
                                                    pnl_Keys_Server_e_Hex.setEnabled(false);
                                                        scp_Keys_Server_e_Hex.setVisible(false);
                                                    pnl_Keys_Server_e_Bin.setEnabled(false);
                                                        scp_Keys_Server_e_Bin.setVisible(false);
                                        //endregion Disable Server's PublicKey(e) UI
                                        //region Disable Server's PrivateKey(d) UI
                                            pnl_Keys_Server_d.setEnabled(false);
                                                tab_Keys_Server_d.setEnabled(false);
                                                    pnl_Keys_Server_d_Dec.setEnabled(false);
                                                        scp_Keys_Server_d_Dec.setVisible(false);
                                                    pnl_Keys_Server_d_Byte.setEnabled(false);
                                                        scp_Keys_Server_d_Byte.setVisible(false);
                                                    pnl_Keys_Server_d_Hex.setEnabled(false);
                                                        scp_Keys_Server_d_Hex.setVisible(false);
                                                    pnl_Keys_Server_d_Bin.setEnabled(false);
                                                        scp_Keys_Server_d_Bin.setVisible(false);
                                        //endregion Disable Server's PrivateKey(d) UI
                            //endregion Disable Server Keys UI
                //endregion Disable Keys UI
            } else {
                //region Enabling RSA UI
                    pnl_RSA.setEnabled(true);
                        tab_RSA.setEnabled(true);
                            pnl_RSA_Dec.setEnabled(true);
                                scp_RSA_Dec.setVisible(true);
                            pnl_RSA_Byte.setEnabled(true);
                                scp_RSA_Byte.setVisible(true);
                            pnl_RSA_Hex.setEnabled(true);
                                scp_RSA_Hex.setVisible(true);
                            pnl_RSA_Bin.setEnabled(true);
                                scp_RSA_Bin.setVisible(true);
                    //Restoring original RSA border's text depending on "flag", if text fields are not empty
                    //"flag" = RSA text fields Enabled/Disabled
                    if (txt_RSA_Dec.getText().equals("") && txt_RSA_Byte.getText().equals("") && txt_RSA_Hex.getText().equals("")) {
                        pnl_RSA.setBorder((BorderFactory.createTitledBorder("RSA")));
                    } else if (txt_RSA_Dec.isEnabled() && txt_RSA_Byte.isEnabled() && txt_RSA_Hex.isEnabled()) {
                        pnl_RSA.setBorder((BorderFactory.createTitledBorder("Before RSA Decryption")));
                    } else {
                        pnl_RSA.setBorder((BorderFactory.createTitledBorder("After RSA Encryption")));
                        txt_RSA_Dec.setEnabled(true);
                        txt_RSA_Byte.setEnabled(true);
                        txt_RSA_Hex.setEnabled(true);
                        txt_RSA_Bin.setEnabled(true);
                    }
                //endregion Enabling RSA UI
                //region Enable Keys UI
                    pnl_Keys.setEnabled(true);
                        tab_Keys.setEnabled(true);
                            //region Enable Client Keys UI
                                pnl_Keys_Client.setEnabled(true);
                                    tab_Keys_Client.setEnabled(true);
                                        //region Enable Client's PublicKey(n) UI
                                            pnl_Keys_Client_n.setEnabled(true);
                                                tab_Keys_Client_n.setEnabled(true);
                                                    pnl_Keys_Client_n_Dec.setEnabled(true);
                                                        scp_Keys_Client_n_Dec.setVisible(true);
                                                    pnl_Keys_Client_n_Byte.setEnabled(true);
                                                        scp_Keys_Client_n_Byte.setVisible(true);
                                                    pnl_Keys_Client_n_Hex.setEnabled(true);
                                                        scp_Keys_Client_n_Hex.setVisible(true);
                                                    pnl_Keys_Client_n_Bin.setEnabled(true);
                                                        scp_Keys_Client_n_Bin.setVisible(true);
                                        //endregion Enable Client's PublicKey(n) UI
                                        //region Enable Client's PublicKey(e) UI
                                            pnl_Keys_Client_e.setEnabled(true);
                                                tab_Keys_Client_e.setEnabled(true);
                                                    pnl_Keys_Client_e_Dec.setEnabled(true);
                                                        scp_Keys_Client_e_Dec.setVisible(true);
                                                    pnl_Keys_Client_e_Byte.setEnabled(true);
                                                        scp_Keys_Client_e_Byte.setVisible(true);
                                                    pnl_Keys_Client_e_Hex.setEnabled(true);
                                                        scp_Keys_Client_e_Hex.setVisible(true);
                                                    pnl_Keys_Client_e_Bin.setEnabled(true);
                                                        scp_Keys_Client_e_Bin.setVisible(true);
                                        //endregion Enable Client's PublicKey(e) UI
                            //endregion Enable Client Keys UI
                            //region Enable Server Keys UI
                                pnl_Keys_Server.setEnabled(true);
                                    tab_Keys_Server.setEnabled(true);
                                        //region Enable Server's PublicKey(n) UI
                                            pnl_Keys_Server_n.setEnabled(true);
                                                tab_Keys_Server_n.setEnabled(true);
                                                    pnl_Keys_Server_n_Dec.setEnabled(true);
                                                        scp_Keys_Server_n_Dec.setVisible(true);
                                                    pnl_Keys_Server_n_Byte.setEnabled(true);
                                                        scp_Keys_Server_n_Byte.setVisible(true);
                                                    pnl_Keys_Server_n_Hex.setEnabled(true);
                                                        scp_Keys_Server_n_Hex.setEnabled(true);
                                                    pnl_Keys_Server_n_Bin.setEnabled(true);
                                                        scp_Keys_Server_n_Bin.setEnabled(true);
                                        //endregion Enable Server's PublicKey(n) UI
                                        //region Enable Server's PublicKey(e) UI
                                            pnl_Keys_Server_e.setEnabled(true);
                                                tab_Keys_Server_e.setEnabled(true);
                                                    pnl_Keys_Server_e_Dec.setEnabled(true);
                                                        scp_Keys_Server_e_Dec.setVisible(true);
                                                    pnl_Keys_Server_e_Byte.setEnabled(true);
                                                        scp_Keys_Server_e_Byte.setVisible(true);
                                                    pnl_Keys_Server_e_Hex.setEnabled(true);
                                                        scp_Keys_Server_e_Hex.setVisible(true);
                                                    pnl_Keys_Server_e_Bin.setEnabled(true);
                                                        scp_Keys_Server_e_Bin.setVisible(true);
                                        //endregion Enable Server's PublicKey(e) UI
                                        //region Enable Server's PrivateKey(d) UI
                                            pnl_Keys_Server_d.setEnabled(true);
                                                tab_Keys_Server_d.setEnabled(true);
                                                    pnl_Keys_Server_d_Dec.setEnabled(true);
                                                        scp_Keys_Server_d_Dec.setVisible(true);
                                                    pnl_Keys_Server_d_Byte.setEnabled(true);
                                                        scp_Keys_Server_d_Byte.setVisible(true);
                                                    pnl_Keys_Server_d_Hex.setEnabled(true);
                                                        scp_Keys_Server_d_Hex.setVisible(true);
                                                    pnl_Keys_Server_d_Bin.setEnabled(true);
                                                        scp_Keys_Server_d_Bin.setVisible(true);
                                        //endregion Enable Server's PrivateKey(d) UI
                            //endregion Enable Server Keys UI
                //endregion Enable Keys UI
            }
        }

        private void txt_Connect_Changed(DocumentEvent e) {
            if (IpV4address.isIpV4Address(txt_Connect.getText())) {
                btn_Connect.setEnabled(true);
            } else {
                btn_Connect.setEnabled(false);
            }
        }

        private void txt_Message_KeyTyped(KeyEvent e) {
            if (e.getKeyChar() == '\n' || e.getKeyChar() == '\r') {
                sendMessage(txt_Message.getText());
                txt_Message.setText("");
            }
        }

        private void txt_Messages_Clicked(MouseEvent e) {
            if (e.isPopupTrigger() || e.getButton() == 3) {
                JPopupMenu mnu_Messages = new JPopupMenu();
                JMenuItem mni_Clear = new JMenuItem("Clear");

                mni_Clear.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        txt_Messages.setText("");
                    }
                });
                mnu_Messages.add(mni_Clear);

                mnu_Messages.show(e.getComponent(), e.getX(), e.getY());
            }
        }
        //endregion GUI Event handler(s)

        //region chatServer Event handlers
        private void chatServer_ConnectionClosed(ChatServer source, boolean byClient) {
            if (byClient) {
                appendLog("[Server] - Connection closed by client.");
            } else {
                appendLog("[Server] - Connection closed.");
            }
            txt_ServerAuthStatus.setText("Not authenticated");
            txt_ServerAuthStatus.setForeground(Color.black);
        }
        private void chatServer_ConnectionReceived(ChatServer source, String connectFrom) {
            appendLog("[Server] - Connection received from " + connectFrom + ".");

            chatRSA = new RSA(2048);
            if (chk_ServerAuthConf.isSelected()) {
                appendLog("[Server] - Requesting authentication");

                pnl_AUTH_REQ_Server.setVisible(false);
                pnl_AUTH_REP_Server.setVisible(false);
                pnl_AUTH_OK_Server.setVisible(false);
                pnl_ST_Server.setVisible(false);

                txt_AUTH_REQ_Server.setText(dtf.format(LocalDateTime.now()) + "\n" + this.getTitle());
                pnl_AUTH_REQ_Server.setVisible(true);
                txt_ServerAuthStatus.setText("Requested");
                txt_ServerAuthStatus.setForeground(Color.blue);
                source.sendMessage("AUTH_REQ" + (char)0 + "ts: " + dtf.format(LocalDateTime.now()) + (char)0 + "srv: " + this.getTitle());
            } else {
                send_RSA_PKey();
            }
        }

        private void send_RSA_PKey() {
            txt_Keys_Server_n_Dec.setText(CryptFormatter.toDecFormat(chatRSA.PrivateKey.modulus));
            txt_Keys_Server_n_Byte.setText(CryptFormatter.toByteFormat(chatRSA.PrivateKey.modulus.toByteArray()));
            txt_Keys_Server_n_Hex.setText(CryptFormatter.toHexFormat(new HexString(chatRSA.PrivateKey.modulus.toByteArray())));
            txt_Keys_Server_n_Bin.setText(CryptFormatter.toBinaryFormat(new BinaryString(chatRSA.PrivateKey.modulus.toByteArray())));
            txt_Keys_Server_e_Dec.setText(CryptFormatter.toDecFormat(chatRSA.PublicKey.exponent));
            txt_Keys_Server_e_Byte.setText(CryptFormatter.toByteFormat(chatRSA.PublicKey.exponent.toByteArray()));
            txt_Keys_Server_e_Hex.setText(CryptFormatter.toHexFormat(new HexString(chatRSA.PublicKey.exponent.toByteArray())));
            txt_Keys_Server_e_Bin.setText(CryptFormatter.toBinaryFormat(new BinaryString(chatRSA.PublicKey.exponent.toByteArray())));
            txt_Keys_Server_d_Dec.setText(CryptFormatter.toDecFormat(chatRSA.PrivateKey.exponent));
            txt_Keys_Server_d_Byte.setText(CryptFormatter.toByteFormat(chatRSA.PrivateKey.exponent.toByteArray()));
            txt_Keys_Server_d_Hex.setText(CryptFormatter.toHexFormat(new HexString(chatRSA.PrivateKey.exponent.toByteArray())));
            txt_Keys_Server_d_Bin.setText(CryptFormatter.toBinaryFormat(new BinaryString(chatRSA.PrivateKey.exponent.toByteArray())));

            chatServer.sendMessage("PublicKey(n)=" + new HexString(chatRSA.PublicKey.modulus));
            chatServer.sendMessage("PublicKey(e)=" + new HexString(chatRSA.PublicKey.exponent));
            appendLog("[Server] - RSA PublicKey(n,e) sent.");
        }
        private void chatServer_Initialized(ChatServer source) {
            appendLog("[Server] - Server initialized.");
        }
        private void chatServer_MessageReceived(ChatServer source, String message, String msgFrom) {
            switch (message.split(String.valueOf((char)0))[0]) {
                case "msg: RSA_PKEY_REQ" -> {
                    appendLog("[Server] - RSA PublicKey requested without auhentification.");

                    send_RSA_PKey();
                }
                case "msg: AUTH_REP" -> {
                    appendLog("[Server] - AUTH_ID (encrypted with \"Session key\" and \"Service Ticket\" (encrypted with Server's key) received.");

                    chatServer_ST = message.split(String.valueOf((char)0))[2];

                    message = message.split(String.valueOf((char)0))[1];

                    chatServer_SK = (new TGS_ST(AES.decrypt(new HexString(chatServer_ST), serverKey).split(String.valueOf((char)0)))).getSk();

                    txt_ST_Server.setText((new TGS_ST(AES.decrypt(new HexString(chatServer_ST), serverKey).split(String.valueOf((char)0)))).getSk().toString().substring(0, 25) + "..." + "\n" +
                            dtf.format((new TGS_ST(AES.decrypt(new HexString(chatServer_ST), serverKey).split(String.valueOf((char)0)))).getExp()) + "\n" +
                            (new TGS_ST(AES.decrypt(new HexString(chatServer_ST), serverKey).split(String.valueOf((char)0)))).getUid() + "\n" +
                            (new TGS_ST(AES.decrypt(new HexString(chatServer_ST), serverKey).split(String.valueOf((char)0)))).getAddr().toString() + "\n" +
                            dtf.format((new TGS_ST(AES.decrypt(new HexString(chatServer_ST), serverKey).split(String.valueOf((char)0)))).getTs()));
                    pnl_ST_Server.setVisible(true);

                    appendLog("[Server] - Session key extracted from \"Service Ticket\"");

                    message = AES.decrypt(new HexString(message), chatServer_SK);

                    txt_AUTH_REP_Server.setText(message.substring(message.indexOf(":") +1).trim());
                    pnl_AUTH_REP_Server.setVisible(true);

                    if((new TGS_ST(AES.decrypt(new HexString(chatServer_ST), serverKey).split(String.valueOf((char)0)))).getUid().equals(message.substring(message.indexOf(":") +1).trim()) ) {
                        appendLog("[Server] - AUTH_REP decrypted using \"SessionKey\".");
                        appendLog("[Server] - User ID from AUTH_REP is same as User ID from \"Service Ticket\"");

                        txt_AUTH_OK_Server.setText(message.substring(message.indexOf(":") +1).trim());
                        pnl_AUTH_OK_Server.setVisible(true);

                        txt_ServerAuthStatus.setText("\"" + message.substring(message.indexOf(":") +1).trim() +"\" " + "Authenticated.");
                        txt_ServerAuthStatus.setForeground(Color.green);
                        chatServer.sendMessage("msg: AUTH_OK" + (char)0 + AES.encrypt(message.substring(message.indexOf(":") +1).trim(),chatServer_SK));
                        appendLog("[Server] - AUTH_OK (encrypted with \"Session Key\") sent.");
                    }
                }
                default -> {
                    appendLog("[Server] - Message received from " + msgFrom + ".");

                    processMessage(message, msgFrom);
                }
            }
        }
        private void chatServer_Stopped(ChatServer source) {
            appendLog("[Server] - Server stopped.");
        }
        private void chatServer_WaitingForConnection(ChatServer source, int port) {
            appendLog("[Server] - Waiting for connection on port: " + port + ".");
        }
        //endregion chatServer Event Handlers
        //region chatClient Event Handlers
        private void chatClient_Connected(ChatClient source, IpV4address remoteAddress, int remotePort) {
            lbl_Connect.setText("Connected to:");
            txt_Connect.setEnabled(false);
            txt_Port.setEnabled(false);
            btn_Connect.setText("Disconnect");
            txt_Message.setEnabled(true);
            btn_Send.setEnabled(true);

            appendLog("[Client] - Connected to /" + remoteAddress + ":" + remotePort);
        }
        private void chatClient_ConnectFailed(ChatClient source, IpV4address remoteAddress,  int remotePort, String failMessage) {
            appendLog("[Client] - Failed to connect to /"+remoteAddress + ":" + remotePort + " (" + failMessage + ")");
        }
        private void chatClient_Disconnected() {
            lbl_Connect.setText("Connect to:");
            txt_Connect.setEnabled(true);
            txt_Port.setEnabled(true);
            btn_Connect.setText("Connect");
            txt_Message.setEnabled(false);
            btn_Send.setEnabled(false);
            dest_RSAPublicKey = new RSAKey(BigInteger.ZERO, BigInteger.ZERO);

            appendLog("[Client] - Client disconnected from /" + txt_Connect.getText() + ":" + txt_Port.getText() + ".");
        }
        private void chatClient_DisconnectFailed(String failMessage) {
            appendLog("[Client] - Failed to disconnect: " + failMessage);
        }
        private void chatClient_Initialized(ChatClient source) {
            appendLog("[Client] - Initialized.");
        }
        private void chatClient_MessageReceived(String message, String msgFrom) {
            if (message.split(String.valueOf((char)0))[0].equals("AUTH_REQ")) {
                //appendLog("[Client] - Requesting RSA PublicKey.");

                appendLog("[Client] - Authentication requested by remote server.");

                txt_ClientAuthStatus.setText("Authenticating.");
                txt_ClientAuthStatus.setForeground(Color.blue);

                pnl_AUTH_REQ_Client.setVisible(false);
                pnl_AS_REQ.setVisible(false);
                pnl_AS_REP.setVisible(false);
                pnl_AS_TGT.setVisible(false);
                pnl_TGS_REQ.setVisible(false);
                pnl_TGS_TGT.setVisible(false);
                pnl_TGS_REP.setVisible(false);
                pnl_TGS_ST.setVisible(false);
                pnl_ST_Client.setVisible(false);
                pnl_AUTH_REP_Client.setVisible(false);
                pnl_AUTH_OK_Client.setVisible(false);
                pnl_AUTH_ERR.setVisible(false);

                if (message.length() > 8) {
                    if (IpV4address.isIpV4Address(txt_KDCAddress.getText())) {
                        chat_TGT = null;
                        String password = doAuthenticate();
                        if (!password.equals("")) {
                            appendLog("[Client] - Encrypting current TimeStamp with key generated using entered password & client name.");
                            clientKey = AES.getPasswordKey(password + (char) 0 + this.getTitle());
                            password = null;
                            chat_AS_REQ = new AS_REQ();
                            chat_AS_REQ.setTs(AES.encrypt(dtf.format(LocalDateTime.now()), clientKey));
                            chat_AS_REQ.setUid(this.getTitle());
                            chat_AS_REQ.setReq(message.split(String.valueOf((char)0))[2].substring(message.split(String.valueOf((char)0))[2].indexOf(":") + 1).trim());
                            chat_AS_REQ.setAddr(new IpV4address(chatClient.getLocalAddress().replace("/", "")));

                            txt_AUTH_REQ_Client_Value.setText(message.split(String.valueOf((char)0))[1].substring(message.split(String.valueOf((char)0))[1].indexOf(":") + 1) + "\n" +
                                                                message.split(String.valueOf((char)0))[2].substring(message.split(String.valueOf((char)0))[2].indexOf(":") + 1));
                            pnl_AUTH_REQ_Client.setVisible(true);

                            txt_AS_REQ.setText(AES.decrypt(chat_AS_REQ.getTs(),clientKey) + "\n" + chat_AS_REQ.getUid() + "\n" + chat_AS_REQ.getReq() + "\n" + chat_AS_REQ.getAddr().toString());
                            txt_AS_REQ_ts.setText(chat_AS_REQ.getTs().toString());
                            pnl_AS_REQ.setVisible(true);

                            appendLog("[Client] - Connecting to KDC: " + txt_KDCAddress.getText() + ":" + txt_KDCPort.getText());
                            kdcClient.connect(new IpV4address(txt_KDCAddress.getText()), Integer.parseInt(txt_KDCPort.getText()));
                        } else {
                            appendLog("[Client] - Authentication canceled by user.");
                            txt_ClientAuthStatus.setText("Canceled");
                            txt_ClientAuthStatus.setForeground(Color.black);
                            disconnectClient();
                            kdcClient.disconnect();
                        }
                    } else {
                        appendLog("[Client] - Unable to authenticate (Invalid KDC address).");
                        chatClient.disconnect();
                    }
                } else {
                    appendLog("[Client] - Unable to authenticate (cannot identify server).");
                }
                //chatClient.sendMessage("RSA_PKEY_REQ");
            } else {
                if (message.substring(0, 12).equals("msg: AUTH_OK")) {
                    appendLog("[Client] - AUTH_OK received.");

                    txt_AUTH_OK_Client.setText(this.getTitle());
                    pnl_AUTH_OK_Client.setVisible(true);

                    txt_ClientAuthStatus.setText("Authenticated");
                    txt_ClientAuthStatus.setForeground(Color.green);

                    chatClient.sendMessage("msg: RSA_PKEY_REQ");
                    appendLog("[Client] - Request for RSA PublicKey sent.");
                } else {
                    if (message.substring(0, 9).equals("PublicKey")) {
                        switch (message.substring(0, 13)) {
                            case "PublicKey(n)=":
                                System.out.println("[Client<" + txt_Port.getText() + ">] - PublicKey(n) received.");
                                dest_RSAPublicKey.modulus = new BigInteger((new HexString(message.substring(13))).toByteArray());
                                break;
                            case "PublicKey(e)=":
                                System.out.println("[Client<" + txt_Port.getText() + ">] - PublicKey(e) received.");
                                dest_RSAPublicKey.exponent = new BigInteger((new HexString(message.substring(13))).toByteArray());
                                break;
                        }
                        if (!dest_RSAPublicKey.modulus.equals(BigInteger.ZERO) && !dest_RSAPublicKey.exponent.equals(BigInteger.ZERO)) {
                            txt_Keys_Client_n_Dec.setText(CryptFormatter.toDecFormat(dest_RSAPublicKey.modulus));
                            txt_Keys_Client_n_Byte.setText(CryptFormatter.toByteFormat(dest_RSAPublicKey.modulus.toByteArray()));
                            txt_Keys_client_n_Hex.setText(CryptFormatter.toHexFormat(new HexString(dest_RSAPublicKey.modulus)));
                            txt_Keys_Client_n_Bin.setText(CryptFormatter.toBinaryFormat(new BinaryString(dest_RSAPublicKey.modulus)));
                            txt_Keys_Client_e_Dec.setText(CryptFormatter.toDecFormat(dest_RSAPublicKey.exponent));
                            txt_Keys_Client_e_Byte.setText(CryptFormatter.toByteFormat(dest_RSAPublicKey.exponent.toByteArray()));
                            txt_Keys_Client_e_Hex.setText(CryptFormatter.toHexFormat(new HexString(dest_RSAPublicKey.exponent)));
                            txt_Keys_Client_e_Bin.setText(CryptFormatter.toBinaryFormat(new BinaryString(dest_RSAPublicKey.exponent)));

                            System.out.println("[Client<" + txt_Port.getText() + ">] - Remote PublicKey(n,e) received.");
                            appendLog("[Client] - Remote PublicKey(n,e) received from " + msgFrom + ".");
                        }
                    }
                }
            }
        }
        //endregion chatClient Event handlers
        //region kdcClient Event Handlers
        private void kdcClient_Connected(KDCClient source, IpV4address remoteAddress, int remotePort) {
            appendLog("[Client] - Connected to KDC: " + remoteAddress.toString() + ":" + String.valueOf(remotePort));
            if (chat_TGT == null) {
                appendLog("[Client] - Sending AS_REQ (with encrypted TimeStamp) to KDC.");
                kdcClient.sendMessage("msg: AS_REQ" + (char)0 + chat_AS_REQ.getPackage());
            } else {
                appendLog("[Client] - Sending TGS_REQ (encrypted with \"Short Term Key\") with \"Ticket Granting Ticket\" (encrypted with TGS key) attached to KDC.");
                kdcClient.sendMessage("msg: TGS_REQ" +(char)0 + AES.encrypt(chat_TGS_REQ.getPackage(), shortTermKey) + (char)0 + chat_TGT);
            }
        }
        private void kdcClient_ConnectFailed(KDCClient source, IpV4address remoteAddress,  int remotePort, String failMessage) {
            appendLog("[Client] - Failed to connect to KDC. (" + failMessage + ")");
            chatClient.disconnect();
        }
        private void kdcClient_Disconnected() {
            appendLog("[Client] - Disconnected from KDC.");
        }
        private void kdcClient_DisconnectFailed(String failMessage) {
            appendLog("[Client] - Failed to disconnect from KDC: " + failMessage);
        }
        private void kdcClient_Initialized(KDCClient source) {

        }
        private void kdcClient_MessageReceived(String message, String msgFrom) {
            switch (message.split(String.valueOf((char)0))[0]) {
                case "msg: AUTH_ERR" -> {
                    txt_AUTH_ERR.setText("Authentication refused by AS:\n<" + message.split(String.valueOf((char)0))[1] + ">");
                    pnl_AUTH_ERR.setVisible(true);
                    appendLog("[Client] - Authentication refused by AS: <" + message.split(String.valueOf((char)0))[1] + ">");
                    kdcClient.disconnect();
                    chatClient.disconnect();
                    txt_ClientAuthStatus.setText("Failed.");
                    txt_ClientAuthStatus.setForeground(Color.red);
                }
                case "msg: AS_REP" -> {
                    appendLog("[Client] - AS_REP (encrypted with own key) with attached \"Ticket Granting Ticket\" (encrypted with TGS key) received from KDC.");

                    chat_TGT = message.split(String.valueOf((char)0))[2];
                    LocalDateTime tmp_ts;
                    IpV4address tmp_addr_ip;
                    int tmp_addr_port;

                    message = message.split(String.valueOf((char)0))[0] + (char)0 +
                            AES.decrypt(new HexString(message.split(String.valueOf((char)0))[1]), clientKey) + (char)0 ;
                    tmp_ts = new AS_REP(message.split(String.valueOf((char)0))).getTs();

                    if (LocalDateTime.now().minusMinutes(5).compareTo(tmp_ts) < 0 && LocalDateTime.now().plusMinutes(5).compareTo(tmp_ts) > 0) {
                        appendLog("[Client] - TimeStamp received from KDC is within required limits.");

                        txt_AS_REP.setText(new AS_REP(message.split(String.valueOf((char) 0))).getSTK().toString().substring(0, 25) + "..." + "\n" +
                                dtf.format(new AS_REP(message.split(String.valueOf((char) 0))).getTs()) + "\n" +
                                new AS_REP(message.split(String.valueOf((char) 0))).getAddr() + "\n" +
                                dtf.format(new AS_REP(message.split(String.valueOf((char) 0))).getExp()));
                        pnl_AS_REP.setVisible(true);

                        txt_AS_TGT.setText(chat_TGT);
                        pnl_AS_TGT.setVisible(true);

                        shortTermKey = new AS_REP(message.split(String.valueOf((char)0))).getSTK();
                        appendLog("[Client] - AS_REP decrypted, \"Short Term Key\" and \"Ticket Granting Ticket\" extracted.");

                        kdcClient.disconnect();
                        appendLog("[Client] - Client disconnected from KDC (" + txt_KDCAddress.getText() + ":" + txt_KDCPort.getText() + ").");

                        tmp_addr_ip = new IpV4address((new AS_REP(message.split(String.valueOf((char) 0))).getAddr()).split(":")[0]);
                        tmp_addr_port = Integer.parseInt((new AS_REP(message.split(String.valueOf((char) 0))).getAddr()).split(":")[1]);

                        chat_TGS_REQ = new TGS_REQ();
                        chat_TGS_REQ.setUid(this.getTitle());
                        chat_TGS_REQ.setReq(chat_AS_REQ.getReq());

                        txt_TGS_REQ.setText(dtf.format(chat_TGS_REQ.getTs()) + "\n" +
                                chat_TGS_REQ.getUid() + "\n" +
                                chat_TGS_REQ.getReq());
                        pnl_TGS_REQ.setVisible(true);

                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            System.out.println("Thread: End.");
                        }

                        txt_TGS_TGT.setText(chat_TGT);
                        pnl_TGS_TGT.setVisible(true);

                        kdcClient.connect(tmp_addr_ip, tmp_addr_port);
                    } else {
                        appendLog("[Client] - Received TimeStamp is not within accepted limits. -> Authentication failed.");
                        kdcClient.disconnect();
                        chatClient.disconnect();
                    }
                }
                case "msg: TGS_REP" -> {
                    appendLog("[Client] - TGS_REP (encrypted with \"Short Term Key\") with attached \"Service Ticket\" (encrypted with Server's key) received from KDC.");

                    chatClient_ST = message.split(String.valueOf((char)0))[2];
                    LocalDateTime tmp_ts;

                    message = message.split(String.valueOf((char)0))[0] + (char)0 +
                            AES.decrypt(new HexString(message.split(String.valueOf((char)0))[1]), shortTermKey) + (char)0 ;
                    tmp_ts = new TGS_REP(message.split(String.valueOf((char)0))).getTs();

                    if (LocalDateTime.now().minusMinutes(5).compareTo(tmp_ts) < 0 && LocalDateTime.now().plusMinutes(5).compareTo(tmp_ts) > 0) {
                        appendLog("[Client] - TimeStamp received from KDC is within required limits.");

                        txt_TGS_REP.setText(new TGS_REP(message.split(String.valueOf((char) 0))).getSk().toString().substring(0, 25) + "..." + "\n" +
                                dtf.format(new TGS_REP(message.split(String.valueOf((char) 0))).getExp()) + "\n" +
                                new TGS_REP(message.split(String.valueOf((char) 0))).getDest() + "\n" +
                                dtf.format(new TGS_REP(message.split(String.valueOf((char) 0))).getTs()));
                        pnl_TGS_REP.setVisible(true);

                        txt_TGS_ST.setText(chatClient_ST);
                        pnl_TGS_ST.setVisible(true);

                        chatClient_SK = new TGS_REP(message.split(String.valueOf((char)0))).getSk();
                        appendLog("[Client] - TGS_REP decrypted, \"Session Key\" and \"Service Ticket\" extracted.");

                        kdcClient.disconnect();

                        txt_AUTH_REP_Client.setText(this.getTitle());
                        pnl_AUTH_REP_Client.setVisible(true);

                        txt_ST_Client.setText(chatClient_ST);
                        pnl_ST_Client.setVisible(true);

                        chatClient.sendMessage("msg: AUTH_REP" + (char)0 + AES.encrypt("uid: " + this.getTitle(),chatClient_SK) + (char)0 + chatClient_ST);
                        appendLog("[Client] - AUTH_REP and \"Service Ticket\" sent to Server.");
                    } else {
                        appendLog("[Client] - Received TimeStamp is not within accepted limits. -> Authentication failed.");
                        kdcClient.disconnect();
                        chatClient.disconnect();
                    }
                }
            }
        }
        //endregion kdcClient Event handlers
    //endregion Event handler(s)

    //region Private method(s)
        private void appendLog(String log) {
 //           Runnable  runnable = new Runnable() {
 //               public void run() {
                    boolean doScroll = scr_Log.getVerticalScrollBar().getMaximum() == scr_Log.getVerticalScrollBar().getValue() + scr_Log.getVerticalScrollBar().getVisibleAmount();
                    if (!txt_Log.getText().equals("")) {
                        txt_Log.append("\n");
                    }
                    LocalDateTime now = LocalDateTime.now();
                    txt_Log.append("[" + dtf.format(now) + "] - " + log);
                    if (doScroll) {
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                scr_Log.getVerticalScrollBar().setValue(scr_Log.getVerticalScrollBar().getMaximum());
                            }
                        });
                    }
 //               }
 //           };

 //           SwingUtilities.invokeLater(runnable);
        }

        private void connectClientTo(IpV4address remoteAddress, int remotePort) {
            txt_Connect.setText(remoteAddress.toString());
            txt_Port.setText(String.valueOf(remotePort));
            appendLog("[Client] - Connecting to /" + remoteAddress + ":" + remotePort);
            chatClient.connect(remoteAddress, remotePort);
        }

        private void disconnectClient() {
            chatClient.disconnect();
        }

        private void processMessage(String message, String msgFrom) {
            int stepNo = 0;
            int cntMsg = 0;
            String hdrMsg;
            int cntRSA = 0;
            String hdrRSA;
            int cntOAEP = 0;
            String hdrOAEP;
            int maxLength;
            int maxPlus;
            byte[] msgStart = new byte[2];
            String msgPart;
            String msgFinal = "";

            maxLength = message.length();
            if (!chk_DisableRSA.isSelected()) {
                maxLength = ((int) Math.ceil(chatRSA.PrivateKey.modulus.bitLength() / 8.0)) * 2;
            }

            if (message.length() > maxLength) {
                cntMsg = 1;
                txt_Msg_Bin.setText("-".repeat(20) + "01" + "-".repeat(20) + "\n");
                txt_Msg_Byte.setText("-".repeat(41) + "01" + "-".repeat(42) + "\n");
                txt_Msg_Hex.setText("-".repeat(51) + "01" + "-".repeat(51) + "\n");
                txt_Msg_Dec.setText("-".repeat(52) + "01" + "-".repeat(53) + "");
                if (!chk_DisableRSA.isSelected()) {
                    cntRSA = 1;
                    txt_RSA_Bin.setText("-".repeat(16) + "01" + "-".repeat(16) + "\n");
                    txt_RSA_Byte.setText("-".repeat(28) + "01" + "-".repeat(28) + "\n");
                    txt_RSA_Hex.setText("-".repeat(29) + "01" + "-".repeat(28) + "\n");
                    txt_RSA_Dec.setText("-".repeat(23) + "01" + "-".repeat(23) + "");
                }
                if (!chk_DisableOAEP.isSelected()) {
                    cntOAEP = 1;
                    txt_OAEP_Bin.setText("-".repeat(16) + "01" + "-".repeat(16) + "\n");
                    txt_OAEP_Byte.setText("-".repeat(28) + "01" + "-".repeat(28) + "\n");
                    txt_OAEP_Hex.setText("-".repeat(29) + "01" + "-".repeat(28) + "\n");
                    txt_OAEP_Dec.setText("-".repeat(23) + "01" + "-".repeat(23) + "");
                }
            } else {
                txt_RSA_Dec.setText("");
                txt_RSA_Byte.setText("");
                txt_RSA_Hex.setText("");
                txt_RSA_Bin.setText("");
                txt_OAEP_Dec.setText("");
                txt_OAEP_Byte.setText("");
                txt_OAEP_Hex.setText("");
                txt_OAEP_Bin.setText("");
                txt_Msg_Dec.setText("");
                txt_Msg_Byte.setText("");
                txt_Msg_Hex.setText("");
                txt_Msg_Bin.setText("");
            }

            while (message.length() > 0) {
                if (message.length() >= 4) {
                    msgStart = CryptConverter.toByteArray(new HexString(message.substring(0, 4)));
                    if (msgStart[0] == Byte.parseByte("0") && msgStart[1] < Byte.parseByte("0")) {
                        maxPlus = 2;
                    } else {
                        maxPlus = 0;
                    }
                } else {
                    maxPlus = 0;
                }
                if (message.length() > maxLength + maxPlus) {
                    msgPart = message.substring(0, maxLength + maxPlus);
                    message = message.substring(maxLength + maxPlus);
                } else {
                    msgPart = message;
                    message = "";
                }
                //Convert received message to BigInteger, byte[], Hex and Binary formats
                BigInteger msgCryptDec = CryptConverter.toDecimal(new HexString(msgPart));
                byte[] msgCryptBytes = CryptConverter.toByteArray(new HexString(msgPart));
                HexString msgCryptHex = new HexString(msgPart);
                BinaryString msgCryptBin = CryptConverter.toBinaryString(new HexString(msgPart));

                //Decrypt message using RSA
                if (!chk_DisableRSA.isSelected()) {
                    stepNo += 1;

                    pnl_RSA.setBorder(BorderFactory.createTitledBorder("Before RSA Decryption (Step " + stepNo + ")"));
                    txt_RSA_Dec.setText(txt_RSA_Dec.getText() + CryptFormatter.toDecFormat(msgCryptDec));
                    txt_RSA_Byte.setText(txt_RSA_Byte.getText() + CryptFormatter.toByteFormat(msgCryptBytes));
                    txt_RSA_Hex.setText(txt_RSA_Hex.getText() + CryptFormatter.toHexFormat(msgCryptHex));
                    txt_RSA_Bin.setText(txt_RSA_Bin.getText() + CryptFormatter.toBinaryFormat(msgCryptBin));
                    if (message.length() > 0) {
                        cntRSA += 1;
                        hdrRSA = "0".repeat(2 - String.valueOf(cntRSA).length()) + cntRSA;
                        txt_RSA_Dec.setText(txt_RSA_Dec.getText() +
                                "\n" + "-".repeat(23) +
                                hdrRSA +
                                "-".repeat(23) + "");
                        txt_RSA_Byte.setText(txt_RSA_Byte.getText() +
                                "\n" + "-".repeat(28) +
                                hdrRSA + cntRSA +
                                "-".repeat(28) + "\n");
                        txt_RSA_Hex.setText(txt_RSA_Hex.getText() +
                                "\n" + "-".repeat(29) +
                                hdrRSA + cntRSA +
                                "-".repeat(28) + "\n");
                        txt_RSA_Bin.setText(txt_RSA_Bin.getText() +
                                "\n" + "-".repeat(16) +
                                hdrRSA + cntRSA +
                                "-".repeat(16) + "\n");
                    }
                    txt_RSA_Dec.setCaretPosition(0);
                    txt_RSA_Byte.setCaretPosition(0);
                    txt_RSA_Hex.setCaretPosition(0);
                    txt_RSA_Bin.setCaretPosition(0);

                    //Decrypt (using RSA) received message
                    msgCryptDec = chatRSA.decrypt(msgCryptDec);
                    msgCryptBytes = CryptConverter.toByteArray(msgCryptDec);
                    msgCryptHex = CryptConverter.toHexString(msgCryptDec);
                    msgCryptBin = CryptConverter.toBinaryString(msgCryptDec);
                }

                BigInteger msgDecDec = msgCryptDec;
                byte[] msgDecBytes = msgCryptBytes;
                HexString msgDecHex = msgCryptHex;
                BinaryString msgDecBin = msgCryptBin;

                //Decode message using OAEP
                if (!chk_DisableOAEP.isSelected()) {
                    stepNo += 1;

                    pnl_OAEP.setBorder(BorderFactory.createTitledBorder("Before OAEP Decoding (Step " + stepNo + ")"));
                    txt_OAEP_Dec.setText(txt_OAEP_Dec.getText() + CryptFormatter.toDecFormat(msgDecDec));
                    txt_OAEP_Byte.setText(txt_OAEP_Byte.getText() + CryptFormatter.toByteFormat(msgDecBytes));
                    txt_OAEP_Hex.setText(txt_OAEP_Hex.getText() + CryptFormatter.toHexFormat(msgDecHex));
                    txt_OAEP_Bin.setText(txt_OAEP_Bin.getText() + CryptFormatter.toBinaryFormat(msgDecBin));
                    if (message.length() > 0) {
                        cntOAEP += 1;
                        hdrOAEP = "0".repeat(2 - String.valueOf(cntOAEP).length()) + cntOAEP;
                        txt_OAEP_Dec.setText(txt_OAEP_Dec.getText() +
                                "\n" + "-".repeat(23) +
                                hdrOAEP +
                                "-".repeat(23) + "");
                        txt_OAEP_Byte.setText(txt_OAEP_Byte.getText() +
                                "\n" + "-".repeat(28) +
                                hdrOAEP +
                                "-".repeat(28) + "\n");
                        txt_OAEP_Hex.setText(txt_OAEP_Hex.getText() +
                                "\n" + "-".repeat(29) +
                                hdrOAEP +
                                "-".repeat(28) + "\n");
                        txt_OAEP_Bin.setText(txt_OAEP_Bin.getText() +
                                "\n" + "-".repeat(16) +
                                hdrOAEP +
                                "-".repeat(16) + "\n");
                    }
                    txt_OAEP_Dec.setCaretPosition(0);
                    txt_OAEP_Byte.setCaretPosition(0);
                    txt_OAEP_Hex.setCaretPosition(0);
                    txt_OAEP_Bin.setCaretPosition(0);

                    // Decode (using OAEP) previously decrypted (using RSA) HEX message
                    msgDecBytes = chatOAEP.decode(msgCryptBytes, dest_RSAPublicKey.modulus);
                    msgDecDec = CryptConverter.toDecimal(msgDecBytes);
                    msgDecHex = CryptConverter.toHexString(msgDecBytes);
                    msgDecBin = CryptConverter.toBinaryString(msgDecBytes);
                }

                stepNo += 1;

                BigInteger msgDec = msgDecDec;
                byte[] msgBytes = msgDecBytes;
                HexString msgHex = msgDecHex;
                BinaryString msgBin = msgDecBin;

                pnl_Msg.setBorder(BorderFactory.createTitledBorder("Converted Message (Step " + stepNo + ")"));
                txt_Msg_Dec.setText(txt_Msg_Dec.getText() + CryptFormatter.toDecFormat(msgDec));
                txt_Msg_Byte.setText(txt_Msg_Byte.getText() + CryptFormatter.toByteFormat(msgBytes));
                txt_Msg_Hex.setText(txt_Msg_Hex.getText() +  CryptFormatter.toHexFormat(msgHex));
                txt_Msg_Bin.setText(txt_Msg_Bin.getText() + CryptFormatter.toBinaryFormat(msgBin));
                if (message.length() > 0) {
                    cntMsg += 1;
                    hdrMsg = "0".repeat(2 - String.valueOf(cntMsg).length()) + cntMsg;
                    txt_Msg_Dec.setText(txt_Msg_Dec.getText() +
                            "\n" + "-".repeat(52) +
                            hdrMsg +
                            "-".repeat(53) + "");
                    txt_Msg_Byte.setText(txt_Msg_Byte.getText() +
                            "\n" + "-".repeat(41) +
                            hdrMsg +
                            "-".repeat(42) + "\n");
                    txt_Msg_Hex.setText(txt_Msg_Hex.getText() +
                            "\n" + "-".repeat(51) +
                            hdrMsg +
                            "-".repeat(51) + "\n");
                    txt_Msg_Bin.setText(txt_Msg_Bin.getText() +
                            "\n" + "-".repeat(20) +
                            hdrMsg +
                            "-".repeat(20) + "\n");
                }
                txt_Msg_Dec.setCaretPosition(0);
                txt_Msg_Byte.setCaretPosition(0);
                txt_Msg_Hex.setCaretPosition(0);
                txt_Msg_Bin.setCaretPosition(0);

                //Convert message to String format
                String msg = msgHex.toText();

                msgFinal += msg;
            }
            showMessage(msgFinal, msgFrom.replace("/", ""), false);
        }

        private void sendMessage(String message) {
            int stepNo = 0;
            int cntMsg = 0;
            String hdrMsg;
            int cntRSA = 0;
            String hdrRSA;
            int cntOAEP = 0;
            String hdrOAEP;
            byte[] messageBytes;
            String msgPart;
            byte[] msgPartBytes;
            String msgSend = "";
            String msgSendHex = "";
            int maxLength;

            maxLength = message.length();
            if (!chk_DisableRSA.isSelected()) {
                if (chk_DisableOAEP.isSelected()) {
                    maxLength = dest_RSAPublicKey.modulus.toByteArray().length;
                } else {
                    maxLength = chatOAEP.getMaxLength(dest_RSAPublicKey.modulus);
                }
            }

            messageBytes = message.getBytes(StandardCharsets.UTF_8);

            if (messageBytes.length > maxLength) {
                cntMsg = 1;
                txt_Msg_Bin.setText("-".repeat(20) + "01" + "-".repeat(20) + "\n");
                txt_Msg_Byte.setText("-".repeat(41) + "01" + "-".repeat(42) + "\n");
                txt_Msg_Hex.setText("-".repeat(51) + "01" + "-".repeat(51) + "\n");
                txt_Msg_Dec.setText("-".repeat(52) + "01" + "-".repeat(53) + "");
                if (!chk_DisableRSA.isSelected()) {
                    cntRSA = 1;
                    txt_RSA_Bin.setText("-".repeat(16) + "01" + "-".repeat(16) + "\n");
                    txt_RSA_Byte.setText("-".repeat(28) + "01" + "-".repeat(28) + "\n");
                    txt_RSA_Hex.setText("-".repeat(29) + "01" + "-".repeat(28) + "\n");
                    txt_RSA_Dec.setText("-".repeat(23) + "01" + "-".repeat(23) + "");
                }
                if (!chk_DisableOAEP.isSelected()) {
                    cntOAEP = 1;
                    txt_OAEP_Bin.setText("-".repeat(16) + "01" + "-".repeat(16) + "\n");
                    txt_OAEP_Byte.setText("-".repeat(28) + "01" + "-".repeat(28) + "\n");
                    txt_OAEP_Hex.setText("-".repeat(29) + "01" + "-".repeat(28) + "\n");
                    txt_OAEP_Dec.setText("-".repeat(23) + "01" + "-".repeat(23) + "");
                }
            } else {
                txt_RSA_Dec.setText("");
                txt_RSA_Byte.setText("");
                txt_RSA_Hex.setText("");
                txt_RSA_Bin.setText("");
                txt_OAEP_Dec.setText("");
                txt_OAEP_Byte.setText("");
                txt_OAEP_Hex.setText("");
                txt_OAEP_Bin.setText("");
                txt_Msg_Dec.setText("");
                txt_Msg_Byte.setText("");
                txt_Msg_Hex.setText("");
                txt_Msg_Bin.setText("");
            }

            //Encode, encrypt and send message
            //Split in fragments if it's length is bigger than maximum length supported by OAEP and/or RSA
            while (messageBytes.length > 0) {
                if (messageBytes.length > maxLength) {
                    msgPartBytes = new byte[maxLength];
                    messageBytes = new byte[message.getBytes(StandardCharsets.UTF_8).length - maxLength];
                    System.arraycopy(message.getBytes(StandardCharsets.UTF_8), 0, msgPartBytes, 0, maxLength);
                    System.arraycopy(message.getBytes(StandardCharsets.UTF_8), maxLength, messageBytes, 0, message.getBytes(StandardCharsets.UTF_8).length - maxLength);
                    msgPart = new String(msgPartBytes);
                    message = new String(messageBytes);
                } else {
                    msgPart = message;
                    messageBytes = new byte[0];
                }

                //Convert message to byte[], BigInteger, Hex and Binary formats
                stepNo += 1;

                byte[] msgByte = CryptConverter.toByteArray(msgPart);
                BigInteger msgDec = CryptConverter.toDecimal(msgPart);
                HexString msgHex = CryptConverter.toHexString(msgByte);
                BinaryString msgBin = CryptConverter.toBinaryString(msgByte);

                pnl_Msg.setBorder((BorderFactory.createTitledBorder("Converted Message (Step " + stepNo + ")")));
                txt_Msg_Dec.setText(txt_Msg_Dec.getText() + CryptFormatter.toDecFormat(msgDec));
                txt_Msg_Byte.setText(txt_Msg_Byte.getText() + CryptFormatter.toByteFormat(msgByte));
                txt_Msg_Hex.setText(txt_Msg_Hex.getText() + CryptFormatter.toHexFormat(msgHex));
                txt_Msg_Bin.setText(txt_Msg_Bin.getText() + CryptFormatter.toBinaryFormat(msgBin));
                if (message.length() > 0) {
                    cntMsg += 1;
                    hdrMsg = "0".repeat(2 - String.valueOf(cntMsg).length()) + cntMsg;
                    txt_Msg_Dec.setText(txt_Msg_Dec.getText() +
                            "\n" + "-".repeat(52) +
                            hdrMsg +
                            "-".repeat(53) + "");
                    txt_Msg_Byte.setText(txt_Msg_Byte.getText() +
                            "\n" + "-".repeat(41) +
                            hdrMsg +
                            "-".repeat(42) + "\n");
                    txt_Msg_Hex.setText(txt_Msg_Hex.getText() +
                            "\n" + "-".repeat(51) +
                            hdrMsg +
                            "-".repeat(51) + "\n");
                    txt_Msg_Bin.setText(txt_Msg_Bin.getText() +
                            "\n" + "-".repeat(20) +
                            hdrMsg +
                            "-".repeat(20) + "\n");
                }
                txt_Msg_Dec.setCaretPosition(0);
                txt_Msg_Byte.setCaretPosition(0);
                txt_Msg_Hex.setCaretPosition(0);
                txt_Msg_Bin.setCaretPosition(0);

                BigInteger msgEncDec = msgDec;
                byte[] msgEncByte = msgByte;
                HexString msgEncHex = msgHex;
                BinaryString msgEncBin = msgBin;

                //Encode message using OAEP
                if (!chk_DisableOAEP.isSelected()) {
                    stepNo += 1;

                    //Repeat OAEP Encoding if resulted decimal value is a negative BigInteger value
                    //otherwise OAEP decoding will fail
//                    do {
                    msgEncByte = chatOAEP.encode(msgByte, dest_RSAPublicKey.modulus);
//                    } while (CryptConverter.toDecimal(msgEncByte).compareTo(BigInteger.ZERO) == -1);
                    msgEncDec = CryptConverter.toDecimal(msgEncByte);
                    msgEncHex = CryptConverter.toHexString(msgEncByte);
                    msgEncBin = CryptConverter.toBinaryString(msgEncByte);

                    pnl_OAEP.setBorder((BorderFactory.createTitledBorder("After OAEP Encoding (Step " + stepNo + ")")));
                    txt_OAEP_Dec.setText(txt_OAEP_Dec.getText() + CryptFormatter.toDecFormat(msgEncDec));
                    txt_OAEP_Byte.setText(txt_OAEP_Byte.getText() + CryptFormatter.toByteFormat(msgEncByte));
                    txt_OAEP_Hex.setText(txt_OAEP_Hex.getText() + CryptFormatter.toHexFormat(msgEncHex));
                    txt_OAEP_Bin.setText(txt_OAEP_Bin.getText() + CryptFormatter.toBinaryFormat(msgEncBin));
                    if (message.length() > 0) {
                        cntOAEP += 1;
                        hdrOAEP = "0".repeat(2 - String.valueOf(cntOAEP).length()) + cntOAEP;
                        txt_OAEP_Dec.setText(txt_OAEP_Dec.getText() +
                                "\n" + "-".repeat(23) +
                                hdrOAEP +
                                "-".repeat(23) + "");
                        txt_OAEP_Byte.setText(txt_OAEP_Byte.getText() +
                                "\n" + "-".repeat(28) +
                                hdrOAEP +
                                "-".repeat(28) + "\n");
                        txt_OAEP_Hex.setText(txt_OAEP_Hex.getText() +
                                "\n" + "-".repeat(29) +
                                hdrOAEP +
                                "-".repeat(28) + "\n");
                        txt_OAEP_Bin.setText(txt_OAEP_Bin.getText() +
                                "\n" + "-".repeat(16) +
                                hdrOAEP +
                                "-".repeat(16) + "\n");
                    }
                    txt_OAEP_Dec.setCaretPosition(0);
                    txt_OAEP_Byte.setCaretPosition(0);
                    txt_OAEP_Hex.setCaretPosition(0);
                    txt_OAEP_Bin.setCaretPosition(0);
                }

                BigInteger msgCryptDec = msgEncDec;
                byte[] msgCryptByte = msgEncByte;
                HexString msgCryptHex = msgEncHex;
                BinaryString msgCryptBin = msgEncBin;

                //Encrypt message using RSA
                if (!chk_DisableRSA.isSelected()) {
                    stepNo += 1;

                    msgCryptDec = chatRSA.encrypt(msgEncDec, dest_RSAPublicKey);
                    msgCryptByte = CryptConverter.toByteArray(msgCryptDec);
                    msgCryptHex = CryptConverter.toHexString(msgCryptDec);
                    msgCryptBin = CryptConverter.toBinaryString(msgCryptDec);

                    pnl_RSA.setBorder(BorderFactory.createTitledBorder("After RSA Encryption (Step " + stepNo + ")"));
                    txt_RSA_Dec.setText(txt_RSA_Dec.getText() + CryptFormatter.toDecFormat(msgCryptDec));
                    txt_RSA_Byte.setText(txt_RSA_Byte.getText() + CryptFormatter.toByteFormat(msgCryptByte));
                    txt_RSA_Hex.setText(txt_RSA_Hex.getText() + CryptFormatter.toHexFormat(msgCryptHex));
                    txt_RSA_Bin.setText(txt_RSA_Bin.getText() + CryptFormatter.toBinaryFormat(msgCryptBin));
                    if (message.length() > 0) {
                        cntRSA += 1;
                        hdrRSA = "0".repeat(2 - String.valueOf(cntRSA).length()) + cntRSA;
                        txt_RSA_Dec.setText(txt_RSA_Dec.getText() +
                                "\n" + "-".repeat(23) +
                                hdrRSA +
                                "-".repeat(23) + "");
                        txt_RSA_Byte.setText(txt_RSA_Byte.getText() +
                                "\n" + "-".repeat(28) +
                                hdrRSA +
                                "-".repeat(28) + "\n");
                        txt_RSA_Hex.setText(txt_RSA_Hex.getText() +
                                "\n" + "-".repeat(29) +
                                hdrRSA +
                                "-".repeat(28) + "\n");
                        txt_RSA_Bin.setText(txt_RSA_Bin.getText() +
                                "\n" + "-".repeat(16) +
                                hdrRSA +
                                "-".repeat(16) + "\n");
                    }
                    txt_RSA_Dec.setCaretPosition(0);
                    txt_RSA_Byte.setCaretPosition(0);
                    txt_RSA_Hex.setCaretPosition(0);
                    txt_RSA_Bin.setCaretPosition(0);
                }

                msgSend += msgPart;
                msgSendHex += msgCryptHex.toString();
            }

            chatClient.sendMessage(msgSendHex);

            showMessage(msgSend, chatClient.getLocalAddress() + ":" + chatClient.getLocalPort(), true);

            //if (message.length() > 0) {
            //    appendLog("[Client] - Truncated message (limited to " + maxLength + " bytes) sent.");
            //} else {
                appendLog("[Client] - Message sent.");
            //}
        }

        private void showMessage(String message, String address, boolean isSentMessage) {
            boolean doScroll = scp_Messages.getVerticalScrollBar().getMaximum() == scp_Messages.getVerticalScrollBar().getValue() + scp_Messages.getVerticalScrollBar().getVisibleAmount();

            String htmMsg = "";
            if (isSentMessage) {
                htmMsg += txt_Messages.getText().substring(0, txt_Messages.getText().length() - 28).trim() + "\n";
                htmMsg += "    </p><p style=\"margin-top: 5\"; align=\"right\">\n";
                htmMsg += "      <font color=\"silver\">\n";
                htmMsg += "        " + message + "\n";
                htmMsg += "      </font>\n";
                htmMsg += "      <font color=\"LightGrey\">\n";
                htmMsg += "        <b>\n";
                htmMsg += "           - [" + address + "]\n";
                htmMsg += "        </b>\n";
                htmMsg += "      </font>\n";
                htmMsg += "    </p>\n";
                htmMsg += "  </body>\n";
                htmMsg += "</html>\n";
            } else {
                htmMsg += txt_Messages.getText().substring(0,txt_Messages.getText().length() - 28).trim() + "\n";
                htmMsg += "    </p><p style=\"margin-top: 5\">\n";
                htmMsg += "      <font color=\"LightGrey\">\n";
                htmMsg += "        <b>\n";
                htmMsg += "          [" + address + "] - \n";
                htmMsg += "        </b>\n";
                htmMsg += "      </font>\n";
                htmMsg += "      <font color=\"blue\">\n";
                htmMsg += "        " + message + "\n";
                htmMsg += "      </font>\n";
                htmMsg += "    </p>\n";
                htmMsg += "  </body>\n";
                htmMsg += "</html>\n";
            }
            txt_Messages.setText(htmMsg);

            if (doScroll) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        scp_Messages.getVerticalScrollBar().setValue(scp_Messages.getVerticalScrollBar().getMaximum());
                    }
                });
            }
        }

        private String doAuthenticate() {
            JPasswordField passwordField = new JPasswordField();
            Object[] obj = {"Please enter the password:\n\n", passwordField};
            Object stringArray[] = {"OK","Cancel"};
            if (JOptionPane.showOptionDialog(this, obj, "Need password",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, stringArray, obj) == JOptionPane.YES_OPTION) {

                return new String(passwordField.getPassword());
            } else {
                return "";
            }
        }

        private void send_AS_REQ() {

            kdcClient.sendMessage("msg: AS_REQ" + (char)0 + chat_AS_REQ.getPackage());
        }

        private void send_TGS_REQ() {

            kdcClient.sendMessage("msg: TGS_REQ" +(char)0 + AES.encrypt(chat_TGS_REQ.getPackage(), shortTermKey) + (char)0 + chat_TGT);
        }
    //endregion Private method(s)

    public void setServerKey(HexString serverKey) {
        this.serverKey = serverKey;
    }
}
