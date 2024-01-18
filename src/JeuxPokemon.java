import extensions.File;
import extensions.CSVFile;


class JeuxPokemon extends Program{

    //Bordure

    // Creation tab
    final String[][] POKEDEX = toTabCSV(loadCSV("./ressources/Pokemon/pokemon.csv"));
    final String[][] tabType = toTabCSV(loadCSV("./ressources/Pokemon/type.csv"));
    final String[][] listAttack = toTabCSV(loadCSV("./ressources/Pokemon/attack.csv"));
    final String[][] map = toTabCSV(loadCSV("./ressources/map/map3.csv"));
    final String[][] mapSansJ = toTabCSV(loadCSV("./ressources/map/mapSansJ.csv"));

    // Map
    final String BORDURE_HAUT = "╔═════════════════════════════════════════════════════QIKEMON════════════════════════════════════════════════════╗";
    final String BORDURE_BAS = "╚════════════════════════════════════════════════════════╩═══════════════════════════════════════════════════════╝"; 
    final Joueurs joueurs = newJoueurs();

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
        pokemon.lvlEvo = stringToInt(POKEDEX[id][IDX_LVLEVO]);
        pokemon.xp = 0;
        pokemon.xpRequis = xpRequis(lvl);
        pokemon.hp = hpPokemon(stringToInt(POKEDEX[id][IDX_STATPV]),lvl);
        pokemon.hpMax = hpPokemon(stringToInt(POKEDEX[id][IDX_STATPV]),lvl);
        pokemon.statPv = stringToInt(POKEDEX[id][IDX_STATPV]);
        pokemon.statAttack = statAttack(stringToInt(POKEDEX[id][IDX_STATDEGAT]),lvl);
        pokemon.statDefense = statDefense(stringToInt(POKEDEX[id][IDX_STATDEFENSE]),lvl);
        pokemon.statVitesse = stringToInt(POKEDEX[id][IDX_STATVITESSE]);
        pokemon.attacks = newListAttack(POKEDEX[id][IDX_ATTACK1],POKEDEX[id][IDX_ATTACK2],POKEDEX[id][IDX_ATTACK3],POKEDEX[id][IDX_ATTACK4],pokemon.lvl);
        String[] affiniterdetype = newaffiniterDeType(pokemon.type1, pokemon.type2);
        pokemon.faiblesseDeType = faiblessesDeType(affiniterdetype);
        pokemon.ineficasseDeType = ineficasseDeType(affiniterdetype);
        pokemon.resistanceDeType = resistanceDeType(affiniterdetype);

