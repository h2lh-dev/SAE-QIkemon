import extensions.File;
import extensions.CSVFile;

class JeuxPokemon extends Program{

    // Creation tab
    final String[][] POKEDEX = toTabCSV(loadCSV("../ressources/Pokemon/pokemon.csv"));
    final String[][] tabType = toTabCSV(loadCSV("../ressources/Pokemon/type.csv"));
    final String[][] listAttack = toTabCSV(loadCSV("../ressources/Pokemon/Attack.csv"));

    // IDX POKEDEX
    final int IDX_ID = 0;
    final int IDX_NAME_POKEMON = 1;
    final int IDX_TYPE1 = 2;
    final int IDX_TYPE2 = 3;
    final int IDX_STATPV = 4;
    final int IDX_STATDEGAT = 5;
    final int IDX_STATDEFENSE = 6;
    final int IDX_STATVITESSE = 7;
    final int IDX_LVLEVO = 8;
    final int IDX_ATTACK1 = 9;
    final int IDX_ATTACK2 = 10;
    final int IDX_ATTACK3 = 11;
    final int IDX_ATTACK4 = 12;

    // IDX listAttack
    final int IDX_NAME_ATTACK = 0;
    final int IDX_STAT_ATTACK = 1;
    final int IDX_PP = 2;
    final int IDX_TYPE = 3;


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
        pokemon.xpRequis = xpRequis(lvl);
        pokemon.statPv = stringToInt(POKEDEX[id][5]);
        pokemon.statAttack = stringToInt(POKEDEX[id][6]);
        pokemon.statDefense = stringToInt(POKEDEX[id][7]);
        pokemon.statVitesse = stringToInt(POKEDEX[id][8]);
        //pokemon.attacks = newListAttack();
        pokemon.affiniterDeType = newaffiniterDeType(POKEDEX[id][3], POKEDEX[id][4]);

        return pokemon;
    }

    Attack newAttack(String nomAttack){
        Attack attack = new Attack();

        return attack;
    }

    //Attack[] newListAttack(String attack1, String attack2, String attack3, String attack4){
        
    //}

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

    // Permet de return l'indice d'une attaque depuis le tableau listAttack
    int idxAttack(String Attack){
        for(int i=1;i<length(listAttack,2);i++){
            if(equals(Attack,listAttack[i][IDX_NAME_ATTACK])){
                return i;
            }
        }
    return -1;
    }

    void testIdxAttack(){
        assertEquals(idxAttack("Flammeche"), 1);
    }


    // Fontion permettant de calculer Xp nécessaire au passage d'un niveau
    int xpRequis(int lvl){
        int xpRequis = 100;
        for(int i = 0; i<lvl; i++ ){
            xpRequis = (int) ((int) xpRequis * 1.10);
        }
        return xpRequis; 
    }

    void testXpRequis(){
        assertEquals(xpRequis(1),110);
        assertEquals(xpRequis(15),409);
        assertEquals(xpRequis(20),655);
        assertEquals(xpRequis(30),1694);
    }





    void _algorithm(){
        String[] test2 = new String []{};
        test2 = newaffiniterDeType("Sol","Eau");
        for(int i = 0;i<length(test2);i++){
            println(test2[i]);
        }
        
    }


}
