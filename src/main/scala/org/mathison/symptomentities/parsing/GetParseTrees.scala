package org.mathison.symptomentities.parsing

import edu.stanford.nlp.trees.Tree
import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation
import edu.stanford.nlp.util.CoreMap

import scala.collection.JavaConverters._

// Gets a list of SentencesAnnotations' TreeAnnotations.
object GetParseTrees {

    def apply(annotatedSentences: List[CoreMap]): List[Tree] = {

        annotatedSentences flatMap { annotatedSentence =>

            annotatedSentence.get(classOf[TreeAnnotation])
                .asScala.toList

        }

    }

}
