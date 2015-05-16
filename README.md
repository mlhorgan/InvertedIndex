# InvertedIndex

Implement an Inverted Index with Positional Information.
Dataset

The dataset is the scenes from the complete works of Shakespeare. This data has been tokenized and is made available as shakespeare-scenes.json.gz on Moodle. If you're curious, the original source of the materials (from Open Shakespeare) is here.

This dataset has been preprocessed by stripping out punctuation and using the Krovetz Stemmer. No stopwords have been removed.

An example scene is below:

{
  "playId" : "antony_and_cleopatra",
  "sceneId" : "antony_and_cleopatra:2.8",
  "sceneNum" : 549 ,
  "text" : "scene ix another part of the plain enter mark antony and domitiu enobarbu mark antony set we our squadron on yond side o the hill in eye of caesar s battle from which place we may the number of the ship behold and so proceed accordingly exeunt "
}
This is from the play "Anthony and Cleopatra", Act III, Scene ix (that is not a mistake: the "sceneId" and the actual act/scene are not the same). When you present results of your queries, please use the id we created antony_and_cleopatra:2.8, rather than the traditional numbering. When you retrieve plays, you will need to use the "play_id" key rather than the "scene_id" key.

Read in the tokenized and stemmed document collection provided in the assignment.
Please use an existing JSON library for this purpose. We recommend json-simple's convenient way for Java. Python has a JSON library in the standard library which should suffice.
Term vectors should be constructed by splitting the given text by spaces (the regex \\s+), and ignoring blank strings. You've already shown you can tokenize and stem, so we're making it easier for this project.
Build a simple inverted index with positional information. See figure 5.5 (p.135) in the textbook. This can be constructed in memory using standard collections, though that means that every time you run the program you will need to build the index again.
We recommend having the data storage behind your InvertedIndex being a Map<Term, List<Postings>>
Another possible organization is to have a Map<Term, Map<DocId, Positions>>
Postings might be a DocId and Positions pair.
Positions might be either an int[] or a List<Integer>
If you choose to provide a mechanism to save your inverted index to disk and to restore it from disk, we will award 10 bonus points on this assignment.
Although the queries we have provided vary in goals, we expect you to reuse as much code as possible. It is possible to have a general query-processing technique for all of them. It should be obvious and straightforward how to run similar queries with different terms. That does not mean that you are required to create and support a query language, but it does mean that it should take very little effort to run minor variations on queries.
One way of reusing code is to define helper functions for processes such as counting phrases and terms. Can you implement terms as phrases of length 1? Can you make aggregating by play or by scene a flag or Boolean to this process?
P5 will involve implementing scoring functions and models on top of this framework, so putting time in now will benefit you in the next assignment.
Evaluation
We provide a number of "queries" specified in plain English. Please try to satisfy the information need described in the query. If there are any ambiguities, report these in your discussion.

Note that because these queries do not require a ranking, we expect you to save the documents retrieved as a set. Please alphabetize your lists.

Term-based Queries

Answer the following Boolean/counting queries using your index. Save the required output to the named file.

terms0.txt Find scene(s) where "thee" or "thou" is used more than "you"
terms1.txt Find scene(s) where Verona, Rome, or Italy is mentioned.
terms2.txt Find the play(s) where "falstaff" is mentioned.
terms3.txt Find the play(s) where "soldier" is mentioned.
Phrase-based Queries

Answer the following queries using your index. For these you will need to use positional information. Save the required output to the named file.

phrase0.txt Find scene(s) where "lady macbeth" is mentioned.
phrase1.txt Find the scene(s) where "a rose by any other name" is mentioned.
phrase2.txt Find the scene(s) where "cry havoc" is mentioned.
Grading Rubric
(10%) Submission is in the correct format.

A single archive file (zip, tar.gz) was submitted to Moodle and it contains at least the following contents:

report.pdf - your report (see below)

src/* - your source code

README
It has instructions for downloading dependencies the code.
It has instructions for building the code.
It has instructions for running the code.
Query Output Files: these should contain either scene ids or play ids describing the results of the queries. Because the queries are Boolean, there is no ranking required, please order alphabetically.
terms1.txt, terms2.txt, terms3.txt, terms4.txt
phrase1.txt, phrase2.txt, phrase3.txt
