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
        BuildTable.createPokemonLanguageTable(cnxn);
        BuildTable.createPokemonVarietyTable(cnxn);
        BuildTable.createPokemonAbilitiesMapping(cnxn);
        BuildTable.createPokemonFormsMapping(cnxn);
        BuildTable.createPokemonVersionGameIndexTable(cnxn);

        getPokemonData(1, cnxn);
//        getMoveData(1, cnxn);

        try{
            cnxn.close();
        } catch (Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }

    }


    private static void getPokemonData(int pokemonId, Connection cnxn) {
        PokeApi pokeApi = new PokeApiClient();
        Pokemon poke = pokeApi.getPokemon(pokemonId);
        PokemonSpecies pokeSpec = pokeApi.getPokemonSpecies(pokemonId);

        int pokeId = poke.getId();
        String pokeName = poke.getName();
        int baseExp = poke.getBaseExperience();
        int pokeHeight = poke.getHeight();
        boolean defaultSpecies = poke.isDefault();
        int pokeOrder = poke.getOrder();
        int pokeWeight = poke.getWeight();
        int genderRate = pokeSpec.getGenderRate();
        int captureRate = pokeSpec.getCaptureRate();
        int happy = pokeSpec.getBaseHappiness();
        boolean baby = pokeSpec.isBaby();
        int hatch = pokeSpec.getHatchCounter();
        boolean genderDiff = pokeSpec.getHasGenderDifferences();
        boolean formSwitch = pokeSpec.getFormsSwitchable();
        int growthRate = pokeSpec.getGrowthRate().getId();
        int shapeId = pokeSpec.getShape().getId();
        int evolveForm;
        try {
            evolveForm = pokeSpec.getEvolvesFromSpecies().getId();
        } catch (NullPointerException ne) {
            evolveForm = -999;
        }
        int evoChain = pokeSpec.getEvolutionChain().getId();
        int genId = pokeSpec.getGeneration().getId();
        int habitatId;
        try {
            habitatId = pokeSpec.getHabitat().getId();
        } catch (NullPointerException ne) {
            habitatId = -999;
        }

        BuildTable.insertPokemonData(cnxn, pokeId, pokeName, baseExp, pokeHeight, defaultSpecies, pokeOrder,
                pokeWeight, genderRate, captureRate, happy, baby, hatch, genderDiff, formSwitch, growthRate, shapeId,
                evolveForm, evoChain, genId, habitatId);


        //USING CALL TO CENTRAL TABLE DATA ALLOWS US TO MAKE THESE CALLS IN ONE FUNCTION - DOES NOT BELONG IN CENTRAL TABLE
        //MAPS TO CENTRAL TABLE
        //TODO: SHOULD CONVERT SOME OF THSE MAPPING TABLES TO LOOKUP TABLES AFTER I DECIDE HOW TO MERGE. THESE ARE GOOD PLACEHOLDERS THAT WILL ALLOW ME TO MATCH INFO
        for (PokemonAbility a : poke.getAbilities()) {

            BuildTable.insertPokemonAbilityMapping(cnxn, pokeId, a.getAbility().getId());
        }

        for (NamedApiResource f : poke.getForms()) {

            BuildTable.insertPokemonFormsMapping(cnxn, pokeId, f.getId());
        }

        for (VersionGameIndex g : poke.getGameIndices()) {
            BuildTable.insertPokemonVersionGameIndexTable(cnxn, pokeId, g.getGameIndex(), g.getVersion().getName(),
                    g.getVersion().getId());
        }
        for (NamedApiResource e : pokeSpec.getEggGroups()) {
            BuildTable.insertEggGroupMapping(cnxn, pokeId, e.getId());
        }
        for (PokemonSpeciesDexEntry p : pokeSpec.getPokedexNumbers()) {
            BuildTable.insertPokedexMapping(cnxn, pokeId, p.getPokedex().getId());
        }
        for (Name i : pokeSpec.getNames()) {
            BuildTable.insertPokemonLanguageTable(cnxn, pokeId, i.getLanguage().getId(), i.getLanguage().getName(), i.getName());
        }
        for (PokemonSpeciesVariety v : pokeSpec.getVarieties()) {
            BuildTable.insertPokemonVarietyMapping(cnxn, pokeId, v.getPokemon().getId(), v.getPokemon().getName());
        }


        for (PokemonHeldItem h : poke.getHeldItems()) {
            System.out.println(h);

            System.out.println(pokeId);
            System.out.println(h.getItem().getId());
        }
    }

    private static void getMoveData(int pokemonId, Connection cnxn){

        PokeApi pokeApi = new PokeApiClient();
    }
}
