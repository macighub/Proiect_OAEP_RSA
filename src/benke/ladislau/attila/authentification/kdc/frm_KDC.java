package benke.ladislau.attila.authentification.kdc;

import benke.ladislau.attila.authentification.packages.*;
import benke.ladislau.attila.crypto.AES;
import benke.ladislau.attila.crypto.cryptUtils.HexString;

import javax.swing.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Vector;

public class frm_KDC extends JFrame {
    //region Instance Variable(s)

        //region GUI Element(s)
            private JPanel pnl_KDC;
    private JPanel pnl_AS;
    private JPanel pnl_TGS;
    private JScrollPane scr_Log;
    private JTextArea txt_Log;
    private JPanel pnl_Log;
    private JTabbedPane tab_AS_REQ;
    private JPanel pnl_AS_REQ;
    private JTextArea lbl_AS_REQ;
    private JTextArea txt_AS_REQ;
    private JTextField txt_AS_REQ_ts;
    private JLabel lbl_AS_REQ_ts;
    private JPanel pnl_AS_REQ_Labels;
    private JPanel pnl_AS_REQ_Values;
    private JPanel pnl_AS_REP;
    private JPanel pnl_AS_REP_Labels;
    private JTextArea lbl_AS_REP;
    private JPanel pnl_AS_REP_Values;
    private JTextArea txt_AS_REP;
    private JPanel pnl_Legend;
    private JPanel pnl_Legend_1;
    private JPanel pnl_Legend_nokey;
    private JTextField txt_Chat1_Key;
    private JPanel pnl_DB;
    private JScrollPane scr_DB;
    private JPanel scp_DB;
    private JPanel pnl_Chat1;
    private JPanel pnl_Chat1_Key;
    private JPanel pnl_Chat1_Principals;
    private JPanel pnl_Chat1_Principal2;
    private JLabel lbl_Chat1_Principal2_uid;
    private JTextField txt_Chat1_Principal2_uid;
    private JTextField txt_Chat1_Principal1_uid;
    private JTextField txt_Chat1_Principal1_valid;
    private JTextField txt_Chat1_Principal1_key;
    private JTextField txt_Chat1_Principal1_keyvalid;
    private JTextField txt_Chat1_Principal1_tktval;
    private JTextField txt_Chat1_Principal2_valid;
    private JPanel pnl_Chat1_Principal1;
    private JTextField txt_Chat1_Principal2_key;
    private JTextField txt_Chat1_Principal2_keyvalid;
    private JTextField txt_Chat1_Principal2_tktval;
    private JPanel pnl_Chat2;
    private JPanel pnl_Chat2_Key;
    private JTextField txt_Chat2_Key;
    private JPanel pnl_Chat2_Principals;
    private JPanel pnl_Chat2_Principal1;
    private JPanel pnl_Chat2_Principal2;
    private JTextField txt_Chat2_Principal2_uid;
    private JTextField txt_Chat2_Principal2_valid;
    private JTextField txt_Chat2_Principal2_key;
    private JTextField txt_Chat2_Principal2_keyvalid;
    private JTextField txt_Chat2_Principal2_tktval;
    private JPanel pnl_Chat3;
    private JPanel pnl_Chat3_Key;
    private JTextField txt_Chat3_Key;
    private JPanel pnl_Chat3_Principals;
    private JPanel pnl_Chat3_Principal1;
    private JPanel pnl_Chat3_Principal2;
    private JTextField txt_Chat3_Principal1_uid;
    private JTextField txt_Chat3_Principal1_valid;
    private JTextField txt_Chat3_Principal1_key;
    private JTextField txt_Chat3_Principal1_keyvalid;
    private JTextField txt_Chat3_Principal1_tktval;
    private JTextField txt_Chat3_Principal2_uid;
    private JTextField txt_Chat3_Principal2_valid;
    private JTextField txt_Chat3_Principal2_key;
    private JTextField txt_Chat3_Principal2_keyvalid;
    private JTextField txt_Chat3_Principal2_tktval;
    private JTextField txt_Chat2_Principal1_uid;
    private JTextField txt_Chat2_Principal1_valid;
    private JTextField txt_Chat2_Principal1_key;
    private JTextField txt_Chat2_Principal1_keyvalid;
    private JTextField txt_Chat2_Principal1_tktval;
    private JPanel pnl_AS_TGT;
    private JTextArea txt_AS_TGT;
    private JPanel pnl_TGS_REQ;
    private JTextArea txt_TGS_REQ;
    private JTextArea txt_TGS_TGT;
    private JPanel pnl_TGS_TGT;
    private JTextArea txt_TGS_ST;
    private JPanel pnl_TGS_ST;
    private JTextArea txt_TGS_REP;
    private JPanel pnl_TGS_REP;
    private JTextField txt_AS_REG_ts;
    private JTextField txt_AS_REQ_uid;
    private JTextField txt_AS_REQ_req;
    private JTextField txt_AS_REQ_addr;

