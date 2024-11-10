import java.io.*;
import java.util.Random;
import java.util.Scanner;

public class Main{

    public static int nbMotsFichier(File file) throws IOException{
        int nbMots = 0;
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String mots;
        while((mots = br.readLine()) != null){
           if (!mots.isEmpty()) { 
            nbMots++;
           }
        }
        br.close();
        return nbMots;
    }

    public static String[] initialiseTab(File file) throws IOException{
        int nbMots = nbMotsFichier(file);
        String[] tab = new String[nbMots];
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String mot;
        int i = 0;
        while((mot = br.readLine())!=null){
            if(!mot.isEmpty()){
                tab[i++] = mot;
            }
        }
        br.close();
        return tab;
    }

    public static String genererMot(String[]tab){
        Random r = new Random();
        int n = r.nextInt(tab.length); 
        return tab[n];
    }

    public static boolean compareMot(String mot1, String mot2){
        for (int i = 0; i<mot1.length(); i++){
            if (mot1.charAt(i)!=mot2.charAt(i)){
                return false;
            }
        }
        return true;
    }

    public static boolean compareCaractere(int i, String mot1, String mot2){
        return mot1.charAt(i) == mot2.charAt(i);
    }

    public static int rechercheCaractere(int i, String mot1, String mot2){
        for (int indice = 0; indice<mot2.length(); indice++){
            if (mot1.charAt(i)==mot2.charAt(indice)){
                return indice;
            }
        }
        return -1;
    }

    public static void demandeUtilisateur(String[] tab) {
        int nbTentatives = 6;
        String MotADeviner = genererMot(tab).toLowerCase();  

        System.out.println("Mot à deviner : "+ MotADeviner);
        String GREEN = "\u001B[32m";
        String YELLOW = "\u001B[33m";
        String RESET = "\u001B[0m"; 
        String mot = ""; 
        boolean motDevine = false; 
    
        while(nbTentatives > 0 && !motDevine) {
            Scanner scan = new Scanner(System.in);
            System.out.print("Entrez un mot de 5 lettres : ");
            mot = scan.nextLine().toLowerCase();
    
            if (mot.length() != 5) {
                System.out.println("Le mot doit avoir exactement 5 lettres");
                continue;
            }
    
            if (compareMot(MotADeviner, mot)) {
                System.out.println("Bravo vous avez deviné le bon mot ! ");
                System.out.println(GREEN + MotADeviner + RESET);
                motDevine = true;  
            } else {
                for (int i = 0; i < mot.length(); i++) {
                    if (compareCaractere(i, MotADeviner, mot)) {
                        System.out.print(GREEN + mot.charAt(i) + RESET); 
                    } else if (rechercheCaractere(i, mot, MotADeviner) != -1) {
                        System.out.print(YELLOW + mot.charAt(i) + RESET); //si la lettre est présente dans le mot à deviner, mais mal placée
                    } else {
                        System.out.print(mot.charAt(i)); 
                    }
                }
                System.out.println();  
            }
    
            nbTentatives--; 
        }
    
        if (!motDevine) {
            System.out.println("Dommage, vous n'avez pas deviné le mot. Réessayez une prochaine fois ! ");
            System.out.println("Le mot à deviner était : " + MotADeviner);
        }
    }
    

    public static void afficheTab(String[]tab){
        System.out.print("[");
        for (int i = 0; i<tab.length-1; i++){
            System.out.print(tab[i]+", ");
        }
        System.out.println(tab[tab.length-1]+"]");
    }

    public static void intialiseJeu(){
        File file = new File("mots.txt");
        try{
            String[] tab = initialiseTab(file);
            System.out.println("#######################################################################################################");
            System.out.println("Bienvenue dans le jeu bien connu : Wordle"); 
            System.out.println("Vous devez deviner un mot de 5 lettres : pour cela vous avez 5 tentatives");
            System.out.println("NB : si une lettre s'affiche en vert, cela signifie qu'elle est bien placée."); 
            System.out.println("Si une lettre s'affiche en jaune, cela signifie qu'elle est bien dans le mot que vous devez deviner, mais qu'elle est mal placée");
            System.out.println("Si elle reste blanche, cela signifie qu'elle n'est pas présente dans le mot à deviner");
            System.out.println("C'est parti, bonne chance !");
            System.out.println("#######################################################################################################");

            demandeUtilisateur(tab);
        }
        catch(IOException e){
            System.out.println("Erreur de lecture du fichier : "+e.getMessage());
        }


    }

    public static void main (String[]args){
        intialiseJeu();
    }
}
