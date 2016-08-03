package services;
import java.sql.*;

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

        return cnxn;
    }

    static void createPokemonTable(Connection cnxn) {

        try{

            cnxn.setAutoCommit(false);
            Statement stmt;
            stmt = cnxn.createStatement();

            stmt.executeUpdate("DROP TABLE IF EXISTS Pokemon");

            String createQuery = "CREATE TABLE Pokemon (Id INT PRIMARY KEY NOT NULL, " +
                    "Name TEXT NOT NULL, " +
                    "BaseExp INT NOT NULL, " +
                    "Height INT NOT NULL, " +
                    "DefaultSpecies INT NOT NULL, " +
                    "PokeOrder INT NOT NULL, " +
                    "Weight INT NOT NULL, " +
                    "GenderRate INT NOT NULL, " +
                    "CaptureRate INT NOT NULL, " +
                    "BaseHappiness INT NOT NULL, " +
                    "IsBaby INT NOT NULL," +
                    "HatchCounter INT NOT NULL, " +
                    "GenderDifferences INT NOT NULL, " +
                    "FormSwitchable INT NOT NULL, " +
                    "GrowthRateId INT NOT NULL, " +
                    "ShapeId INT NOT NULL, " +
                    "PreEvolutionId INT, " +
                    "EvolutionChainId INT, " +
                    "GenerationId INT, " +
                    "HabitatId INT)";

            stmt.executeUpdate(createQuery);

            cnxn.commit();
            stmt.close();

        } catch (Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);

        }
    }

    static void createPokedexMappingTable(Connection cnxn){
        // TODO: Add foreign key constraints to ids when tables are built

        Statement stmt;
        try{

            cnxn.setAutoCommit(false);
            stmt = cnxn.createStatement();

            stmt.executeUpdate("DROP TABLE IF EXISTS PokedexEntryMapping");
            cnxn.commit();

            String createQuery = "CREATE TABLE PokedexEntryMapping (" +
                    "PokemonId INT NOT NULL, " +
                    "PokedexEntryId INT," +
                    "PRIMARY KEY(PokemonId, PokedexEntryId)," +
                    "FOREIGN KEY(pokemonId) REFERENCES Pokemon(Id))";

            stmt.executeUpdate(createQuery);

            cnxn.commit();

            stmt.close();

        } catch (Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

    }

    static void createEggGroupMappingTable(Connection cnxn){
        // TODO: Add foreign key constraints to ids when tables are built

        Statement stmt;
        try{
            cnxn.setAutoCommit(false);
            stmt = cnxn.createStatement();

            stmt.executeUpdate("DROP TABLE IF EXISTS EggGroupMapping");
            cnxn.commit();

            String createQuery = "CREATE TABLE EggGroupMapping (" +
                    "PokemonId INT NOT NULL, " +
                    "EggGroupId INT," +
                    "PRIMARY KEY(PokemonId, EggGroupId)," +
                    "FOREIGN KEY(pokemonId) REFERENCES Pokemon(Id))";

            stmt.executeUpdate(createQuery);
        }catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    static void createPokemonLanguageTable(Connection cnxn){

        Statement stmt;
        try{
            cnxn.setAutoCommit(false);
            stmt = cnxn.createStatement();

            stmt.executeUpdate("DROP TABLE IF EXISTS PokemonLanguage");
            cnxn.commit();

            String createQuery = "CREATE TABLE PokemonLanguage (" +
                    "pokemonId INT NOT NULL," +
                    "languageId INT NOT NULL, " +
                    "languageCode TEXT NOT NULL, " +
                    "Name TEXT NOT NULL, " +
                    "PRIMARY KEY(PokemonId, LanguageId)," +
                    "FOREIGN KEY(pokemonId) REFERENCES Pokemon(Id))";

            stmt.executeUpdate(createQuery);
            cnxn.commit();

            stmt.close();

        }catch (Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    static void createPokemonVarietyTable(Connection cnxn){
        Statement stmt;
        try{
            cnxn.setAutoCommit(false);
            stmt = cnxn.createStatement();

            stmt.executeUpdate("DROP TABLE IF EXISTS PokemonVarietyMapping");
            cnxn.commit();

            String createQuery = "CREATE TABLE PokemonVarietyMapping (" +
                    "pokemonId INT NOT NULL, " +
                    "VarietyId INT NOT NULL, " +
                    "VarietyName TEXT NOT NULL, " +
                    "PRIMARY KEY (pokemonId, VarietyId), " +
                    "FOREIGN KEY(pokemonId) REFERENCES Pokemon(Id))";

            stmt.executeUpdate(createQuery);
            cnxn.commit();

            stmt.close();

        }catch(Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    static void createPokemonAbilitiesMapping(Connection cnxn){
        Statement stmt;
        try{
            cnxn.setAutoCommit(false);
            stmt = cnxn.createStatement();

            stmt.executeUpdate("DROP TABLE IF EXISTS PokemonAbilityMapping");
            cnxn.commit();

            String createQuery = "CREATE TABLE PokemonAbilityMapping (" +
                    "pokemonId INT NOT NULL, " +
                    "abilityId INT NOT NULL, " +
                    "PRIMARY KEY (pokemonId, abilityId), " +
                    "FOREIGN KEY(pokemonId) REFERENCES Pokemon(Id))";

            stmt.executeUpdate(createQuery);
            cnxn.commit();
        }catch(Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    static void createPokemonFormsMapping(Connection cnxn){
        Statement stmt;
        try{
            cnxn.setAutoCommit(false);
            stmt = cnxn.createStatement();

            stmt.executeUpdate("DROP TABLE IF EXISTS PokemonFormsMapping");
            cnxn.commit();

            String createQuery = "CREATE TABLE PokemonFormsMapping (" +
                    "pokemonId INT NOT NULL, " +
                    "formId INT NOT NULL, " +
                    "PRIMARY KEY (pokemonId, formId)," +
                    "FOREIGN KEY(pokemonId) REFERENCES Pokemon(Id))";

            stmt.executeUpdate(createQuery);
            cnxn.commit();
        }catch(Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    static void createPokemonVersionGameIndexTable(Connection cnxn){
        Statement stmt;
        try{
            cnxn.setAutoCommit(false);
            stmt = cnxn.createStatement();

            stmt.executeUpdate("DROP TABLE IF EXISTS PokemonVersionGameIndex");
            cnxn.commit();

            String createQuery = "CREATE TABLE PokemonVersionGameIndex (" +
                    "pokemonId INT NOT NULL, " +
                    "gameIndex INT NOT NULL, " +
                    "versionName TEXT NOT NULL, " +
                    "versionId INT NOT NULL, " +
                    "PRIMARY KEY (pokemonId, gameIndex, versionId), " +
                    "FOREIGN KEY(pokemonId) REFERENCES Pokemon(Id))";

            stmt.executeUpdate(createQuery);
            cnxn.commit();

        }catch(Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    static void insertPokemonData(Connection cnxn, int id, String name, int baseExp, int height, boolean dfSpecies,
                                  int order, int weight, int genderRate, int captureRate, int happy, boolean isBaby, int hatch,
                                  boolean genderDiff, boolean forms, int growthRate, int shapeId,
                                  int preEvoId, int evoChainId, int genId, int habitatId){

        try{

            cnxn.setAutoCommit(false);

            PreparedStatement prep;
            String sql = "INSERT INTO Pokemon (Id, Name, BaseExp, Height, DefaultSpecies, Weight, PokeOrder, GenderRate," +
                    "CaptureRate, BaseHappiness, IsBaby, HatchCounter, GenderDifferences, FormSwitchable, " +
                    "GrowthRateId, ShapeId, PreEvolutionId, EvolutionChainId, GenerationId, HabitatId) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            prep = cnxn.prepareStatement(sql);

            prep.setInt(1, id);
            prep.setString(2, name);
            prep.setInt(3, baseExp);
            prep.setInt(4, height);
            prep.setBoolean(5, dfSpecies);
            prep.setInt(6, order);
            prep.setInt(7, weight);
            prep.setInt(8, genderRate);
            prep.setInt(9, captureRate);
            prep.setInt(10, happy);
            prep.setBoolean(11, isBaby);
            prep.setInt(12, hatch);
            prep.setBoolean(13, genderDiff);
            prep.setBoolean(14, forms);
            prep.setInt(15, growthRate);
            prep.setInt(16, shapeId);
            prep.setInt(17, preEvoId);
            prep.setInt(18, evoChainId);
            prep.setInt(19, genId);
            prep.setInt(20, habitatId);
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

    static void insertPokemonLanguageTable(Connection cnxn, int pokeId, int languageId, String language, String name){

        PreparedStatement prep;
        try{
            cnxn.setAutoCommit(false);
            String insertQuery = "INSERT INTO PokemonLanguage (pokemonId, languageId, languageCode, name) " +
                    "VALUES (?, ?, ?, ?)";
            prep = cnxn.prepareStatement(insertQuery);

            prep.setInt(1, pokeId);
            prep.setInt(2, languageId);
            prep.setString(3, language);
            prep.setString(4, name);

            prep.addBatch();
            prep.executeBatch();
            cnxn.commit();

            prep.close();

        }catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    static void insertPokemonVarietyMapping(Connection cnxn, int pokeId, int varietyId, String varietyName){

        PreparedStatement prep;
        try{
            cnxn.setAutoCommit(false);
            String insertQuery = "INSERT INTO PokemonVarietyMapping (pokemonId, VarietyId, VarietyName) " +
                    "VALUES (?, ?, ?)";

            prep = cnxn.prepareStatement(insertQuery);

            prep.setInt(1, pokeId);
            prep.setInt(2, varietyId);
            prep.setString(3, varietyName);

            prep.addBatch();
            prep.executeBatch();

            cnxn.commit();

            prep.close();

        }catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    static void insertPokemonAbilityMapping(Connection cnxn, int pokeId, int abilityId){

        PreparedStatement prep;
        try{
            cnxn.setAutoCommit(false);
            String insertQuery = "INSERT INTO PokemonAbilityMapping (pokemonId, abilityId) " +
                    "VALUES (?, ?)";

            prep = cnxn.prepareStatement(insertQuery);

            prep.setInt(1, pokeId);
            prep.setInt(2, abilityId);

            prep.addBatch();
            prep.executeBatch();

            cnxn.commit();
            prep.close();

        }catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    static void insertPokemonFormsMapping(Connection cnxn, int pokemonId, int formId){
        PreparedStatement prep;
        try{
            cnxn.setAutoCommit(false);
            String insertQuery = "INSERT INTO PokemonFormsMapping (pokemonId, formId) " +
                    "VALUES (?, ?)";

            prep = cnxn.prepareStatement(insertQuery);

            prep.setInt(1, pokemonId);
            prep.setInt(2, formId);

            prep.addBatch();
            prep.executeBatch();

            cnxn.commit();
            prep.close();

        }catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    static void insertPokemonVersionGameIndexTable(Connection cnxn, int pokeId, int gameIndex, String versionName,
                                                   int versionId){
        PreparedStatement prep;
        try{
            cnxn.setAutoCommit(false);
            String insertQuery = "INSERT INTO PokemonVersionGameIndex (pokemonId, gameIndex, versionName, versionId) " +
                    "VALUES (?, ?, ?, ?)";

            prep = cnxn.prepareStatement(insertQuery);

            prep.setInt(1, pokeId);
            prep.setInt(2, gameIndex);
            prep.setString(3, versionName);
            prep.setInt(4, versionId);

            prep.addBatch();
            prep.executeBatch();

            cnxn.commit();
            prep.close();

        }catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }
}
