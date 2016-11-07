package org.mathison.symptomentities.idilia.categoryextraction.finesensekeys

import org.mathison.symptomentities.idilia.categoryextraction.IdiliaClient

/*
Gets the fine sense keys associated with a word by the Idilia Language Graph Query API.
 */
class FineSenseKeysQueryPipeline(client: IdiliaClient) {

    def apply(lemma: String): List[String] = {

        val query = PackageFineSenseKeysQuery(lemma)

        val response = client(query)

        UnpackFskQueryResponse(response)

    }

}
