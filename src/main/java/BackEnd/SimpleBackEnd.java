package BackEnd;

import FrontEnd.SimpleFrontEnd;
import IMEngine.IMEngineFactoryService;

public class SimpleBackEnd implements BackEndService {
    private IMEngineFactoryService imEngineFactoryService;

    public SimpleBackEnd(IMEngineFactoryService imEngineFactoryService) {
        this.imEngineFactoryService = imEngineFactoryService;
    }

    public IMEngineFactoryService getIMEngineFactory() {
        return imEngineFactoryService;
    }
}
