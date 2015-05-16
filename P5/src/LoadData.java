import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;

/**
 * @author jfoley
 */
public class LoadData {
	public static Map<SceneId,Integer> docLength = new HashMap<SceneId,Integer>();
	public static int collectionSize = 0;
	public static double averageDocLength = 0.0;
	public static int totalNumWords = 0;
  public static SceneIndex loadIndex(String inputFilePath) throws IOException {
    SceneIndex out = new SceneIndex();
    try (BufferedReader rdr = new BufferedReader(new InputStreamReader(new GZIPInputStream(new FileInputStream(inputFilePath)), "UTF-8"))) {
      JSONObject json = (JSONObject) JSONValue.parse(rdr);
      JSONArray docs = (JSONArray) json.get("corpus");

      for (Object o : docs) {
        JSONObject doc = (JSONObject) o;
        SceneId scene = new SceneId((String) doc.get("sceneId"));
        docLength.put(scene, (((String) doc.get("text")).split("\\s+")).length);
        collectionSize++;
        for (String token : ((String) doc.get("text")).split("\\s+")) {
          if(token.isEmpty()) continue;
          out.process(token, scene);
        }
      }

    }
    for(Integer x : docLength.values()){
    	totalNumWords+=x;
    }
    averageDocLength = totalNumWords/collectionSize;
    return out;
  }
}
