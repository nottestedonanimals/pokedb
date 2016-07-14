package client;
import me.sargunvohra.lib.pokekotlin.client.*;
import me.sargunvohra.lib.pokekotlin.model.*;
import java.sql.Connection;
import java.util.List;
import java.util.ArrayList;

public class RefreshAPI {

    public static void main(String args[]){

        Connection cnxn;
        cnxn = BuildTable.setConnection();
        BuildTable.createPokemonTable(cnxn);
        BuildTable.createPokedexMappingTable(cnxn);
        BuildTable.createEggGroupMappingTable(cnxn);

        getPokemonData(1, cnxn);

        try{
            cnxn.close();
        } catch (Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }

    }


    private static void getPokemonData(int pokemonId, Connection cnxn){

        PokeApi pokeApi = new PokeApiClient();
        PokemonSpecies poke = pokeApi.getPokemonSpecies(pokemonId);

        int pokeId = poke.getId();
        int pokeOrder = poke.getOrder();
        int genderRate = poke.getGenderRate();
        int captureRate = poke.getCaptureRate();
        int happy = poke.getBaseHappiness();
        boolean baby = poke.isBaby();
        int hatch = poke.getHatchCounter();
        boolean genderDiff = poke.getHasGenderDifferences();
        boolean formSwitch = poke.getFormsSwitchable();
        int growthRate = poke.getGrowthRate().getId();
        for(PokemonSpeciesDexEntry p: poke.getPokedexNumbers()){
            BuildTable.insertPokedexMapping(cnxn, pokeId, p.getPokedex().getId());
        }
        for(NamedApiResource e: poke.getEggGroups()){
            BuildTable.insertEggGroupMapping(cnxn, pokeId, e.getId());
        }
        int shapeId = poke.getShape().getId();
        int evolveForm;
        try {
            evolveForm = poke.getEvolvesFromSpecies().getId();
        } catch (NullPointerException ne){
            evolveForm = -999;
        }
        int evoChain = poke.getEvolutionChain().getId();
        for(Name i: poke.getNames()){

            i.getLanguage().getId();

        }
        int genId = poke.getGeneration().getId();
//        System.out.println(poke.getVarieties());
        int habitatId;
        try{
            habitatId = poke.getHabitat().getId();
        } catch (NullPointerException ne){
            habitatId = -999;
        }

        BuildTable.insertPokemonData(cnxn, pokeId, pokeOrder, genderRate, captureRate, happy, baby, hatch, genderDiff, formSwitch,
                growthRate, shapeId, evolveForm, evoChain, genId, habitatId);
    }
}
