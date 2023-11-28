import extensions.File;
import extensions.CSVFile;

class JeuxPokemon extends Program{

    //Bordure

    // Creation tab
    final String[][] POKEDEX = toTabCSV(loadCSV("../ressources/Pokemon/pokemon.csv"));
    final String[][] tabType = toTabCSV(loadCSV("../ressources/Pokemon/type.csv"));
    final String[][] listAttack = toTabCSV(loadCSV("../ressources/Pokemon/Attack.csv"));
    final String[][] map = toTabCSV(loadCSV("../ressources/map/map.csv"));

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
    final int IDX_TYPE_ATTACK = 3;


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

    // Initialiser Pokemon 
    
    //Pour la création d'un pokemon on aura besoin de son ID qui va permettre de récuperer les information du pokemon dans le csv, et de son lvl poour lui attribuer
    Pokemon newPokemon(int id, int lvl){
        Pokemon pokemon = new Pokemon();
        pokemon.id = id;
        pokemon.nom = POKEDEX[id][IDX_NAME_POKEMON];
        pokemon.type1 = POKEDEX[id][IDX_TYPE1];
        pokemon.type2 = POKEDEX[id][IDX_TYPE2];
        pokemon.lvl = lvl ;
        pokemon.xp = 0;
        pokemon.xpRequis = xpRequis(lvl);
        pokemon.statPv = stringToInt(POKEDEX[id][IDX_STATPV]);
        pokemon.statAttack = stringToInt(POKEDEX[id][IDX_STATDEGAT]);
        pokemon.statDefense = stringToInt(POKEDEX[id][IDX_STATDEFENSE]);
        pokemon.statVitesse = stringToInt(POKEDEX[id][IDX_STATVITESSE]);
        pokemon.attacks = newListAttack(POKEDEX[id][IDX_ATTACK1],POKEDEX[id][IDX_ATTACK2],POKEDEX[id][IDX_ATTACK3],POKEDEX[id][IDX_ATTACK4],pokemon.lvl);
        String[] affiniterdetype = newaffiniterDeType(pokemon.type1, pokemon.type2);
        pokemon.faiblesseDeType = faiblessesDeType(affiniterdetype);
        pokemon.ineficasseDeType = ineficasseDeType(affiniterdetype);
        pokemon.resistanceDeType = resistanceDeType(affiniterdetype);

        return pokemon;
    }

    // Fonction qui permet la création d'une attaque
    Attack newAttack(String nomAttack){
        Attack attack = new Attack();
        int idx = idxAttack(nomAttack);
        attack.name = listAttack[idx][IDX_NAME_ATTACK];
        attack.pp = stringToInt(listAttack[idx][IDX_PP]);
        attack.ppRemaining = attack.pp;
        attack.stat = stringToInt(listAttack[idx][IDX_STAT_ATTACK]);
        attack.type = listAttack[idx][IDX_TYPE_ATTACK];
        return attack;
    }

    // Fonction test de NewAttack
    void testNewAttack(){
        Attack attack = new Attack();
        attack = newAttack("Flammeche");
        assertEquals(attack.name,"Flammeche");
        assertEquals(attack.pp,25);
        assertEquals(attack.ppRemaining,25);
        assertEquals(attack.stat,40);
        assertEquals(attack.type,"Feu");
    }

    // Fonction qui renvoie une attack null contenant aucun info
    Attack attackNULL(){
        Attack attack = new Attack();
        attack.name = "NULL";
        attack.pp = 0;
        attack.ppRemaining = 0;
        attack.stat = 0;
        attack.type = "NULL";
        return attack;
    }

    // Fonction qui renvoie la liste des attaque utilisable par un pokemon
    Attack[] newListAttack(String attack1, String attack2, String attack3, String attack4, int lvl){
        Attack[] attack = new Attack[4];
        attack[0] = newAttack(attack1);
        if(lvl >= 10){
            attack[1] = newAttack(attack2);
        }
        else{
            attack[1] = attackNULL();
        }
        if(lvl >= 20){
            attack[2] = newAttack(attack3);
        }
        else{
            attack[2] = attackNULL();
        }
        if(lvl >= 30){
            attack[3] = newAttack(attack4);
        }
        else{
            attack[3] = attackNULL();
        }
        return attack;
    }