    //endregion GUI Element(s)

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        private KDCServer kdcServer_AS;
        private KDCServer kdcServer_TGS;
        AS kdcAS;
        TGS kdcTGS;
        DB kdcDB;

        private AS_REQ kdc_AS_REQ;

    //endregion Instance Variable(s)

    public frm_KDC(String title) {
        this(title, 50000, 55000);
    }

    public frm_KDC(String title, int kdcPort_AS, int kdcPort_TGS) {
        kdcServer_AS = new KDCServer(kdcPort_AS, new KDCServer.InitializedListener() {
            @Override
            public void Initialized(KDCServer source) {
                kdcServer_AS_Initialized(source);
            }
        });

        kdcServer_TGS = new KDCServer(kdcPort_TGS, new KDCServer.InitializedListener() {
            @Override
            public void Initialized(KDCServer source) {
                kdcServer_TGS_Initialized(source);
            }
        });

        //region GUI Init
            super.setTitle(title);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setContentPane(pnl_KDC);
            this.pack();

            //region GUI Listeners
            //endregion GUI Listeners

        //endregion GUI Init

        //region Listener(s)
            //region kdcServer Listeners
                kdcServer_AS.addConnectionClosedListener(new KDCServer.ConnectionClosedListener() {
                    @Override
                    public void ConnectionClosed(KDCServer source, boolean byClient) {
                        kdcServer_AS_ConnectionClosed(source, byClient);
                    }
                });
                kdcServer_AS.addConnectionReceivedListener(new KDCServer.ConnectionReceivedListener() {
                    @Override
                    public void ConnectionReceived(KDCServer source, String connectFrom) {
                        kdcServer_AS_ConnectionReceived(source, connectFrom);
                    }
                });
                kdcServer_AS.addMessageReceivedListener(new KDCServer.MessageReceivedListener() {
                    @Override
                    public void MessageReceived(KDCServer source, String message, String msgFrom) {
                        kdcServer_AS_MessageReceived(source, message, msgFrom);
                    }
                });
                kdcServer_AS.addStoppedListener(new KDCServer.StoppedListener() {
                    @Override
                    public void Stopped(KDCServer source) {
                        kdcServer_AS_Stopped(source);
                    }
                });
                kdcServer_AS.addWaitingForConnectionListener(new KDCServer.WaitingForConnectionListener() {
                    @Override
                    public void WaitingForConnection(KDCServer source, int port) {
                        kdcServer_AS_WaitingForConnection(source, port);
                    }
                });
            kdcServer_TGS.addConnectionClosedListener(new KDCServer.ConnectionClosedListener() {
                @Override
                public void ConnectionClosed(KDCServer source, boolean byClient) {
                    kdcServer_TGS_ConnectionClosed(source, byClient);
                }
            });
            kdcServer_TGS.addConnectionReceivedListener(new KDCServer.ConnectionReceivedListener() {
                @Override
                public void ConnectionReceived(KDCServer source, String connectFrom) {
                    kdcServer_TGS_ConnectionReceived(source, connectFrom);
                }
            });
            kdcServer_TGS.addMessageReceivedListener(new KDCServer.MessageReceivedListener() {
                @Override
                public void MessageReceived(KDCServer source, String message, String msgFrom) {
                    kdcServer_TGS_MessageReceived(source, message, msgFrom);
                }
            });
            kdcServer_TGS.addStoppedListener(new KDCServer.StoppedListener() {
                @Override
                public void Stopped(KDCServer source) {
                    kdcServer_TGS_Stopped(source);
                }
            });
            kdcServer_TGS.addWaitingForConnectionListener(new KDCServer.WaitingForConnectionListener() {
                @Override
                public void WaitingForConnection(KDCServer source, int port) {
                    kdcServer_TGS_WaitingForConnection(source, port);
                }
            });
            //endregion KDCServer Listeners
        //endregion Listener(s)

        kdcAS = new AS();
        kdcTGS = new TGS();
        kdcDB = new DB();

        fill_DB_GUI();

        kdcServer_AS.startServer();
        kdcServer_TGS.startServer();
    }

