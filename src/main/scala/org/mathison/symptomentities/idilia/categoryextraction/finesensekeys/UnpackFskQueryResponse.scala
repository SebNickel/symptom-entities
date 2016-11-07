package org.mathison.symptomentities.idilia.categoryextraction.finesensekeys

import com.google.gson.Gson
import com.mashape.unirest.http.{HttpResponse, JsonNode}

import scala.collection.JavaConverters._

// Retrieves the fine sense keys from the response of the Idilia Language Graph Query API.
object UnpackFskQueryResponse {

    private val gson = new Gson()

    def apply(response: HttpResponse[JsonNode]): List[String] = {

        if (response.getStatus != 200)

            return List()

        val body = response.getBody.toString

        val resultDto = gson.fromJson(body, classOf[FineSenseKeysQueryResultDto])

        val fskQueryDto =
            resultDto.result
                .asScala.toList
                .head

        fskQueryDto.fsk
            .asScala.toList

    }

}
