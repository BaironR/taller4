/**
 * Clase PokemonBasico, hereda de Pokemon
 */
public class PokemonBasico extends Pokemon{

    private String evolucion1;
    private String evolucion2;

    /**
     * The Constructor : Primer constructor de PokemonBasico
     *
     * @param ID : ID del pokemon
     * @param nombre : Nombre del pokemon
     * @param etapa : Etapa del pokemon
     * @param tipo1 : Tipo de pokemon
     * @param tipo2 : Tipo de pokemon
     */
    public PokemonBasico(int ID, String nombre, String etapa, String tipo1, String tipo2) {
        super(ID, nombre, etapa, tipo1, tipo2);
    }

    /**
     * The Constructor : Segundo constructor de PokemonBasico
     *
     * @param ID : ID del pokemon
     * @param nombre : Nombre del pokemon
     * @param etapa : Etapa del pokemon
     * @param evolucion1 : Primera evolucion del pokemon
     * @param tipo1 : Tipo de pokemon
     * @param tipo2 : Tipo de pokemon
     */
    public PokemonBasico(int ID, String nombre, String etapa, String evolucion1, String tipo1, String tipo2) {
        super(ID, nombre, etapa, tipo1, tipo2);
        this.evolucion1 = evolucion1;
    }

    /**
     * The Constructor : Tercer constructor de PokemonBasico
     *
     * @param ID : ID del pokemon
     * @param nombre : Nombre del pokemon
     * @param etapa : Etapa del pokemon
     * @param evolucion1 : Primera evolucion del pokemon
     * @param evolucion2 : Segunda evolucion del pokemon
     * @param tipo1 : Tipo de pokemon
     * @param tipo2 : Tipo de pokemon
     */
    public PokemonBasico(int ID, String nombre, String etapa, String evolucion1, String evolucion2, String tipo1, String tipo2) {
        super(ID, nombre, etapa, tipo1, tipo2);
        this.evolucion1 = evolucion1;
        this.evolucion2 = evolucion2;
    }

    /**
     *
     * @return evolucion1 : Primera evolucion del pokemon (si es que la posee)
     */
    public String getEvolucion1() {
        return evolucion1;
    }

    /**
     *
     * @return evolucion2 : Segunda evolucion del pokemon (si es que la posee)
     */
    public String getEvolucion2() {
        return evolucion2;
    }
}