    //region KDCServer Event handlers
    private void kdcServer_AS_ConnectionClosed(KDCServer source, boolean byClient) {
        if (byClient) {
            appendLog("[KDC] - AS Connection closed by client.");
        } else {
            appendLog("[KDC] - AS Connection closed.");
        }
    }
    private void kdcServer_AS_ConnectionReceived(KDCServer source, String connectFrom) {
        appendLog("[KDC] - Connection to AS received from " + connectFrom + ".");
        pnl_AS_REQ.setVisible(false);
        pnl_AS_REP.setVisible(false);
        pnl_AS_TGT.setVisible(false);
        pnl_TGS_REQ.setVisible(false);
        pnl_TGS_TGT.setVisible(false);
        pnl_TGS_REP.setVisible(false);
        pnl_TGS_ST.setVisible(false);
    }
    private void kdcServer_AS_Initialized(KDCServer source) {
        appendLog("[KDC] - AS Server initialized.");
    }
    private void kdcServer_AS_MessageReceived(KDCServer source, String message, String msgFrom) {
        String[] msgSplit = message.split(String.valueOf((char)0));
        switch (msgSplit[0]){
            case "msg: AS_REQ" -> {
                received_AS_REQ(msgSplit);
            }
        }

    }
    private void kdcServer_AS_Stopped(KDCServer source) {
        appendLog("[KDC] - AS Server stopped.");
    }
    private void kdcServer_AS_WaitingForConnection(KDCServer source, int port) {
        appendLog("[KDC] - AS Waiting for connection on port: " + port + ".");
    }
    private void kdcServer_TGS_ConnectionClosed(KDCServer source, boolean byClient) {
        if (byClient) {
            appendLog("[KDC] - TGS Connection closed by client.");
        } else {
            appendLog("[KDC] - TGS Connection closed.");
        }
    }
    private void kdcServer_TGS_ConnectionReceived(KDCServer source, String connectFrom) {
        appendLog("[KDC] - Connection to TGS received from " + connectFrom + ".");
        pnl_TGS_REQ.setVisible(false);
        pnl_TGS_TGT.setVisible(false);
        pnl_TGS_REP.setVisible(false);
        pnl_TGS_ST.setVisible(false);
    }
    private void kdcServer_TGS_Initialized(KDCServer source) {
        appendLog("[KDC] - TGS Server initialized.");
    }
    private void kdcServer_TGS_MessageReceived(KDCServer source, String message, String msgFrom) {
        String[] msgSplit = message.split(String.valueOf((char)0));
        switch (msgSplit[0]){
            case "msg: TGS_REQ" -> {
                received_TGS_REQ(msgSplit);
            }
        }

    }
    private void kdcServer_TGS_Stopped(KDCServer source) {
        appendLog("[KDC] - TGS Server stopped.");
    }
    private void kdcServer_TGS_WaitingForConnection(KDCServer source, int port) {
        appendLog("[KDC] - TGS Waiting for connection on port: " + port + ".");
    }    //endregion KDCServer Event Handlers
    //region Private method(s)
    void appendLog(String log) {
        boolean doScroll = scr_Log.getVerticalScrollBar().getMaximum() == scr_Log.getVerticalScrollBar().getValue() + scr_Log.getVerticalScrollBar().getVisibleAmount();
        if (!txt_Log.getText().equals("")) {
            txt_Log.append("\n");
        }
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        txt_Log.append("[" + dtf.format(now) + "] - "+log);
        if (doScroll) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    scr_Log.getVerticalScrollBar().setValue(scr_Log.getVerticalScrollBar().getMaximum());
                }
            });
        }
    }

    private void received_AS_REQ(String[] msgPackage) {
        AS_REQ kdc_AS_REQ;
        AS_REP kdc_AS_REP;
        AS_TGT kdc_AS_TGT;

        appendLog("[KDC] - AS_REQ received.");
        pnl_AS_REQ.setVisible(false);
        pnl_AS_REP.setVisible(false);

        kdc_AS_REQ = new AS_REQ(msgPackage);

        txt_AS_REQ.setText(AES.decrypt(kdc_AS_REQ.getTs(), AES.getPasswordKey("Chat_1_pwd" + (char)0 + kdc_AS_REQ.getUid())) + "\n" + kdc_AS_REQ.getUid() + "\n" + kdc_AS_REQ.getReq() + "\n" + kdc_AS_REQ.getAddr());
        txt_AS_REQ.setText(AES.decrypt(kdc_AS_REQ.getTs(), kdcDB.getRealms().getRealm(kdc_AS_REQ.getReq()).getPrincipals().getPrincipal(kdc_AS_REQ.getUid()).getKey()) + "\n" + kdc_AS_REQ.getUid() + "\n" + kdc_AS_REQ.getReq() + "\n" + kdc_AS_REQ.getAddr());
        txt_AS_REQ_ts.setText(kdc_AS_REQ.getTs().toString());
        pnl_AS_REQ.setVisible(true);

        try {
            kdc_AS_REP = kdcAS.get_AS_REP(this, kdc_AS_REQ);
            kdc_AS_TGT = kdcAS.get_AS_TGT(this, kdc_AS_REQ);

            txt_AS_REP.setText(kdc_AS_REP.getSTK().toString().substring(0, 25) + "..." + "\n" +
                    dtf.format(kdc_AS_REP.getTs()) + "\n" +
                    kdc_AS_REP.getAddr().toString() + "\n" +
                    dtf.format(kdc_AS_REP.getExp()));
            pnl_AS_REP.setVisible(true);

            txt_AS_TGT.setText(kdc_AS_TGT.getSTK().toString().substring(0, 25) + "..." + "\n" +
                    kdc_AS_TGT.getUid() + "\n" +
                    dtf.format(kdc_AS_TGT.getTs()) + "\n" +
                    kdc_AS_TGT.getAddr().toString() + "\n" +
                    dtf.format(kdc_AS_TGT.getExp()));
            pnl_AS_TGT.setVisible(true);

            kdcServer_AS.sendMessage("msg: AS_REP" + (char)0 +
                    AES.encrypt(kdc_AS_REP.getPackage(), kdcDB.getRealms().getRealm(kdc_AS_REQ.getReq()).getPrincipals().getPrincipal(kdc_AS_REQ.getUid()).getKey()) + (char)0 +
                    AES.encrypt(kdc_AS_TGT.getPackage(), kdcTGS.getKey()));
            appendLog("[KDC] - AS_REP sent (encrypted using client's key from database) with attached \"Ticket Granting Ticket\" (encrypted with TGS key).");
        } catch(Exception ex) {
            kdcServer_AS.sendMessage("msg: AUTH_ERR" + (char)0 + "err: " + ex.getMessage());
            appendLog("[KDC] - Authentication failed. -> AUTH_ERR sent to client.");
        }

    }

    private void received_TGS_REQ(String[] msgPackage){
        String msg_AS_TGT;
        String msg_TGS_REQ;
        AS_TGT kdc_AS_TGT;
        TGS_REQ kdc_TGS_REQ;
        TGS_REP kdc_TGS_REP;
        TGS_ST kdc_TGS_ST;

        appendLog("[KDC] - TGS_REQ received.");

        msg_AS_TGT = "msg: AS_TGT" + (char)0 + AES.decrypt(new HexString(msgPackage[2]), kdcTGS.getKey());
        msg_TGS_REQ = msgPackage[0] + (char)0 + msgPackage[1];

        kdc_AS_TGT = new AS_TGT(msg_AS_TGT.split(String.valueOf((char)0)));

        txt_TGS_TGT.setText(kdc_AS_TGT.getSTK().toString().substring(0, 25) + "..." + "\n" +
                            kdc_AS_TGT.getUid() + "\n" +
                            dtf.format(kdc_AS_TGT.getTs()) + "\n" +
                            kdc_AS_TGT.getAddr().toString() + "\n" +
                            dtf.format(kdc_AS_TGT.getExp()));
        pnl_TGS_TGT.setVisible(true);

        msg_TGS_REQ = msg_TGS_REQ.split(String.valueOf((char)0))[0] + (char)0 + AES.decrypt(new HexString(msg_TGS_REQ.split(String.valueOf((char)0))[1]),kdc_AS_TGT.getSTK());

        kdc_TGS_REQ = new TGS_REQ(msg_TGS_REQ.split(String.valueOf((char)0)));

        txt_TGS_REQ.setText(dtf.format(kdc_TGS_REQ.getTs()) + "\n" +
                                        kdc_TGS_REQ.getUid() + "\n" +
                                        kdc_TGS_REQ.getReq());
        pnl_TGS_REQ.setVisible(true);

        try {
             kdc_TGS_ST = kdcTGS.get_TGS_ST(this, kdc_TGS_REQ, kdc_AS_TGT);
             kdc_TGS_REP = kdcTGS.get_TGS_REP(this, kdc_TGS_REQ, kdc_AS_TGT, kdc_TGS_ST);

            txt_TGS_REP.setText(kdc_TGS_REP.getSk().toString().substring(0, 25) + "..." + "\n" +
                                dtf.format(kdc_TGS_REP.getExp()) + "\n" +
                                kdc_TGS_REP.getDest() + "\n" +
                                dtf.format(kdc_TGS_REP.getTs()));
            pnl_TGS_REP.setVisible(true);

            txt_TGS_ST.setText(kdc_TGS_ST.getSk().toString().substring(0,25) + "..." + "\n" +
                    dtf.format(kdc_TGS_ST.getExp()) + "\n" +
                    kdc_TGS_ST.getUid() + "\n" +
                    kdc_TGS_ST.getAddr().toString() + "\n" +
                    dtf.format(kdc_TGS_ST.getTs()));
            pnl_TGS_ST.setVisible(true);

            kdcServer_TGS.sendMessage("msg: TGS_REP" + (char)0 +
                    AES.encrypt(kdc_TGS_REP.getPackage(), kdc_AS_TGT.getSTK()) + (char)0 +
                    AES.encrypt(kdc_TGS_ST.getPackage(), kdcDB.getRealms().getRealm(kdc_TGS_REQ.getReq()).getKey()));
            appendLog("[KDC] - TGS_REP sent (encrypted using \"Short Term Key\") with attached \"Service Ticket\" (encrypted with Server's key).");
        } catch (Exception ex) {
            kdcServer_AS.sendMessage("msg: AUTH_ERR" + (char)0 + "err: " + ex.getMessage());
            appendLog("[KDC] - Authentication failed. -> AUTH_ERR sent to client.");
        }
    }

    private void fill_DB_GUI() {
        txt_Chat1_Key.setText(kdcDB.getRealms().getRealm("Chat_1").getKey().toString());
        txt_Chat2_Key.setText(kdcDB.getRealms().getRealm("Chat_2").getKey().toString());
        txt_Chat3_Key.setText(kdcDB.getRealms().getRealm("Chat_3").getKey().toString());

        txt_Chat1_Principal1_uid.setText(kdcDB.getRealms().getRealm("Chat_1").getPrincipals().getPrincipal("Chat_2").getUid());
        if (dtf.format(kdcDB.getRealms().getRealm("Chat_1").getPrincipals().getPrincipal("Chat_2").getValidity()).equals("31/12/9999 00:00:00")) {
            txt_Chat1_Principal1_valid.setText("Unlimited");
        } else {
            txt_Chat1_Principal1_valid.setText("Before " + dtf.format(kdcDB.getRealms().getRealm("Chat_1").getPrincipals().getPrincipal("Chat_2").getValidity()));
        }
        txt_Chat1_Principal1_key.setText(kdcDB.getRealms().getRealm("Chat_1").getPrincipals().getPrincipal("Chat_2").getKey().toString());
        if (dtf.format(kdcDB.getRealms().getRealm("Chat_1").getPrincipals().getPrincipal("Chat_2").getKeyValidity()).equals("31/12/9999 00:00:00")) {
            txt_Chat1_Principal1_keyvalid.setText("Unlimited");
        } else {
            txt_Chat1_Principal1_keyvalid.setText("Before " + dtf.format(kdcDB.getRealms().getRealm("Chat_1").getPrincipals().getPrincipal("Chat_2").getKeyValidity()));
        }
        if (kdcDB.getRealms().getRealm("Chat_1").getPrincipals().getPrincipal("Chat_2").getTicketValidity() == 0) {
            txt_Chat1_Principal1_tktval.setText("Unlimited");
        } else {
            txt_Chat1_Principal1_tktval.setText(String.valueOf(kdcDB.getRealms().getRealm("Chat_1").getPrincipals().getPrincipal("Chat_2").getTicketValidity()) + " seconds");
        }

        txt_Chat1_Principal2_uid.setText(kdcDB.getRealms().getRealm("Chat_1").getPrincipals().getPrincipal("Chat_3").getUid());
        if (dtf.format(kdcDB.getRealms().getRealm("Chat_1").getPrincipals().getPrincipal("Chat_3").getValidity()).equals("31/12/9999 00:00:00")) {
            txt_Chat1_Principal2_valid.setText("Unlimited");
        } else {
            txt_Chat1_Principal2_valid.setText("Before " + dtf.format(kdcDB.getRealms().getRealm("Chat_1").getPrincipals().getPrincipal("Chat_3").getValidity()));
        }
        txt_Chat1_Principal2_key.setText(kdcDB.getRealms().getRealm("Chat_1").getPrincipals().getPrincipal("Chat_3").getKey().toString());
        if (dtf.format(kdcDB.getRealms().getRealm("Chat_1").getPrincipals().getPrincipal("Chat_3").getKeyValidity()).equals("31/12/9999 00:00:00")) {
            txt_Chat1_Principal2_keyvalid.setText("Unlimited");
        } else {
            txt_Chat1_Principal2_keyvalid.setText("Before " + dtf.format(kdcDB.getRealms().getRealm("Chat_1").getPrincipals().getPrincipal("Chat_3").getKeyValidity()));
        }
        if (kdcDB.getRealms().getRealm("Chat_1").getPrincipals().getPrincipal("Chat_3").getTicketValidity() == 0) {
            txt_Chat1_Principal2_tktval.setText("Unlimited");
        } else {
            txt_Chat1_Principal2_tktval.setText(String.valueOf(kdcDB.getRealms().getRealm("Chat_1").getPrincipals().getPrincipal("Chat_3").getTicketValidity()) + " seconds");
        }

        txt_Chat2_Principal1_uid.setText(kdcDB.getRealms().getRealm("Chat_2").getPrincipals().getPrincipal("Chat_1").getUid());
        if (dtf.format(kdcDB.getRealms().getRealm("Chat_2").getPrincipals().getPrincipal("Chat_1").getValidity()).equals("31/12/9999 00:00:00")) {
            txt_Chat2_Principal1_valid.setText("Unlimited");
        } else {
            txt_Chat2_Principal1_valid.setText("Before " + dtf.format(kdcDB.getRealms().getRealm("Chat_2").getPrincipals().getPrincipal("Chat_1").getValidity()));
        }
        txt_Chat2_Principal1_key.setText(kdcDB.getRealms().getRealm("Chat_2").getPrincipals().getPrincipal("Chat_1").getKey().toString());
        if (dtf.format(kdcDB.getRealms().getRealm("Chat_2").getPrincipals().getPrincipal("Chat_1").getKeyValidity()).equals("31/12/9999 00:00:00")) {
            txt_Chat2_Principal1_keyvalid.setText("Unlimited");
        } else {
            txt_Chat2_Principal1_keyvalid.setText("Before " + dtf.format(kdcDB.getRealms().getRealm("Chat_2").getPrincipals().getPrincipal("Chat_1").getKeyValidity()));
        }
        if (kdcDB.getRealms().getRealm("Chat_2").getPrincipals().getPrincipal("Chat_1").getTicketValidity() == 0) {
            txt_Chat2_Principal1_tktval.setText("Unlimited");
        } else {
            txt_Chat2_Principal1_tktval.setText(String.valueOf(kdcDB.getRealms().getRealm("Chat_2").getPrincipals().getPrincipal("Chat_1").getTicketValidity()) + " seconds");
        }

        txt_Chat2_Principal2_uid.setText(kdcDB.getRealms().getRealm("Chat_2").getPrincipals().getPrincipal("Chat_3").getUid());
        if (dtf.format(kdcDB.getRealms().getRealm("Chat_2").getPrincipals().getPrincipal("Chat_3").getValidity()).equals("31/12/9999 00:00:00")) {
            txt_Chat2_Principal2_valid.setText("Unlimited");
        } else {
            txt_Chat2_Principal2_valid.setText("Before " + dtf.format(kdcDB.getRealms().getRealm("Chat_2").getPrincipals().getPrincipal("Chat_3").getValidity()));
        }
        txt_Chat2_Principal2_key.setText(kdcDB.getRealms().getRealm("Chat_2").getPrincipals().getPrincipal("Chat_3").getKey().toString());
        if (dtf.format(kdcDB.getRealms().getRealm("Chat_2").getPrincipals().getPrincipal("Chat_3").getKeyValidity()).equals("31/12/9999 00:00:00")) {
            txt_Chat2_Principal2_keyvalid.setText("Unlimited");
        } else {
            txt_Chat2_Principal2_keyvalid.setText("Before " + dtf.format(kdcDB.getRealms().getRealm("Chat_2").getPrincipals().getPrincipal("Chat_3").getKeyValidity()));
        }
        if (kdcDB.getRealms().getRealm("Chat_2").getPrincipals().getPrincipal("Chat_3").getTicketValidity() == 0) {
            txt_Chat2_Principal2_tktval.setText("Unlimited");
        } else {
            txt_Chat2_Principal2_tktval.setText(String.valueOf(kdcDB.getRealms().getRealm("Chat_2").getPrincipals().getPrincipal("Chat_3").getTicketValidity()) + " seconds");
        }

        txt_Chat3_Principal1_uid.setText(kdcDB.getRealms().getRealm("Chat_3").getPrincipals().getPrincipal("Chat_1").getUid());
        if (dtf.format(kdcDB.getRealms().getRealm("Chat_3").getPrincipals().getPrincipal("Chat_1").getValidity()).equals("31/12/9999 00:00:00")) {
            txt_Chat3_Principal1_valid.setText("Unlimited");
        } else {
            txt_Chat3_Principal1_valid.setText("Before " + dtf.format(kdcDB.getRealms().getRealm("Chat_3").getPrincipals().getPrincipal("Chat_1").getValidity()));
        }
        txt_Chat3_Principal1_key.setText(kdcDB.getRealms().getRealm("Chat_3").getPrincipals().getPrincipal("Chat_1").getKey().toString());
        if (dtf.format(kdcDB.getRealms().getRealm("Chat_3").getPrincipals().getPrincipal("Chat_1").getKeyValidity()).equals("31/12/9999 00:00:00")) {
            txt_Chat3_Principal1_keyvalid.setText("Unlimited");
        } else {
            txt_Chat3_Principal1_keyvalid.setText("Before " + dtf.format(kdcDB.getRealms().getRealm("Chat_3").getPrincipals().getPrincipal("Chat_1").getKeyValidity()));
        }
        if (kdcDB.getRealms().getRealm("Chat_3").getPrincipals().getPrincipal("Chat_1").getTicketValidity() == 0) {
            txt_Chat3_Principal1_tktval.setText("Unlimited");
        } else {
            txt_Chat3_Principal1_tktval.setText(String.valueOf(kdcDB.getRealms().getRealm("Chat_3").getPrincipals().getPrincipal("Chat_1").getTicketValidity()) + " seconds");
        }

        txt_Chat3_Principal2_uid.setText(kdcDB.getRealms().getRealm("Chat_3").getPrincipals().getPrincipal("Chat_2").getUid());
        if (dtf.format(kdcDB.getRealms().getRealm("Chat_3").getPrincipals().getPrincipal("Chat_2").getValidity()).equals("31/12/9999 00:00:00")) {
            txt_Chat3_Principal2_valid.setText("Unlimited");
        } else {
            txt_Chat3_Principal2_valid.setText("Before " + dtf.format(kdcDB.getRealms().getRealm("Chat_2").getPrincipals().getPrincipal("Chat_3").getValidity()));
        }
        txt_Chat3_Principal2_key.setText(kdcDB.getRealms().getRealm("Chat_3").getPrincipals().getPrincipal("Chat_2").getKey().toString());
        if (dtf.format(kdcDB.getRealms().getRealm("Chat_3").getPrincipals().getPrincipal("Chat_2").getKeyValidity()).equals("31/12/9999 00:00:00")) {
            txt_Chat3_Principal2_keyvalid.setText("Unlimited");
        } else {
            txt_Chat3_Principal2_keyvalid.setText("Before " + dtf.format(kdcDB.getRealms().getRealm("Chat_2").getPrincipals().getPrincipal("Chat_3").getKeyValidity()));
        }
        if (kdcDB.getRealms().getRealm("Chat_3").getPrincipals().getPrincipal("Chat_2").getTicketValidity() == 0) {
            txt_Chat3_Principal2_tktval.setText("Unlimited");
        } else {
            txt_Chat3_Principal2_tktval.setText(String.valueOf(kdcDB.getRealms().getRealm("Chat_3").getPrincipals().getPrincipal("Chat_2").getTicketValidity()) + " seconds");
        }
    }
    //endregion Private method(s)

    public HexString getServerKey(String server) {
        return kdcDB.getRealms().getRealm(server).getKey();
    }
}

