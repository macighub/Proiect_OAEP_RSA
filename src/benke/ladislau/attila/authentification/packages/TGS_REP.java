package benke.ladislau.attila.authentification.packages;

import benke.ladislau.attila.crypto.AES;
import benke.ladislau.attila.crypto.cryptUtils.HexString;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TGS_REP {
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    private HexString sk;
    private LocalDateTime exp;
    private String dest;
    private LocalDateTime ts;

    public TGS_REP() {
        this(AES.getRandomKey(), LocalDateTime.now().plusMinutes(10), "server", LocalDateTime.now());
    }

    public TGS_REP(HexString sk, LocalDateTime exp, String dest) {
        this(sk, LocalDateTime.now().plusMinutes(10), dest, LocalDateTime.now());
    }

    public TGS_REP(String[] pkg) {
        for (int i=0; i<pkg.length; i++) {
            switch (pkg[i].split(":")[0]) {
                case "sk" -> this.sk = new HexString(pkg[i].substring(pkg[i].indexOf(":") + 1).trim());
                case "exp" -> this.exp = LocalDateTime.parse(pkg[i].substring(pkg[i].indexOf(":") + 1).trim(), dtf);
                case "dest" -> this.dest = pkg[i].substring(pkg[i].indexOf(":") + 1).trim();
                case "ts" -> this.ts = LocalDateTime.parse(pkg[i].substring(pkg[i].indexOf(":") + 1).trim(), dtf);
            }
        }
    }

    public TGS_REP(HexString sk, LocalDateTime exp, String dest, LocalDateTime ts) {
        this.sk = sk;
        this.exp = exp;
        this.dest = dest;
        this.ts = ts;
    }

    public void setSk(HexString sk) {
        this.sk = sk;
    }

    public void setExp(LocalDateTime exp) {
        this.exp = exp;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public void setTs(LocalDateTime ts) {
        this.ts = ts;
    }

    public HexString getSk() {
        return this.sk;
    }

    public LocalDateTime getExp() {
        return this.exp;
    }

    public String getDest() {
        return this.dest;
    }

    public LocalDateTime getTs() {
        return this.ts;
    }

    public String getPackage() {
        String tmp_Package = "";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        tmp_Package += "sk: " + this.sk.toString() + (char)0;
        tmp_Package += "exp: " + dtf.format(this.exp) + (char)0;
        tmp_Package += "dest: " + this.dest + (char)0;
        tmp_Package += "ts: " + dtf.format(this.ts);

        return tmp_Package;
    }
}
