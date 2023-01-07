package benke.ladislau.attila.crypto.cryptUtils;

import java.math.BigInteger;

public class CryptFormatter {
    //region Format to Decimal (BigInteger)
    public static String toDecFormat(BigInteger value) {
        String result;

        result = "{" + value.toByteArray().length + " Byte(s); " +
                 value.bitLength() + " Bit(s) + 1 SignBit; " +
                 "SignBit: " + value.signum() + "(" + ((value.signum() < 0) ? "negative" : ((value.signum() > 0) ? "positive" : "zero")) + ")}\n\n" +
                 value.toString();

        if (value.signum() == -1) {
            byte[] valueBytes = value.toByteArray();
        }

        return result;
    }

    public static String toDecFormat(byte[] value) {
        return toDecFormat(new BigInteger(value));
    }

    public static String toDecFormat(HexString value) {
        return toDecFormat(new BigInteger(value.toByteArray()));
    }
    //endregion Format to Decimal (BigInteger)

    //region Format to Byte Array (byte[])
    public static String toByteFormat(byte[] value) {
        String result;

        result = "{" + value.length + " Bytes}\n\n";

        for (int setCnt = 0; setCnt < Math.ceil((value.length + 1) / 10.0); setCnt++) {
            for (int byteCnt = 0; byteCnt < 10; byteCnt++) {
                if ((setCnt * 10) + byteCnt < value.length) {
                    if (byteCnt > 0) {
                        result += ",\t";
                    }
                    result += String.format("%4s", value[(setCnt * 10) + byteCnt]);
                } else {
                    break;
                }
            }
            if (setCnt != (Math.ceil((value.length + 1) / 10.0)) - 1) {
                result += "\n";
            }
        }

        return result;
    }

    public static String toByteFormat(BigInteger value) {
        return toByteFormat(value.toByteArray());
    }

    public static String toByteFormat(HexString value) {
        return toByteFormat(value.toByteArray());
    }
    //endregion Format to Byte Array (byte[])

    //region Format to Hex
    public static String toHexFormat(BigInteger value) {
        return toHexFormat(new HexString(value));
    }

    public static String toHexFormat(byte[] value) {
        return toHexFormat(new HexString(value));
    }

    public static String toHexFormat(HexString value) {
        String result;
        int maxWords = (int) Math.ceil(value.toString().length() / 2.0);
        int maxGroups = (int) Math.ceil(maxWords / 16.0);
        int startWord, endWord;

        result = "{" + maxWords + " Words}\n\n";

        for (int groupCnt = 0; groupCnt < maxGroups; groupCnt++) {
            startWord = groupCnt * 16;
            endWord = ((startWord + 15) < maxWords) ? (startWord + 15) : (startWord + (maxWords - 1 - startWord));
            result += "("
                    + "0".repeat(String.valueOf(maxWords).length() - String.valueOf(startWord + 1).length())
                    + (startWord + 1)
                    + " - "
                    + "0".repeat(String.valueOf(maxWords).length() - String.valueOf(endWord + 1).length())
                    + (endWord + 1)
                    + ")";
            for (int wordCnt = startWord; wordCnt <= endWord; wordCnt++) {
                result += "\t" + value.toString().substring(wordCnt * 2, wordCnt * 2 + 2);
            }
            if (groupCnt != maxGroups - 1) {
                result += "\n";
            }
        }

        return result;
    }
    //endregion Format to Hex

    //region Format to Binary
    public static String toBinaryFormat(BigInteger value) {
        return toBinaryFormat(value.toByteArray());
    }

    public static String toBinaryFormat(byte[] value) {
        return toBinaryFormat(CryptConverter.toBinaryString(value));
    }

    public static String toBinaryFormat(BinaryString value) {
        String result = "";

        for (int byteCnt = value.toString().length() - 8; byteCnt >= 0; byteCnt -= 8) {
            String bitString = value.toString().substring(byteCnt, byteCnt + 8);

            result = "\n("
                   + "0".repeat(String.valueOf(value.toString().length()).length() - String.valueOf(byteCnt + 1).length()) + (byteCnt + 1)
                   + " - "
                   + "0".repeat(String.valueOf(value.toString().length()).length() - String.valueOf(byteCnt + 8).length()) + (byteCnt + 8)
                   + ")\t"
                   + String.format("%4s", (byte)(Integer.parseInt(bitString, 2)))
                   + "\t"
                   + String.join(" ",bitString.split(""))
                   + "\t"
                   + result;
        }

        result = "{" + value.toString().length() + " Bit(s)}\n" + result;

        return result;
    }

    public static String toBinaryFormat(HexString value) {
        return toBinaryFormat(value.toByteArray());
    }
    //endregion Format to Binary
}
