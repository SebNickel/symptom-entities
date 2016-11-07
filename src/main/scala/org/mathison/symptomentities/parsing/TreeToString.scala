package org.mathison.symptomentities.parsing

import edu.stanford.nlp.trees.Tree

import scala.collection.JavaConverters._

// Converts a parse tree back into a String.
object TreeToString {

    def apply(tree: Tree): String = {

        val words = tree.yieldWords().asScala.toList

        val strings = words map { _.word() }

        strings reduce { (string1, string2) => string1 ++ " " ++ string2 }

    }

}