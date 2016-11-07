package org.mathison.symptomentities.scraping

import org.jsoup.Jsoup
import org.jsoup.select.Elements

// If we don't know anything about a page's DOM tree contents, we simply scape everything off it.
object ScrapeAnyWebPage {

    def apply(url: String): Elements = {

        val document = Jsoup.connect(url).get()

        document.select("*")

    }

}
