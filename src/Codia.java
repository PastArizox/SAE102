class Codia extends Program {
    final char bordure = '#';
    final char mur = '/';
    final char arrivee = '!';

    void println(char[][] tab){
        String result = "";
        for (int i = 0; i<length(tab, 1); i++){
            for (int j = 0; j<length(tab, 2); j++){
                result += tab[i][j] + " ";
            }
            result += "\n";
        }
        println(result);
    }

    char[][] initPlateau(Joueur joueur){
        char[][] plateau = new char[][]{
            {'#', '#', '#', '#', '#', '#', '#'},
            {'#', ' ', ' ', ' ', '/', '/', '#'},
            {'#', '/', '/', ' ', '/', '/', '#'},
            {'#', '/', '/', ' ', '/', '/', '#'},
            {'#', '/', '/', '?', '/', '/', '#'},
            {'#', '#', '#', '#', '#', '#', '#'}
        };
        plateau[joueur.pos.i][joueur.pos.y] = joueur.car;
        return plateau;
    }

    Joueur initJoueur(int i, int y){
        Joueur joueur = new Joueur();
        print("Choisissez votre pseudo : ");
        joueur.pseudo = readString();
        joueur.pos = new Position();
        joueur.pos.i = i;
        joueur.pos.y = y;
        return joueur;
    }

    String[] choixInstructions(char[][] plateau){
        String[] instructions = new String[20];
        String[] choix = new String[]{"haut", "bas", "droite", "gauche", "fin"};
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
            instructions[indexChoix] = choix[entreeInt-1];
            indexChoix++;
        } while (!equals(choix[entreeInt-1], "fin"));
        return instructions;
    }

    void afficherInstructions(String[] instructions){
        String result = "Instructions : {";
        for (int i = 0; i<length(instructions); i++){
            if (instructions[i] != null){
                result += instructions[i] + ";";
            }
        }
        result += "}";
        println(result);
    }

    void afficherChoix(String[] choix){
        String result = "Choisir une instruction : \n\n";
        for (int i = 0; i<length(choix); i++){
            result += "  [" + (i+1) + "] - " + choix[i] + "\n";
        }
        println(result);
    }

    boolean haut(char[][] plateau, Joueur joueur){
        boolean result = false;
        if (plateau[joueur.pos.i-1][joueur.pos.y] == '#' || 
            plateau[joueur.pos.i-1][joueur.pos.y] == '/'){
                result = true;
        } else if (plateau[joueur.pos.i-1][joueur.pos.y] == '?'){
            plateau[joueur.pos.i][joueur.pos.y] = ' ';
            joueur.pos.i += 1;
            plateau[joueur.pos.i][joueur.pos.y] = '!';
        } else {
            plateau[joueur.pos.i][joueur.pos.y] = ' ';
            joueur.pos.i -= 1;
            plateau[joueur.pos.i][joueur.pos.y] = '@';
        }
        return result;
    }

    boolean bas(char[][] plateau, Joueur joueur){
        boolean result = false;
        if (plateau[joueur.pos.i+1][joueur.pos.y] == '#' || 
            plateau[joueur.pos.i+1][joueur.pos.y] == '/'){
                result = true;
        } else if (plateau[joueur.pos.i+1][joueur.pos.y] == '?'){
            plateau[joueur.pos.i][joueur.pos.y] = ' ';
            joueur.pos.i += 1;
            plateau[joueur.pos.i][joueur.pos.y] = '!';
        } else {
            plateau[joueur.pos.i][joueur.pos.y] = ' ';
            joueur.pos.i += 1;
            plateau[joueur.pos.i][joueur.pos.y] = '@';
        }
        return result;
    }

    boolean droite(char[][] plateau, Joueur joueur){
        boolean result = false;
        if (plateau[joueur.pos.i][joueur.pos.y+1] == '#' || 
            plateau[joueur.pos.i][joueur.pos.y+1] == '/'){
                result = true;
        } else if (plateau[joueur.pos.i][joueur.pos.y+1] == '?'){
            plateau[joueur.pos.i][joueur.pos.y] = ' ';
            joueur.pos.i += 1;
            plateau[joueur.pos.i][joueur.pos.y] = '!';
        } else {
            plateau[joueur.pos.i][joueur.pos.y] = ' ';
            joueur.pos.y += 1;
            plateau[joueur.pos.i][joueur.pos.y] = '@';
        }
        return result;
    }

    boolean gauche(char[][] plateau, Joueur joueur){
        boolean result = false;
        if (plateau[joueur.pos.i][joueur.pos.y-1] == '#' || 
            plateau[joueur.pos.i][joueur.pos.y-1] == '/'){
                result = true;
        } else if (plateau[joueur.pos.i][joueur.pos.y-1] == '?'){
            plateau[joueur.pos.i][joueur.pos.y] = ' ';
            joueur.pos.i += 1;
            plateau[joueur.pos.i][joueur.pos.y] = '!';
        } else {
            plateau[joueur.pos.i][joueur.pos.y] = ' ';
            joueur.pos.y -= 1;
            plateau[joueur.pos.i][joueur.pos.y] = '@';
        }
        return result;
    }

    boolean execution(char[][] plateau, Joueur joueur, String[] instructions){
        int indexInstructions = 0;
        boolean dead = false;
        boolean win = false;
        while (instructions[indexInstructions] != null && !dead){
            println(plateau);
            delay(1000);
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
        if (plateau[joueur.pos.i][joueur.pos.y] == '!'){
            win = true;
        }
        return win;
    }

    void afficherRegles(Joueur joueur){
        println("Bonjour, " + joueur.pseudo + " ! Bienvenue dans Codia !\n");
        println("Le but du jeu est tres simple.");
        println("Le '"+ joueur.car +"' represente ton joueur.");
        println("Le '"+ mur +"' represente les murs et le '"+ bordure +"' represente la bordure.");
        println("Ton but est d'arriver au '"+ arrivee +"' sans toucher les murs ni la bordure.\n");
        println("Bonne chance ! :D");
        readString();
    }

    void algorithm(){
        Joueur joueur = initJoueur(1, 1);
        char[][] plateau = initPlateau(joueur);
        clearScreen();
        afficherRegles(joueur);
        String[] instructions = choixInstructions(plateau);
        clearScreen();
        boolean result = execution(plateau, joueur, instructions);
        if (result){
            println("Bien joue tu as gagne !");
        } else {
            println("Dommage, tu as perdu, retente ta chance !");
        }
    }

}