package benke.ladislau.attila.authentification.packages;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TGS_REQ {
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    private LocalDateTime ts;
    private String uid;
    private String req;

    public TGS_REQ() {
        this(LocalDateTime.now(), "user", "server");
    }

    public TGS_REQ(String[] pkg) {
        for (int i=0; i<pkg.length; i++) {
            switch (pkg[i].split(":")[0]) {
                case "ts" -> this.ts = LocalDateTime.parse(pkg[i].substring(pkg[i].indexOf(":") + 1).trim(), dtf);
                case "uid" -> this.uid = pkg[i].substring(pkg[i].indexOf(":") + 1).trim();
                case "req" -> this.req = pkg[i].substring(pkg[i].indexOf(":") + 1).trim();
            }
        }
    }

    public TGS_REQ(LocalDateTime ts, String uid, String req) {
        this.ts = ts;
        this.uid = uid;
        this.req = req;
    }

    public void setTs(LocalDateTime ts) {
        this.ts = ts;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setReq(String req) {
        this.req = req;
    }

    public LocalDateTime getTs() {
        return this.ts;
    }

    public String getUid() {
        return this.uid;
    }

    public String getReq() {
        return this.req;
    }

    public String getPackage() {
        String tmp_Package = "";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        tmp_Package += "ts: " + dtf.format(this.ts) + (char)0;
        tmp_Package += "uid: " + this.uid + (char)0;
        tmp_Package += "req: " + this.req;

        return tmp_Package;
    }
}
