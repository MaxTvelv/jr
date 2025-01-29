package cipher;

import java.util.Arrays;

public enum Operation {
    EXIT(0, "exit", () -> System.out.println("Operation 1")),
    ENCRYPTION(1, "encrypt file", () -> System.out.println("Operation 2")),
    DECRYPTION(2, "decrypt file", () -> System.out.println("Operation 3")),
    BRUTE_FORCE(3, "try co crack file with Brute Force", () -> System.out.println("Operation 4"));

    private final int number;
    private final String description;
    private final Runnable runnable;

    Operation(int number, String descryption, Runnable runnable) {
        this.number = number;
        this.description = descryption;
        this.runnable = runnable;
    }

    public int getNumber() {
        return this.number;
    }

    public String getDescription() {
        return this.description;
    }

    public static Operation getByNumber(int number) {
        for (Operation operation: values()) {
            if (operation.getNumber() == number) {
                return  operation;
            }
        }

        throw new IllegalArgumentException("Wrong number for operation");
    }


}
