package mycode.main.Test;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import jodd.net.URLCoder;

public class UrlDecodeTest {
    public static void main(String[] args) throws UnsupportedEncodingException, DecoderException {
        String encodeStr = "UTF-16BE";
        System.out.println(hexEncodeString("108013", encodeStr));
        System.out.println(hexEncodeString("12345", encodeStr));
        System.out.println(hexEncodeString("0977788978", encodeStr));
        System.out.println(hexEncodeString("Unicode簡訊發送測試test20240105", encodeStr));
//        System.out.println(hexEncodeString("This is SpLmGet Test20240105  This is SpLmGet Test20240105  This is SpLmGet Test20240105  This is SpLmGet Test20240105  This is SpLmGet Test20240105  This is SpLmGet Test20240105  ", encodeStr));

        System.out.println("---------------------------------");

        System.out.println(URLCoder.encode("108013", Charset.forName(encodeStr)));
        System.out.println(URLCoder.encode("12345", Charset.forName(encodeStr)));
        System.out.println(URLCoder.encode("0977788978", Charset.forName(encodeStr)));
        System.out.println(URLCoder.encode("Unicode簡訊發送測試test20240105", Charset.forName(encodeStr)));
//        System.out.println(URLCoder.encode("This is SpLmGet Test20240105  This is SpLmGet Test20240105  This is SpLmGet Test20240105  This is SpLmGet Test20240105  This is SpLmGet Test20240105  This is SpLmGet Test20240105  ", Charset.forName(encodeStr)));


//		System.out.println("---------------------------------");
    }

    public static String hexEncodeString(String s, String encode) throws UnsupportedEncodingException {
        String hexString = Hex.encodeHexString(s.getBytes(encode));
//		System.out.println(hexString);
        String results = "";
        for (int i = 0; i < hexString.length(); i = i + 2) {

            results += "%" + hexString.substring(0 + i, 2 + i);
        }
        return results;
    }
}
