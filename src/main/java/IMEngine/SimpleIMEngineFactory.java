package IMEngine;

import javafx.util.Pair;
import org.apache.log4j.Logger;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;

import static Config.TrainConfig.*;

public class SimpleIMEngineFactory implements IMEngineFactoryService {

    Logger logger = Logger.getLogger(SimpleIMEngineFactory.class);
    private HashMap<String, ArrayList<Pair<String, Double>>> simpleSpellDict;
    private HashMap<String, HashMap<String, Double>> py2ch;
    private HashMap<String, Double> PI;
    private HashMap<String, HashMap<String, Double>> emit;
    private HashMap<String, HashMap<String, Double>> trans;
    public SimpleIMEngineFactory() throws IOException, ClassNotFoundException {
        loadSpellDict();
        loadData();
    }

    private void loadSpellDict() throws IOException, ClassNotFoundException {

        FileInputStream fis = new FileInputStream(TRAIN_DATA_OUTPUT_ROOT_PATH + SIMPLE_SPELL_DICT_FILE_NAME);
        ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(fis));
        simpleSpellDict = (HashMap<String, ArrayList<Pair<String, Double>>>) ois.readObject();
        logger.debug("loadSpellDict dict size : " + simpleSpellDict.size());
        ois.close();
    }

    private void loadData() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(TRAIN_DATA_OUTPUT_ROOT_PATH + PI_FILE_NAME);
        ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(fis));
        PI = (HashMap<String, Double>) ois.readObject();
        logger.debug("PI dict size : " + simpleSpellDict.size());
        ois.close();
        py2ch = loadMap(PY2CH_FILE_NAME);
        emit = loadMap(EMIT_FILE_NAME);
        trans = loadMap(TRANS_FILE_NAME);
    }

    private HashMap<String, HashMap<String, Double>> loadMap(String filename) throws IOException, ClassNotFoundException {
        HashMap<String, HashMap<String, Double>> map;
        FileInputStream fis = new FileInputStream(TRAIN_DATA_OUTPUT_ROOT_PATH + filename);
        ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(fis));
        map = (HashMap<String, HashMap<String, Double>>) ois.readObject();
        logger.debug(filename + " size : " + simpleSpellDict.size());
        ois.close();
        return map;
    }
    public IMEngineInstance getIMEngineInstance() {
        //return new SimpleIMEngineInstance();

        return new SimpleIMEngineInstance(simpleSpellDict,
                py2ch,
                PI,
                emit,
                trans);
    }
}
