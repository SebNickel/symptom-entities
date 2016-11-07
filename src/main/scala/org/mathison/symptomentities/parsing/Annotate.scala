package org.mathison.symptomentities.parsing

import java.util.Properties

import edu.stanford.nlp.pipeline.{Annotation, StanfordCoreNLP}

// Annotates a String using Stanford CoreNLP's "tokenize", "ssplit" and "parse" annotators.
object Annotate {

    val properties = new Properties()
    properties.setProperty("annotators", "tokenize, ssplit, parse")

    val nlpPipeline = new StanfordCoreNLP(properties)

    def apply(text: String): Annotation = {

        val annotatedText = new Annotation(text)

        nlpPipeline.annotate(annotatedText)

        annotatedText

    }

}