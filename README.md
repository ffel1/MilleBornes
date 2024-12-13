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



