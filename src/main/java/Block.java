import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Date;

public class Block {

    public String hash; //블록을 식별할 수 있는 데이터들의 집합을 해싱한 결과값
    public String previousHash; //이 전 블록의 hash 값public String merkleRoot;
    public String merkleRoot;
    public ArrayList<Transaction> transactions = new ArrayList<>(); //our data will be a simple message.
    private long timeStamp;
    private int nonce; //다른 변수값

    //Block Constructor.
    public Block(String previousHash ) {
        this.previousHash = previousHash;
        this.timeStamp = new Date().getTime();

        this.hash = calculateHash(); //Making sure we do this after we set the other values.
    }

    //Calculate new hash based on blocks contents
    public String calculateHash() {
        String calculatedhash = StringUtil.applySha256(
                previousHash +
                        Long.toString(timeStamp) +
                        Integer.toString(nonce) +
                        merkleRoot
        );
        return calculatedhash;
    }

    public void mineBlock(int difficulty) {
        merkleRoot = StringUtil.getMerkleRoot(transactions);
        String target = StringUtil.getDificultyString(difficulty); //Create a string with difficulty * "0"
        while(!hash.substring(0, difficulty).equals(target)) {
            nonce ++;
            hash = calculateHash();
        }
        System.out.println("Block Mined!!! : " + hash);
    }

    //Add transactions to this block
    //트랜잭션의 유효성을 검사하고 블록에 추가할 지 말지를 결정하는 메소드
    public boolean addTransaction(Transaction transaction) {
        //process transaction and check if valid, unless block is genesis block then ignore.
        if(transaction == null) return false;
        if((previousHash != "0")) {
            if((!transaction.processTransaction())) {
                System.out.println("Transaction failed to process. Discarded.");
                return false;
            }
        }
        transactions.add(transaction);
        System.out.println("Transaction Successfully added to Block");
        return true;
    }

}