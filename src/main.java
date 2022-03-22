import extensions.CSVFile;
import extensions.RGBColor;

class Codia extends Program {
    final String[] authors = new String[]{"Marache Bastien", "Hessel Aymeric"};
    final String green = RGBColor.GREEN.name;
    final String red = RGBColor.RED.name;
    final String blue = RGBColor.BLUE.name;
    final String purple = RGBColor.PURPLE.name;
    final String white = RGBColor.WHITE.name;
    final String black = RGBColor.BLACK.name;

/*
=====================================================================================================================
MENUS
=====================================================================================================================
*/

    void showCodia(){
        CSVFile CodiaCSV = loadCSV("../csv/Codia.csv", ';');
        String[][] Codia = new String[rowCount(CodiaCSV)][columnCount(CodiaCSV)];
        text(blue);
        for (int i = 0; i<length(Codia, 1); i++){
            for (int j = 0; j<length(Codia, 2); j++){
                print(getCell(CodiaCSV, i, j));
            }
            println();
        }
        print("\n\n\n\n");
    }

    void showMenus(String[] menus){
        for (int i = 0; i<length(menus); i++){
            text(purple);
            print("    [");
            text(green);
            print(i+1);
            text(purple);
            print("] - ");
            text(green);
            println(menus[i] + "\n");
        }
        print("\n\n");
        text(white);
    }

    // Fontion principale
    void algorithm(){
        String[] menus = new String[]{"Jouer", "Regles", "Credits", "Quitter"};
        boolean running = true;
        int choix;
        while (running){
            clearScreen();
            showCodia();
            showMenus(menus);
            text(green);
            print("Votre choix : "); text(red);
            choix = readInt(); text(green);
            while(choix < 1 || choix > length(menus)){
                println("Veuillez choisir un chiffre valide (entre 1 et "+length(menus)+")");
                print("Votre choix : "); text(red);
                choix = readInt(); text(green);
            }
            switch (choix){
                case 1:
                    play();
                    break;
                case 2:
                    afficherRegles();
                    break;
                case 3:
                    credits();
                    break;
                case 4:
                    running = false;
                    clearScreen();
                    break;
            }
        }
        text(white);
    }

/*
=====================================================================================================================
PLAYING
=====================================================================================================================
*/

    // Initialise le joueur et sa position
    Joueur initJoueur(int i, int y){
        Joueur joueur = new Joueur();
        text(green);
        print("Choisissez votre pseudo : ");
        text(red);
        joueur.pseudo = readString();
        joueur.pos = new Position();
        joueur.pos.i = i;
        joueur.pos.y = y;
        text(white);
        return joueur;
    }

    // Affiche un tableau à 2 dimensions
    void println(String[][] tab){
        for (int i = 0; i<length(tab, 1); i++){
            for (int j = 0; j<length(tab, 2); j++){
                switch (tab[i][j]){
                    case "?":
                        text(green);
                        print(tab[i][j]);
                        break;
                    case "@":
                        text(white);
                        print(tab[i][j]);
                        break;
                    case "#":
                        text(red);
                        print(tab[i][j]);
                        break;
                    default:
                        text(black);
                        print(tab[i][j]);
                        break;
                }
                print(" ");
            }
            print("\n");
        }
        print("\n\n");
    }

    // Initialise un tableau à 2 dimensions avec des caractères
    String[][] initPlateau(Joueur joueur){
        CSVFile Niveau = loadCSV("../csv/niveau"+joueur.niveau+".csv", ';');
        String[][] plateau = new String[rowCount(Niveau)][columnCount(Niveau)];
        for (int i = 0; i<length(plateau, 1); i++){
            for (int j = 0; j<length(plateau, 2); j++){
                plateau[i][j] = getCell(Niveau, i, j);
            }
        }
        return plateau;
    }

