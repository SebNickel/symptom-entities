package org.mathison.symptomentities.scraping

import org.jsoup.Jsoup
import org.jsoup.select.Elements

/*
For pages under http://www.nhs.uk/conditions/, we know exactly where in the DOM tree the contents that interest us are
found, so we scrape only these nodes.
 */
object ScrapeNHSWebPage {

    def apply(url: String): Elements = {

        val document = Jsoup.connect(url).get()

        val elements = document.select("div.main-content")

        elements.select("div.WebPartZone-Vertical").remove()
        elements.select("div.review-dates").remove()

        elements

    }

}