package benke.ladislau.attila.authentification.packages;

import benke.ladislau.attila.chat.IpV4address;
import benke.ladislau.attila.crypto.cryptUtils.HexString;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AS_REP {
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    private HexString stk;
    private LocalDateTime ts;
    private String addr;
    private LocalDateTime exp;

    public AS_REP(HexString stk) throws NoSuchAlgorithmException {
        this(stk, LocalDateTime.now(), new IpV4address("localhost").toString() + ":55000", LocalDateTime.now().plusMinutes(5));
    }

    public AS_REP(String[] pkg) {
        for (int i=0; i<pkg.length; i++) {
            switch (pkg[i].split(":")[0]) {
                case "stk" -> this.stk = new HexString(pkg[i].substring(pkg[i].indexOf(":") + 1).trim());
                case "ts" -> this.ts = LocalDateTime.parse(pkg[i].substring(pkg[i].indexOf(":") + 1).trim(), dtf);
                case "addr" -> this.addr = pkg[i].substring(pkg[i].indexOf(":") + 1).trim();
                case "exp" -> this.exp = LocalDateTime.parse(pkg[i].substring(pkg[i].indexOf(":") + 1).trim(), dtf);
            }
        }
    }

    public AS_REP(HexString stk, LocalDateTime ts, String addr, LocalDateTime exp) {
        this.stk = stk;
        this.ts = ts;
        this.addr = addr;
        this.exp = exp;
    }

    public void setSTK(HexString key) {
        this.stk = key;
    }

    public void setTs(LocalDateTime ts) {
        this.ts = ts;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public void setExp(LocalDateTime exp) {
        this.exp = exp;
    }

    public HexString getSTK() {
        return this.stk;
    }

    public LocalDateTime getTs() {
        return  this.ts;
    }

    public String getAddr() {
        return addr;
    }

    public LocalDateTime getExp() {
        return exp;
    }

    public String getPackage () {
        String tmp_Package = "";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        tmp_Package += "stk: " + this.stk.toString() + (char)0;
        tmp_Package += "ts: " + dtf.format(this.ts) + (char)0;
        tmp_Package += "addr: " + this.addr + (char)0;
        tmp_Package += "exp: " + dtf.format(this.exp) + (char)0;

        return tmp_Package;
    }
}
