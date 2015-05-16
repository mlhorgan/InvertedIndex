import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author jfoley
 */
public class RunAlphabetQueries {
  public static String OITUserName = "jjfoley";

  public static void main(String[] args) throws IOException {
    String inputFile = args.length > 0 ? args[0] : "shakespeare-scenes.json.gz";
    SceneIndex index = LoadData.loadIndex(inputFile);

    List<Query> queries = Arrays.asList(
        new Query("Q1", "the", "king", "queen", "royalty"),
        new Query("Q2", "servant", "guard", "soldier"),
        new Query("Q3", "hope", "dream", "sleep"),
        new Query("Q4", "ghost", "spirit"),
        new Query("Q5", "fool", "jester", "player"),
        new Query("Q6", "to", "be", "or", "not", "to", "be")
    );

    String method = "alphabetic";
    for (Query query : queries) {
      List<SceneId> hits = new ArrayList<>(index.findScenesWithAny(query.terms));
      Collections.sort(hits); // sort alphabetically.
      for (int i = 0; i < hits.size(); i++) {
        SceneId scene = hits.get(i);
        int rank = i+1;
        System.out.printf("%s skip %-40s %d %.3f %s-%s\n", query.id, scene, rank, 1.0 / ((double) rank), OITUserName, method);
      }
    }
  }
}
