package BackEnd;

import IMEngine.IMEngineFactoryService;

public interface BackEndService {
    IMEngineFactoryService getIMEngineFactory(String uuid);
    IMEngineFactoryService getIMEngineFactory();
}
