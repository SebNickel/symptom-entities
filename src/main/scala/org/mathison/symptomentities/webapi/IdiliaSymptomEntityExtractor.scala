package org.mathison.symptomentities.webapi

import com.google.gson.Gson
import edu.stanford.nlp.trees.Tree
import org.mathison.symptomentities._
import org.mathison.symptomentities.scraping.{ScrapeAnyWebPage, ScrapeNHSWebPage}
import spark.{Request, Response}

import scala.collection.JavaConverters._

/*
Determines whether or not the url points to an NHS web page of the kind given in the task description, and picks
a web scraping strategy accordingly.
Then runs the EntityExtractionPipeline, handling exceptions, and returns a response via the REST API.
 */
class IdiliaSymptomEntityExtractor(containsSymptomEntity: (Tree) => Boolean,
                                   isSymptomEntity: (Tree) => Boolean) {

    val gson = new Gson()

    val symptomEntityFinder =
        new SymptomEntityFinder(
            containsSymptomEntity,
            isSymptomEntity
        )

    def apply(request: Request, response: Response): String = {

        val url = request.queryParams("url")

        val scrapeWebPage =
            if (url.toLowerCase.startsWith("http://www.nhs.uk/conditions/"))
                (u: String) => ScrapeNHSWebPage(u)
            else
                (u: String) => ScrapeAnyWebPage(u)

        val entityExtractionPipeline =
            new EntityExtractionPipeline(
                scrapeWebPage,
                symptomEntityFinder
            )

        try {

            val entities = entityExtractionPipeline(url).asJava

            val json = gson.toJson(entities)

            response.status(200)

            response.header("content-type", "application/json")

            response.body(json)

            json

        }
        catch {

            case exception: Exception => HandleException(exception, response)

        }

    }

}