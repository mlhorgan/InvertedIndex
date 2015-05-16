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

import javax.swing.RowFilter.Entry;


public class bm25 {
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

		//	SceneId s = new SceneId("othello:0.0");
		//	System.out.println(index.counts.get("venice").get(s));
		//	System.out.println(LoadData.docLength.get(s));
		//System.out.println(Math.log10(5));

		String method = "bm25";
		Map<Query, Map<SceneId, Double>> queryScores = new HashMap<>();

		for (Query query : queries) {
			List<SceneId> hits = new ArrayList<>(index.findScenesWithAny(query.terms));
			Collections.sort(hits); // sort alphabetically.

			Map<SceneId,Double> scores= new HashMap<SceneId,Double>();

			for (int i = 0; i < hits.size(); i++) {
				SceneId scene = hits.get(i);
				double score =  0.0;

				for (String term : query.terms){

					//calculate score for each scene
					double k1 = 1.2;
					double k2 = 100;
					double ri = 0; //case we have no relevant info
					double R = 0; //case we have no relevant info

					double ni = 0; // number of occurences of term
					Map<SceneId,Integer> scenesWithTerm = index.counts.get(term);
					for( Integer x : scenesWithTerm.values()){
						ni ++;
					}

					double N = LoadData.collectionSize;
					double qfi = 0;//frequency of term i in the query
					for(String y : query.terms){
						if(y.equals(term)){qfi++;}
					}

					double fi;
					try{
						fi = index.counts.get(term).get(scene);//frequency of term i in the document
					}
					catch(NullPointerException e) {
						continue;
					}

					double b = 0.75;
					double dl = LoadData.docLength.get(scene);
					double avdl = LoadData.averageDocLength;
					double K = k1*((1-b)+b*(dl/avdl));

					score += Math.log10((((ri+0.5)/(R-ri+0.5)) /((ni-ri+0.5)/(N-ni-R+ri+0.5)))*(((k1+1)*fi)/(K+fi))*(((k2+1)*qfi)/(k2+qfi)));
					
				}
				scores.put(scene, score);
			}
			queryScores.put(query, scores);
		}
			
		// provides output
		
		PrintWriter writer = new PrintWriter("bm25.trecrun.txt", "UTF-8");
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

