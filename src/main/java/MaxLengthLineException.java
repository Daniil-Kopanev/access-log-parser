public class MaxLengthLineException extends RuntimeException {

    private final String text;

        public MaxLengthLineException(String message) {
            super(message);
            this.text=message;
        }

    @Override
    public String toString() {
        return text;
    }
}

