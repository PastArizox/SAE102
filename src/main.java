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

    char[][] initPlateau(){
        char[][] plateau = new char[][]{
            {'#', '#', '#', '#', '#', '#', '#'},
            {'#', '@', ' ', ' ', '/', '/', '#'},
            {'#', '/', '/', ' ', '/', '/', '#'},
            {'#', '/', '/', ' ', '/', '/', '#'},
            {'#', '/', '/', '!', '/', '/', '#'},
            {'#', '#', '#', '#', '#', '#', '#'}
        };
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

    void haut(char[][] plateau, Joueur joueur){

    }

    void bas(char[][] plateau, Joueur joueur){

    }

    void droite(char[][] plateau, Joueur joueur){

    }

    void gauche(char[][] plateau, Joueur joueur){

    }

    void algorithm(){
        Joueur joueur = initJoueur(1, 1);
        char[][] plateau = initPlateau();
        println(plateau);
    }
}