package org.mathison.symptomentities.idilia.categoryextraction

import org.apache.logging.log4j.LogManager
import org.mathison.symptomentities.idilia.categoryextraction.categories.CategoriesQueryPipeline
import org.mathison.symptomentities.idilia.categoryextraction.finesensekeys.FineSenseKeysQueryPipeline

/*
This determines all the semantic categories of a word, as given by the Idilia Language Graph Query API.
 */
class CategoryExtractor(client: IdiliaClient) {

    private val logger = LogManager.getLogger(classOf[CategoryExtractor])

    private val fskQueryPipeline = new FineSenseKeysQueryPipeline(client)
    private val categoriesQueryPipeline = new CategoriesQueryPipeline(client)

    def apply(lemma: String): List[String] = {

        logger.info(s"Extracting categories for lemma '$lemma'")

        val fineSenseKeys = fskQueryPipeline(lemma)

        val categories = fineSenseKeys flatMap { fineSenseKey =>

            categoriesQueryPipeline(fineSenseKey)

        }

        categories.distinct

    }

}
