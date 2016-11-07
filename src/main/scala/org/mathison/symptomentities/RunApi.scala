package org.mathison.symptomentities

import org.mathison.symptomentities.webapi.RunIdiliaSymptomEntityExtractor
import spark.Spark._
import spark.route.RouteOverview

// The main entry point to the REST API.
object RunApi {

    def main(args: Array[String]): Unit = {

        RouteOverview.enableRouteOverview("/")

        get("/symptoms", (request, response) => {

            val strategy = request.queryParams("strategy")

            strategy match {

                case "idilia" =>

                    RunIdiliaSymptomEntityExtractor(request, response)

                case _ =>

                    response.status(400)
                    response.header("content-type", "text/plain")

                    val errorMessage =
                        s"Unknown symptom entity extraction strategy: $strategy.\n" ++
                            "The only strategy that is currently defined is 'idilia'.\n" ++
                            "Please re-run your query with 'strategy=idilia'."

                    response.body(errorMessage)

                    errorMessage

            }

        })

    }

}