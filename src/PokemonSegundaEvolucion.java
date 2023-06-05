/**
 * Clase PokemonSegundaEvolucion, hereda de Pokemon
 */
public class PokemonSegundaEvolucion extends Pokemon{

    private final String primeraEvolucion;
    private final String etapaBasica;

    /**
     * The Constructor
     *
     * @param ID : ID del pokemon
     * @param nombre : Nombre del pokemon
     * @param etapa : Etapa del pokemon
     * @param primeraEvolucion : Primera evolucion del pokemon
     * @param etapaBasica : Etapa basica del pokemon
     * @param tipo1 : Tipo de pokemon
     * @param tipo2 : Tipo de pokemon
     */
    public PokemonSegundaEvolucion(int ID, String nombre, String etapa, String primeraEvolucion, String etapaBasica, String tipo1, String tipo2) {
        super(ID, nombre, etapa, tipo1, tipo2);
        this.etapaBasica = etapaBasica;
        this.primeraEvolucion = primeraEvolucion;
    }

    /**
     *
     * @return primeraEvolucion : Primera evolucion del pokemon
     */
    public String getPrimeraEvolucion() {
        return primeraEvolucion;
    }

    /**
     *
     * @return etapaBasica : Etapa basica del pokemon
     */
    public String getEtapaBasica() {
        return etapaBasica;
    }
}
