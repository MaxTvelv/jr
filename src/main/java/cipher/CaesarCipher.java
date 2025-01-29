package cipher;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CaesarCipher{

    private final char[] alphabet;

    private final Map<Character, Integer> alphabetSymbolsMap = new HashMap<>();

    public CaesarCipher(char[] alphabet) {
        this.alphabet = alphabet;

        for (int i = 0; i < alphabet.length; i++) {
            alphabetSymbolsMap.put(alphabet[i], i);
        }
    }

    public String encrypt(String text, int shift) {
        StringBuilder encryptStringBuilder = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            char originalChar = charToLowerCase(text.charAt(i));

            Integer symbolIndex = alphabetSymbolsMap.get(originalChar);

            if (symbolIndex == null) {
                encryptStringBuilder.append(originalChar);
            } else {
                int newSymbolIndex = (symbolIndex + shift) % alphabet.length;

                if (newSymbolIndex < 0) {
                    newSymbolIndex += alphabet.length;
                }

                encryptStringBuilder.append(alphabet[newSymbolIndex]);
            }
        }

        return encryptStringBuilder.toString();
    }
    public String decrypt(String encryptedText, int shift) {
        return encrypt(encryptedText, -shift);
    }

    public char charToLowerCase(char charValue) {
        return (charValue + "").toLowerCase().charAt(0);
    }

    public char[] getAlphabet() {
        return alphabet.clone();
    }
}
