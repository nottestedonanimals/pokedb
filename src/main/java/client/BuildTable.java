package client;
import java.sql.*;
import java.util.List;

public class BuildTable {

    public static void main(String[] args){

    }

    static Connection setConnection(){

        Connection cnxn = null;

        try {
            Class.forName("org.sqlite.JDBC");
            cnxn = DriverManager.getConnection("jdbc:sqlite:src\\main\\resources\\test.db");
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Opened database successfully");

        return cnxn;
    }

    static void createPokemonTable(Connection cnxn) {

        try{

            cnxn.setAutoCommit(false);
            Statement stmt;
            stmt = cnxn.createStatement();

            stmt.executeUpdate("DROP TABLE IF EXISTS Pokemon");

            String sql = "CREATE TABLE Pokemon (Id INT PRIMARY KEY NOT NULL, " +
                    "OrderCol INT NOT NULL, " +
                    "GenderRate INT NOT NULL, " +
                    "CaptureRate INT NOT NULL, " +
                    "BaseHappiness INT NOT NULL, " +
                    "IsBaby INT NOT NULL," +
                    "HatchCounter INT NOT NULL, " +
                    "GenderDifferences INT NOT NULL, " +
                    "Forms INT NOT NULL, " +
                    "GrowthRateId INT NOT NULL, " +
                    "ShapeId INT NOT NULL, " +
                    "PreEvolutionId INT, " +
                    "EvolutionChainId INT, " +
                    "GenerationId INT, " +
                    "HabitatId INT)";

            stmt.executeUpdate(sql);

            cnxn.commit();
            stmt.close();

        } catch (Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);

        }
    }

    static void createPokedexMappingTable(Connection cnxn){

        Statement stmt;
        try{

            cnxn.setAutoCommit(false);
            stmt = cnxn.createStatement();

            stmt.executeUpdate("DROP TABLE IF EXISTS PokedexEntryMapping");
            cnxn.commit();

            String createTable = "CREATE TABLE PokedexEntryMapping (" +
                    "PokemonId INT NOT NULL, " +
                    "PokedexEntryId INT PRIMARY KEY)";

            stmt.executeUpdate(createTable);

            cnxn.commit();

            stmt.close();

        } catch (Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

    }

    static void createEggGroupMappingTable(Connection cnxn){

        Statement stmt;
        try{
            cnxn.setAutoCommit(false);
            stmt = cnxn.createStatement();

            stmt.executeUpdate("DROP TABLE IF EXISTS EggGroupMapping");
            cnxn.commit();

            String createStatement = "CREATE TABLE EggGroupMapping (" +
                    "PokemonId INT NOT NULL, " +
                    "EggGroupId INT PRIMARY KEY)";

            stmt.executeUpdate(createStatement);
        }catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    static void insertPokemonData(Connection cnxn, int id, int orderCol, int genderRate, int captureRate,
                                  int happy, boolean isBaby, int hatch, boolean genderDiff,
                                  boolean forms, int growthRate, int shapeId,
                                  int preEvoId, int evoChainId, int genId, int habitatId){


        try{

            cnxn.setAutoCommit(false);

            PreparedStatement prep;
            String sql = "INSERT INTO Pokemon (Id, OrderCol, GenderRate, CaptureRate, BaseHappiness, IsBaby, " +
                    "HatchCounter, GenderDifferences, Forms, GrowthRateId, ShapeId, " +
                    "PreEvolutionId, EvolutionChainId, GenerationId, HabitatId) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            prep = cnxn.prepareStatement(sql);

            prep.setInt(1, id);
            prep.setInt(2, orderCol);
            prep.setInt(3, genderRate);
            prep.setInt(4, captureRate);
            prep.setInt(5, happy);
            prep.setBoolean(6, isBaby);
            prep.setInt(7, hatch);
            prep.setBoolean(8, genderDiff);
            prep.setBoolean(9, forms);
            prep.setInt(10, growthRate);
            prep.setInt(11, shapeId);
            prep.setInt(12, preEvoId);
            prep.setInt(13, evoChainId);
            prep.setInt(14, genId);
            prep.setInt(15, habitatId);
            prep.addBatch();
            prep.executeBatch();
            cnxn.commit();
            prep.close();

        } catch (Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);

        }
    }

    static void insertPokedexMapping(Connection cnxn, int pokemonId, int pokedexEntryId){

        PreparedStatement prep;
        try {
            cnxn.setAutoCommit(false);

            String insertQuery = "INSERT INTO PokedexEntryMapping (PokemonId, PokedexEntryId) " +
                    "VALUES (?, ?)";
            prep = cnxn.prepareStatement(insertQuery);

            prep.setInt(1, pokemonId);
            prep.setInt(2, pokedexEntryId);
            prep.addBatch();
            prep.executeBatch();

            cnxn.commit();

            prep.close();

        } catch (Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    static void insertEggGroupMapping(Connection cnxn, int pokemonId, int eggGroupId){

        PreparedStatement prep;
        try{
            cnxn.setAutoCommit(false);

            String insertQuery = "INSERT INTO EggGroupMapping (PokemonId, EggGroupId) " +
                    "VALUES (?, ?)";
            prep = cnxn.prepareStatement(insertQuery);

            prep.setInt(1, pokemonId);
            prep.setInt(2, eggGroupId);

            prep.addBatch();
            prep.executeBatch();

            cnxn.commit();

            prep.close();

        } catch (Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }
}
