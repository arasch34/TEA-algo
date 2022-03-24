

public class TEA {

    private static final int DELTA = 0x9e3779b9; // key scheduling constant
    private static long M = (1L << 32) - 1;  

    public static long encrypt(long b, int[] key) {   //TEA encrytion 

        int R = (int) b; // right half 
        int L = (int) (b >>> 32); // left half

        long sum = 0;
        for (int i = 0; i < 32; i++) {
            sum += DELTA;
            L += ((R << 4) + key[0]) ^ (R + sum) ^ ((R >>> 5) + key[1]);
            R += ((L << 4) + key[2]) ^ (L + sum) ^ ((L >>> 5) + key[3]);
        }
        return (L & M) << 32 | (R & M);
    }

    public static long decrypt(long b, int[] key) {     //TEA decryption 

        int R = (int) b; 
        int L = (int) (b >>> 32);

        int sum = DELTA << 5;
        for (int i = 0; i < 32; i++) {
            R -= ((L << 4) + key[2]) ^ (L + sum) ^ ((L >>> 5) + key[3]);
            L -= ((R << 4) + key[0]) ^ (R + sum) ^ ((R >>> 5) + key[1]);
            sum -= DELTA;
        }
        return (L & M) << 32 | (R & M);
    }

    public static void main(String [] args){

        long plaintext = 0x01FA45670CAFCDEFL; // 64 bit 
        int[] key = {0xAF6BABCD, 0xEF00F0F0, 0xFEAFAFAF, 0xACCDEFA1}; // 128 bit    
        System.out.println("Original Plaintext : ");
        System.out.printf("0x%016X", plaintext);
        System.out.println();
        System.out.println();
        System.out.println("Key used for encryption : ");
        System.out.printf("0x%08X%X%X%X", key[0], key[1], key[2], key[3]);
        System.out.println();
        System.out.println();        
        long encryption = encrypt(plaintext, key); //encrypt plaintext  
        System.out.println("Ciphertext resulting from encryption : "); 
        System.out.printf("0x%016X", encryption);
        System.out.println();  
        System.out.println();  
        long decryption = decrypt(encryption, key); // decrypt ciphertext 
        System.out.println("Plaintext resulting from decryption of Ciphertext : ");
        System.out.printf("0x%016X", decryption);

    }
    /*
     * For encoding and decoding longer set of plaintext with CBC and the length is not a multiple of 64 bits, zeros need to be added to the blocks.
     * An initialization vector is also needed for encypting/decrypting a series of blocks. There is an XOR between the Initialization vector and the plaintext and 
     * the initialization vector is the cipher text for the block that was before the current block. 
     * 
     */

        
}