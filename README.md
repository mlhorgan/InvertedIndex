# InvertedIndex

Implement an Inverted Index with Count Information.
Dataset

The dataset is the scenes from the cumulative works of Shakespeare Original Source. This data has been tokenized and is made available as shakespeare-scenes.json.gz on Moodle.

This dataset has been preprocessed by stripping out punctuation and using the Krovetz Stemmer. No stopwords have been removed.

An example scene is below:

{
  "playId" : "antony_and_cleopatra",
  "sceneId" : "antony_and_cleopatra:2.8",
  "sceneNum" : 549 ,
  "text" : "scene ix another part of the plain enter mark antony and domitiu enobarbu mark antony set we our squadron on yond side o the hill in eye of caesar s battle from which place we may the number of the ship behold and so proceed accordingly exeunt "
}
This is from the play "Anthony and Cleopatra", Act II, Scene viii. When you present results of your queries, please use the id we created antony_and_cleopatra:2.8, rather than the traditional numbering. When you retrieve plays, you will need to use the "play_id" key rather than the "scene_id" key.

Read in the tokenized and stemmed document collection provided in the assignment.
Please use an existing JSON library for this purpose. Python has one in the standard library which should suffice. We recommend json-simple's convenient way for Java.
Term vectors should be constructed by splitting the given text by spaces (the regex \\s+), and ignoring blank strings.
Build a simple inverted index with positional information. (See figure 5.5 in your text on page 135). This can be constructed in memory using standard collections. This was part of assignment P4. For this assignment, positional information is not needed, only count information.
Read ahead and consider whether there is other information you need to calculate the required scores, and be sure you collect it at indexing time.
Scoring
BM25

See page 250 of the textbook. Please use the following values: k1=1.2, k2=100. Yes, you will have to make decisions about other variables in the equation.

Language-Modeling or Query-Likelihood (QL)

See page 257 of the textbook, specifically the version implemented with Jelinek-Mercer smoothing. Please use the following values: lambda=0.8.

Evaluation
We provide a number of "queries" specified in plain English. Please run these queries under both the language-modeling and BM25 frameworks.

Please output the results of these queries in trecrun format.

TREC Run format

Output Format for QL and BM25 tasks.

For both tasks, a submission consists of a single text file in the format used for most TREC submissions, which we repeat here for convenience. White space is used to separate columns. The width of the columns in the format is not important, but it is important to have exactly six columns per line with at least one space between the columns.

the first column is the topic number.
the second column is currently unused and should always be "skip".
the third column is the scene identifier of the retrieved document.
the fourth column is the rank the document is retrieved. You will list things sorted by rank within query.
the fifth column shows the score (integer or floating point) that generated the ranking. This score must be in descending (non-increasing) order.
the sixth column is called the "run tag" and is traditionally a unique identifier for your group AND for the method used. That is, each run should have a different tag that identifies the group and the method that produced the run.
Use your OIT identifier, and either bm25 or ql. You will not be producing alphabetic runs this time.
e.g. jjfoley-bm25 and jjfoley-ql
Please do not turn in results with the TA's OIT identifer.
Queries

Q1: the king queen royalty
Q2: servant guard soldier
Q3: hope dream sleep
Q4: ghost spirit
Q5: fool jester player
Q6: to be or not to be
