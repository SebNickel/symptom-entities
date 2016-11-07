# symptom-entities

My task is to extract symptom entities from a list of NHS web pages.

I do not have any training data available, so I concoct a very simple rule-based named entity recogniser.

I read the term "entity" in a narrow sense: Only noun phrases are considered candidate entities.

Of course, symptoms are not exclusively described in noun phrases. But I do not consider other mentions of symptoms "entities".

My code uses the strategy pattern in two places (note that this design pattern may not be easily recognised, since strategies are passed around as lambdas, there are no "XStrategy interfaces", etc. This is Scala.):

- `EntityExtractionPipeline` has a `scrapeWebPage` strategy.
- `SymptomEntityFinder` has a `containsSymptomEntity` and an `isSymptomEntity` strategy.

Two specific `scrapeWebPage` strategies are provided. `ScrapeNHSWebPage` is tuned specifically for scraping the content of interest from an NHS web page of the kind of those I was provided. `ScrapeAnyWebPage` scrapes any web page, with no prior knowledge of where in the DOM tree interesting content is found.

One strategy is provided for each of `containsSymptomEntity` and `isSymptomEntity`. This strategy uses the [Idilia](http://www.idilia.com/) Language Graph Query API to look up the semantic categories of the terms in a given noun phrase. (I am not allowed the use of web services for NLP, but I am allowed the use of public knowledge bases.)

Use of the Idilia Query API requires an access key. You can easily obtain one by registering [here](https://www.idilia.com/your-profile/) and then creating a project [here](https://www.idilia.com/developer/my-projects/).

You can then pass that key as a query parameter to my application's REST API. To avoid having to do this, you can also pass the key directly to the server application, by setting the environment variable `idilia_key` to your access key before running it.

## Installing and running
(assuming you're on a UNIX-like environment and have git, java, and mvn installed):

    git clone https://github.com/SebNickel/symptom-entities.git
    cd symptom-entities
    mvn package
    cd target
    export idilia_key=YOUR_IDILIA_ACCESS_KEY [optional]
    java -jar symptom-entities-0.0.1-jar-with-dependencies.jar

Then point your browser to `localhost:4567`. This will take you to a route overview. The only (other) route is `/symptoms`. Here you can run a GET query with the following parameters:
- `key`: Your Idilia access key. (Not needed if you've passed it to the server application.)
- `strategy`: Set this to `idilia`, which remains the only implemented strategy. (I could have dropped the need for this parameter as long as there is only one strategy, but I want my application to reflect the fact that it can use different strategies from the start.)
- `ratio`: (optional - default value is 0.1) A decimal number between 0 and 1 that controls the fraction of terms from the semantic categories deemed indicative of a symptom entity, that must be reached in order for the entire noun phrase to be deemed a symptom entity. (This only applies if the noun phrase contains further noun phrases.)
- `url`: The url of the web page you want to search for symptom entities. (Must include the `http[s]://` part.)

## Evaluation of the symptom entity recognition strategy
The "Idilia" strategy is a reasonable first pass, but it has considerable drawbacks. For one thing, it is slow. Examining the noun phrases requires many calls to the Idilia API. It also has relatively low recall. I have focused more on providing an end-to-end pipeline whose building blocks can be easily swapped and improved than on providing the best possible strategy. It is my view that one's first pass should always be as simple as possible, and that it's important to set up an end-to-end pipeline from the start.

Another weak point in the strategy lies with Stanford CoreNLP's parser, which seems to frequently lose good candidate noun phrases by parsing the containing sentence wrong. Of course I have only used the default model for parsing.

## Possibilites for improvement
There are many obvious ways of tweaking the "Idilia" strategy that may yield considerable improvements. We may be able to get more informative semantic category information by querying the Idilia API for bigrams instead of just individual words. With a bit more parsing we could intelligently skip many terms to improve the strategy's speed.

Where speed is concerned, it would be obviously preferable to directly store a lexicon of terms deemed indicative of symptom entities. The implemented strategy points to ways of partially automating the compilation of such a lexicon.

However, what I would do next is implement a first pass at a version of the same strategy (or close) using a different knowledge base. After narrowing down candidates, my choice was between the Idilia Language Graph and [UMLS Terminology Services](https://uts.nlm.nih.gov/home.html#apidocumentation). I chose Idilia because it provides a much more intuitive interface. However, I was disappointed by its usefulness for this task. Its categories are too coarse, and too many words go uncategorised. One example I found noteworthy is that the only semantic category found for the terms in the noun phrase "underdeveloped ovaries" was "photography/N1".

## Entirely different approaches
If we had training data, we could train a supervised machine learning model. I am not aware of available training datasets, but I know that the authors of at least one paper have annotated symptom entities in a large part of the i2b2 corpus (and perhaps they've made these annotations available or would provide them on request).

The approach I would favour, then, is to train a model consisting of an RNN followed by a CRF layer. I have seen two papers from authors who have had good results on a similar task by combining a bidirectional LSTM network with a CRF output layer. Another author has had almost equally good results with the combination of a CNN with a CRF layer.

Symptom entities do not share some of the convenient characteristics of other, oft-recognised entity types, such as organisations and commercial products. Word shape features (e.g. the presence and location of uppercase letters, hyphens, digits, etc) are of little use here. Instead it seems that we cannot rely on much more than the semantic contents of the words.

And I remain of the view that a good way to do this is with word embeddings. Indeed, word embeddings have yielded good results in the experiments described in the papers just mentioned.
