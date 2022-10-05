import java.util.Date;

public class Block {

    public String hash; //블록을 식별할 수 있는 데이터들의 집합을 해싱한 결과값
    public String previousHash; //이 전 블록의 hash 값
    private String data; //블록의 데이터
    private long timeStamp;
    private int nonce; //다른 변수값

    //Block Constructor.
    public Block(String data, String previousHash) {
        this.data = data;
        this.previousHash = previousHash;
        this.timeStamp = new Date().getTime();
        this.hash = calculateHash();
    }

    //블록의 고유 식별자가 될 hash 값을 얻기 위해 블록을 hashing하는 메서드
    public String calculateHash() {
        String calculatedhash = StringUtil.applySha256(
            previousHash + timeStamp + data
        );
        return calculatedhash;
    }

    public void mineBlock(int difficulty) {
        String target = new String(new char[difficulty]).replace('\0', '0'); //Create a string with difficulty * "0"
        while(!hash.substring(0, difficulty).equals(target)){
            nonce ++;
            hash = calculateHash();
        }
        System.out.println("Block Mined!!! : " + hash);
    }

}