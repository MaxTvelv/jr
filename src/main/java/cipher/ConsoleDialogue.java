package cipher;

import java.io.File;
import java.util.Scanner;

public class ConsoleDialogue {

    private final Scanner in;
    private CaesarCoder coder;

    private static final String WELCOME_MESSAGE =
            """
                    *************
                    ** Welcome **
                    *************
                    """;

    private static final String OPERATION_PATTERN = "%d - %s";
    private static final String TRY_AGAIN_COMMAND = "again";

    public ConsoleDialogue(CaesarCoder coder) {
        this.in = new Scanner(System.in);
        this.coder = coder;
    }

    public void run() {
        
        Operation operation = null;
        
        do {
            showMenu();
            try {
                operation = readOperation();
                processOperation(operation);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            
        } while (operation != Operation.EXIT);
        
    }

    private void showMenu() {
        System.out.println(WELCOME_MESSAGE);
        System.out.println("Choose next option to continue");

        for (Operation operation: Operation.values()) {
            System.out.println(String.format(OPERATION_PATTERN, operation.getNumber(), operation.getDescription()));
        }
    }

    private Operation readOperation() {
        boolean shouldTryAgain = false;

        do {
            try {
                int option = readInt();
                return Operation.getByNumber(option);
            } catch (Exception exception) {
                System.out.println("Operation number is wrong");
                System.out.println("Reason " + exception.getMessage());
                System.out.println("Enter 'again' for trying again and something other for next");

                String input = readString();
                if (TRY_AGAIN_COMMAND.equalsIgnoreCase(readString())) {
                    shouldTryAgain = true;
                }
            }
        } while (shouldTryAgain);

        return Operation.EXIT;
    }

    private void processOperation(Operation operation) {
        switch (operation) {
            case EXIT -> processExit();
            case ENCRYPTION -> processEncryptionOperation();
            case DECRYPTION -> processDecryptionOperation();
            case BRUTE_FORCE -> processBruteForceOperation();
        }
    }

    private void processEncryptionOperation() {
        System.out.println("Enter filename which contains original text:");
        String inputFilename = readString();

        System.out.println("Enter filename which will be used for result saving:");
        String outputFilename = readString();

        try {
            System.out.println("Enter key:");
            int key = readInt();
            coder.encrypt(inputFilename, outputFilename, key);
            System.out.println("Done");
        } catch (Exception e) {
            System.err.println("Error happened. Reason " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void processDecryptionOperation() {
        System.out.println("Enter filename which contains encrypted text:");
        String inputFilename = readString();

        System.out.println("Enter filename which will be used for result saving:");
        String outputFilename = readString();

        try {
            System.out.println("Enter key:");
            int key = readInt();

            coder.decrypt(inputFilename, outputFilename, key);
            System.out.println("Done");
        } catch (Exception exception) {
            System.err.println("Error happened. Reason " + exception.getMessage());
            exception.printStackTrace();
        }
    }

    private void processBruteForceOperation() {
        System.out.println("Enter filename which contains encrypted text:");
        String inputFilename = readString();

        System.out.println("Enter filename which will be used for result saving:");
        String outputFilename = readString();

        System.out.println("Enter the file name that will be used to analyze the decryption:");
        String dictionaryFilename = readString();

        try {
            coder.bruteForceDecrypt(inputFilename, outputFilename, dictionaryFilename);
            System.out.println("Done");
        } catch (Exception exception) {
            System.err.println("Error happened. Reason " + exception.getMessage());
            exception.printStackTrace();
        }
    }

    private void processExit() {
        System.out.println("Bye");
    }

    private int readInt() {
        return Integer.parseInt(in.nextLine());
    }

    private String readString() {
        return in.nextLine();
    }
}
