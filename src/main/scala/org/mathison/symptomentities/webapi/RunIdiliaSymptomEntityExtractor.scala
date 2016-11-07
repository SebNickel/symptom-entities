package org.mathison.symptomentities.webapi

import org.mathison.symptomentities.idilia.categoryextraction.{CategoryExtractor, IdiliaClient}
import org.mathison.symptomentities.idilia.{IdiliaSymptomEntityDetector, IdiliaSymptomEntityIdentifier}
import spark.{Request, Response}

/*
Called by the main method, this makes the necessary preparations and does the necessary error handling before actually
running the entity extractor.
 */

object RunIdiliaSymptomEntityExtractor {

    val telltaleCategories: List[String] =
        List(
            "medicine/N3",
            "disease/N1",
            "psychology/N1",
            "sport/N1",
            "microbiology/N1",
            "chemistry/N1",
            "biology/N1",
            "sex/N1",
            "radiation/N1",
            "CATEGORY_personal_hygiene_cosmetics_and_grooming/N1"
        )

    def apply(request: Request, response: Response): String = {

        val keyParam = request.queryParams("key")

        val key =
            if (keyParam != null)
                keyParam
            else
                System.getenv("idilia_key")

        if (key == null)

            HandleMissingKeyError(response)

        else {

            val ratioOption = GetRatio(request)

            if (ratioOption.isEmpty || ratioOption.get < 0 || ratioOption.get > 1) {

                HandleInvalidRatioError(request, response)

            }
            else {

                val ratio = ratioOption.get

                val idiliaClient = new IdiliaClient(key)
                val categoryExtractor = new CategoryExtractor(idiliaClient)

                val symptomEntityIdentifier = new IdiliaSymptomEntityIdentifier(
                    categoryExtractor,
                    telltaleCategories
                )


                val idiliaSymptomEntityExtractor =
                    new IdiliaSymptomEntityExtractor(
                        IdiliaSymptomEntityDetector.apply,
                        (node) => symptomEntityIdentifier.apply(node, ratio)
                    )

                idiliaSymptomEntityExtractor(request, response)

            }

        }

    }

}