    // Fonction pour demander les instructions au joueur
    String[] choixInstructions(String[][] plateau){
        String[] instructions = new String[50];
        String[] choix = new String[]{"haut", "bas", "droite", "gauche", "for", "fin"};
        String[] choix_for = new String[]{"haut", "bas", "droite", "gauche", "fin"};
        int indexChoix = 0;
        int entreeInt = 0;
        do {
            clearScreen();// println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
            println(plateau);
            afficherInstructions(instructions);
            afficherChoix(choix);

            text(green);
            print("Votre choix : "); text(red);
            entreeInt = readInt(); text(green);
            while(entreeInt < 1 || entreeInt > length(choix)){
                println("Veuillez choisir un chiffre valide (entre 1 et "+length(choix)+")");
                print("Votre choix : "); text(red);
                entreeInt = readInt(); text(green);
            }

            if (equals(choix[entreeInt-1],"for")){
                print("Entre un nombre de repetition : "); text(red);
                String for_in = "for("; for_in += readInt(); for_in += ") |";
                int entree_for = 0;
                do{
                    clearScreen();// println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
                    println(plateau);
                    afficherInstructions(instructions);
                    afficherChoix(choix_for);
                    text(green);
                    println(for_in + "\n");
                    
                    print("Votre choix : "); text(red);
                    entree_for = readInt(); text(green);
                    while(entree_for < 1 || entree_for > length(choix_for)){
                        println("Veuillez choisir un chiffre valide (entre 1 et "+length(choix_for)+")");
                        print("Votre choix : "); text(red);
                        entree_for = readInt(); text(green);
                    }

                    for_in += choix_for[entree_for-1]+"|"; 
                }while(!equals(choix_for[entree_for-1],"fin"));
                instructions[indexChoix] = for_in;
            }
            else{
                instructions[indexChoix] = choix[entreeInt-1];
            }

            indexChoix++;
        } while (!equals(choix[entreeInt-1], "fin"));
        return instructions;
    }

    // Fonction pour afficher les instructions choisies par le joueur
    void afficherInstructions(String[] instructions){
        text(green);
        print("Instructions : "); text(purple); print("{");
        for (int i = 0; i<length(instructions); i++){
            if (instructions[i] != null){
                text(green);
                print(instructions[i]);
                text(purple);
                print(";");
            }
        }
        println("}\n");
        text(white);
    }

    // Affiche les instructions disponibles
    void afficherChoix(String[] choix){
        text(green);
        println("Choisir une instruction :\n");
        for (int i = 0; i<length(choix); i++){
            text(purple);
            print("  [");
            text(green);
            print(i+1);
            text(purple);
            print("] - ");
            text(green);
            print(choix[i] + "\n");
        }
        print("\n");
        text(white);
    }

    // Déplacement vers le haut
    boolean haut(String[][] plateau, Joueur joueur){
        boolean result = false;
        if (equals(plateau[joueur.pos.i-1][joueur.pos.y], "#")){
                result = true;
        } else if (equals(plateau[joueur.pos.i-1][joueur.pos.y], "?")){
            plateau[joueur.pos.i][joueur.pos.y] = " ";
            joueur.pos.i -= 1;
            plateau[joueur.pos.i][joueur.pos.y] = "!";
        } else {
            plateau[joueur.pos.i][joueur.pos.y] = " ";
            joueur.pos.i -= 1;
            plateau[joueur.pos.i][joueur.pos.y] = "@";
        }
        return result;
    }
    // Déplacement vers le bas
    boolean bas(String[][] plateau, Joueur joueur){
        boolean result = false;
        if (equals(plateau[joueur.pos.i+1][joueur.pos.y], "#")){
                result = true;
        } else if (equals(plateau[joueur.pos.i+1][joueur.pos.y], "?")){
            plateau[joueur.pos.i][joueur.pos.y] = " ";
            joueur.pos.i += 1;
            plateau[joueur.pos.i][joueur.pos.y] = "!";
        } else {
            plateau[joueur.pos.i][joueur.pos.y] = " ";
            joueur.pos.i += 1;
            plateau[joueur.pos.i][joueur.pos.y] = "@";
        }
        return result;
    }

    // Déplacement vers la droite
    boolean droite(String[][] plateau, Joueur joueur){
        boolean result = false;
        if (equals(plateau[joueur.pos.i][joueur.pos.y+1], "#")){
                result = true;
        } else if (equals(plateau[joueur.pos.i][joueur.pos.y+1], "?")){
            plateau[joueur.pos.i][joueur.pos.y] = " ";
            joueur.pos.y += 1;
            plateau[joueur.pos.i][joueur.pos.y] = "!";
        } else {
            plateau[joueur.pos.i][joueur.pos.y] = " ";
            joueur.pos.y += 1;
            plateau[joueur.pos.i][joueur.pos.y] = "@";
        }
        return result;
    }

