package org.mathison.symptomentities.idilia.categoryextraction.finesensekeys

import com.google.gson.Gson

import scala.collection.JavaConverters._

/*
Builds the JSON String containing the fine sense keys query for a word, to be sent to
the Idilia Language Graph Query API.
 */
object PackageFineSenseKeysQuery {

    private val gson = new Gson()

    def apply(lemma: String): String = {

        val dto = FineSenseKeysQueryDto(lemma, List().asJava)

        val listOfDto = List(dto).asJava

        gson.toJson(listOfDto)

    }

}
