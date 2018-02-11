import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Block {
	public int num;
	public int amount;
	public Hash prevHash;
	public long nonce;
	public Hash hash;

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
		hash = new Hash(md.digest());
		
		if (this.num == 0) { // First block
			while(!hash.isValid()) {
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
	}
	
	public Block (int num, int amount, Hash prevHash, long nonce) throws NoSuchAlgorithmException {
		this.num = num;
		this.amount = amount;
		this.prevHash = prevHash;
		this.nonce = nonce;
		
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
				md.update(prevHash.getData());
				md.update(nonceByte);
				hash = new Hash(md.digest());		
	}
	
	public int getNum () {
		return num;
	}
	
	public int getAmount () {
		return amount;
	}
	
	public long getNonce() {
		return nonce;
	}
	
	public Hash getPrevHash () {
		return prevHash;
	}
	
	public Hash getHash () {
		return hash;
	}
	
	public String toString () {
		if (num == 0) {
		return "Block " + num + " (Amount: " + amount + ", Nonce: " + nonce
				+ ", prevHash: " + null + ", hash: " + hash.toString() + ")";
		} else {
			return "Block " + num + " (Amount: " + amount + ", Nonce: " + nonce
					+ ", prevHash: " + prevHash.toString() + ", hash: " + hash.toString() + ")";
		}
	}
}


