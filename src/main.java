import extensions.CSVFile;
import extensions.RGBColor;

class Codia extends Program {
    // Initialise les variables globales
    final char mur = '#';
    final char arrivee = '?';
    // Initialise les couleurs
    final RGBColor RED = RGBColor.RED;
    final RGBColor WHITE = RGBColor.WHITE;
    final RGBColor GREEN = RGBColor.GREEN;
    final RGBColor PURPLE = RGBColor.PURPLE;


    // Affiche un tableau à 2 dimensions
    void println(String[][] tab){
        String result = "";
        for (int i = 0; i<length(tab, 1); i++){
            for (int j = 0; j<length(tab, 2); j++){
                switch (tab[i][j]){
                    case "#":
                        text(RED.name);
                        print(tab[i][j]+" ");
                        break;
                    case "@":
                        text(WHITE.name);
                        print(tab[i][j]+" ");
                        break;
                    case "?":
                        text(GREEN.name);
                        print(tab[i][j]+" ");
                        break;
                    default:
                        print("  ");
                        break;
                }
            }
            print("\n");
        }
        print("\n\n\n");
    }

    // Initialise un tableau à 2 dimensions avec des caractères (en string)
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
        int indexChoix = 0;
        int entreeInt = 0;
        do {
            clearScreen(); println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
            println(plateau);
            afficherInstructions(instructions);
            println("\n");
            afficherChoix(choix);
            text(PURPLE.name);
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
                    afficherChoix(choix);
                    println("\n");
                    println(for_in);
                    print("Votre choix : ");
                    entree_for = readInt();
                    for_in += choix[entree_for-1]+"|"; 
                }while(!equals(choix[entree_for-1],"fin"));
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
        text(PURPLE.name);
        print("Instructions : {");
        for (int i = 0; i<length(instructions); i++){
            if (instructions[i] != null){
                text(GREEN.name);
                print(instructions[i]);
                text(PURPLE.name);
                print(";");
            }
        }
        print("}");
    }

    // Affiche les instructions disponibles
    void afficherChoix(String[] choix){
        text(PURPLE.name);
        print("Choisir une instruction : \n\n");
        for (int i = 0; i<length(choix); i++){
            text(PURPLE.name);
            print("  [");
            text(GREEN.name);
            print(i+1);
            text(PURPLE.name);
            print("] - ");
            text(GREEN.name);
            print(choix[i] + "\n");
        }
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

    boolean for_inst(String[][] plateau, Joueur joueur, String for_in){
        
        return false;
    }

    // Fonction pour exécuter les fonctions précédentes (choix d'instructions ...)
    boolean execution(String[][] plateau, Joueur joueur, String[] instructions){
        int indexInstructions = 0;
        boolean dead = false;
        boolean win = false;
        while (instructions[indexInstructions] != null && !dead){
            println(plateau);
            delay(500);
            clearScreen();
            String instruction = instructions[indexInstructions];
            switch (instruction){
                case "haut":
                    dead = haut(plateau, joueur);
                    break;
                case "bas":
                    dead = bas(plateau, joueur);
                    break;
                case "droite":
                    dead = droite(plateau, joueur);
                    break;
                case "gauche":
                    dead = gauche(plateau, joueur);
                    break;
            }
            indexInstructions++;
        }
        if (equals(plateau[joueur.pos.i][joueur.pos.y], "!")){
            win = true;
        }
        return win;
    }

    // Affichage des règles
    void afficherRegles(Joueur joueur){
        println("Bonjour, " + joueur.pseudo + " ! Bienvenue dans Codia !\n");
        println("Le but du jeu est tres simple.");
        println("Le '" + joueur.car + "' represente ton joueur.");
        println("Le '" + mur + "' represente les murs.");
        println("Ton but est d'arriver au '" + arrivee + "' sans toucher les murs.\n");
        println("Bonne chance ! :D");
        readString();
    }

    // Fonction principale
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
            result = execution(plateau, joueur, instructions);
            if (result){
                if (joueur.niveau < length(getAllFilesFromDirectory("../csv"))){
                    println("Bien joue, tu passes au prochaine niveau ! Bonne chance !");
                } else {
                    println("Tu as fini tous les niveaux du jeu, bien joue !");
                }
                joueur.niveau++;
                joueur.pos.i = 1;
                joueur.pos.y = 1;
            } else {
                println("Dommage, tu as perdu, retente ta chance !");
            }
            delay(2000);
        } while (result && joueur.niveau <= length(getAllFilesFromDirectory("../csv")));
    }
}