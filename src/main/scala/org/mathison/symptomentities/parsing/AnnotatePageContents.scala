package org.mathison.symptomentities.parsing

import edu.stanford.nlp.pipeline.Annotation

object AnnotatePageContents {

    def apply(pageContents: List[String]): List[Annotation] =

        pageContents map { text => Annotate(text) }

}