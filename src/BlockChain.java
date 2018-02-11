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
//		byte[] data = "0123456789".getBytes("UTF-8");
//		Hash hash = new Hash(data);
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
		//System.out.println("1");
		int total = 0;
		if (size > 1) {
		//	System.out.println("2");
			Node current = first;
			for (; current.next != null; current = current.next) {
				total += current.blk.getAmount();
				//System.out.println("1 " + total);
				if (!current.blk.getHash().equals(current.next.blk.getPrevHash())) {
					System.out.println("a");
					return false;
				}
			}
			//System.out.println("x");
			total += current.blk.getAmount();
			//System.out.println(total);
			//System.out.println("3");
				if (total < 0) {
		//			System.out.println("b");
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
			//System.out.println("F");
			blockChain += (current.blk).toString() + "\n";
		}
		return blockChain;
	}
}
