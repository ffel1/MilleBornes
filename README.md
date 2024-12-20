# MilleBornes
Projet Génie Logiciel


Journal de bord :

Xavier :

J'ai fait le choix de commencé par créer les assets du jeu, pour pouvoir réfléchir l'interface en sachant à quoi elle ressemblerait.
Une fois le circuit et les voitures dessinés et animés j'ai commencé par travailler sur la correction de certains bugs d'affichages.
J'ai géré le cas où on lance le jeu pour la première fois et lorsque la sauvegarde n'existe pas.
J'ai ensuite implémenté le système de tour dans le modèle et utilisé la fenêtre de log implémenté par Enzo.
Dans un premier temps, j'ai implémenté le système de tour dans une boucle while qui appellait les joueurs tant qu'il n'y avait pas de gagnants.
Cette implémentation m'a posé des problèmes, notamment de performances et entrainait beaucoup de problèmes avec le tour de l'utilisateur.
j'ai donc opté pour un simple système de booléan qui autorisé le joueur à jouer (à cliquer sur les boutons), et lorsque son tour prends fin, son boolean est mit à false et la méthode d'action des bots est appelée. La méthode d'action des bots appelle une méthode qui met le boolean du joueur à true.

Il y avait un problème concernant la sauvegarde qui prenait de plus en plus de place au fur et à mesure des parties (alors que la sauvegarde de partie n'enregistre que la dernière partie si elle a été laissé en suspends). Le problème venait du fait que la liste de joueurs n'étaient pas rénitialisée

Enzo :
J'ai commencé par faire la structure du système (Modèle Vue Controleur) pour pouvoir directement commencer avec cette structure. Puis j'ai créé la base de la page de menu puis la page de jeu avec ses différents éléments (Les cartes, le circuit, la fenêtre d'affichage des messages, les boutons).
Et j'ai également réaliser le déplacement des avec les imagess et gifs réalisées par Xavier, cela pris pas mal de temps car cela necéssitait d'attendre que certains gifs se finissent pour les remplacer par d'autres, et également d'adapter les déplacements par rapport à la taille de l'écran de l'utilisateur pour que les voitures suivent la route du circuit.
Et j'ai commencé les premiers tests sur les cartes et leurs utilisations grâce à JUnit.
