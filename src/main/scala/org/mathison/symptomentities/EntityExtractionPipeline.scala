package org.mathison.symptomentities

import org.jsoup.select.Elements
import org.mathison.symptomentities.parsing.{AnnotatePageContents, GetAnnotatedSentences, GetParseTrees, TreeToString}
import org.mathison.symptomentities.scraping.GetPageContents

class EntityExtractionPipeline(scrapeWebPage: (String) => Elements,
                               symptomEntityFinder: SymptomEntityFinder) {

    def apply(url: String): List[String] = {

        val elements = scrapeWebPage(url)

        val contents = GetPageContents(elements)

        val annotations = AnnotatePageContents(contents)

        val sentences = GetAnnotatedSentences(annotations)

        val parseTrees = GetParseTrees(sentences)

        val symptomEntityTrees = parseTrees flatMap { tree =>

            symptomEntityFinder(tree)

        }

        symptomEntityTrees map { tree =>

            TreeToString(tree)

        }

    }

}
