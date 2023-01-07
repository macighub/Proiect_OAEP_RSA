package benke.ladislau.attila.crypto.cryptUtils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

public class HexString {
    //region Instance Variable(s)
    private static final byte[] HEX_ARRAY = "0123456789ABCDEF".getBytes(StandardCharsets.UTF_8);
    private String value;
    //endregion Instance Variables

    //region Constructor(s)
    public HexString(String hexString) {
        this(toByteArray(hexString));
    }

    public HexString(BigInteger bigint) {
        this(bigint.toByteArray());
    }

    public HexString(byte[] byteArray) {
        byte[] hexChars = new byte[byteArray.length * 2];

        for (int byteCnt = 0; byteCnt < (byteArray.length * 2); byteCnt += 2) {
            int v = byteArray[byteCnt / 2] & 0xFF;
            hexChars[byteCnt] = HEX_ARRAY[v >>> 4];
            hexChars[byteCnt + 1] = HEX_ARRAY[v & 0x0F];
        }
        this.value = new String(hexChars, StandardCharsets.UTF_8);
    }
    //endregion Constructor(s)

    //region Public Method(s)
    public byte[] toByteArray() {
        return this.toByteArray(this.value);
    }

    public String toString() {
        return value;
    }

    public String toText() {
        return new String(this.toByteArray());
    }
    //endregion Public Method(s)

    //region Private Method(s)
    private static byte[] toByteArray(String hexValue) {
        byte[] result = new byte[]{0};
        byte[] byteArray = new byte[hexValue.length() / 2];

        try {
            for (int i = 0; i < hexValue.length(); i += 2) {
                byteArray[i / 2] = (byte) ((Character.digit(hexValue.toString().charAt(i), 16) << 4)
                        + Character.digit(hexValue.toString().charAt(i + 1), 16));
            }
            result = byteArray;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            return result;
        }
    }
    //endregion Private Method(s)
}
