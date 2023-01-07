package benke.ladislau.attila.authentification.packages;

import benke.ladislau.attila.chat.IpV4address;
import benke.ladislau.attila.crypto.cryptUtils.HexString;

import java.time.format.DateTimeFormatter;

public class AS_REQ {
    private HexString ts;
    private String uid;
    private String req;
    private IpV4address addr;

    public AS_REQ() {
        this(new HexString(""), "user", "server", new IpV4address("localhost"));
    }

    public AS_REQ(String[] pkg) {
        for (int i=0; i<pkg.length; i++) {
            switch (pkg[i].split(":")[0]) {
                case "ts" -> this.ts = new HexString(pkg[i].substring(pkg[i].indexOf(":") + 1).trim());
                case "uid" -> this.uid = pkg[i].substring(pkg[i].indexOf(":") + 1).trim();
                case "req" -> this.req = pkg[i].substring(pkg[i].indexOf(":") + 1).trim();
                case "addr" -> this.addr = new IpV4address(pkg[i].substring(pkg[i].indexOf(":") + 1).trim());
            }
        }
    }

    public AS_REQ(HexString ts, String uid, String req, IpV4address addr) {
        this.ts = ts;
        this.uid = uid;
        this.req = req;
        this.addr = addr;
    }

    public void setTs(HexString ts) {
        this.ts = ts;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setReq(String req) {
        this.req = req;
    }

    public void setAddr(IpV4address addr) {
        this.addr = addr;
    }

    public HexString getTs() {
        return this.ts;
    }

    public String getUid() {
        return this.uid;
    }

    public String getReq() {
        return this.req;
    }

    public IpV4address getAddr() {
        return this.addr;
    }

    public String getPackage() {
        String tmp_Package = "";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        tmp_Package += "ts: " + this.ts.toString() + (char)0;
        tmp_Package += "uid: " + this.uid + (char)0;
        tmp_Package += "req: " + this.req +(char)0;
        tmp_Package += "addr: " + this.addr.toString();

        return tmp_Package;
    }
}
