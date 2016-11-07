package org.mathison.symptomentities.idilia

import org.mathison.symptomentities.idilia.categoryextraction.CategoryExtractor

/*
This is part of our "isSymptomEntity" strategy used by SymptomEntityFinder.

telltaleCategories is a list of category identifiers of the kind returned by the Idilia Language Graph Query API.
These are the semantic categories of terms that we consider indicative of a noun phrase being a symptom entity.

This part of our strategy, applied to noun phrase parse trees that contain no further noun phrases, simply deems
the phrase to be a symptom entity if any of the telltaleCategories is present in it.
 */
class PresenceBasedIdentifier(categoryExtractor: CategoryExtractor,
                              val telltaleCategories: List[String]) {

    def apply(lemmas: Vector[String]): Boolean =

        lemmas exists { word =>

            val categories = categoryExtractor(word)

            val tellTageCategoriesDetected = categories.intersect(telltaleCategories)

            tellTageCategoriesDetected.nonEmpty

        }

}