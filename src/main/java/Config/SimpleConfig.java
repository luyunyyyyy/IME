package Config;

public final class SimpleConfig implements ConfigService {

    private SimpleConfig() {
        // restrict instantiation
    }

    public static final double PI = 3.14159;
    public static final double CANDIDATE_WORDS_COUNT = 15;
}