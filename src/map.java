import extensions.File;
import extensions.CSVFile;

class map extends Program{
    final String BORDURE_HAUT = "╔════════════════════════════════════════════════════════════╦════════════════════════════════════════════════════════════╗";
    final String BORDURE_BAS = "╚════════════════════════════════════════════════════════════╩════════════════════════════════════════════════════════════╝";
    final String[][] map = toTabCSV(loadCSV("../ressources/map/map2.csv"));

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
        int ligne;
        int colonne;
        for(int x = 0; x<length(map,1);x++){
            for(int y = 0; y<length(map,2);y++){
                ligne = 
                if(i2==55){
                    print(map[i][i2]);
                    println();
                    
                }
                if(equals(map[i][i2],"A")){
                    print(couleur.COLOR_GREEN+"A");
                }
                if(equals(map[i][i2],"M")){
                    print(couleur.COLOR_BLUE+"M");
                }
                if(equals(map[i][i2],"H")){
                    print(couleur.COLOR_RED+"H");
                }
                if(equals(map[i][i2],"G")){
                    print(couleur.COLOR_PURPLE+"G");
                }
                if(equals(map[i][i2],"B")){
                    print(couleur.COLOR_GREY+"B");
                }
                if(equals(map[i][i2],"T")){
                    print(couleur.COLOR_YELLOW+"T");
                }
                if(equals(map[i][i2],"F")){
                    print(couleur.COLOR_LIGHTGREEN+"F");
                }
                if(equals(map[i][i2],"V")){
                    print(couleur.COLOR_CYAN+"V");
                }
                print(couleur.COLOR_RESET);
                
            }
        
        }


    }

    void algorithm(){
        afficherMap(map);
    }


}