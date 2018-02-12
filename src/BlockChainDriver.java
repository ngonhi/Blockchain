import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
public class BlockChainDriver {

	// Print out the list of available commands
	public static void commandMenu () {
		System.out.println("Valid commands:");
		System.out.println("\tmine: discovers the nonce for a given transaction");
		System.out.println("\tappend: appends a new block onto the end of the chain");
		System.out.println("\tremove: removes the last block from the end of the chain");
		System.out.println("\tcheck: checks that the block chain is valid");
		System.out.println("\treport: reports the balances of Alice and Bob");
		System.out.println("\thelp: prints this list of commands");
		System.out.println("\tquit: quits the program");
	} // commandMenu

	// Discover appropriate nonce with entered transferred amount
	public static void mineCommand (Scanner in, BlockChain blkChain) throws NoSuchAlgorithmException {
		System.out.print("Amount transferred? ");
		int amount = Integer.parseInt(in.nextLine());	

		// Create a new block to mine the valid nonce
		Hash prevHash = blkChain.getHash();
		int num = blkChain.getSize();
		Block newBlk = new Block(num, amount, prevHash);
		System.out.println("amount = " + amount + ", nonce = " + newBlk.getNonce());
	} // mineCommand

	// Add the block with discovered nonce to the end of the blockchain
	public static void appendCommand (Scanner in, BlockChain blkChain) throws NoSuchAlgorithmException {
		System.out.print("Amount transferred? ");
		int amount = Integer.parseInt(in.nextLine());

		System.out.print("Nonce? ");
		long nonce = Long.parseLong(in.nextLine());

		// Create a new block with the discovered nonce
		Hash prevHash = blkChain.getHash();
		int num = blkChain.getSize();
		Block newBlk = new Block(num, amount, prevHash, nonce);
		blkChain.append(newBlk);
	} // appendCommand

	// Check validity of the blockchain
	public static void checkCommand (BlockChain blkChain) {
		if (blkChain.isValidBlockChain())
			System.out.println("Chain is valid!");
		else
			System.out.println("Chain is invalid!");
	} // checkCommand

	// Execute all commands
	public static void commandRunner (String command, Scanner in, BlockChain blkChain) throws NoSuchAlgorithmException {
		if (command.equals("mine"))
			mineCommand(in, blkChain);
		else if (command.equals("append"))
			appendCommand(in, blkChain);
		else if (command.equals("remove"))
			blkChain.removeLast();
		else if (command.equals("check"))
			checkCommand(blkChain);
		else if (command.equals("report"))
			blkChain.printBalances();
		else if (command.equals("help"))
			commandMenu();
		else if (command.equals("quit"))
			System.exit(0);
		else
			System.err.println("Invalid commands!");
	} // commandRunner

	/**
	 * Main method
	 * 1. Take a single command-line argument
	 * 2. Print out the content of blockchain
	 * 3. Read in a command from user
	 * 4. Execute that command
	 * @param args
	 * @throws NoSuchAlgorithmException
	 */
	public static void main(String[] args) throws NoSuchAlgorithmException {
		int amount = 0;
		String command = new String();
		Scanner in = new Scanner(System.in);

		// Check validity of command-line argument
		if (args.length != 1) {
			System.err.println("Invalid number of arguments");
			System.out.print("Enter initial amount: ");
			amount = Integer.parseInt(in.nextLine());
		} else
			amount = Integer.parseInt(args[0]);

		if (amount < 0) {
			System.err.println("Amount has to be non-negative");
			System.out.print("Re-enter amount: ");
			amount = Integer.parseInt(in.nextLine());
		}

		// Create the first block with initial amount
		BlockChain blkChain = new BlockChain(amount);

		while (true) {
			System.out.println();

			// Print out the contents of the blockchain.
			System.out.print(blkChain.toString());

			// Read in a command from the user.
			System.out.print("Command? ");
			command = in.nextLine();

			// Execute command
			commandRunner(command, in, blkChain);
		}
	} // main
} // BlockChainDriver class
