/**
 * Clase PokemonPrimeraEvolucion, hereda de Pokemon
 */
public class PokemonPrimeraEvolucion extends Pokemon{

    private final String etapaBasica;
    private String segundaEvolucion;

    /**
     * The Constructor : Primer constructor de PokemonPrimeraEvolucion
     *
     * @param ID : ID del pokemon
     * @param nombre : Nombre del pokemon
     * @param etapa : Etapa del pokemon
     * @param etapaBasica : Etapa basica del pokemon
     * @param tipo1 : Tipo de pokemon
     * @param tipo2 : Tipo de pokemon
     */
    public PokemonPrimeraEvolucion(int ID, String nombre, String etapa, String etapaBasica, String tipo1, String tipo2) {
        super(ID, nombre, etapa, tipo1, tipo2);
        this.etapaBasica = etapaBasica;
    }

    /**
     * The Constructor : Segundo constructor de PokemonPrimeraEvolucion
     *
     * @param ID : ID del pokemon
     * @param nombre : Nombre del pokemon
     * @param etapa : Etapa del pokemon
     * @param segundaEvolucion : Segunda evolucion del pokemon
     * @param etapaBasica : Etapa basica del pokemon
     * @param tipo1 : Tipo de pokemon
     * @param tipo2 : Tipo de pokemon
     */
    public PokemonPrimeraEvolucion(int ID, String nombre, String etapa, String segundaEvolucion, String etapaBasica, String tipo1, String tipo2) {
        super(ID, nombre, etapa, tipo1, tipo2);
        this.etapaBasica = etapaBasica;
        this.segundaEvolucion = segundaEvolucion;
    }

    /**
     *
     * @return etapaBasica : Etapa basica del pokemon
     */
    public String getEtapaBasica() {
        return etapaBasica;
    }

    /**
     *
     * @return segundaEvolucion : Segunda evolucion del pokemon (si es que la posee)
     */
    public String getSegundaEvolucion() {
        return segundaEvolucion;
    }
}
