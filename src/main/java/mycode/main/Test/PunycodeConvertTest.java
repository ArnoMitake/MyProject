package mycode.main.Test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.IDN;
import java.nio.charset.StandardCharsets;

/**
 * 檔案下載連結:  https://data.iana.org/TLD/tlds-alpha-by-domain.txt
 */

public class PunycodeConvertTest {

	public static void main(String[] args) {
		//D:\工作事項\Temp\tlds-alpha-by-domain.txt
		try (FileInputStream input = new FileInputStream("D:\\工作事項\\Temp\\tlds-alpha-by-domain.txt");
			 InputStreamReader reader = new InputStreamReader(input, StandardCharsets.UTF_8)) {
			BufferedReader br = new BufferedReader(reader);
			String line = null;
			int i=0;
			while ((line = br.readLine()) != null) {
				i++;
				// Decode back to the original domain name
				String decodedDomain = IDN.toUnicode(line);
//				String decodedDomain = line;
				System.out.println(i + ". Decoded from Punycode: " + decodedDomain);

			}

		} catch (IOException io) {
			io.printStackTrace();
		}

	}
}
