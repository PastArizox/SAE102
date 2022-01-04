import extensions.CSVFile;
import extensions.RGBColor;

class Codia extends Program {
    final char mur = '#';
    final char arrivee = '?';
    //les couleur
    final String green = RGBColor.GREEN.name;
    final String red = RGBColor.RED.name;
    final String purple = RGBColor.PURPLE.name;
    final String white = RGBColor.WHITE.name;
    final String black = RGBColor.BLACK.name;


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

    // Initialise le joueur et sa position
    Joueur initJoueur(int i, int y){
        Joueur joueur = new Joueur();
        print("Choisissez votre pseudo : ");
        joueur.pseudo = readString();
        joueur.pos = new Position();
        joueur.pos.i = i;
        joueur.pos.y = y;
        return joueur;
    }

    // Fonction pour demander les instructions au joueur
    String[] choixInstructions(String[][] plateau){
        String[] instructions = new String[50];
        String[] choix = new String[]{"haut", "bas", "droite", "gauche", "for", "fin"};
        String[] choix_for = new String[]{"haut", "bas", "droite", "gauche", "fin"};
        int indexChoix = 0;
        int entreeInt = 0;
        do {
            clearScreen(); println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
            println(plateau);
            afficherInstructions(instructions);
            println("\n");
            afficherChoix(choix);
            print("Votre choix : ");
            entreeInt = readInt();
            if (equals(choix[entreeInt-1],"for")){
                String for_in = "for(";
                println("nombre de repetition ?");
                for_in += readInt();
                for_in += ") |";
                int entree_for;
                do{
                    clearScreen(); println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
                    println(plateau);
                    afficherChoix(choix_for);
                    println("\n");
                    println(for_in);
                    print("Votre choix : ");
                    entree_for = readInt();
                    for_in += choix_for[entree_for-1]+"|"; 
                }while(!equals(choix_for[entree_for-1],"fin"));
                instructions[indexChoix] = for_in;

                
            }else{
                instructions[indexChoix] = choix[entreeInt-1];
            }
            indexChoix++;
        } while (!equals(choix[entreeInt-1], "fin"));
        return instructions;
    }

    // Fonction pour afficher les instructions choisies par le joueur
    void afficherInstructions(String[] instructions){
        text(purple);
        print("Instructions : {");
        for (int i = 0; i<length(instructions); i++){
            if (instructions[i] != null){
                text(green);
                print(instructions[i]);
                text(purple);
                print(";");
            }
        }
        print("}");
    }

    // Affiche les instructions disponibles
    void afficherChoix(String[] choix){
        text(purple);
        print("Choisir une instruction : \n\n");
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
        text(purple);
        print("\n");
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
            // delay(2000);
            // clearScreen();
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

    // Affichage des règles
    void afficherRegles(Joueur joueur){
        text(purple);
        println("Bonjour, " + joueur.pseudo + " ! Bienvenue dans Codia !\n");
        println("Le but du jeu est tres simple.");
        println("Le '" + joueur.car + "' represente ton joueur.");
        println("Le '" + mur + "' represente les murs.");
        println("Ton but est d'arriver au '" + arrivee + "' sans toucher les murs.\n");
        println("Bonne chance ! :D");
        readString();
    }

    // Fontion principale
    void algorithm(){
        Joueur joueur = initJoueur(1, 1);
        String[][] plateau = initPlateau(joueur);
        clearScreen();
        afficherRegles(joueur);
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
}