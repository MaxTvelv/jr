package cipher;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CaesarCoder {
    private CaesarCipher caesarCipher;
    private Validator validator;
    private FileManager fileManager;
    private BruteForce bruteForce;

    private final String defaultDictionary = "и в не на я что с как у за он но по из\n" +
            "к я оно ты мы они она то так потому от";

    public CaesarCoder() {
        this.caesarCipher = new CaesarCipher(Alphabets.RUSSIAN.getAlphabetSymbols());
        this.validator = new Validator();
        this.fileManager = new FileManager();
        this.bruteForce = new BruteForce();
    }

    public void encrypt(String inputFileName, String outputFileName, int key) {
        validator.validateForReading(inputFileName);
        validator.validateForWriting(outputFileName);
        validator.validateKey(key, caesarCipher.getAlphabet());

        String encryptString = caesarCipher.encrypt(fileManager.readFile(inputFileName), key);
        fileManager.writeFile(encryptString, outputFileName);
    }

    public void decrypt(String inputFileName, String outputFileName, int key) {
        validator.validateForReading(inputFileName);
        validator.validateForWriting(outputFileName);
        validator.validateKey(key, caesarCipher.getAlphabet());

        String decryptString = caesarCipher.decrypt(fileManager.readFile(inputFileName), key);
        fileManager.writeFile(decryptString, outputFileName);
    }

    public void bruteForceDecrypt(String inputFileName, String outputFileName, String dictionaryFileName) {
        validator.validateForReading(inputFileName);
        validator.validateForWriting(outputFileName);
        validator.validateForWriting(dictionaryFileName);

        Set<String> dictionary = loadDictionary(dictionaryFileName);

        String encryptedText = fileManager.readFile(inputFileName);

        String decryptText = bruteForce.decrypt(caesarCipher, encryptedText, dictionary);
        fileManager.writeFile(decryptText, outputFileName);
    }

    private Set<String> loadDictionary(String dictionaryFileName) {
        if (dictionaryFileName == null || dictionaryFileName.isEmpty()) {
            return new HashSet<>(Arrays.asList(defaultDictionary.split("\\s+")));
        } else {
            String text = fileManager.readFile(dictionaryFileName).toLowerCase();
            String[] lines = text.split("\\s+");

            return new HashSet<>(Arrays.asList(lines));
        }

    }
}