    // fonction test de newListAttack
    void testNewListAttack(){
        Attack[] attack = new Attack[4];
        attack = newListAttack("Flammeche","Lance Flame", "Lame Feuille","Surf", 21);
        assertEquals(attack[0].name,"Flammeche");
        assertEquals(attack[1].name,"Lance Flame");
        assertEquals(attack[2].name,"Lame Feuille");
        assertEquals(attack[3].name,"NULL");
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

    // Fonction qui renvoie les faiblesses d'un type
    String[] faiblessesDeType(String[] affiniterDeType){

    if(length(affiniterDeType == 36)){
        String[] faiblesse = new String[10];
        int cpt = 0;
        for(int i = 4;i<14;i++){
            faiblesse[cpt] = affiniterDeType[i];
            cpt++;
        }
        return faiblesse;
    }else{
        String[] faiblesse = new String[5];
        int cpt = 0;
        for(int i = 2;i<7;i++){
            faiblesse[cpt] = affiniterDeType[i];
            cpt++
        }
        return faiblesse;
    }

    // Fonction qui renvoie les ineficasse d'un pokemon
    String[] ineficasseDeType(String[] affiniterDeType){

        if(length(affiniterDeType == 36)){
            String[] ineficasse = new String[4];
            for(int i = 0;i<4;i++){
                ineficasse[i] = affiniterDeType[i];
            }
            return ineficasse;
        }else{
            String[] ineficasse = new String[2];
            for(int i = 0;i<2;i++){
                ineficasse[i] = affiniterDeType[i];
            }
            return ineficasse;
    }

    // Fonction qui renvoie les resistance d'un pokemon
    String[] resistanceDeType(String[] affiniterDeType){

        if(length(affiniterDeType == 36)){
            String[] resistance = new String[22];
            int cpt = 0;
            for(int i = 15;i<36;i++){
                resistance[cpt] = affiniterDeType[i];
                cpt++;
            }
            return resistance;
        }else{
            String[] resistance = new String[11];
            int cpt = 0;
            for(int i = 7;i<19;i++){
                resistance[cpt] = affiniterDeType[i];
                cpt++;
            }
            return resistance;
    }
    

    // Permet de return l'indice d'un type depuis le tableau tabType
    int idxType(String type){
        for(int i=1 ; i<length(tabType,1);i++){
            if(equals(type,tabType[i][0])){
                return i;
            }
        }
    return -1;
    }
    
    // Test de idxType
    void testIdxType(){
        assertEquals(idxType("Feu"), 2);
    }

    // Permet de return l'indice d'une attaque depuis le tableau listAttack
    int idxAttack(String Attack){
        for(int i=1;i<length(listAttack,1);i++){
            if(equals(Attack,listAttack[i][IDX_NAME_ATTACK])){
                return i;
            }
        }
    return -1;
    }

    // Test fonction idxAttack
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

    // Test fonction xpRequis
    void testXpRequis(){
        assertEquals(xpRequis(1),110);
        assertEquals(xpRequis(15),409);
        assertEquals(xpRequis(20),655);
        assertEquals(xpRequis(30),1694);
    }

    // JOUEURS

    Joueurs newJoueurs(){
        Joueurs joueurs = new Joueurs();
        joueurs.name = "temporaire";
        joueurs.genre = 1;
        joueurs.money = 500;
        joueurs.team = new Pokemon[6];
        joueurs.nbPokeball = 0;
        return joueurs;
    }

    void addPokemon(Pokemon pokemon){
        boolean estVide = false;
        int cpt = 0;
        while(estVide == false){
            if(joueurs.team[cpt] == null){
                
            }
            cpt++;
        }
        
    }

    // MENU 


    // MAP 

    //Afficher Map
    void afficherMap(String[][] map){
        for(int i = 0; i<length(map,1);i++){
            for(int i2 = 0; i2<length(map,2);i2++){
                println(map[i][i2]);
            }
        }


    }





    void _algorithm(){
        String[] test2 = new String []{};
        test2 = newaffiniterDeType("Sol","Eau");
        for(int i = 0;i<length(test2);i++){
            println(test2[i]);
        }
        afficherMap(map);
        
    }


}
