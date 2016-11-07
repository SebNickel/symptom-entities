package org.mathison.symptomentities.parsing

import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation
import edu.stanford.nlp.pipeline.Annotation
import edu.stanford.nlp.util.CoreMap

import scala.collection.JavaConverters._

// Splits annotated pieces of text into SentencesAnnotations.
object GetAnnotatedSentences {

    def apply(annotations: List[Annotation]): List[CoreMap] = {

        annotations flatMap { annotation =>

            annotation.get(classOf[SentencesAnnotation])
                .asScala.toList

        }

    }

}