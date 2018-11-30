import java.util.ArrayList;
import com.google.gson.GsonBuilder;

public class startchain {

    public static ArrayList<Block> blockchain = new ArrayList<Block>();
    public static int difficulty = 5; //difficulty is currently a static value for testing

    public static void main(String[] args) {
        blockchain.add(new Block("Hi Im the first block", "0"));
        System.out.println("trying to mine block 1...");
        blockchain.get(0).mineBlock(difficulty);

        blockchain.add(new Block("Block 2 electric boogaloo", blockchain.get(blockchain.size() -1).hash));
        System.out.println("trying to mine block 2...");
        blockchain.get(1).mineBlock(difficulty);

        blockchain.add(new Block("Round the block yet again", blockchain.get(blockchain.size() -1).hash));
        System.out.println("trying to mine block 3...");
        blockchain.get(2).mineBlock(difficulty);

        System.out.println("\nBlockchain is valid: " + isChainValid());

        String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
        System.out.println("\nThe Blockchain: ");
        System.out.println(blockchainJson);
    }

    public static Boolean isChainValid() {
        Block previousBlock = blockchain.get(0);
        String hashTarget = new String(new char[difficulty]).replace('\0', '0');

        for (Block block: blockchain){
            if (!block.hash.equals(block.calculateHash()) ){
                System.out.println("Current hashes not equal");
                return false;
            }

            if (!previousBlock.hash.equals(block.previousHash) && block != blockchain.get(0)){
                System.out.println("Previous hashes not equal");
                return false;
            }
            if (!block.hash.substring( 0, difficulty).equals(hashTarget)) {
                System.out.println("This block hasn't been mined");
                return false;
            }
            previousBlock = block;
        }

        return true;
    }
}
