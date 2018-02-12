import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Block {
	// Fields
	public int num;
	public int amount;
	public Hash prevHash;
	public long nonce;
	public Hash hash;

	// Constructors
	public Block (int num, int amount, Hash prevHash) throws NoSuchAlgorithmException {
		this.num = num;
		this.amount = amount;
		this.prevHash = prevHash;
		this.nonce = 0;

		// Create byte array from block's number
		byte[] numByte = new byte[Integer.BYTES];
		numByte = ByteBuffer.allocate(Integer.BYTES).putInt(num).array();

		// Create byte array from data contained in the block
		byte[] amountByte = new byte[Integer.BYTES];
		amountByte = ByteBuffer.allocate(Integer.BYTES).putInt(amount).array();

		// Create byte array from nonce value of the block
		byte[] nonceByte = new byte[Long.BYTES];
		nonceByte = ByteBuffer.allocate(Long.BYTES).putLong(nonce).array();

		// Mining process
		MessageDigest md = MessageDigest.getInstance("sha-256");
		md.update(numByte);
		md.update(amountByte);
		md.update(nonceByte);
		hash = new Hash(md.digest()); // Create hash with nonce = 0

		if (this.num == 0) { // First block without prevHash
			while(!hash.isValid()) {
				// Create byte array from nonce value of the block
				nonceByte = ByteBuffer.allocate(Long.BYTES).putLong(nonce).array();

				md.reset();
				md.update(numByte);
				md.update(amountByte);
				md.update(nonceByte);
				hash = new Hash(md.digest());
				nonce++;
			} 
			nonce -= 1;
		} else {
			while(!hash.isValid()) {
				// Create byte array from nonce value of the block
				nonceByte = ByteBuffer.allocate(Long.BYTES).putLong(nonce).array();

				md.reset();
				md.update(numByte);
				md.update(amountByte);
				md.update(prevHash.getData());
				md.update(nonceByte);
				hash = new Hash(md.digest());
				nonce++;
			}
			nonce -= 1;
		}
	} // Block

	public Block (int num, int amount, Hash prevHash, long nonce) throws NoSuchAlgorithmException {
		this.num = num;
		this.amount = amount;
		this.prevHash = prevHash;
		this.nonce = nonce;

		// Create byte array from block's number
		byte[] numByte = ByteBuffer.allocate(Integer.BYTES).putInt(num).array();

		// Create byte array from data contained in the block
		byte[] amountByte = ByteBuffer.allocate(Integer.BYTES).putInt(amount).array();

		// Create byte array from nonce value of the block
		byte[] nonceByte = ByteBuffer.allocate(Long.BYTES).putLong(nonce).array();

		// Mining process
		MessageDigest md = MessageDigest.getInstance("sha-256");
		md.update(numByte);
		md.update(amountByte);
		md.update(prevHash.getData());
		md.update(nonceByte);
		hash = new Hash(md.digest());		
	} // Block

	// Returns the number of this block
	public int getNum () {
		return num;
	}

	// Returns the amount transferred that is recorded in this block
	public int getAmount () {
		return amount;
	}

	// Returns the nonce of this block
	public long getNonce() {
		return nonce;
	}

	// Returns the hash of the previous block in the blockchain
	public Hash getPrevHash () {
		return prevHash;
	}

	// Returns the hash of this block
	public Hash getHash () {
		return hash;
	}

	// Returns a string representation of the block
	public String toString () {
		if (num == 0) // One single block
			return "Block " + num + " (Amount: " + amount + ", Nonce: " + nonce
					+ ", prevHash: " + null + ", hash: " + hash.toString() + ")";
		else
			return "Block " + num + " (Amount: " + amount + ", Nonce: " + nonce
					+ ", prevHash: " + prevHash.toString() + ", hash: " + hash.toString() + ")";
	} // toString
} // class Block 


