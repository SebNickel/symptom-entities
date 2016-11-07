package org.mathison.symptomentities.idilia.categoryextraction.categories

import com.google.gson.Gson
import com.mashape.unirest.http.{HttpResponse, JsonNode}

import scala.collection.JavaConverters._

// Retrieves the semantic categories from the response of the Idilia Language Graph Query API.
object UnpackCategoriesQueryResponse {

    private val gson = new Gson()

    def apply(response: HttpResponse[JsonNode]): List[String] = {

        val body = response.getBody.toString

        val resultDto = gson.fromJson(body, classOf[CategoriesQueryResultDto])

        val categoriesQueryDto =
            resultDto.result
                .asScala.toList
                .head

        categoriesQueryDto.categories
            .asScala.toList

    }

}
