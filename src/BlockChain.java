import java.security.NoSuchAlgorithmException;

public class BlockChain {

	// Nested Node class
	private static class Node {
		private Block blk;
		private Node next;

		public Node(Block blk, Node next)
		{
			this.blk = blk;
			this.next = next;
		}
	} // class Node


	// Fields
	public Node first;
	public Node last;


	// Constructor
	public BlockChain (int initial) throws NoSuchAlgorithmException {
		first = new Node(new Block (0, initial, null), null);
		last = first;
	}

	// Methods
	/**
	 * Mines a new candidate block to be added to the list.
	 * @param amount
	 * @return a valid block from specified amount to add onto the list
	 * @throws NoSuchAlgorithmException
	 */
	public Block mine (int amount) throws NoSuchAlgorithmException {
		return new Block(last.blk.getNum() + 1, amount, last.blk.getPrevHash());
	} // mine

	// Returns the size of the blockchain
	public int getSize() {
		return last.blk.getNum() + 1;
	}

	/**
	 * Add this blk to the list
	 * @param blk
	 * @throws IllegalArgumentException
	 */
	public void append (Block blk) throws IllegalArgumentException {
		Node appendNode = new Node(blk, null);
		Node current = first;
		for (; current.next != null; current = current.next) {}
		current.next = appendNode;
		last.next = appendNode;
		last = appendNode;
	} // append

	/**
	 * Removes the last block from the chain
	 * @return true if the last block has been removed
	 *         false if the chain only contains a single block
	 */
	public boolean removeLast () {
		if (this.getSize() == 1) { // One single block
			return false;
		} else {
			Node current = first;
			// Get the node right before the last node
			for (; current.next != last; current = current.next) {}

			last = current;
			current.next = null;

			return true;
		}
	} // removeLast

	// Returns the hash of the last block in the chain.
	public Hash getHash () {
		return last.blk.getHash();
	}

	/**
	 * Walks the blockchain and ensures that its blocks are consistent and valid.
	 * @return true if all blocks are consistent and valid
	 *         false if blocks don't have consistent hash with other blocks
	 *               if blocks don't have valid hash
	 *               if Anna's balance or Bob's balance is negative
	 */
	public boolean isValidBlockChain () {
		int annaBal = 0;

		if (this.getSize() > 1) {
			Node current = first;
			for (; current.next != null; current = current.next) {
				annaBal += current.blk.getAmount();
				// Check the consistency of hash and prevHash
				if (!current.blk.getHash().equals(current.next.blk.getPrevHash()) ||
						!current.blk.hash.isValid()) // Check the hash of a block excluding last block
					return false;
			}

			annaBal += last.blk.getAmount(); // Calculate Anna's balance for the whole transaction

			// Check validity of the hash of the last block
			if (!last.blk.hash.isValid() ||
					// Check validity of Anna's and Bob's balance
					annaBal < 0 || first.blk.getAmount() - annaBal < 0)
				return false;	

			return true;
		} else // One single block
			return true;
	} // isValidBlockChain

	// Prints Alice’s and Bob’s respective balances
	public void printBalances () {
		int annaBal = 0;
		for (Node current = first; current != null; current = current.next) {
			annaBal += current.blk.getAmount();
		}
		int bobBal = first.blk.getAmount() - annaBal;
		System.out.println("Anna: " + annaBal + ", Bob: " + bobBal);
	} //printBalances

	// Returns a string representation of the BlockChain 
	public String toString () {
		String blockChain = new String();
		for (Node current = first; current != null; current = current.next) {
			blockChain += (current.blk).toString() + "\n";
		}
		return blockChain;
	} // toString
} // class BlockChain
