package benke.ladislau.attila.crypto;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.SecureRandom;

public class OAEP {
    //region Instance Variable(s)
    SecureRandom random = new SecureRandom();
    byte[] k0Bytes;
    //endregion instance Variable(s)

    //region Constructor(s)
    public OAEP() {
        this(32);
    }

    OAEP(int randomLength) {
        this.k0Bytes = new byte[randomLength];
    }
    //endregion Constructor(s)

    //region Public Method(s)
    public byte[] decode(byte[] m,  BigInteger n) {
        byte[] result;
        int mLength = 0;

        byte[] xBytes;
        byte[] xMask;
        byte[] yBytes;
        byte[] yMask;
        byte[] mBytes;

        //Extract X(random positive byte at beginning excluded) and Y from m
        xBytes = new byte[m.length - k0Bytes.length - 1];
        yBytes = new byte[k0Bytes.length];

        System.arraycopy(m, 1, xBytes, 0, xBytes.length);
        System.arraycopy(m, m.length - k0Bytes.length, yBytes, 0, yBytes.length);

        //Create mask of length(k0) for Y using X
        yMask = createMask(xBytes, k0Bytes.length);

        //Calculate random by applying XOR between Y and last mask created
        for (int byteCnt = 0; byteCnt < k0Bytes.length; byteCnt++) {
            k0Bytes[byteCnt] = (byte) (yBytes[byteCnt] ^ yMask[byteCnt]);
        }

        //Create mask of length(X) for X using previously calculated random
        xMask = createMask(k0Bytes, xBytes.length);

        //Calculate message padded with zeros up to length(xBytes) by applying XOR between X and last mask created
        mBytes = new byte[xBytes.length];
        for (int byteCnt = 0; byteCnt < xBytes.length; byteCnt++) {
            mBytes[byteCnt] = (byte) (xBytes[byteCnt] ^ xMask[byteCnt]);
        }

        //Remove padded zeros from message
        for (int cnt = 0; cnt < mBytes.length; cnt++) {
            if (mBytes[cnt] != 0) {
                mLength = cnt + 1;
            }
        }
        if (mLength > 0) {
            result = new byte[mLength];
            System.arraycopy(mBytes, 0, result, 0, mLength);
        } else {
            result = new byte[]{0};
        }

        return result;
    }

    public byte[] encode(byte[] m, BigInteger n) {
        byte[] result;

        int k1Length;
        int maxBitLength;

        byte[] mBytes;
        byte[] mMask;
        byte[] k0Mask;
        byte[] xBytes;
        byte[] yBytes;

        /*
         Calculate length of required padding with zeros
         length(k1) = length(n) - length(m) - length(k0) - 1
         (one byte reserved for positive byte at beginning to prevent
         negative BigInteger as RSA input)

         k1 - zeros for padding
         n - RSA modulus
         m - message
         k0 - OAEP random
        */
        if (n.bitLength() == BigInteger.ZERO.bitLength()) {
            maxBitLength = 2048;
        } else {
            maxBitLength = n.bitLength();
        }
        k1Length = (int) Math.ceil(maxBitLength / 8.0) - m.length - k0Bytes.length - 1;

        if (k1Length >= 0) {
            // Generate random of length(k0)
            random.nextBytes(k0Bytes);

            // Create message padded with zeros up to length(m) + length(k1) in bytes
            mBytes = new byte[m.length + k1Length];
            System.arraycopy(m, 0, mBytes, 0, m.length);

            // Create mask of length(m) + length(k1) for message padded with zeros using random as input
            mMask = createMask(k0Bytes, m.length + k1Length);

            // Calculate X by applying XOR between message padded with zeros and previously created mask
            xBytes = new byte[m.length + k1Length];
            for (int byteCnt = 0; byteCnt < mBytes.length; byteCnt++) {
                xBytes[byteCnt] = (byte) (mBytes[byteCnt] ^ mMask[byteCnt]);
            }

            // Create mask of length(k0) for random using previously applied XOR
            k0Mask = createMask(xBytes, k0Bytes.length);

            // Calculate Y by applying XOR between random and last mask created
            yBytes = new byte[k0Bytes.length];
            for (int byteCnt = 0; byteCnt < k0Bytes.length; byteCnt++) {
                yBytes[byteCnt] = (byte) (k0Bytes[byteCnt] ^ k0Mask[byteCnt]);
            }

            // Create encoded message: {random positive byte} || X || Y
            result = new byte[m.length + k1Length + k0Bytes.length + 1];
            result[0] = (byte) (random.nextInt(127) + 1);
            System.arraycopy(xBytes, 0, result, 1, xBytes.length);
            System.arraycopy(yBytes, 0, result, xBytes.length + 1, yBytes.length);
        } else {
            result = new byte[(n.bitLength() + 1) / 8];
        }

        return result;
    }

    /*
    Maximum message length is length(n) - length(k0) - 1
    (one byte reserved for positive byte at beginning to prevent
    negative number as RSA input)

    n - RSA modulus
    k0 - OAEP Random
     */
    public int getMaxLength(BigInteger n) {
        return (int) Math.ceil(n.bitLength() / 8) - k0Bytes.length - 1;
    }
    //endregion Public Method(s)

    //region Private Method(s)
    private byte[] createMask(byte[] input, int targetLength) {
        byte[] result = new byte[targetLength];
        int position = 0;
        int remaining;

        while (position < targetLength) {
            input = sha256(input);
            if (position == 0) {
                byte[] firstByte = {input[0]};
            }
            remaining = targetLength - position;
            System.arraycopy(input,0, result, position, (remaining >= 32) ? 32 : remaining);
            position += 32;
        }

        return result;
    }

    private byte[] sha256(byte[] input) {
        byte[] result = new byte[32];

        try {
            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
            result = sha256.digest(input);
        } finally {
            return result;
        }
    }
    //endregion Private Method(s)
}
