package org.mathison.symptomentities.idilia.categoryextraction

import com.mashape.unirest.http.{HttpResponse, JsonNode, Unirest}

// The REST client for querying the Idilia Language Graph Query API.
class IdiliaClient(key: String) {

    def apply(query: String): HttpResponse[JsonNode] =

        Unirest.get("https://api.idilia.com/1/kb/query.json")
            .queryString("key", key)
            .queryString("query", query)
            .asJson()

}
