package fr.unice.polytech.soa1.beerShop.data;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.soa1.beerShop.model.Beer;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tom on 29/09/15.
 */
public class DaoUtils {

    public static  ObjectMapper mapper = new ObjectMapper();

    public static void writeData(String dataFileName, Object data){

        String filePath = "data/appData/beerShop/"+dataFileName;

        try {
            mapper.writeValue(new File(filePath), data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static <T,V> HashMap<T,V> readData(String dataFileName, Class<T> keyType, Class<V> valueType) {

        //If some data has already bean written on the server.
        /*
        String storedDataFilePath = "data/appData/beerShop/"+dataFileName;
        File storedData = new File(storedDataFilePath);
        System.out.println("Exists : " + storedData.exists());

        String data = null;

        if (!storedData.exists()){
            try {
                storedData.getParentFile().mkdirs();
                storedData.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("New Data");
            String filePath = "data/"+dataFileName;
            data = getData(filePath);
        }else{
            System.out.println("Loading Existing Data");
            try {
                data = getDataFromInputStream(new FileInputStream(storedData));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        */

        String filePath = "data/"+dataFileName;
        String data = getData(filePath);

        try {
            return mapper.readValue(data, mapper.getTypeFactory().constructMapType(HashMap.class, keyType, valueType));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static String getData(String fileName) {

        StringBuilder result = new StringBuilder();
        String line;

        try {
            ClassLoader loader = DaoUtils.class.getClassLoader();
            InputStream resourceAsStream = loader.getResourceAsStream(fileName);
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(resourceAsStream));
            while ((line = bufferedReader.readLine()) != null)
                result.append(line);
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    private static String getDataFromInputStream(InputStream inputStream){

        StringBuilder result = new StringBuilder();

        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));

        try {
            String line;
            while ((line = bufferedReader.readLine()) != null)
                result.append(line);
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result.toString();

    }
}
