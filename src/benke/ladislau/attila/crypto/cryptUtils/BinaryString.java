package benke.ladislau.attila.crypto.cryptUtils;

import java.math.BigInteger;

public class BinaryString {
    //region Instance Variable(s)
    private String value;
    //endregion Instance Variable(s)

    //region Constructor(s)
    public BinaryString(String binaryString) {
        this(toByteArray(binaryString));
    }

    public BinaryString(BigInteger bigInt) {
        this(bigInt.toByteArray());
    }

    public BinaryString(byte[] byteArray) {
        String result = "";

        for (int byteCnt = 0; byteCnt < byteArray.length; byteCnt++) {
            for (int bitCnt = 7; bitCnt >= 0; --bitCnt) {
                result += (byteArray[byteCnt] >>> bitCnt & 1);
            }
        }
        value = result;
    }
    //endregion Constructor(s)

    //region Public Method(s)
    public byte[] toByteArray() {
        return toByteArray(this.value);
    }

    public String toString() {
        return value;
    }

    public String toText() {
        return new String(this.toByteArray());
    }
    //endregion Public Method(s)

    //region Private Method(s)
    private static byte[] toByteArray(String binaryValue) {
        byte[] result = null;
        byte[] byteArray = new byte[(int) Math.ceil(binaryValue.length() / 8)];

        for (int byteCnt = byteArray.length - 1; byteCnt >= 0; byteCnt--) {
            String bitString = binaryValue.substring(byteCnt * 8, (byteCnt * 8) + 8);

            byteArray[byteCnt] = (byte)(Integer.parseInt(bitString, 2));
        }

        return byteArray;
    }
    //endregion Private Method(s)
}
