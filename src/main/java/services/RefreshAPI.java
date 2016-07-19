package services;
import me.sargunvohra.lib.pokekotlin.client.*;
import me.sargunvohra.lib.pokekotlin.model.*;
import java.sql.Connection;

public class RefreshAPI {

    public static void main(String args[]){

        Connection cnxn;
        cnxn = BuildTable.setConnection();
        BuildTable.createPokemonTable(cnxn);
        BuildTable.createPokedexMappingTable(cnxn);
        BuildTable.createEggGroupMappingTable(cnxn);
        BuildTable.createPokemonLanguageMapping(cnxn);

        getPokemonData(3, cnxn);

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
        int shapeId = poke.getShape().getId();
        int evolveForm;
        try {
            evolveForm = poke.getEvolvesFromSpecies().getId();
        } catch (NullPointerException ne){
            evolveForm = -999;
        }
        int evoChain = poke.getEvolutionChain().getId();
        int genId = poke.getGeneration().getId();
        for(PokemonSpeciesVariety v: poke.getVarieties()){
            System.out.println(v);
        }
        int habitatId;
        try{
            habitatId = poke.getHabitat().getId();
        } catch (NullPointerException ne){
            habitatId = -999;
        }

        BuildTable.insertPokemonData(cnxn, pokeId, pokeOrder, genderRate, captureRate, happy, baby, hatch, genderDiff, formSwitch,
                growthRate, shapeId, evolveForm, evoChain, genId, habitatId);

        for(NamedApiResource e: poke.getEggGroups()){
            BuildTable.insertEggGroupMapping(cnxn, pokeId, e.getId());
        }
        for(PokemonSpeciesDexEntry p: poke.getPokedexNumbers()){
            BuildTable.insertPokedexMapping(cnxn, pokeId, p.getPokedex().getId());
        }
        for(Name i: poke.getNames()){
            BuildTable.insertEggGroupMapping(cnxn, pokeId, i.getLanguage().getId());
        }
    }
}
