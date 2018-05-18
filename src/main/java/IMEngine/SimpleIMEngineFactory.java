package IMEngine;

public class SimpleIMEngineFactory implements IMEngineFactoryService {
    public SimpleIMEngineFactory() {

    }

    public IMEngineInstance getIMEngineInstance() {
        return new SimpleIMEngineInstance();
    }
}
