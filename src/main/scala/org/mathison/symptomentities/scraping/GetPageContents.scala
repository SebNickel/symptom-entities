package org.mathison.symptomentities.scraping

import org.jsoup.select.Elements

import scala.collection.JavaConverters._

// Gets a scraped page's text content as Strings.
object GetPageContents {

    def apply(elements: Elements): List[String] = {

        val headers =
            elements.select("h1, h2, h3, h4, h5, h6")
                .asScala.toList
                .map { _.text }

        val paragraphs =
            elements.select("p")
                .asScala.toList
                .map { _.text }

        val listItems =
            elements.select("li")
                .asScala.toList
                .map { _.text }

        headers ++ paragraphs ++ listItems

    }

}