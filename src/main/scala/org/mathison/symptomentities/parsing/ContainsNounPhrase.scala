package org.mathison.symptomentities.parsing

import edu.stanford.nlp.trees.Tree

import scala.collection.JavaConverters._

object ContainsNounPhrase {

    def apply(tree: Tree): Boolean = {

        val treeAsList = tree.asScala.toList

        treeAsList exists { subTree => subTree.label.value == "NP" }

    }

}