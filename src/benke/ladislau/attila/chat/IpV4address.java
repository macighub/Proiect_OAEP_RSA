package benke.ladislau.attila.chat;

public class IpV4address {
    Byte[] addressBytes;

    //region Public Method(s)
    public IpV4address(String address) {
        if (address.toUpperCase().equals("LOCALHOST")) {
            addressBytes = new Byte[]{127, 0, 0, 1};
        } else if (isIpV4Address(address)) {
            addressBytes = new Byte[4];
            String[] addrFields = address.split("\\.");

            for (int fieldCnt=0; fieldCnt <= 3; fieldCnt++) {
                addressBytes[fieldCnt] = Byte.parseByte(addrFields[fieldCnt]);
            }
        } else {
            addressBytes = new Byte[]{0, 0, 0, 0};
        }
    }

    public String toString() {
        String returnVal = "";

        for (int fieldCnt = 0; fieldCnt <= 3; fieldCnt++) {
            returnVal += String.valueOf(addressBytes[fieldCnt].intValue());
            if (fieldCnt < 3) {
                returnVal += ".";
            }
        }

        return returnVal;
    }

    public static boolean isIpV4Address(String address) {
        boolean returnVal = false;
        String[] addrFields = address.split("\\.");

        if (addrFields.length == 4) {
            for (int fieldCnt=0; fieldCnt <= 3; fieldCnt++) {
                if (isNumber(addrFields[fieldCnt])) {
                    if (Integer.parseInt(addrFields[fieldCnt]) < 0 || Integer.parseInt(addrFields[fieldCnt]) > 256) {
                        returnVal = false;
                        break;
                    } else if (fieldCnt == 3) {
                        returnVal = true;
                    }
                } else {
                    returnVal = false;
                    break;
                }
            }
        } else {
            returnVal = false;
        }

        return returnVal;
    }
    //endregion Public Method(s)

    //region Private Method(s)
    private static boolean isNumber (String val) {
        if (val == "") {
            return false;
        } else {
            try {
                int nr = Integer.parseInt(val);
            } catch (NumberFormatException e) {
                return false;
            }
            return true;
        }
    }
    //endregion Private Method(s)
}
