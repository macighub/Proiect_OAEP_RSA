package benke.ladislau.attila.crypto.rsa;

import java.math.BigInteger;

public class RSAKey {
    public BigInteger modulus = BigInteger.ZERO;
    public BigInteger exponent = BigInteger.ZERO;

    public RSAKey(BigInteger modulus, BigInteger exponent) {
        this.exponent = exponent;
        this.modulus = modulus;
    }
}
