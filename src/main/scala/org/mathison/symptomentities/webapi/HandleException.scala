package org.mathison.symptomentities.webapi

import spark.Response

object HandleException {

    def apply(exception: Exception, response: Response): String = {

        exception match {

            case _: IllegalArgumentException =>

                response.status(400)
                response.header("content-type", "text/plain")

                val errorMessage =
                    "You have probably entered a malformed URL. Make sure to include the http:// part.\n" ++
                        s"I have caught the following exception: $exception"

                response.body(errorMessage)

                errorMessage

            case _: Exception =>

                response.status(500)
                response.header("content-type", "text/plain")

                val errorMessage = s"Something went wrong. I have caught this exception: $exception"

                response.body(errorMessage)

                errorMessage

        }

    }

}