    // Déplacement vers la gauche
    boolean gauche(String[][] plateau, Joueur joueur){
        boolean result = false;
        if (equals(plateau[joueur.pos.i][joueur.pos.y-1], "#")){
                result = true;
        } else if (equals(plateau[joueur.pos.i][joueur.pos.y-1], "?")){
            plateau[joueur.pos.i][joueur.pos.y] = " ";
            joueur.pos.y -= 1;
            plateau[joueur.pos.i][joueur.pos.y] = "!";
        } else {
            plateau[joueur.pos.i][joueur.pos.y] = " ";
            joueur.pos.y -= 1;
            plateau[joueur.pos.i][joueur.pos.y] = "@";
        }
        return result;
    }

    boolean for_inst(String[][] plateau, Joueur joueur, String for_in){
        String[] instructions = new String[50];
        stringToTable(instructions, substring(for_in, 7, length(for_in)));
        boolean dead = false;
        int c = charAt(for_in, 4) - 48;
        while (c> 0 && ! dead){
            dead = execution(plateau, joueur, instructions);
            c -= 1;
        }
        return dead;
    }

    // Fonction pour exécuter les fonctions précédentes (choix d'instructions ...)
    boolean execution(String[][] plateau, Joueur joueur, String[] instructions){
        int indexInstructions = 0;
        boolean dead = false;
        boolean win = false;
        while (instructions[indexInstructions] != null && !dead && !equals(instructions[indexInstructions], "")){
            println(plateau);
            String instruction = instructions[indexInstructions];
            switch (instruction){
                case "haut":
                    delay(500);
                    clearScreen();
                    dead = haut(plateau, joueur);
                    break;
                case "bas":
                    delay(500);
                    clearScreen();
                    dead = bas(plateau, joueur);
                    break;
                case "droite":
                    delay(500);
                    clearScreen();
                    dead = droite(plateau, joueur);
                    break;
                case "gauche":
                    delay(500);
                    clearScreen();
                    dead = gauche(plateau, joueur);
                    break;
                default:
                    clearScreen();
                    if (equals(substring(instruction, 0, 3), "for")){
                        dead = for_inst(plateau, joueur, instruction);
                    }
                    break;
            }
            indexInstructions++;
        }
        // if (equals(plateau[joueur.pos.i][joueur.pos.y], "!")){
        //     win = true;
        // }
        return dead;
    }

    void checkJoueur(Joueur joueur){
        CSVFile JoueursCSV = loadCSV("../csv/players.csv", ';');
        if (rowCount(JoueursCSV) > 0){
            String[][] Joueurs = new String[rowCount(JoueursCSV)][columnCount(JoueursCSV)];
            for (int i = 0; i<length(Joueurs, 1); i++){
                for (int j = 0; j<length(Joueurs, 2); j++){
                    Joueurs[i][j] = getCell(JoueursCSV, i, j);
                }
            }

            for (int i = 0; i<rowCount(JoueursCSV); i++){
                if (equals(joueur.pseudo, Joueurs[i][0])){
                    joueur.niveau = toInt(Joueurs[i][1]);
                }
            }
        }
    }

    void save(Joueur joueur){
        boolean isIn = false;
        CSVFile JoueursCSV = loadCSV("../csv/players.csv", ';');
        for (int i = 0; i<rowCount(JoueursCSV); i++){
            for (int j = 0; j<columnCount(JoueursCSV); j++){
                if (equals(getCell(JoueursCSV, i, 0), joueur.pseudo)){
                    isIn = true;
                }
            }
        }

        if (isIn){
            String[][] Joueurs = new String[rowCount(JoueursCSV)][columnCount(JoueursCSV)];
            for (int i = 0; i<length(Joueurs, 1); i++){
                for (int j = 0; j<length(Joueurs, 2); j++){
                    Joueurs[i][j] = getCell(JoueursCSV, i, j);
                    if (equals(getCell(JoueursCSV, i, 0), joueur.pseudo)){
                        Joueurs[i][1] = joueur.niveau + "";
                    }
                }
            }
            saveCSV(Joueurs, "../csv/players.csv", ';') ;
        }else {
            if (rowCount(JoueursCSV) > 0){
                String[][] Joueurs = new String[rowCount(JoueursCSV)+1][columnCount(JoueursCSV)];
                for (int i = 0; i<length(Joueurs, 1); i++){
                    for (int j = 0; j<length(Joueurs, 2); j++){
                        Joueurs[i][j] = getCell(JoueursCSV, i, j);
                    }
                }
                Joueurs[rowCount(JoueursCSV)+1][0] = joueur.pseudo + "";
                Joueurs[rowCount(JoueursCSV)+1][1] = joueur.niveau + "";
                saveCSV(Joueurs, "../csv/players.csv", ';') ;
            } else {
                String[][] Joueurs = new String[1][2];
                Joueurs[0][0] = joueur.pseudo + "";
                Joueurs[0][1] = joueur.niveau + "";
                saveCSV(Joueurs, "../csv/players.csv", ';') ;
            }
        }
    }

