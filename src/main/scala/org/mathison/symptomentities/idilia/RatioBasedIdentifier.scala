package org.mathison.symptomentities.idilia

import org.mathison.symptomentities.idilia.categoryextraction.CategoryExtractor

/*
This is part of our "isSymptomEntity" strategy used by SymptomEntityFinder.

telltaleCategories is a list of category identifiers of the kind returned by the Idilia Language Graph Query API.
These are the semantic categories of terms that we consider indicative of a noun phrase being a symptom entity.

This part of the strategy declares a noun phrase a symptom entity if the ratio of terms from one of the
telltaleCategories to the overall number of tokens in the phrase is at least the value given by 'ratio'.
 */
class RatioBasedIdentifier(categoryExtractor: CategoryExtractor,
                           val telltaleCategories: List[String]) {

    def apply(lemmas: Vector[String],
              ratio: Double): Boolean = {

        if (ratio <= 0 || ratio > 1)
            throw new IllegalArgumentException(s"Illegal ratio: $ratio. Ratio must satisfy 0 < ratio <= 1.")

        val length = lemmas.size

        var index = 0
        var detections = 0

        while (detections / length < ratio && index < length) {

            val nextLemma = lemmas(index)

            val categories = categoryExtractor(nextLemma)

            val tellTageCategoriesDetected = categories.intersect(telltaleCategories)

            if (tellTageCategoriesDetected.nonEmpty)

                detections += 1

            index += 1

        }

        detections / length >= ratio

    }

}