import com.berry.BCrypt;

import java.sql.*;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final Connection connexion = connexionDb();

    public static void main(String[] args) {
        System.out.println("Bienvenue dans l'application centrale dédiée aux administrateurs.");
        System.out.println();

        /*String pseudo, mdp;
        System.out.println("Quel est ton mot de passe?");
        String sel = BCrypt.gensalt();
        mdp = BCrypt.hashpw(scanner.next(), sel);*/

        int choix;
        do{
            System.out.println("Que voulez vous faire?");
            System.out.println();

            System.out.println("1 -> Ajouter une ue");
            System.out.println("2 -> Ajouter un prerequis à une ue existante");
            System.out.println("3 -> Ajouter un étudiant");
            System.out.println("4 -> Encoder une ue validée pour un étudiant");
            System.out.println("5 -> Visualiser tous les étudiants d'un bloc particulier");
            System.out.println("6 -> Visualiser tous les étudiants");
            System.out.println("7 -> Visualiser tous les étudiants qui n'ont pas encore validé leur PAE");
            System.out.println("8 -> Visualiser les UEs d'un bloc en particulier");

            choix = scanner.nextInt();
            switch (choix) {
                case 1:
                    ajouterUe();
                    break;
                case 2:
                    ajouterPrerequis();
                    break;
                case 3:
                    ajouterEtudiant();
                    break;
                case 4:
                    encoderUeValidee();
                    break;
                case 5:
                    visualiserTousLesEtudiantDUnBloc();
                    break;
                case 6:
                    visualiserTout();
                    break;
                case 7:
                    visualiserEtudiantPAENonValide();
                    break;
                case 8:
                    visualiserUEDUnBloc();
                    break;
                default:
                    System.out.println("Fin du programme. Bonne journée.");
                    System.out.println();
                    break;
            }
        } while (1 <= choix && choix <= 8);
    }

    public static void ajouterUe(){
        System.out.println("Quel est le code de l'ue?");
        String codeUe = scanner.next();

        System.out.println("Quel est le nom de l'ue?");
        String nomUe = scanner.next();

        System.out.println("Quel est le bloc de l'ue?");
        int bloc = scanner.nextInt();

        System.out.println("Quel est le nombre de crédits pour cette ue?");
        int nombreDeCredits = scanner.nextInt();

        try{
            PreparedStatement s = (PreparedStatement) connexion.createStatement();
            s.executeUpdate();

        } catch (SQLException e){
            System.out.println("Erreur lors de l'insertion!");
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static void ajouterPrerequis(){
        //TODO
    }

    public static void ajouterEtudiant(){
        //TODO
    }

    public static void encoderUeValidee(){
        //TODO
    }

    public static void visualiserTousLesEtudiantDUnBloc(){
        int bloc = -1;
        while (bloc < 0 || bloc > 3) {
            System.out.println("De quel bloc voulez vous voir les étudiant?");
            System.out.println("0 -> Bloc indéterminé");
            System.out.println("1 -> Bloc 1");
            System.out.println("2 -> Bloc 2");
            System.out.println("3 -> Bloc 3");
            bloc = scanner.nextInt();
        }
        String query;
        try {
            if(bloc == 0)
                query = """
                        SELECT nom, prenom, nombre_de_credits_valides FROM project_sql.etudiants WHERE "bloc" IS NULL
                        """;
            else
                query = "SELECT nom, prenom, nombre_de_credits_valides FROM project_sql.etudiants WHERE \"bloc\" ="+bloc+"";
            Statement statement = connexion.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                String nom = resultSet.getString(1);
                String prenom = resultSet.getString(2);
                String nombreDeCredits = resultSet.getString(3);
                System.out.println(nom + ", " + prenom + ", " + nombreDeCredits);
            }
        } catch (SQLException e) {
            System.out.println("Problème lors de la demande à la base de données");
            e.printStackTrace();
        }
    }

    public static void visualiserTout(){
        //TODO � voir si le nom doit changer ou pas (de la fonction)
    }

    public static void visualiserEtudiantPAENonValide(){
        //TODO
    }

    public static void visualiserUEDUnBloc(){
        //TODO
    }

    private static Connection connexionDb(){
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e){
            System.out.println("Driver PostgeSQL manquant!");
            System.exit(1);
        }

        String url = "jdbc:postgresql://127.0.0.1:5432/postgres";
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, "postgres", "06192000");
        } catch (SQLException e){
            System.out.println("Impossible de joindre le server !");
            System.exit(1);
        }
        return connection;
    }
}