class DB{
    private Realms realms;

    DB(){
        this.realms = new Realms();

        realms.Add("Chat_1");
        realms.Add("Chat_2");
        realms.Add("Chat_3");

        realms.getRealm("Chat_1").getPrincipals().Add("Chat_2", AES.getPasswordKey("Pwd_2" + (char)0 + "Chat_2"));
        realms.getRealm("Chat_1").getPrincipals().Add("Chat_3", AES.getPasswordKey("Pwd_3" + (char)0 + "Chat_3"));

        realms.getRealm("Chat_2").getPrincipals().Add("Chat_1", AES.getPasswordKey("Pwd_1" + (char)0 + "Chat_1"));
        realms.getRealm("Chat_2").getPrincipals().Add("Chat_3", AES.getPasswordKey("Pwd_3" + (char)0 + "Chat_3"));

        realms.getRealm("Chat_3").getPrincipals().Add("Chat_1", AES.getPasswordKey("Pwd_1" + (char)0 + "Chat_1"));
        realms.getRealm("Chat_3").getPrincipals().Add("Chat_2", AES.getPasswordKey("Pwd_2" + (char)0 + "Chat_2"));
    }

    public Realms getRealms(){
        return this.realms;
    }

    class Realms{
        private Vector<Realm> realms;

        Realms(){
            realms = new Vector<Realm>();
        }

