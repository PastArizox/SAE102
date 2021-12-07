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

    void algorithm(){
        char[][] plateau = initPlateau();
        println(plateau);
    }
}