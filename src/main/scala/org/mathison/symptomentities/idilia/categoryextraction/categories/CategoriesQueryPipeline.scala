package org.mathison.symptomentities.idilia.categoryextraction.categories

import org.mathison.symptomentities.idilia.categoryextraction.IdiliaClient

/*
Gets the semantic categories associated with a fine sense key by the Idilia Language Graph Query API.
 */
class CategoriesQueryPipeline(client: IdiliaClient) {

    def apply(fineSenseKey: String): List[String] = {

        val query = PackageCategoriesQuery(fineSenseKey)

        val response = client(query)

        UnpackCategoriesQueryResponse(response)

    }

}
