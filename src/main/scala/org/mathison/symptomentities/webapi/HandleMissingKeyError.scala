package org.mathison.symptomentities.webapi

import spark.Response

// Returns an error message if no Idilia access key is set.
object HandleMissingKeyError {

    def apply(response: Response): String = {

        response.status(400)
        response.header("content-type", "text/plain")

        val errorMessage =
            "No access key is set for the Idilia Language Graph Query API.\n" ++
                "This access key can be set in two ways:\n" ++
                "   - On the server, by setting the environment variable 'idilia_key' before running this web API\n" ++
                "   - On the client, by passing the key to the query paramenter 'key'\n" ++
                "Please re-run your query with 'key=YOUR_IDILIA_ACCESS_KEY.\n'" ++
                "Refer to the README (or to the Idilia website) for information on how to get an access key."

        response.body(errorMessage)

        errorMessage

    }

}
