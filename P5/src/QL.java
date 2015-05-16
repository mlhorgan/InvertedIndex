import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class QL {
	public static String OITUserName = "mlhorgan";

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

		String method = "ql";
		Map<Query, Map<SceneId, Double>> queryScores = new HashMap<>();

		for (Query query : queries) {
			List<SceneId> hits = new ArrayList<>(index.findScenesWithAny(query.terms));
			Collections.sort(hits); // sort alphabetically.

			Map<SceneId,Double> scores= new HashMap<SceneId,Double>();

			for (int i = 0; i < hits.size(); i++) {
				SceneId scene = hits.get(i);
				double score =  0.0;

				for (String term : query.terms){

					double lambda = 0.8;
					double fqid;
					try{
						fqid = index.counts.get(term).get(scene); //the number of times word qi occurs in document D
					}
					catch(NullPointerException e) {
						fqid = 0;
					}
					
					double D = LoadData.docLength.get(scene); //is the number of words in D
					double cqi = 0; //the number of times a query word occurs in the collection of documents
					Map<SceneId,Integer> scenesWithTerm = index.counts.get(term);
					for( Integer x : scenesWithTerm.values()){
						cqi +=x;
					}
					double C = LoadData.totalNumWords; //the total number of word occurrences in the collection

					score+= Math.log10( ((1-lambda)*(fqid/D))+(lambda*(cqi/C)));

				}
				scores.put(scene, score);
			}
			queryScores.put(query, scores);
		}

		// provides output

		PrintWriter writer = new PrintWriter("ql.trecrun.txt", "UTF-8");
		StringBuilder sb = new StringBuilder();
		for(int i = 1; i <= queryScores.size() ; i++){
			String qId = "Q"+i;
			int rank = 0;

			Map<SceneId,Double> map = queryScores.get(queries.get(i-1));
			Map<SceneId,Double> sortedMap = sortByValue(map);

			for(SceneId s : sortedMap.keySet()){

				double score = sortedMap.get(s);
				rank++;
				writer.printf("%s skip %-40s %d %.3f %s-%s\r\n", qId, s.toString(), rank, score, OITUserName, method);
			}
           writer.println("\r\n");
		}
		writer.close();


	}//end main

	public static <K, V extends Comparable<? super V>> Map<K, V> 
	sortByValue( Map<K, V> map )
	{
		List<Map.Entry<K, V>> list =
				new LinkedList<>( map.entrySet() );
				Collections.sort( list, new Comparator<Map.Entry<K, V>>()
						{
					@Override
					public int compare( Map.Entry<K, V> o1, Map.Entry<K, V> o2 )
					{
						return (o2.getValue()).compareTo( o1.getValue() );
					}
						} );

				Map<K, V> result = new LinkedHashMap<>();
				for (Map.Entry<K, V> entry : list)
				{
					result.put( entry.getKey(), entry.getValue() );
				}
				return result;
	}

}//end class

