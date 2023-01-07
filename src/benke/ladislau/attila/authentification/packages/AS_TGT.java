package benke.ladislau.attila.authentification.packages;

import benke.ladislau.attila.chat.IpV4address;
import benke.ladislau.attila.crypto.cryptUtils.HexString;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AS_TGT {
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    private HexString stk;
    private String uid;
    private LocalDateTime ts;
    private IpV4address addr;
    private LocalDateTime exp;

    public AS_TGT(HexString stk, String uid, IpV4address addr) throws NoSuchAlgorithmException {
        this(stk, uid, LocalDateTime.now(), addr, LocalDateTime.now().plusMinutes(5));
    }

    public AS_TGT(String[] pkg) {
        for (int i=0; i<pkg.length; i++) {
            switch (pkg[i].split(":")[0]) {
                case "stk" -> this.stk = new HexString(pkg[i].substring(pkg[i].indexOf(":") + 1).trim());
                case "uid" -> this.uid = pkg[i].substring(pkg[i].indexOf(":") + 1).trim();
                case "ts" -> this.ts = LocalDateTime.parse(pkg[i].substring(pkg[i].indexOf(":") + 1).trim(), dtf);
                case "addr" -> this.addr = new IpV4address(pkg[i].substring(pkg[i].indexOf(":") + 1).trim());
                case "exp" -> this.exp = LocalDateTime.parse(pkg[i].substring(pkg[i].indexOf(":") + 1).trim(), dtf);
            }
        }
    }

    public AS_TGT(HexString stk, String uid, LocalDateTime ts, IpV4address addr, LocalDateTime exp) {
        this.stk = stk;
        this.uid = uid;
        this.ts = ts;
        this.addr = addr;
        this.exp = exp;
    }

    public void setSTK(HexString key) {
        this.stk = key;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setTs(LocalDateTime ts) {
        this.ts = ts;
    }

    public void setAddr(IpV4address addr) {
        this.addr = addr;
    }

    public void setExp(LocalDateTime exp) {
        this.exp = exp;
    }

    public HexString getSTK() {
        return this.stk;
    }

    public String getUid() {
        return this.uid;
    }

    public LocalDateTime getTs() {
        return  this.ts;
    }

    public IpV4address getAddr() {
        return addr;
    }

    public LocalDateTime getExp() {
        return exp;
    }

    public String getPackage () {
        String tmp_Package = "";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        tmp_Package += "stk: " + this.stk.toString() + (char)0;
        tmp_Package += "uid: " + this.uid + (char)0;
        tmp_Package += "ts: " + dtf.format(this.ts) + (char)0;
        tmp_Package += "addr: " + this.addr.toString() + (char)0;
        tmp_Package += "exp: " + dtf.format(this.exp);

        return tmp_Package;
    }
}
