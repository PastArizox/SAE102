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

    void haut(char[][] plateau, Joueur joueur){
        println("haut");
    }

    void bas(char[][] plateau, Joueur joueur){
        println("bas");
    }

    void droite(char[][] plateau, Joueur joueur){
        println("droite");
    }

    void gauche(char[][] plateau, Joueur joueur){
        println("gauche");
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
        while (instructions[indexInstructions] != null){
            String instruction = instructions[indexInstructions];
            switch (instruction){
                case "haut":
                    haut(plateau, joueur);
                    delay(500);
                    break;
                case "bas":
                    bas(plateau, joueur);
                    delay(500);
                    break;
                case "droite":
                    droite(plateau, joueur);
                    delay(500);
                    break;
                case "gauche":
                    gauche(plateau, joueur);
                    delay(500);
                    break;
            }
            indexInstructions++;
        }
    }
}