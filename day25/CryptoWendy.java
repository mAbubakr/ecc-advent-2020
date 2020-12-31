
public class CryptoWendy {

	public static final long SUBJECT_NUMBER = 7;
	public static final long MODULO = 20201227;
	
	
	public static long findLoopSize(long publicKey) {
		long value = 1;
		long loopSize = 0;
		while (value != publicKey) {
			loopSize++;
			value = value * SUBJECT_NUMBER;
			value = value % MODULO;
		}	
		return loopSize;
	}
	
	public static long findEncryptionKey(long subjectNumber, long loopSize) {		
		long value=1;
		for (long i=0; i<loopSize; i++) {
			value = value * subjectNumber;
			value = value % MODULO;
		}
		return value;
	}
	
	public static void main(String[] args) {
		
		long cardPublicKey = 5764801;
		long cardLoopSize = findLoopSize(cardPublicKey);
		assert  cardLoopSize == 8;
		long doorPublicKey = 17807724;
		long doorLoopSize = findLoopSize(doorPublicKey);
		assert doorLoopSize == 11;
		long cardEncryptionKey = findEncryptionKey(doorPublicKey, cardLoopSize);
		assert cardEncryptionKey == 14897079;
		long doorEncryptionKey = findEncryptionKey(cardPublicKey, doorLoopSize);
		assert doorEncryptionKey == 14897079;
		
		
		/*
		 * Input:
		 * 3248366
		 * 4738476
		 */
		cardPublicKey = 3248366;
		doorPublicKey = 4738476;
		cardLoopSize = findLoopSize(cardPublicKey);
		doorLoopSize = findLoopSize(doorPublicKey);
		cardEncryptionKey = findEncryptionKey(doorPublicKey, cardLoopSize);
		doorEncryptionKey = findEncryptionKey(cardPublicKey, doorLoopSize);
		assert cardEncryptionKey == doorEncryptionKey;
		System.out.println("Part 1: " + cardEncryptionKey);
	}



}
