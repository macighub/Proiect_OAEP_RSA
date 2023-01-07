package benke.ladislau.attila.crypto.cryptUtils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

public class CryptConverter {
    //region Conversion to Decimal (BigInteger) type
    public static BigInteger toDecimal(byte[] value) {
        return new BigInteger(value);
    }

    public static BigInteger toDecimal(HexString value) {
        return new BigInteger(value.toByteArray());
    }

    public static BigInteger toDecimal(BinaryString value) {
        return new BigInteger(value.toByteArray());
    }

    public static BigInteger toDecimal(String value) {
        return new BigInteger(value.getBytes(StandardCharsets.UTF_8));
    }
    //endregion Conversion to Decimal (BigInteger) type

    //region Conversion to Byte Array (byte[]) type
    public static byte[] toByteArray(BigInteger value) {
        return value.toByteArray();
    }

    public static byte[] toByteArray(HexString value) {
        return value.toByteArray();
    }

    public static byte[] toByteArray(BinaryString value) {
        return value.toByteArray();
    }

    public static byte[] toByteArray(String value) {
        return value.getBytes(StandardCharsets.UTF_8);
    }
    //endregion Conversion to Byte Array (byte[]) type

    //region Conversion to Hex type
    public static HexString toHexString(BigInteger value) {
        return new HexString(value);
    }

    public static HexString toHexString(byte[] value) {
        return new HexString(value);
    }

    public static HexString toHexString(BinaryString value) {
        return toHexString(value.toByteArray());
    }

    public static HexString toHexString(String value) {
        return toHexString(value.getBytes(StandardCharsets.UTF_8));
    }
    //endregion Conversion to Hex type

    //region Conversion to Binary type
    public static BinaryString toBinaryString(BigInteger value) {
        return toBinaryString(value.toByteArray());
    }

    public static BinaryString toBinaryString(byte[] value) {
        return new BinaryString(value);
    }

    public static BinaryString toBinaryString(HexString value) {
        return toBinaryString(value.toByteArray());
    }

    public static BinaryString toBinaryString(String value) {
        return toBinaryString(value.getBytes(StandardCharsets.UTF_8));
    }
    //endregion Conversion to Binary type
}