        public void Add(String name){
            realms.add(new Realm(name));
        }

        public int getSize(){
            return realms.size();
        }

        public Realm getRealm(String name) {
            for (int i=0;i<realms.size();i++) {
                if (realms.get(i).getName().equals(name)) {
                    return realms.get(i);
                }
            }

            return null;
        }
    }

    class Realm {
        private String name;
        private HexString key;
        private Principals principals;

        Realm(String name){
            this.principals = new Principals();
            this.name = name;
            this.key = AES.getRandomKey();
        }

        public String getName() {
            return this.name;
        }

        public HexString getKey() {
            return this.key;
        }

        public Principals getPrincipals() {
            return this.principals;
        }

        class Principals{
            private Vector<Principal> principals;

            Principals(){
                principals = new Vector<Principal>();
            }

            public void Add(String uid, HexString key){
                principals.add(new Principal(uid, key));
            }

            public int getSize(){
                return principals.size();
            }

            public Principal getPrincipal(String uid) {
                for (int i=0;i<principals.size();i++) {
                    if (principals.get(i).getUid().equals(uid)) {
                        return principals.get(i);
                    }
                }

                return null;
            }
        }

        class Principal {
            private String uid;
            private LocalDateTime validity;
            private HexString key;
            private LocalDateTime keyValidity;
            private int ticketValidity;

