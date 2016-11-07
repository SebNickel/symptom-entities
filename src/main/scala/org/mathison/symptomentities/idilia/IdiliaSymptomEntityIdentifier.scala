package org.mathison.symptomentities.idilia

import edu.stanford.nlp.trees.Tree
import org.mathison.symptomentities.idilia.categoryextraction.CategoryExtractor
import org.mathison.symptomentities.parsing.ContainsNounPhrase

import scala.collection.JavaConverters._

/*
This is our "isSymptomEntity" strategy used by SymptomEntityFinder.

telltaleCategories is a list of category identifiers of the kind returned by the Idilia Language Graph Query API.
These are the semantic categories of terms that we consider indicative of a noun phrase being a symptom entity.

We combine the parts of the strategy given by PresenceBasedIdentifier and RatioBasedIdentifier, applying the former
to noun phrases that contain no further noun phrases, and the latter to noun phrases that do.
 */
class IdiliaSymptomEntityIdentifier(categoryExtractor: CategoryExtractor,
                                    val telltaleCategories: List[String]) {

    private val presenceBasedDetector =
        new PresenceBasedIdentifier(
            categoryExtractor,
            telltaleCategories
        )

    private val ratioBasedDetector =
        new RatioBasedIdentifier(
            categoryExtractor,
            telltaleCategories
        )

    def apply(node: Tree, ratio: Double): Boolean = {

        if (ratio < 0 || ratio > 1)
            throw new IllegalArgumentException(s"Illegal ratio: $ratio. Ratio must satisfy 0 <= ratio <= 1.")

        val isNounPhrase = node.label.value == "NP"

        if (!isNounPhrase)

            return false

        val lemmas =
            node.yieldWords
                .asScala.toVector
                .map { _.word }

        if (!ContainsNounPhrase(node) || ratio == 0) {

            presenceBasedDetector(lemmas)

        }
        else {

            ratioBasedDetector(lemmas, ratio)

        }

    }

}
