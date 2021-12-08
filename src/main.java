class Codia extends Program {
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
            {'#', '/', '/', '!', '/', '/', '#'},
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
        } else {
            plateau[joueur.pos.i][joueur.pos.y] = ' ';
            joueur.pos.y -= 1;
            plateau[joueur.pos.i][joueur.pos.y] = '@';
        }
        return result;
    }

    void algorithm(){
        Joueur joueur = initJoueur(1, 1);
        char[][] plateau = initPlateau(joueur);
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

        clearScreen();

        int indexInstructions = 0;
        boolean dead = false;
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
    }
}