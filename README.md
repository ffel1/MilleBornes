# MilleBornes
Projet Génie Logiciel


Journal de bord :

Xavier :


Semaine du 25/10, 08/11, 15/11:

J'ai fait le choix de commencer par créer les assets du jeu, pour pouvoir concevoir l'interface en sachant déjà à quoi elle ressemblerait. J'ai utilisé le logiciel gratuit piskel pour faire l'entierté des visuels du jeu.



Semaine du 22/11 :

Une fois le circuit et les Cars dessinés et animés j'ai commencé par travailler sur la correction de certains bugs d'Displays.
J'ai géré le cas où on lance le jeu pour la première fois et lorsque la sauvegarde n'existe pas.
J'ai ensuite implémenté le système de tour dans le modèle et utilisé la winêtre de log implémenté par Enzo.


Dans un premier temps, j'ai implémenté le système de tour dans une boucle while qui appellait les players tant qu'il n'y avait pas de winners.
Cette implémentation m'a posé des problèmes, notamment de performances et entrainait beaucoup de problèmes avec le tour de l'utilisateur.
j'ai donc opté pour un simple système de booléan qui autorisé le player à play (à cliquer sur les Buttons), et lorsque sound tour prends End, sound boolean est mit à false et la méthode d'action des bots est appelée. La méthode d'action des bots appelle une méthode qui met le boolean du player à true.


Il y avait un problème concernant la sauvegarde qui prenait de plus en plus de place au fur et à mesure des Games (alors que la sauvegarde de Game n'enregistre que la dernière Game si elle a été laissé en suspends). Le problème venait du fait que la liste de players n'étaient pas rénitialisée

Il y avait aussi un problème qui venait de la manière dont on initialisé les Buttons. On ajoutait les actionListeners en boucle sans jamais les enlever, il y avait donc des soucis quand on cliquait sur les Buttons, car les méthodes était appelés plusieurs fois au lieu d'une. J'ai réglé le soucis en forçant les Buttons à n'avoir qu'un seul ActionListener (Delete tous les listeners avant de les radd lors de l'initialisation)



Semaine du 29/11 :

J'ai ensuite commencé à travailler sur les interactions entre le player et l'interface graphique, notamment lorsque le player joue une card. Le Controler est devenu un peu complexe, j'aurais pu mieux gérer la distinction entre Controler et modele pour faciliter la compréhension du code.
Lorsque les éléments les plus importants ont été implémenté sur le player, j'ai implémenté le système de jeu des CPU, basé sur un arbre de décision en utilisant les méthodes faites par Felix vérification() et playcard() en les adaptant en fonction du type de player. L'architecture MVC a facilité le travail. A la End, les bots pouvaient draw, défausser, Endir leur tour, et play en fonction de leur persoundnalité. Les attaques soundt lancés sur le player le plus avancé (si l'attaque est possible). En cas de player rendu à la même borne, la target est choisie au hasard.

Il fallait ensuite permettre au player de suivre la Game, j'ai donc implémenté un système complet qui informe des coups joués par le player et les bots. Le système de log permet aussi de savoir pourquoi une action du player n'est pas possible.


Semaine du 06/12 :

J'ai implémenté le système de Game, le player qui commence à play est choisit au hasard. J'ai aussi commencé à implémenté le système de sauvegarde des Games. J'ai fait en sorte que certains logs importants soient stockés dans nouveau un file .txt à chaque Game. Lorsqu'on reprends une Game, le file .txt associé à la round que l'on reprend est chargé dans la console de log.

Semaine du 20/12 :

Le jeu était globalement opérationnel, j'ai donc commencé à add des éléments visuels secondaires (des cards persoundnalisées en pixel art, des colors différentes pour chaque Car, une interface entièrement en pixel art pour plus de cohérence visuelle, des meilleurs animations de Cars avec des ombres). J'ai prit soin que l'inteface s'adapte à la size à l'écran sur lequel on affiche le jeu.

J'ai continué l'implémentation du système de round et de Game qu'avait commencé Felix. Il faut Endir la round à pile 700 bornes pour gagner une round. Lorsqu'un player gagne le jeu s'arrête quelques secondes en annonçant le winner, et lance ensuite une autre round en annonçant les pts des players. Si la Game est gagnée, le jeu annonce le winner de la Game et relance une Game. Les files de sauvegardes de round soundt tous mit dans un nouveau file .txt.

J'ai ensuite résolu des bugs mineurs en faisant de nombreuses de Games tests






Enzo :
J'ai commencé par faire la structure du système (Modèle Vue Controler) pour pouvoir directement commencer avec cette structure. Puis j'ai créé la base de la page de menu puis la page de jeu avec ses différents éléments (Les cards, le circuit, la winêtre d'Display des messages, les Buttons).
Et j'ai également réaliser le déplacement des avec les imagess et gifs réalisées par Xavier, cela pris pas mal de temps car cela necéssitait d'attendre que certains gifs se Endissent pour les remplacer par d'autres, et également d'adapter les déplacements par rapport à la size de l'écran de l'utilisateur pour que les Cars suivent la route du circuit.
Et j'ai commencé les premiers tests sur les cards et leurs utilisations grâce à JUnit.


Felix : 

Semaine 25/10 :

Mise en place du git et apprentissage de ses commandes.

Semaine 08/11 :

Début d’implémentation des méthodes des classes player, CPUFast et CPU Agro et de leur logique, tel que savoir si une card est posable ou non ou quel card les CPU vont vouloir poser en premier.

Semaine 15/11 :

Suite de l’implémentation de ces méthodes.

Semaine 22/11 :

Implémentation de nouvelles méthodes dans la classe player pour que le player et les CPU puisse play leurs cards et que cela soit affiché en fonction du type de card.

Semaine 29/11 : 

Modification de certaines méthodes pour aider à l’Display des cards qui soundt jouées par chaque player.

Semaine 06/12 :

Implémentation de l’Display des bottes et attaques pour que le player ait un visuel des bottes et attaques en cours des CPU et de lui même.

Semaine 20/12 :

Implémentation du comptage des pts pour chaque player dans la classe Game et implémentation des coups fourrés dans la classe player.