    void play(){
        clearScreen();
        showCodia();
        Joueur joueur = initJoueur(1, 1);
        checkJoueur(joueur);
        String[][] plateau = initPlateau(joueur);
        clearScreen();
        boolean result = true;
        do {
            plateau = initPlateau(joueur);
            String[] instructions = choixInstructions(plateau);
            clearScreen();
            execution(plateau, joueur, instructions);
            if (equals(plateau[joueur.pos.i][joueur.pos.y], "!")){
                if (joueur.niveau < length(getAllFilesFromDirectory("../csv"))){
                    text(purple);
                    println("Bien joue, tu passes au prochaine niveau ! Bonne chance !");
                } else {
                    text(purple);
                    println("Tu as fini tous les niveaux du jeu, bien joue !");
                }
                joueur.niveau++;
                save(joueur);
                joueur.pos.i = 1;
                joueur.pos.y = 1;
            } else {
                text(purple);
                println("Dommage, tu as perdu, retente ta chance !");
                joueur.pos.i = 1;
                joueur.pos.y = 1;
            }
            delay(2000);
        } while (result && joueur.niveau <= length(getAllFilesFromDirectory("../csv")));
    }

/*
=====================================================================================================================
REGLES
=====================================================================================================================
*/

    // Affichage des règles
    void afficherRegles(){
        clearScreen();
        showCodia();
        text(green);
        print("Bonjour et bienvenue dans "); text(blue); println("Codia !\n"); text(green);
        println("Le but du jeu est tres simple.");
        print("Le caractere "); text(purple); print('@'); text(green); println(" represente ton joueur.");
        print("Le caractere "); text(purple); print('#'); text(green); println(" represente les murs.");
        print("Ton but est d'arriver au caractere "); text(purple); print('?'); text(green); println(" sans toucher les murs.");
        println("Si tu touches un mur, tu devras recommencer le niveau depuis le debut ! :c");
        text(red); print("Attention ! "); text(green); println("Ton personnage ne bougeras pas avant que tu aies fini de lui donner toutes les instructions.\n");
        println("Bonne chance ! :D");
        print("\n\n\n\n");
        print("Appuie sur une touche pour continuer...");
        String rep = readString();
        if (equals(toLowerCase(rep), "codia")){
            text(red); println("Prout."); text(white);
            delay(1000);
        }
    }

/*
=====================================================================================================================
CREDITS
=====================================================================================================================
*/

    void credits(){
        clearScreen();
        showCodia();
        text(green);
        println("Developpeurs : \n");
        for (int i = 0; i<length(authors); i++){
            text(purple);
            print("    [");
            text(green);
            print(i+1);
            text(purple);
            print("] - ");
            text(green);
            println(authors[i] + "\n");
        }
        print("\n\n\n\n");
        print("Appuie sur une touche pour continuer...");
        String rep = readString();
        if (equals(toLowerCase(rep), "codia")){
            text(red); println("Prout."); text(white);
            delay(1000);
        }
        text(white);
    }

/*
=====================================================================================================================
UTILS
=====================================================================================================================
*/

    int toInt(String nombre){
        int rep = 0;
        for (int i = 0; i< length(nombre);i++){
            rep = rep *10;
            rep += (int)(charAt(nombre,i) - 48);
        }
        return rep;
    }

    void stringToTable(String[] for_tab, String for_S){
        //prend seulement les instruction donc pas le for(x)
        int index = 1;
        int index_tab = 0;
        while (index < length(for_S) && index_tab < length(for_tab)){
            if (charAt(for_S,index) == '|'){
                for_tab[index_tab] = substring(for_S,1,index);
                index_tab += 1;
                for_S = substring(for_S,index-1, length(for_S));
                index = 0;
            }
            index += 1;
        }
    }
}