/**
 * Interface de Pokedex
 */
public interface Pokedex {

    /**
     * Desplegar pokemons en un rango de numeros, ordenados por ID
     *
     * @param numero1 primer valor del rango
     * @param numero2 segundo valor del rango
     */
    public void desplegarPorRango(int numero1, int numero2);

    /**
     * Desplegar pokemons ordenados por letra
     */
    public void desplegarPokemonPorLetra();

    /**
     * Desplegar pokemons dado un tipo en particular
     *
     * @param tipo de pokemon
     */
    public void desplegarPorTipo(String tipo);

    /**
     * Desplegar pokemons en su primera evolucion
     */
    public void desplegarPrimeraEvolucion();

    /**
     * Desplegar pokemons por ID o nombre del pokemon
     */
    public void busquedaPersonalizada();
}
