package FrontEnd;

public interface FrontEndService {
    void init();

    String handleInputWords(String input);

    int addIMEngineInstance(String instanceUUID);

    int addIMEngineInstance();
}
