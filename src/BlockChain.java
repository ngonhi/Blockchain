import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public class BlockChain {

	private static class Node {
		private Block blk;
		private Node next;

		public Node(Block blk, Node next)
		{
			this.blk = blk;
			this.next = next;
		}
	}

	public Node first;
	public Node last;
	public int size;

	public BlockChain (int initial) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		first = new Node(new Block (0, initial, null), null);
		last = first;
		size = 1;
	}

	public Block mine (int amount) throws NoSuchAlgorithmException {
		return new Block(last.blk.getNum() + 1, amount, last.blk.getPrevHash());
	}

	public int getSize() {
		return size;
	}

	public void append (Block blk) throws IllegalArgumentException {
		Node appendNode = new Node(blk, null);
		Node current = first;
		for (; current.next != null; current = current.next) {}
		current.next = appendNode;
		last.next = appendNode;
		last = appendNode;
		size++;
	}

	public boolean removeLast () {
		if (size == 1) {
			return false;
		} else {
			Node current = first;
			for (; current.next != last; current = current.next) {}
			last = current;
			current.next = null;
			return true;
		}
	}
	
	public Hash getHash () {
		return last.blk.getHash();
	}
	
	public boolean isValidBlockChain () {
		int total = 0;
		if (size > 1) {
			Node current = first;
			for (; current.next != null; current = current.next) {
				total += current.blk.getAmount();
				if (!current.blk.getHash().equals(current.next.blk.getPrevHash())) {
					return false;
				}
				if(!current.blk.hash.isValid()) {
					return false;
				}
			}
			if(!last.blk.hash.isValid()) {
				return false;
			}
			
			total += last.blk.getAmount();
				if (total < 0) {
					return false;
				} else
					return true;
		} else {
			return true;
		}
	}
	
	public void printBalances () {
		int annaBal = 0;
		for (Node current = first; current != null; current = current.next) {
			annaBal += current.blk.getAmount();
		}
		int bobBal = first.blk.getAmount() - annaBal;
		System.out.println("Anna: " + annaBal + ", Bob: " + bobBal);
	}
	
	public String toString () {
		String blockChain = new String();
		for (Node current = first; current != null; current = current.next) {
			blockChain += (current.blk).toString() + "\n";
		}
		return blockChain;
	}
}
