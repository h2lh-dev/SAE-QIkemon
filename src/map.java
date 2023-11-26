import extensions.File;
import extensions.CSVFile;

class map extends Program{
    final String BORDURE_HAUT = "╔═════════════════════════════════════════════════════QIKEMON════════════════════════════════════════════════════╗";
    final String BORDURE_BAS = "╚════════════════════════════════════════════════════════╩═══════════════════════════════════════════════════════╝";
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
        println(BORDURE_HAUT);
        for(int i = 0; i<length(map,1);i++){
            print("|");
            for(int i2 = 0; i2<length(map,2);i2++){
                
                if(equals(map[i][i2],"A")){
                    print("🌲");
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

    void algorithm(){
        afficherMap(map);
    }


}