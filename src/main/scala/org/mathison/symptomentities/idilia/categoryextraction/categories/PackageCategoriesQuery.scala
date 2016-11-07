package org.mathison.symptomentities.idilia.categoryextraction.categories

import com.google.gson.Gson

import scala.collection.JavaConverters._

/*
Builds the JSON String containing the categories query for a word, to be sent to
the Idilia Language Graph Query API.
 */
object PackageCategoriesQuery {

    private val gson = new Gson()

    def apply(fineSenseKey: String): String = {

        val dto = CategoriesQueryDto(fineSenseKey, List().asJava)

        val listOfDto = List(dto).asJava

        gson.toJson(listOfDto)

    }

}
