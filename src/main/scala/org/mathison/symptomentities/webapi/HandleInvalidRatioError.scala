package org.mathison.symptomentities.webapi

import spark.{Request, Response}

object HandleInvalidRatioError {

    def apply(request: Request, response: Response): String = {

        val ratioParam = request.queryParams("ratio")

        response.status(400)
        response.header("content-type", "text/plain")

        val errorMessage = s"Invalid ratio: $ratioParam.\n" ++
            "The ratio must be a (possibly decimal) number, satisfying 0 <= ratio <= 1."

        response.body(errorMessage)

        errorMessage

    }

}