        return pokemon;
    }

    // Creer un pokemon NULL qui sert d'emplacement vide dans l'equipe
    Pokemon newNullPokemon(){
        Pokemon pokemon = new Pokemon();
        pokemon.hp = -1;
        pokemon.hpMax = -1;
        pokemon.lvl = -1;
        pokemon.id = 0;
        pokemon.xp = 0;
        pokemon.xpRequis = 1;
        pokemon.lvlEvo = 1;
        return pokemon;
    }

    // Donne les hp du pokemon selon sa stat et son niveau
    int hpPokemon(int stat, int lvl){
        return (((stat*2)*lvl)/100) + 10 + lvl;
    }

    // Donne la stat attaque selon son niveau
    int statAttack(int stat, int lvl){
        return (((stat*2)*lvl)/100) + 10 + lvl;
    }

    // Donne la defense selon son niveau
    int statDefense(int stat, int lvl){
        return (((stat*2)*lvl)/100) + 10 + lvl;
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

    // Fonction qui permet l'evolution d'un pokemon
    Pokemon evoPokemon(Pokemon pokemon){
        Pokemon pokemonf = newPokemon(pokemon.id+1, pokemon.lvl);
        if(pokemon.lvl == pokemon.lvlEvo){
            println("Félicitations ! Ton" + pokemon.nom + " a évolué en "+ pokemonf.nom );
            delay(3000);
            return pokemonf;
            
        }
        return pokemon;
    }

    // Fonction qui renvoie une attack null contenant aucune info
    Attack attackNULL(){
        Attack attack = new Attack();
        attack.name = "NULL";
        attack.pp = 0;
        attack.ppRemaining = 0;
        attack.stat = 0;
        attack.type = "NULL";
        return attack;
    }

    // Fonction qui renvoie la liste des attaques utilisable par un pokemon
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
        attack = newListAttack("Flammeche","Lance Flamme", "Lame Feuille","Surf", 21);
        assertEquals(attack[0].name,"Flammeche");
        assertEquals(attack[1].name,"Lance Flamme");
        assertEquals(attack[2].name,"Lame Feuille");
        assertEquals(attack[3].name,"NULL");
    }

    // permet la création du tableau contenant les affiniterDeType du pokemon celon son ou ses types
    String[] newaffiniterDeType( String type1, String type2){

            String[] affiniterDeType = new String[18];
            for(int i =0;i<18;i++ ){
                affiniterDeType[i] = tabType[idxType(type1)][i+1] ;
            }
            return affiniterDeType;
        
    }

    // Test pour verifier la fonction newaffiniterDeType
    void testNewaffiniterDeType(){
        String[] test1 = new String []{"NULL", "NULL", "Eau", "Sol", "Roche", "NULL", "NULL", "Feu", "Plante", "Glace", "Insecte", "Acier", "NULL", "NULL", "NULL", "NULL", "NULL", "NULL"};
        assertArrayEquals(test1,newaffiniterDeType("Feu","NULL"));

    }

    // Fonction qui renvoie les faiblesses d'un type
    String[] faiblessesDeType(String[] affiniterDeType){

    if(length(affiniterDeType) == 36){
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
            cpt++;
        }
        return faiblesse;
    }
    }

    // Fonction qui renvoie les inefficace d'un Type d'un pokemon
    String[] ineficasseDeType(String[] affiniterDeType){

        if(length(affiniterDeType) == 36){
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
    }

    // Fonction qui renvoie les resistances d'un pokemon
    String[] resistanceDeType(String[] affiniterDeType){

        if(length(affiniterDeType )== 36){
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
            for(int i = 7;i<=17;i++){
                resistance[cpt] = affiniterDeType[i-1];
                cpt++;
            }
            return resistance;
        }
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

    // initialisation du joueur
    Joueurs newJoueurs(){
        Joueurs joueurs = new Joueurs();
        joueurs.name = "temporaire";
        joueurs.genre = 1;
        joueurs.money = 500;
        joueurs.team = new Pokemon[6];
        joueurs.nbPokeball = 0;
        return joueurs;
    }

    //COMBAT

    // Verifie si les pokemons du joueur ont encore de la vie 
    boolean joueursPokemonValide(Joueurs joueurs){
        boolean enVie = false;
        for(int i = 0 ; i<6; i++){
            if(joueurs.team[i].hp > 0){
                enVie = true;
            }
        }
        return enVie;
    }

    // Verifie si les pokemons adverses ont encore de la vie
    boolean adversairePokemonValide(Pokemon[] pokemons){
        boolean enVie = false;
        for(int i = 0 ; i<length(pokemons); i++){
            if(pokemons[i].hp > 0){
                enVie = true;
            }
        }
        return enVie;
    }

    // Permet d'afficher les attaques
    void printCombat(Pokemon jPokemon, Pokemon aPokemon){
        
        println("Votre Pokemon : " + jPokemon.nom + " " + jPokemon.hp + "/" + hpPokemon(jPokemon.statPv, jPokemon.lvl) );
        println("Pokemon adverse : " + aPokemon.nom + " " + aPokemon.hp + "/" + hpPokemon(aPokemon.statPv, aPokemon.lvl) );
        println("Attaque : 1-" + jPokemon.attacks[0].name +" 2-" + jPokemon.attacks[1].name +" 3-" + jPokemon.attacks[2].name +" 4-"+ jPokemon.attacks[3].name );
    }

    // Fonction pour l'utilisation des attaques
    int useAttack(Pokemon jPokemon, int idAttack, Pokemon aPokemon, boolean player){
        int bonus = 1;
        if(player == true){
            int nb1 = (int) (random() *100);
            int nb2 = (int) (random() *100);
            boolean saisieValide = true;
            println(nb1 + " * " + nb2);
            String saisie = "0";
            do{
            saisie = readString();
            saisieValide = true;
                for(int i = 0 ; i<length(saisie);i++){
                    if(charAt(saisie, i) < '0' || charAt(saisie, i) > '9'){
                        saisieValide = false;
                    }
                }
           }
           while(saisieValide == false);
           int reponse = stringToInt(saisie);
           if(nb1*nb2 == reponse){
            println("Bien joué !");
            bonus = 3;
           }else{
            print("Mauvaise réponse.");
           }
           delay(1000);
           }
           
           
                   
        return ((((((jPokemon.lvl *2 % 5) + 2) * jPokemon.attacks[idAttack-1].stat * jPokemon.statAttack /50) / aPokemon.statDefense) *2)*bonus)+1 ;
    }

    // Fonction de lancement de combat
    void startCombat(Joueurs joueurs, Pokemon[] adversaire){
        int tour = 0;
        int cptJ =0;
        int cptA =0;
        Pokemon jPokemon = joueurs.team[cptJ];
        Pokemon aPokemon = adversaire[cptA];
        while(joueursPokemonValide(joueurs) && adversairePokemonValide(adversaire)){
            tour = tour +1;
            printCombat(jPokemon,aPokemon);
            int jattack = useAttack(jPokemon,verifSaisieAttack(jPokemon,readString()), aPokemon,true);
            int aattack = useAttack(aPokemon,1, jPokemon,false);
            aPokemon.hp = aPokemon.hp - jattack;
            jPokemon.hp = jPokemon.hp - aattack;
            clearScreen();
            println(jPokemon.nom + " a fait " + jattack + " dégâts, Mais " + aattack + " lui ont été infligés par l'ennemi." );
            if(jPokemon.hp <= 0 && cptJ < length(joueurs.team)-1 && joueursPokemonValide(joueurs)){
                println(jPokemon.nom + " est tombé ko");
                cptJ++;
                jPokemon = joueurs.team[cptJ];
                println(jPokemon.nom + " va être envoyé");
            }
            if(aPokemon.hp <= 0){

                if(cptA < length(adversaire)-1){
                    cptA++;
                    aPokemon = adversaire[cptA];
                    println(aPokemon.nom + " va être envoyé");
                }
                println(aPokemon.nom + " est tombé ko");
                jPokemon = gainXp(jPokemon, aPokemon.lvl);
                
            }
        }
        if(joueursPokemonValide(joueurs) == false){
            println("Tu as malheureusement perdu ce combat, tous tes Pokémon sont tombés KO. Retourne chez toi pour soigner tes Pokémon.");
        }
        else{
            println("Bravo, tu as gagné le combat.");
        }

        for(int i = 0; i<6;i++){
            if(joueurs.team[i].xp >= joueurs.team[i].xpRequis){
                joueurs.team[i] = newPokemon(joueurs.team[i].id, joueurs.team[i].lvl+1);
                println("Bravo, ton Pokémon est maintenant de niveau " + joueurs.team[i].lvl);
                delay(2000);
                joueurs.team[i] = evoPokemon(joueurs.team[i]);
            }
        }
    

        delay(4000);
    }

    // Fonction d'ajout de l'xp gagner à un pokemon
    Pokemon gainXp(Pokemon pokemon,int lvl){
        pokemon.xp = pokemon.xp + (lvl*100);
        println("Ton Pokémon a gagné " + (lvl*100) + "points d'xp.");
        delay(1000);
        return pokemon;
    }

    // Fonction de vérification du choix d'attaque
    int verifSaisieAttack(Pokemon pokemon, String saisie){
        boolean saisieValide = false;
        int saisieInt = 0;
        while(saisieValide == false){
            if(length(saisie) != 1){
                println("Saisie invalide.");
                saisie = readString();
            } 
            else if(charAt(saisie,0) >= '5' || charAt(saisie,0) <='0' ){
                println("Saisie invalide.");
                saisie = readString();
            }else if(pokemon.lvl <= 9 && (charAt(saisie,0) == '2' || charAt(saisie,0) == '3' ||  charAt(saisie,0) == '4')){
                println("Tu ne possèdes pas cette attaque.");
                saisie = readString();
            }else if(pokemon.lvl <= 19 && (charAt(saisie,0) == '3' ||  charAt(saisie,0) == '4')){
                println("Tu ne possèdes pas cette attaque.");
                saisie = readString();
            }else if(pokemon.lvl <= 29 && charAt(saisie,0) == '4'){
                println("Tu ne possèdes pas cette attaque.");
                saisie = readString();
            }else{
                saisieInt = stringToInt(saisie);
                saisieValide = true ; 
            }
        } 
        return saisieInt;
    }

    // Permet de lancer un combat si le joueur est sur de l'herbe
    void estSurHerbe(String[][] map){
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
        Pokemon[] adversaire = new Pokemon[1];
        adversaire[0] = newPokemon(16,5);
        if(equals(mapSansJ[ligne][colonne],"F") ){
            if( (int)(random()*10) == 0){
                if( joueursPokemonValide(joueurs) == true){
                    startCombat(joueurs, adversaire);
                }
                
            }
        }
        if(adversaire[0].hp <=0){
            lancerCapture(adversaire[0]);
        }
    }

    // Permet de capturer un pokemon à la fin d'un combat
    void lancerCapture(Pokemon pokemon){
        println("Voulez-vous capturer ce Pokemon");
        println("Oui ou Non");
        String r= "";
        int i2 = 0;
        boolean saisie = false;
        do{
            r = readString();
            r = toUpperCase(r);
            if(equals(r,"OUI") ){
                saisie = true;
            }
            if(equals(r,"NON") ){
                saisie = true;
            }
        }while(!saisie);
        if(equals(r,"OUI")){
            for(int i = 0 ; i<6;i++){
                if(joueurs.team[i].id == 0){
                    joueurs.team[i] = pokemon;
                    i = 10;
                    i2=i;
                }
            }
            if(i2 != 10){
                println("Vous n'avez plus de place dans votre équipe.");
            }
        }
        
    }

    // Permet de lancer le combat d'arene
    String combatArene(String[][] map){
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
        Pokemon[] adversaire = new Pokemon[3];
        adversaire[0] = newPokemon(95,15);
        adversaire[1] = newPokemon(20,15);
        adversaire[2] = newPokemon(18,15);
        if(ligne == 7 && colonne == 46){
            startCombat(joueurs, adversaire);
        }
        if(adversaire[2].hp <=0){
            fin();
            delay(6000);
            return "e";
        }
        return "";
    }

    // MAP

    // Permet d'afficher la map
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
                    if(joueurs.genre == 1){
                        print("👨");
                    }else{
                        print("👩");
                    }
                    
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

    // Permet de verifier les déplacement
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

    // Déplace le joueur sur la map
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

    // Heal les pokemon si le joueur se trouve devant sa maison
    void healPokemon(String[][] map){
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
        if(ligne ==9 && colonne ==7 ){
            for(int i = 0; i<6; i++){
                joueurs.team[i].hp = joueurs.team[i].hpMax ;
            }
            println("Tes Pokémon ont été soignés.");
            delay(2000);
        }
    }

    // Permet d'affficher le Menu
    void menu(){
        clearScreen();
        String r;
        int idx = 0;
        clearScreen();
        File nouvellePartie = newFile("./ressources/menu/menuNouvellePartie.txt");
        File continuer = newFile("./ressources/menu/menuContinuer.txt");
        while(ready(nouvellePartie)) println(readLine(nouvellePartie));
        idx = 2;
        print("Choix : ");
        r = readString();
        if(equals("",r)){
            idx = 2;
        }
        while(!equals("",r)){
            
            if(equals("s",r)){
                clearScreen();
                File continuer2 = newFile("./ressources/menu/menuContinuer.txt");
                while(ready(continuer2)) println(readLine(continuer2));
                idx = 1;
            }
            if(equals("z",r)){
                clearScreen();
                File nouvellePartie2 = newFile("./ressources/menu/menuNouvellePartie.txt");
                while(ready(nouvellePartie2)) println(readLine(nouvellePartie2));
                idx = 2;
            }
            if(!equals("z",r) || !equals("s",r)){
                clearScreen();
                if(idx == 2){
                    File nouvellePartie3 = newFile("./ressources/menu/menuNouvellePartie.txt");
                    while(ready(nouvellePartie3)) println(readLine(nouvellePartie3));
                }else{
                    File continuer3 = newFile("./ressources/menu/menuContinuer.txt");
                    while(ready(continuer3)) println(readLine(continuer3));
                    idx = 1;
                }
            }
            print("Choix : ");
            r = readString();
            
        }
        if(idx ==1){
            load();
        }
        if(idx == 2){
            idx =4;
            String choix;
            clearScreen();
            File choisirGenreHomme = newFile("./ressources/menu/nouvellePartieHomme.txt");
            while(ready(choisirGenreHomme)) println(readLine(choisirGenreHomme));
            joueurs.genre = 1;
            print("Choix : ");
            choix = readString();
            while(!equals("",choix)){
                if(equals("z",choix)){
                    clearScreen();
                    File choisirGenreHomme2 = newFile("./ressources/menu/nouvellePartieHomme.txt");
                    while(ready(choisirGenreHomme2)) println(readLine(choisirGenreHomme2));
                    joueurs.genre = 1;
                }
                if(equals("s",choix)){
                    clearScreen();
                    File choisirGenreFemme2 = newFile("./ressources/menu/nouvellePartieFemme.txt");
                    while(ready(choisirGenreFemme2)) println(readLine(choisirGenreFemme2));
                    joueurs.genre = 2;

                }
                if(!equals("z",r) || !equals("s",r)){
                clearScreen();
                if(joueurs.genre == 1){
                    File choisirGenreHomme3 = newFile("./ressources/menu/nouvellePartieHomme.txt");
                    while(ready(choisirGenreHomme3)) println(readLine(choisirGenreHomme3));
                }else{
                    File choisirGenreFemme3 = newFile("./ressources/menu/nouvellePartieFemme.txt");
                    while(ready(choisirGenreFemme3)) println(readLine(choisirGenreFemme3));
                }
            }
                print("Choix : ");
                choix = readString();
                clearScreen();   
            }
            if(idx==4){
                intro();
            }
        }  
        
    }

    // Permet de creer la team apres avoir choisi le pokemon
    void creerTeam(int id){
        joueurs.team[0] = newPokemon(id,5);
        for(int i = 1; i<6;i++){
            joueurs.team[i] = newNullPokemon();
        }
    }

    // Affiche l'introduction et le choix du premier pokemon
    void intro(){
        String r;
        boolean choixValide = false;
        do{ 
            clearScreen();
            File choixPokemon1 = newFile("./ressources/menu/choixPokemon1.txt");
            while(ready(choixPokemon1)) println(readLine(choixPokemon1));
            print("Choix : ");
            r = readString();
            if(equals(r,"1")){
                choixValide = true;
                creerTeam(4);
                
            }
            if(equals(r,"2")){
                choixValide = true;
                creerTeam(7);
            }
            if(equals(r,"3")){
                choixValide = true;
                creerTeam(1);
            }
        }while(!choixValide);
    }

    // Lance la fonction de fin du jeu
     void fin(){
        File fin = newFile("./ressources/menu/menuFin.txt");
        while(ready(fin)) println(readLine(fin));
    }

    // Transforme un int en String
    String intToString(int i){
        String chaine = "";
        chaine+= i;
        return chaine;
    }

    // Permet la sauvegarde du jeu
    void saveGame(){
        String[][] content = new String[6][3];
        String directory = "./ressources/save/";
        for(int i = 0; i<length(joueurs.team);i++){
            content[i][0]= intToString(joueurs.team[i].id);
            content[i][1]= intToString(joueurs.team[i].lvl);
        }
        content[0][2] = intToString(joueurs.genre);
        saveCSV(content, directory + "save.csv");
    }

    // Permet de charger la partie sauvegarder
    void load(){
        CSVFile currentFile = loadCSV("./ressources/save/save.csv");
        for(int i = 0; i<rowCount(currentFile); i++){
            if(equals(getCell(currentFile, i, 0),"0")){
                joueurs.team[i] = newNullPokemon();
            }
            if(!equals(getCell(currentFile, i, 0),"0")){
                joueurs.team[i] = newPokemon(stringToInt(getCell(currentFile, i ,0)), stringToInt(getCell(currentFile, i, 1))); 
            }
            joueurs.genre = stringToInt(getCell(currentFile,0,2));
        }
    }
    // Algorithm
    void algorithm(){
        String r ;
            menu();
            do{
                afficherMap(map);
                println("Appuyer sur [z] pour monter || Appuyer sur [s] pour descendre || Appuyer sur [q] pour aller à gauche || Appouyer sur [d] pour aller à droite");
                println("Appuyer sur [e] pour sauvegarder et quitter le jeu ");
                print("Faites votre choix de déplacement : ");
                r = combatArene(map);
                r = readString();
                deplacerJoueur(map,r);
                estSurHerbe(map);
                healPokemon(map);    
            }while(!equals("e",r));
            clearScreen();
            saveGame();
            println("Partie sauvegarder !");
        }
    }
