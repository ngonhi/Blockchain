
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Hash {
	public byte[] hash;

	public Hash(byte[] data) throws NoSuchAlgorithmException{
		//MessageDigest md = MessageDigest.getInstance("sha-256");
		//md.update(data);
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
		if (other == this) {
			return true;
		} else if (!(other instanceof Hash)) {
			System.out.println("1");
			return false;
		} else {
			return Arrays.equals(hash, ((Hash) other).hash);
		}
	}
	
//	public static void main (String[] args) throws UnsupportedEncodingException, NoSuchAlgorithmException {
//		byte[] data = "0123456789".getBytes("UTF-8");
//		Hash hash = new Hash(data);
//		
//		System.out.println(hash.isValid());
//		System.out.println(hash.equals(null));
//		
//	}
//	
	
}
