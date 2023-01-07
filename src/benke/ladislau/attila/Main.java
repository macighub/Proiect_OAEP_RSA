package benke.ladislau.attila;

import benke.ladislau.attila.chat.IpV4address;
import benke.ladislau.attila.authentification.kdc.frm_KDC;

import java.awt.*;

public class Main {
    private static frm_EncryptedComm frm_ChatOne;
    private static frm_EncryptedComm frm_ChatTwo;
    private static frm_KDC frm_Kerberos;

    public static void main(String[] args) throws Exception {
        Thread Thread1 = new Thread() {
            public void run() {Thread1_Run();}
        };
        Thread1.run();

        Thread Thread2 = new Thread() {
            public void run() {Thread2_Run();}
        };
        Thread2.run();

        //Sleep main thread until chat forms are initialized
        while (Thread1.isAlive() || Thread2.isAlive()) {
            Thread.sleep(100);
        }
        Thread Thread3 = new Thread() {
            public void run() {Thread3_run();}
        };
        Thread3.run();

        // Sleep main thread until frm_Kerberos is initialized
        while (Thread3.isAlive()) {
            Thread.sleep(100);
        }
    }

    private static void Thread1_Run() {
        frm_ChatOne = new frm_EncryptedComm("Chat_1", 50001, new IpV4address("localhost"), 50000, new IpV4address("LocalHost"), 50002);

        frm_ChatOne.setVisible(true);
        frm_ChatOne.setSize(GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getWidth() / 2, (GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height / 3) * 2);
    }

    private static void Thread2_Run() {
        frm_ChatTwo = new frm_EncryptedComm("Chat_2", 50002, new IpV4address("localhost"), 50000, new IpV4address("LocalHost"), 50001);

        frm_ChatTwo.setVisible(true);
        frm_ChatTwo.setSize(GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getWidth() / 2, (GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height / 3) * 2);
        frm_ChatTwo.setLocation(GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getWidth() / 2,0);
    }

    private static void Thread3_run() {
        frm_Kerberos = new frm_KDC("Key distribution center (KDC)");

        frm_Kerberos.setVisible(true);
        frm_Kerberos.setSize(GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getWidth(), (GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height / 5) * 2);
        frm_Kerberos.setLocation(0,GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getHeight() - frm_Kerberos.getHeight());

        frm_ChatOne.serverKey = frm_Kerberos.getServerKey("Chat_1");
        frm_ChatTwo.serverKey = frm_Kerberos.getServerKey("Chat_2");
    }
}
