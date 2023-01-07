/**************************************************/
/*                                                */
/*         #####     ##      ####     ##          */
/*        #         # #     #   ##   # #          */
/*        #        #  #     #  # #  #  #          */
/*         #####      #     # #  #     #          */
/*              #     #     # #  #     #          */
/*              #     #     ##   #     #          */
/*         #####      #  #   ####      #          */
/*                                                */
/*  By Tanguy PICUIRA, Raphaêl BAR, Emilien FIEU  */
/**************************************************/

import java.util.Scanner;


public class Main {
    /**
     * C’est ici que tout commence
     * ×
     */
    public static void main(String[] args) {
        //Inintialisation de variables
        int nbPortes;
        int nbCompetiteurs;
        int[] temps = new int[50];
        int[][] podium = {new int[2], new int[2], new int[2]};

        //Saisie et traitement des données
        nbPortes = saisieNBPortes();
        nbCompetiteurs = saisieNBCompe();
        saisieResultats(temps, nbCompetiteurs, nbPortes);
        calculPodium(temps, podium, nbCompetiteurs);


        //Affichage des résultats
        affichageResultats(temps, nbCompetiteurs);
        affichagePodium(podium, nbCompetiteurs);
    }


    /**
     * @author Raphael Bar
     * Saisit un caractère (y ou n) et retourne un booléen correspondant à la valeur saisie
     * @return true ou false
     */
    public static boolean tempsEstValide(){
        Scanner clavier = new Scanner(System.in) ;
        char caractere = 'A';

        while(caractere != 'y' && caractere != 'n'){
            System.out.println("Le temps est-il valide ? y/n ?");
            caractere = clavier.next().charAt(0);
        }
        if (caractere == 'y'){
            return true;
        }

        return false;
    }


    /**
     * @author Raphael Bar
     * calcule et retourne le temps compensé à partir du temps, du nombre de portes touchées et du nombre de portes ratées donnés.
     * @param pfTemps IN temps du joueur, supérieur ou égal à 0
     * @param pfNbPortesTouchees IN nombre de portes touchées par le joueur, supérieur ou égal à 0
     * @param pfNbPortesRatees IN nombre de portes ratées par le joueur, supérieur ou égal à 0
     * @return temps compensé calculé
     */
    public static int calculTempsCompense (int pfTemps, int pfNbPortesTouchees, int pfNbPortesRatees){
        return pfTemps + (2 * 1000 * pfNbPortesTouchees) + (50 * 1000 * pfNbPortesRatees);
    }


    /**
     * Calcule le podium c'est-à-dire les trois premiers
     * qui ont fait le plus petit temps en deux manches
     * @author Tanguy Picuira
     *
     * @param pfTempsCompense IN Tableau d'entiers à une
     * entrée des temps des compétiteurs
     * @param pfBrassardTemps IN OUT Tableau d'entiers à
     * deux entrées du numéro de brassard et de son temps
     * @param pfNbCompetiteur IN Nombre de compétiteurs
     * dans la course
     *
     */
    public static void calculPodium(int[] pfTempsCompense, int[][] pfBrassardTemps, int pfNbCompetiteur) {

        //Initialisation des variables
        int min1 = 1000000000; //ici on initialise à une valeur inatteignable
        int min2 = 1000000000;
        int min3 = 1000000000;
        int indice1 = 0;
        int indice2 = 0;
        int indice3 = 0;

        //Calcul du podium
        if (pfNbCompetiteur==0) {
            System.out.println("Il n'y a aucun compétiteur dans la course.");
        }
        else {
            for (int i = 0; i<pfNbCompetiteur; i++) {
                if (pfTempsCompense[i]<=min1 && pfTempsCompense[i]>0) {
                    indice3 = indice2;
                    indice2 = indice1;
                    indice1 = i;
                    min3 = min2;
                    min2 = min1;
                    min1 = pfTempsCompense[i];
                }
                else if (pfTempsCompense[i]<=min2 && pfTempsCompense[i]>0) {
                    indice3 = indice2;
                    indice2 = i;
                    min3 = min2;
                    min2 = pfTempsCompense[i];
                }
                else if (pfTempsCompense[i]<=min3 && pfTempsCompense[i]>0) {
                    indice3 = i;
                    min3 = pfTempsCompense[i];
                }
            }
            if (pfNbCompetiteur>=3) {
                pfBrassardTemps[0][0] = indice1+1;
                pfBrassardTemps[1][0] = indice2+1;
                pfBrassardTemps[2][0] = indice3+1;
                pfBrassardTemps[0][1] = min1;
                pfBrassardTemps[1][1] = min2;
                pfBrassardTemps[2][1] = min3;
            }
            else if (pfNbCompetiteur==2) {
                pfBrassardTemps[0][0] = indice1+1;
                pfBrassardTemps[1][0] = indice2+1;
                pfBrassardTemps[0][1] = min1;
                pfBrassardTemps[1][1] = min2;
            }
            else if (pfNbCompetiteur==1) {
                pfBrassardTemps[0][0] = indice1+1;
                pfBrassardTemps[0][1] = min1;
            }
        }
    }

