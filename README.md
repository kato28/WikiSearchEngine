# WikiSearchEngine
moteur de recherche des pages wikipédia
Moteur de recherche en Java qui permet de chercher des mot-clés dans toutes les pages wikipedia françaises.
Classement des pages selon l’algorithme PageRank. 
Développement du server en Java EE.
<h3>1. Initiation au fichier XML de Wikipedia</h3>

Wikipedia propose des copies de tout son contenu dans des fichiers appelés dump
sous différents formats (XML, SQL...). Dans le cadre de ce projet, j'ai utilisé le
fichier dump sous format XML de Wikipédia, version française.j'ai commencé
par analyser son contenu et sa syntaxe. En effet, j'ai déterminé ce qui 
m'intéressait le plus dans le fichier, à savoir la balise principale <page> et ses balises
enfants <title> et <text>. Au sein de cette dernière, il a fallu analyser la syntaxe avec
laquelle est écrit le contenu qu’on a souvent l’habitude de voir dans les pages
Wikipedia. Les caractères ‘==’ sont utilisés pour démarquer le commencement d’une
section par exemple “== Biographie ==” désigne qu’il s’agit de la section biographie.
Afin d’alléger le fichier XML, j'ai décidé donc de supprimer des sections
notamment “Notes et références”, “Voir aussi” ...
j'ai également remarqué que les liens externes vers d’autres pages Wikipedia
sont marqués entre des crochets, par exemple: [[Al-Khawarizmi]]. Cela a pour but de
rediriger le lecteur vers cette page. On va ensuite remarquer que cette forme de
dénotation pourrait également être sous le format, [[ | ]] par exemple : [[Camille Jordan
(mathématicien)|Camille Jordan]], le premier texte avant le caractère pipe ‘|’ est le titre
de la page Wikipedia, celui qui va rediriger l’utilisateur vers celle-ci. Le deuxième est
celui utilisé dans le texte courant.
j'ai donc pris cette remarque en considération lorsque j'ai établi la
matrice CLI.
  <h3>2. Parsing et Corpus</h3>
  
  Afin d’éviter les complications que le traitement d’un gros fichier pourrait entraîner, il
nous a demandé de travailler avec un sous-ensemble cohérent des pages Wikipedia
qui s’appelle Corpus. j'ai ainsi choisi les pages dont le titre et le contenu
contient des mots en relation avec le domaine des mathématiques et de
l’informatique. j'ai pris 250 000 pages.
Le parsing du gros fichier XML a pour but de créer un fichier XML final qui a comme
caractéristiques:
  1. “Stopwords” , accents et cédilles supprimés.
  2. Les balises et les sections non désirées sont supprimées.
  3. Corpus mis en place selon notre thème.
  4. Chaque titre lui est associé un ID.

j'ai  téléchargé une liste des Stopwords, j'ai lit le fichier et j'alimente une
ArrayList<String>. Cela se fait dans la classe ParseFile.java (package main).
La classe Optimizer, abrite, entre autres, des fonctions statiques de suppression des
Stopwords et des accents et cédilles à savoir deleteStopWords et replaceOddChar.
  
  <h5>Choix technique:</h5>
  j'ai utilisé l’API StAX de Java pour parcourir le fichier XML. j'ai en
effet, étudié le choix entre la librairie SAX et StAX et j'ai conclus que cette dernière
utilise la technique du stream qui est plus efficace et moins coûteuse.

Le parsing du fichier a en effet, pris beaucoup de temps (dans les environs de 6
heures). 


<h3>3. Matrice CLI:</h3>

Les pages de Wikipedia sont représentée par un graphe dont les sommets sont les
pages et les arcs sont les liens. Comme la matrice d’adjacence comporte beaucoup de
coefficients nuls, il nous a été demandé d’adopter le format CLI.
La classe CLI.java est là où le calcul se fait.
Le calcul des valeurs de la ArrayList<Double> C, ArrayList<Integer> L
et ArrayList<Integer> I, se fait dans la méthode cli qui prends comme arguments un
String ( le contenu de la balise <text> que je récupére suite au parsing du fichier
final output.xml) et la HashMap où on a stocké la relation ID, Titre.
Le calcul de C se fait de la façon suivante: tant que le contenu c’est à dire la balise text
contient les double crochets ouvrants “[[“, on détecte les double crochets fermants,
cela veut dire que j'ai détecté une page vers laquelle pointe la page sur
laquelle se situe la parseur. je l’ajoute dans une ArrayList<Integer> outboundPages,
je traite également le cas du caractère pipe. j'aurai finalement, pour chaque page, le
nombre des pages vers lesquelles elle pointe, je le nomme d, je rempli la liste C avec
la valeur 1/d.
  
La liste I contient l’indice des pages vers lesquelles pointent la page actuelle.
Finalement, afin de construire la liste L, on ajoute à son contenu d’avant, le nombre de
liens sortants. Une liste L qui a des valeurs répétitives sur certaines colonnes veut dire
que la page concernée n’a pas de liens sortant.

<h3>4. Calcul du PageRank</h3>

Le calcul du pagerank se fait en plusieurs itérations, la première met à jour un vecteur
initialisé à 1/N avec N étant le nombre de pages. L’algorithme tire profit des listes C, L
et I en utilisant une boucle imbriquée. La condition d’arrêt étant avoir une différence de
valeur qui s’approche du 0. Le dumping factor utilisé nous permet de régler le
problème des impasses et le facteur zap nous permet de régler le problèmes des
coefficients nuls. La somme des pagerank converge vers 1.

<h3>5. Sauvegarde des données</h3>

Etant donnée que les calculs prennent énormément du temps, il me semblait
judicieux d’enregistrer les résultats dans des fichiers Serializable. La classe utilisée à
cet effet est SaveData.java, les fonctions dont le nom commence par “write” écrivent le
résultat dans un fichier serializable et les fonctions dont le nom commence par “get”
récupèrent ces données.

<h3>6. Diagramme de classe UML</h3>

