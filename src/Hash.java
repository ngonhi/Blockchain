import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Hash {
	// Fields
	public byte[] hash;

	// Constructor
	public Hash(byte[] data) throws NoSuchAlgorithmException{
		hash = data;
	}

	// Methods
	// Returns a string representation of the BlockChain 
	public byte[] getData() {
		return hash;
	}

	// Returns true if this hash meets the criteria for validity
	public boolean isValid() {
		return (hash[0] == 0 && hash[1] == 0 && hash[2] == 0);
	}

	// Returns the string representation of the hash as a string of hexadecimal digits
	public String toString() {
		int[] n = new int[hash.length];
		String hashString = new String();

		for (int i = 0; i < hash.length; i++) {
			n[i] = Byte.toUnsignedInt(hash[i]);
			hashString += String.format("%02x", n[i]);
		}
		return hashString;
	} // toString

	// Returns true if this hash is structurally equal to the argument
	public boolean equals (Object other) {
		if  (!(other instanceof Hash))
			return false;
		else 
			return Arrays.equals(hash, ((Hash) other).hash);
	} // equals
} // class Hash
