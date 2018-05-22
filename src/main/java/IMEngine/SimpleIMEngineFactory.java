package IMEngine;

import javafx.util.Pair;
import org.apache.log4j.Logger;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;

import static Config.TrainConfig.SIMPLE_SPELL_DICT_FILE_NAME;
import static Config.TrainConfig.TRAIN_DATA_OUTPUT_ROOT_PATH;

public class SimpleIMEngineFactory implements IMEngineFactoryService {

    Logger logger = Logger.getLogger(SimpleIMEngineFactory.class);
    private HashMap<String, ArrayList<Pair<String, Double>>> simpleSpellDict;

    public SimpleIMEngineFactory() throws IOException, ClassNotFoundException {
        loadSpellDict();
    }

    private void loadSpellDict() throws IOException, ClassNotFoundException {

        FileInputStream fis = new FileInputStream(TRAIN_DATA_OUTPUT_ROOT_PATH + SIMPLE_SPELL_DICT_FILE_NAME);
        ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(fis));
        simpleSpellDict = (HashMap<String, ArrayList<Pair<String, Double>>>) ois.readObject();
        logger.debug("loadSpellDict dict size : " + simpleSpellDict.size());
    }
    public IMEngineInstance getIMEngineInstance() {
        //return new SimpleIMEngineInstance();

        return new SimpleIMEngineInstance(simpleSpellDict);
    }
}
