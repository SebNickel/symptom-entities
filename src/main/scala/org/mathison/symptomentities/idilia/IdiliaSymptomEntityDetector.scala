package org.mathison.symptomentities.idilia

import edu.stanford.nlp.trees.Tree
import org.mathison.symptomentities.parsing.ContainsNounPhrase

/*
This is our "containsSymptomEntity" strategy used by SymptomEntityFinder.
It simply tells the Finder to keep looking inside that parse tree if it contains at least one noun phrase.
 */
object IdiliaSymptomEntityDetector {

    def apply(node: Tree): Boolean =

        ContainsNounPhrase(node)

}