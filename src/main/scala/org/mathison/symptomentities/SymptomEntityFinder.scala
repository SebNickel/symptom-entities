package org.mathison.symptomentities

import edu.stanford.nlp.trees.Tree

import scala.collection.JavaConverters._

/*
Recursively traverses a parse tree and returns all of its nodes whose contents are identified as being symptom entities.
The class constructor takes two strategies:
    - containsSymptomEntity is a strategy for determining whether a parse tree node *contains* one or more symptom
      entities. (More broadly, whether it's worth checking this node's children for symptom entities.)
    - isSymptomEntity is a strategy for determining whether a parse tree node *is* a symptom entity.
 */

class SymptomEntityFinder(containsSymptomEntity: (Tree) => Boolean,
                          isSymptomEntity: (Tree) => Boolean) {

    def apply(node: Tree): List[Tree] = {

        if (!containsSymptomEntity(node))

            return List()

        /*
        We need to skip the present tree if its label is ROOT. Such trees have only one child whose contents are
        exactly the same. Since our "isSymptomEntity" strategy is slow, it would be wasteful to evaluate it twice
        for both the ROOT tree and its direct descendant; especially since ROOT trees are the longest.
        Since Scala evaluates && lazily, `isSymptomEntity(node)` is not evaluated in such cases.
         */
        if (node.label.value != "ROOT" && isSymptomEntity(node))

            return List(node)

        val children =
            if (node.label.value == "ROOT")
                node
                    .getChildrenAsList
                    .asScala.toList
                    .head
                    .getChildrenAsList
                    .asScala.toList
            else
                node
                    .getChildrenAsList
                    .asScala.toList

        children flatMap { child =>

            this.apply(child)

        }

    }

}