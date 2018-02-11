
//import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Hash {
	public byte[] hash;

	public Hash(byte[] data) throws NoSuchAlgorithmException{
		hash = data;
	}
	
	public byte[] getData() {
		return hash;
	}

	public boolean isValid() {
		return (hash[0] == 0 && hash[1] == 0 && hash[2] == 0);
	}

	public String toString() {
		int[] n = new int[hash.length];
		String hashString = new String();
		
		for (int i = 0; i < hash.length; i++) {
			n[i] = Byte.toUnsignedInt(hash[i]);
			hashString += String.format("%02x", n[i]);
		}
		return hashString;
	}

	public boolean equals (Object other) {
		if  (!(other instanceof Hash)) {
			System.out.println("1");
			return false;
		} else {
			return Arrays.equals(hash, ((Hash) other).hash);
		}
	}
}
