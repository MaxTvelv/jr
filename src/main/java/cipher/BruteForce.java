package cipher;

import java.util.Set;

public class BruteForce {

    public String decrypt(CaesarCipher cipher, String encryptedText, Set<String> dictionary) {
        int maxMatches = 0;
        String bestDecryption = "";

        for (int key = 1; key < cipher.getAlphabet().length; key++) {
            String decryptText = cipher.decrypt(encryptedText, key);

            int matches = countWordMatches(decryptText, dictionary);
            if (matches >= maxMatches) {
                maxMatches = matches;
                bestDecryption = decryptText;
            }
        }

        return bestDecryption;
    }

    public int countWordMatches(String decryptText, Set<String> dictionary) {
        int matches = 0;

        String[] words = decryptText.split("\\s+");

        for (String word: words) {
            if (dictionary.contains(word)) {
                matches++;
            }
        }
        return matches;
    }

}
