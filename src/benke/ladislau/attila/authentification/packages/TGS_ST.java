package benke.ladislau.attila.authentification.packages;

import benke.ladislau.attila.chat.IpV4address;
import benke.ladislau.attila.crypto.cryptUtils.HexString;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TGS_ST {
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    private HexString sk;
    private LocalDateTime exp;
    private String uid;
    private IpV4address addr;
    private LocalDateTime ts;

    public TGS_ST(HexString sk, String uid, IpV4address addr) {
        this(sk, LocalDateTime.now(), uid, addr, LocalDateTime.now().plusMinutes(10));
    }

    public TGS_ST(String[] pkg) {
        for (int i=0; i<pkg.length; i++) {
            switch (pkg[i].split(":")[0]) {
                case "sk" -> this.sk = new HexString(pkg[i].substring(pkg[i].indexOf(":") + 1).trim());
                case "ts" -> this.ts = LocalDateTime.parse(pkg[i].substring(pkg[i].indexOf(":") + 1).trim(), dtf);
                case "uid" -> this.uid = pkg[i].substring(pkg[i].indexOf(":") + 1).trim();
                case "addr" -> this.addr = new IpV4address(pkg[i].substring(pkg[i].indexOf(":") + 1).trim());
                case "exp" -> this.exp = LocalDateTime.parse(pkg[i].substring(pkg[i].indexOf(":") + 1).trim(), dtf);
            }
        }
    }

    public TGS_ST(HexString sk, LocalDateTime ts, String uid, IpV4address addr, LocalDateTime exp) {
        this.sk = sk;
        this.ts = ts;
        this.uid = uid;
        this.addr = addr;
        this.exp = exp;
    }

    public void setSk(HexString sk) {
        this.sk = sk;
    }

    public void setTs(LocalDateTime ts) {
        this.ts = ts;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setAddr(IpV4address addr) {
        this.addr = addr;
    }

    public void setExp(LocalDateTime exp) {
        this.exp = exp;
    }

    public HexString getSk() {
        return sk;
    }

    public LocalDateTime getTs() {
        return this.ts;
    }

    public String getUid() {
        return this.uid;
    }

    public IpV4address getAddr() {
        return this.addr;
    }

    public LocalDateTime getExp() {
        return this.exp;
    }

    public String getPackage() {
        String tmp_Package = "";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        tmp_Package += "sk: " + this.sk.toString() + (char)0;
        tmp_Package += "ts: " + dtf.format(this.ts) + (char)0;
        tmp_Package += "uid: " + this.uid + (char)0;
        tmp_Package += "addr: " + this.addr.toString() + (char)0;
        tmp_Package += "exp: " + dtf.format(this.exp);

        return tmp_Package;
    }
}
