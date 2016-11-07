package org.mathison.symptomentities.webapi

import org.apache.logging.log4j.LogManager
import spark.Request

/*
The "ratio" is needed by idilia.RatioBasedIdentifier. Here we either get that ratio from the query parameters or set it
to a default value of 0.1. We also handle NumberFormatExceptions by returning None if one is thrown.
 */
object GetRatio {

    /*
    Annoyingly, I cannot pass LogManager:getLogger a class reference unless I make GetRatio a class rather than
    an object.
    (If I remember correctly, this problem does not come up in Kotlin. I guess Kotlin objects are Java classes, and
    Scala objects are not.)
     */
    private val logger = LogManager.getLogger("org.mathison.symptomentities.idilia.webapi.GetRatio")

    def apply(request: Request): Option[Double] = {

        val ratioParam = request.queryParams("ratio")

        if (ratioParam != null)

            try
                Some(ratioParam.toDouble)
            catch {
                case exception: NumberFormatException => None
            }

        else {

            logger.info("No ratio provided in the query parameters. Using the default ratio of 0.1.")

            Some(0.1)

        }

    }

}