    /**
     * Affiche le podium
     * @author Tanguy Picuira
     * @param pfBrassardTemps IN Tableau d'entiers à deux entrées
     */
    public static void affichagePodium(int[][] pfBrassardTemps, int pfNbCompetiteur) {

        //Affichage du podium
        if (pfNbCompetiteur>=3) {
            if (pfBrassardTemps[0][1] == pfBrassardTemps[1][1]) { //test si il y a une égalité entre le premier et le deuxième temps et si oui, ils ont tous les deux la médaille d'or
                System.out.println("La médaille d'or revient aux numéros: " + pfBrassardTemps[0][0] + " et " + pfBrassardTemps[1][0] + " avec un temps de " + pfBrassardTemps[0][1] + "millisecondes");
                System.out.println("La médaille de bronze revient au numéro: " + pfBrassardTemps[2][0] + " avec un temps de " + pfBrassardTemps[2][1] + " millisecondes");
            }
            else if (pfBrassardTemps[0][1] == pfBrassardTemps[2][1]) { //test s'il y a une égalité entre le premier, deuxième et troisème temps et si oui, ils ont tous la médaille d'or
                System.out.println("La médaille d'or revient aux numéros: " + pfBrassardTemps[0][0] + ", " + pfBrassardTemps[1][0] + " et " + pfBrassardTemps[2][0] + "avec un temps de " + pfBrassardTemps[0][1] + " millisecondes");
            }
            else if (pfBrassardTemps[1][1] == pfBrassardTemps[2][1]) { //test s'il y a une égalité entre le deuxième et troisième temps et si oui, ils ont tous les deux la médaille d'argent
                System.out.println("La médaille d'or revient au numéro: " + pfBrassardTemps[0][0] + " avec un temps de " + pfBrassardTemps[0][1] + " millisecondes");
                System.out.println("La médaille de'argent revient aux numéros: " + pfBrassardTemps[1][0] + " et " + pfBrassardTemps[2][0] + " avec un temps de " + pfBrassardTemps[1][1] + " millisecondes");
            } else {
                System.out.println("La médaille d'or revient au numéro: " + pfBrassardTemps[0][0] + " avec un temps de " + pfBrassardTemps[0][1] + " millisecondes");
                System.out.println("La médaille d'argent revient au numéro: " + pfBrassardTemps[1][0] + " avec un temps de " + pfBrassardTemps[1][1] + " millisecondes");
                System.out.println("La médaille de bronze revient au numéro: " + pfBrassardTemps[2][0] + " avec un temps de " + pfBrassardTemps[2][1] + " millisecondes");
            }
        }
        else if (pfNbCompetiteur==2) {
            if (pfBrassardTemps[0][1] == pfBrassardTemps[1][1]) {
                System.out.println("Les premiers de la compétition sont les numéros: " + pfBrassardTemps[0][0] + " et " + pfBrassardTemps[1][0] + " avec un temps de " + pfBrassardTemps[0][1] + " millisecondes");
            } else {
                System.out.println("La médaille d'or revient au numéro: " + pfBrassardTemps[0][0] + " avec un temps de " + pfBrassardTemps[0][1] + " millisecondes");
                System.out.println("La médaille d'argent revient au numéro: " + pfBrassardTemps[1][0] + " avec un temps de " + pfBrassardTemps[1][1] + " millisecondes");
            }
        }
        else if (pfNbCompetiteur==1) {
            System.out.println("La médaille d'or revient au numéro: " + pfBrassardTemps[0][0] + " avec un temps de " + pfBrassardTemps[0][1] + " millisecondes");
        }
        else if (pfNbCompetiteur==0) {
            System.out.println("Il n'y a aucun compétiteur dans la course.");
        }
    }

