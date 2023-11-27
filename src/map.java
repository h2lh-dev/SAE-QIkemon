import extensions.File;
import extensions.CSVFile;

class map extends Program{
    final String BORDURE_HAUT = "╔═════════════════════════════════════════════════════QIKEMON════════════════════════════════════════════════════╗";
    final String BORDURE_BAS = "╚════════════════════════════════════════════════════════╩═══════════════════════════════════════════════════════╝";
    final String[][] map = toTabCSV(loadCSV("../ressources/map/map3.csv"));
    final String[][] mapSansJ = toTabCSV(loadCSV("../ressources/map/mapSansJ.csv"));

    String[][] toTabCSV(CSVFile csv){
        String[][] tab = new String[rowCount(csv)][columnCount(csv)];
        for(int x=0;x<length(tab,1);x++){
            for(int y=0;y<length(tab,2);y++){
                tab[x][y] = getCell(csv,x,y);
            }
        }
        return tab;
    }

    void afficherMap(String[][] map){
        Couleur couleur = new Couleur();
        clearScreen();
        println(BORDURE_HAUT);
        for(int i = 0; i<length(map,1);i++){
            print("|");
            for(int i2 = 0; i2<length(map,2);i2++){
                
                if(equals(map[i][i2],"A")){
                    print("🌲");
                }
                if(equals(map[i][i2],"J")){
                    print("👨");
                }
                if(equals(map[i][i2],"M")){
                    print("🏠");
                }
                if(equals(map[i][i2],"H")){
                    print("  ");
                }
                if(equals(map[i][i2],"G")){
                    print("  ");
                }
                if(equals(map[i][i2],"B")){
                    print("🚧");
                }
                if(equals(map[i][i2],"T")){
                    print("  ");
                }
                if(equals(map[i][i2],"L")){
                    print(couleur.COLOR_RED+"  ");
                }
                if(equals(map[i][i2],"C")){
                    print("🐖");
                }
                if(equals(map[i][i2],"E")){
                    print("🌊");
                }
                if(equals(map[i][i2],"R")){
                    print("🌹");
                }
                if(equals(map[i][i2],"P")){
                    print("🚪");
                }
                if(equals(map[i][i2],"F")){
                    print("🌼");
                }
                if(equals(map[i][i2],"V")){
                    print("  ");
                }
                if(i2 == 55){
                    print("|");
                    println();
                }
                print(couleur.COLOR_RESET);
            }
        
        }
        println(BORDURE_BAS);
    }

    boolean deplacementPossible(String[][] map, String deplacement){
        int ligne = 0;
        int colonne = 0;
        boolean trouve = false;
        boolean possible = false;
        while(!trouve){
            for(int x = 0; x<length(map,1);x++){
                for(int y = 0; y<length(map,2);y++){
                    if(equals(map[x][y],"J")){
                        ligne = x;
                        colonne = y;
                        trouve = true;
                    }
                }
        }
        }
        if(equals(deplacement,"z")){
            if(equals(map[ligne-1][colonne],"H") || equals(map[ligne-1][colonne],"F") || equals(map[ligne-1][colonne],"V") || equals(map[ligne-1][colonne],"T") || equals(map[ligne-1][colonne],"G")){
                return true;
            }else{
                return possible;
            }
        }
        if(equals(deplacement,"s")){
            if(equals(map[ligne+1][colonne],"H") || equals(map[ligne+1][colonne],"F") || equals(map[ligne+1][colonne],"V") || equals(map[ligne+1][colonne],"T") || equals(map[ligne+1][colonne],"G")){
                return true;
            }else{
                return possible;
            }
        }
        if(equals(deplacement,"q")){
            if(equals(map[ligne][colonne-1],"H") || equals(map[ligne][colonne-1],"F") || equals(map[ligne][colonne-1],"V") || equals(map[ligne][colonne-1],"T") || equals(map[ligne][colonne-1],"G")){
                return true;
            }else{
                return possible;
            }
        }
        if(equals(deplacement,"d")){
            if(equals(map[ligne][colonne+1],"H") || equals(map[ligne][colonne+1],"F") || equals(map[ligne][colonne+1],"V") || equals(map[ligne][colonne+1],"T") || equals(map[ligne][colonne+1],"G")){
                return true;
            }else{
                return possible;
            }
        }
        return possible;  
    }

    void deplacerJoueur(String[][] map, String deplacement){
        int ligne = 0;
        int colonne = 0;
        boolean trouve = false;
        while(!trouve){
            for(int x = 0; x<length(map,1);x++){
                for(int y = 0; y<length(map,2);y++){
                    if(equals(map[x][y],"J")){
                        ligne = x;
                        colonne = y;
                        trouve = true;
                    }
                }
        }
        }
        if(equals(deplacement,"z")){
            if(deplacementPossible(map,deplacement) == true){
                println("Je me déplace");
                map[ligne-1][colonne] = map[ligne][colonne];
                map[ligne][colonne] = mapSansJ[ligne][colonne];
            }
        }
        if(equals(deplacement,"s")){
            if(deplacementPossible(map,deplacement) == true){
                map[ligne+1][colonne] = map[ligne][colonne];
                map[ligne][colonne] = mapSansJ[ligne][colonne];
            }
        }
        if(equals(deplacement,"d")){
            if(deplacementPossible(map,deplacement) == true){
                map[ligne][colonne+1] = map[ligne][colonne];
                map[ligne][colonne] = mapSansJ[ligne][colonne];
            }
        }
        if(equals(deplacement,"q")){
            if(deplacementPossible(map,deplacement) == true){
                map[ligne][colonne-1] = map[ligne][colonne];
                map[ligne][colonne] = mapSansJ[ligne][colonne];
            }
        }


    }

    void algorithm(){
        String r;
        do{
            afficherMap(map);
            println("Appuyer sur [e] pour quitter le jeu ! ");
            print("Faites votre choix de déplacement : ");
            r = readString();
            deplacerJoueur(map,r);
            
        }while(!equals("e",r));
        clearScreen();
        println("La fin du jeu ! ");
        
    }


}