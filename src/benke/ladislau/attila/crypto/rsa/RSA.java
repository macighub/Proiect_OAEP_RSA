package benke.ladislau.attila.crypto.rsa;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

public class RSA {
    //region Instance Variable(s)
    public RSAKey PublicKey;
    public RSAKey PrivateKey;

    private final static SecureRandom random = new SecureRandom();
    //endregion Instance Variable(s)

    //region Constructor(s)
    RSA() {
        this(2048);
    }

    public RSA(int bitnum) {
        BigInteger p;
        BigInteger q;
        BigInteger n = BigInteger.ZERO;
        BigInteger e = BigInteger.ZERO;
        BigInteger d;
        BigInteger phi = new BigInteger("0");

            // p, q are random prime numbers with bitLength (bitnum / 2)
            p = BigInteger.probablePrime(bitnum / 2, random);
            q = BigInteger.probablePrime(bitnum / 2, random);

            // n is part of both public and private key
            // bitlength of n is bitnum (bitlength of p + bitlength of q)
            n = p.multiply(q);
            //} while ((n.toByteArray().length * 8) > 2048);

            phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE)); // phi = (p - 1) * (q - 1)

            // e is part of public key
            e = e(phi);
            // d is part of private key
            d = d(e, phi);
            //d = e.modInverse(phi);

            PrivateKey = new RSAKey(n, d);
            PublicKey = new RSAKey(n, e);
        if (d.compareTo(BigInteger.ZERO) == -1) {
            d.add(phi);
        };
    }
    //endregion Constructor(s)

    //region Public Method(s)
    // Decrypt using own PrivateKey
    public BigInteger decrypt(BigInteger m) {
        return decrypt(m, this.PrivateKey);
    }

    // Decrypt using provided PrivateKey
    public BigInteger decrypt(BigInteger m, RSAKey PrivateKey) {
        //BigInteger mVal = new BigInteger(m.toByteArray());
        return m.modPow(PrivateKey.exponent, PrivateKey.modulus);
    }

    // Encrypt using own PublicKey
    public BigInteger encrypt(BigInteger m) {
        return encrypt(m, this.PublicKey);
    }

    // Encrypt using provided PublicKey
    public BigInteger encrypt(BigInteger m, RSAKey PublicKey) {
        //BigInteger mVal = new BigInteger(m.toByteArray());
        return m.modPow(PublicKey.exponent, PublicKey.modulus);
    }
    //endregion Public Method(s)

    //region Private Method(s)
    // Calculate d using values of e and phi, where d is the multiplicative inverse of e
    // d * e ≡ 1 (mod phi)
    private BigInteger d(BigInteger a, BigInteger b) {
        return new RSA.ext_Euclid(a, b).x;
    }

    // Find random e such that value of e is between 1 and phi, and the greatest common divisor (gcd) of e and phi is 1
    // 1 < e < phi, gcd(e, phi) = 1
    private BigInteger e(BigInteger phi) {
        BigInteger e;

        do {
            //is Random() enough or should SecureRandom() be used? (e is part of public key)
            e = new BigInteger(phi.bitLength(), new Random());

            // while (e > phi) or (e = 1) generate new random e
            while ((phi.compareTo(e) == -1) || e.equals(BigInteger.ONE)) {
                e = new BigInteger(phi.bitLength(), new Random());
            }
        } while (!gcd(e, phi).equals(BigInteger.ONE)); //

        return e;
    }

    // Calculate the greatest common divisor using Euclidean algorithm
    private BigInteger gcd(BigInteger a, BigInteger b) {
        BigInteger new_b;

        while (!b.equals(BigInteger.ZERO)) {
            new_b = a.mod(b);

            a = new BigInteger(b.toByteArray());
            b = new BigInteger(new_b.toByteArray());
        }

        return a;
    }
    //endregion Private Method(s)

    class ext_Euclid {
        BigInteger gcd;
        BigInteger x;
        BigInteger y;

        ext_Euclid(BigInteger gcd, BigInteger x, BigInteger y) {
            this.gcd = gcd;
            this.x = x;
            this.y = y;
        }

        ext_Euclid(BigInteger a, BigInteger b) {
            if (b.equals(BigInteger.ZERO)) {
                this.gcd = a;
                this.x = BigInteger.ONE;
                this.y = BigInteger.ZERO;
            } else {
                RSA.ext_Euclid gcd_x_y = new RSA.ext_Euclid(b, a.mod(b));

                this.gcd = gcd_x_y.gcd;
                this.x = gcd_x_y.y;
                this.y = gcd_x_y.x.subtract(a.divide(b).multiply(gcd_x_y.y));
            }
        }
    }
}