    /**
     * @author Raphael Bar
     * Saisit et retourne un entier appartenant à un ensemble dont le max et le min sont donnés
     * @param pfMin IN borne minimale
     * @param pfMax IN borne maximale, strictement supérieur à la borne minimale
     * @return un entier appartenant à l'intervalle donnée
     */
    public static int saisieMinMax(int pfMin, int pfMax){
        Scanner clavier = new Scanner(System.in);
        int entierBorne = pfMin - 1;
        while (entierBorne > pfMax || entierBorne < pfMin){
            System.out.println("Entrez un nombre compris entre " + pfMin + " et " + pfMax + " exclus :");
            entierBorne = clavier.nextInt();
        }
        return entierBorne;
    }

    /**
     * @author Raphael Bar
     * Saisit et retourne un entier supérieur à une borne donnée
     * @param pfMin IN borne minimale
     * @return un entier supérieur ou égal à la borne donnée
     */
    public static int saisieMin(int pfMin){
        Scanner clavier = new Scanner(System.in) ;
        int entierBorne = pfMin - 1;
        while (entierBorne < pfMin){
            System.out.println("Entrez un entier supérieur à " + pfMin + " :");
            entierBorne = clavier.nextInt();
        }
        return entierBorne;
    }

    /**
     * Affichage des resultats.
     *
     * @param pfResultats    the pf resultats
     * @param pfnbConcurents the pfnb concurents
     * @author Emilien FIEU
     */
    public static void affichageResultats(int[] pfResultats, int pfnbConcurents){
        String texte ="Résultats : \n";

        for (int i = 0; i < pfnbConcurents; i++) {
            if (pfResultats[i] != -1) {
                texte += "Brassard " + (i + 1) + " : " + pfResultats[i] + "\n";
            }
        }

        System.out.println(texte);
    }

    /**
     * Saisie des résultats.
     *
     * @param pfTabTemps     OUT Le tableau qui va recevoir tout les temps compensé
     * @param pfnbConcurents IN Le nombre de compétiteurs (doit etres supérieur a 0)
     * @param pfNbPortes     IN Le nombre de portes (doit etre compris entre 18 et 22)
     * @author Emilien FIEU
     */
    public static void  saisieResultats(int[] pfTabTemps, int pfnbConcurents, int pfNbPortes){
        int temps; //variable pour stocker temporairement le temps saisi
        int touche;
        int ratee;

        for (int i = 0; i < pfnbConcurents; i++) {
            System.out.println("Concurent n° "+(i+1)+ " : manche 1 :");
            if (tempsEstValide()){
                System.out.println("Temps du concurent");
                temps = saisieMin(0);
                System.out.println("Nombre de portes qu'il a touché :");
                touche = saisieMinMax(0, pfNbPortes);
                System.out.println("Nombre de ports qu'il a raté : ");
                ratee = saisieMinMax(0, pfNbPortes - touche);
                pfTabTemps[i] = calculTempsCompense(temps, touche, ratee);
            }else {
                pfTabTemps[i] = -1;
            }
        }

        for (int i = 0; i < pfnbConcurents; i++) {
            if (pfTabTemps[i] != -1) {
                System.out.println("Concurent n° "+(i+1)+ " : manche 2 :");
                if (tempsEstValide()) {
                    System.out.println("Temps du concurent");
                    temps = saisieMin(0);
                    System.out.println("Nombre de portes qu'il a touché :");
                    touche = saisieMinMax(0, pfNbPortes);
                    System.out.println("Nombre de ports qu'il a raté : ");
                    ratee = saisieMinMax(0, pfNbPortes - touche);
                    pfTabTemps[i] += calculTempsCompense(temps, touche, ratee);
                }else {
                    pfTabTemps[i] = -1;
                }
            }
        }
    }

    /**
     * Saisie du nombre de compétititeurs
     *
     * @return le nombre de compétiteurs
     * @author Emilien FIEU
     */
    public static int saisieNBCompe(){
        System.out.println("Nombre de compétiteurs :");
        return saisieMinMax(1, 49);
    }

    /**
     * Saisie du nombre de portes.
     *
     * @return le nombre de portes.
     * @author Emilien FIEU
     */
    public static int saisieNBPortes(){
        System.out.println("Nombre de portes :");
        int var = saisieMinMax(18, 22);
        return var;
    }
}