            Principal(String uid, HexString key) {
                this(uid, key, 0);
            }

            Principal(String uid, HexString key, int ticketValidity) {
                this(uid, key, LocalDateTime.parse("31/12/9999 00:00:00", DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")), ticketValidity);
            }

            Principal(String uid, HexString key, LocalDateTime keyValidity) {
                this(uid, key, LocalDateTime.parse("31/12/9999 00:00:00", DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")), 0);
            }

            Principal(String uid, HexString key, LocalDateTime keyValidity, int ticketValidity) {
                this(uid, LocalDateTime.parse("31/12/9999 00:00:00", DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")), key, keyValidity, ticketValidity);
            }

            Principal(String uid, LocalDateTime validity, HexString key) {
                this(uid, validity, key, 0);
            }

            Principal(String uid, LocalDateTime validity, HexString key, int ticketValidity) {
                this(uid, validity, key, LocalDateTime.parse("31/12/9999 00:00:00", DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")), ticketValidity);
            }

            Principal(String uid, LocalDateTime validity, HexString key, LocalDateTime keyValidity) {
                this(uid, validity, key, keyValidity, 0);
            }

            Principal(String uid, LocalDateTime validity, HexString key, LocalDateTime keyValidity, int ticketValidity) {
                this.uid = uid;
                this.validity = validity;
                this.key = key;
                this.keyValidity = keyValidity;
                this.ticketValidity = ticketValidity;
            }

            public String getUid() {
                return this.uid;
            }

            public LocalDateTime getValidity() {
                return this.validity;
            }

            public HexString getKey() {
                return this.key;
            }

            public LocalDateTime getKeyValidity() {
                return this.keyValidity;
            }

            public int getTicketValidity() {
                return this.ticketValidity;
            }
        }
    }
}

class AS {
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    private HexString STK;

    AS() {
        this.STK = AES.getRandomKey();
    }

    public AS_REP get_AS_REP(frm_KDC parentKDC, AS_REQ msg_AS_REQ) throws Exception {
        parentKDC.appendLog("[AS] - Processing AS_REQ.");

        try {
            LocalDateTime tmpTS = LocalDateTime.parse(AES.decrypt(msg_AS_REQ.getTs(), parentKDC.kdcDB.getRealms().getRealm(msg_AS_REQ.getReq()).getPrincipals().getPrincipal(msg_AS_REQ.getUid()).getKey()), dtf);
            parentKDC.appendLog("[AS] - Received TimeStamp successfully decrypted (using client's key from database).");
            try {
                if (LocalDateTime.now().minusMinutes(5).compareTo(tmpTS) < 0 && LocalDateTime.now().plusMinutes(5).compareTo(tmpTS) > 0) {
                    parentKDC.appendLog("[AS] - Received TimeStamp is within accepted limits.");
                    parentKDC.appendLog("[AS] - Sending AS_REP.");
                    return new AS_REP(this.STK);
                } else {
                    parentKDC.appendLog("[AS] - Received TimeStamp is not within accepted limits. -> Authentication refused.");
                    throw new Exception("TimeStamp out of accepted limits!");
                }
            } catch (Exception ex) {
                throw new Exception("Failed to check received TimeStamp.");
            }
        } catch (Exception ex) {
            throw new Exception("Failed to decrypt received TimeStamp. Wrong password?");
        }
    }

    public AS_TGT get_AS_TGT(frm_KDC parentKDC, AS_REQ msg_AS_REQ) throws Exception {
        AS_TGT tmp_AS_TGT = new AS_TGT(STK, msg_AS_REQ.getUid(), msg_AS_REQ.getAddr());

        return tmp_AS_TGT;
    }
}

class TGS{
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    private HexString keyTGS;

    TGS() {
        keyTGS = AES.getRandomKey();
    }

    HexString getKey() {
        return keyTGS;
    }

    public TGS_REP get_TGS_REP(frm_KDC parentKDC, TGS_REQ msg_TGS_REQ, AS_TGT msg_AS_TGT, TGS_ST kdc_TGS_ST) throws Exception {
        if (LocalDateTime.now().compareTo(msg_AS_TGT.getTs()) > 0) {
            parentKDC.appendLog("[TGS] - Received \"Short Term Key\" is valid.");
            if (LocalDateTime.now().minusMinutes(5).compareTo(msg_TGS_REQ.getTs()) < 0 && LocalDateTime.now().plusMinutes(5).compareTo(msg_TGS_REQ.getTs()) > 0) {
                parentKDC.appendLog("[TGS] - TGS_REQ TimeStamp is within accepted limits..");
                if (msg_AS_TGT.getUid().equals(msg_TGS_REQ.getUid())) {
                    parentKDC.appendLog("[TGS] - \"User ID\" in TGS_REQ matches with \"User ID\" in \"Ticket Granting Ticket\".");

                    return new TGS_REP(kdc_TGS_ST.getSk(), kdc_TGS_ST.getExp(), msg_TGS_REQ.getReq());
                } else {
                    //kdcServer_AS.sendMessage("msg: AUTH_ERR" + (char)0 + "err: \"User ID\" in TGS_REQ does not match with \"User ID\" in AS_TGT.");
                    throw new Exception("\"User ID\" in TGS_REQ does not match with \"User ID\" in \"Ticket Granting Ticket\".");
                }
            } else {
                throw new Exception("TGS_REQ TimeStamp is not within accepted limits.");
            }
        } else {
            throw new Exception("\"Ticket Granting Ticket\" expired.");
        }
    }

    public TGS_ST get_TGS_ST(frm_KDC parentKDC, TGS_REQ msg_TGS_REQ, AS_TGT msg_AS_TGT) {
        TGS_ST tmp_TGS_ST = new TGS_ST(AES.getRandomKey(), msg_TGS_REQ.getUid(), msg_AS_TGT.getAddr());

        return tmp_TGS_ST;
    }
}
