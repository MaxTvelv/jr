package cipher;

public enum Alphabets {

    RUSSIAN(new char[]{
                'а', 'б', 'в', 'г', 'д', 'е', 'ж', 'з',
                'и','к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ',
                'ъ', 'ы', 'ь', 'э', 'я', '.', ',', '«', '»', '"', '\'', ':', '!', '?', ' '
            });

    private final char[] alphabetSymbols;

    Alphabets(char[] characters) {
        this.alphabetSymbols = characters;
    }

    public char[] getAlphabetSymbols() {
        return alphabetSymbols.clone();
    }
}

