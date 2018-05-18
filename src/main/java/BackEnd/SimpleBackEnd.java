package BackEnd;

import FrontEnd.SimpleFrontEnd;
import IMEngine.IMEngineFactoryService;

public class SimpleBackEnd implements BackEndService {
    private IMEngineFactoryService imEngineFactoryService;

    public SimpleBackEnd(IMEngineFactoryService imEngineFactoryService) {
        this.imEngineFactoryService = imEngineFactoryService;
    }

    @Override
    public IMEngineFactoryService getIMEngineFactory(String uuid) {
        return null;
    }

    public IMEngineFactoryService getIMEngineFactory() {
        return imEngineFactoryService;
    }
}
