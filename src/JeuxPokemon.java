import extensions.File;
import extensions.CSVFile;

class JeuxPokemon extends Program{

    // Creation tab
    final String[][] POKEDEX = toTabCSV(loadCSV("../ressources/Pokemon/pokemon.csv"));
    final String[][] tabType = toTabCSV(loadCSV("../ressources/Pokemon/type.csv"));
    final String[][] listAttack = toTabCSV(loadCSV("../ressources/Pokemon/Attack.csv"));    

    // Permet de transformer un fichier csv en tableaux de String
    String[][] toTabCSV(CSVFile csv){
        String[][] tab = new String[rowCount(csv)][columnCount(csv)];
        for(int x=0;x<length(tab,1);x++){
            for(int y=0;y<length(tab,2);y++){
                tab[x][y] = getCell(csv,x,y);
            }
        }
        return tab;
    }
    
    //Pour la création d'un pokemon on aura besoin de son ID qui va permettre de récuperer les information du pokemon dans le csv, et de son lvl poour lui attribuer
    Pokemon newPokemon(int id, int lvl){
        Pokemon pokemon = new Pokemon();
        pokemon.id = id;
        pokemon.nom = POKEDEX[id][2];
        pokemon.type1 = POKEDEX[id][3];
        pokemon.type2 = POKEDEX[id][4];
        pokemon.lvl = lvl ;
        pokemon.xp = 0;
        pokemon.statPv = stringToInt(POKEDEX[id][6]);
        pokemon.statAttack = stringToInt(POKEDEX[id][7]);
        pokemon.statDefense = stringToInt(POKEDEX[id][8]);
        pokemon.statVitesse = stringToInt(POKEDEX[id][9]);
        pokemon.attack1 = newAttack();
        pokemon.attack2 = newAttack();
        pokemon.attack3 = newAttack();
        pokemon.attack4 = newAttack();
        pokemon.affiniterDeType = newaffiniterDeType(POKEDEX[id][3], POKEDEX[id][4]);

        return pokemon;
    }

    Attack newAttack(){
        return new Attack();
    }

    // permet la création du tableau contenant les affiniterDeType du pokemon celon son ou ses types
    String[] newaffiniterDeType( String type1, String type2){

        if(equals(type2,"NULL")){
            String[] affiniterDeType = new String[18];
            for(int i =0;i<18;i++ ){
                affiniterDeType[i] = tabType[idxType(type1)][i+1] ;
            }
            return affiniterDeType;
        }else{
        String[] affiniterDeType = new String[36];
        int i2 = 1;
        int i3 = 0;
            for(int i = 1;i<=18;i++){

                affiniterDeType[i3] = tabType[idxType(type1)][i];
                affiniterDeType[i2] = tabType[idxType(type2)][i];
                i3 = i3 +2;
                i2 = i2 +2;        
            }
            for(int i =0;i<36;i++){
                for(int cpt = 0;cpt<36;cpt++){
                    if(equals(affiniterDeType[i],affiniterDeType[cpt])){
                        if(i>= 0 && i<= 4){
                            affiniterDeType[cpt] = "NULL";
                        }
                        else if(i>=5 && i<= 15 && cpt >=16 ){
                            affiniterDeType[cpt] = "NULL";
                            affiniterDeType[i] = "NULL";
                        }

                    }
                }
            }
        return affiniterDeType;
        }
    }

    // Test pour verifier la fonction newaffiniterDeType
    void testNewaffiniterDeType(){
        String[] test1 = new String []{"NULL", "NULL", "Eau", "Sol", "Roche", "NULL", "NULL", "Feu", "Plante", "Glace", "Insecte", "Acier", "NULL", "NULL", "NULL", "NULL", "NULL", "NULL"};
        assertEquals(test1,newaffiniterDeType("Feu","NULL"));
        /*String[] test2 = new String []{};
        test2 = newaffiniterDeType("Feu","Eau");
        for(int i = 0;i<length(test2);i++){
            println(test2[i]);
        }
        */

    }

    // Permet de return l'indice d'un type depuis le tableau tabType
    int idxType(String type){
        for(int i=1 ; i<length(tabType,2);i++){
            if(equals(type,tabType[i][0])){
                return i;
            }
        }
    return -1;
    }
    
    void testIdxType(){
        assertEquals(idxType("Feu"), 2);
    }

    void algorithm(){
        String[] test2 = new String []{};
        test2 = newaffiniterDeType("Sol","Eau");
        for(int i = 0;i<length(test2);i++){
            println(test2[i]);
        }
        
    }
}
