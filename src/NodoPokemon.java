/**
 * Clase NodoPokemon
 */
public class NodoPokemon {

    private final Pokemon pokemon;
    private NodoPokemon siguiente;

    /**
     * The Constructor
     *
     * @param pokemon : pokemon a almacenar
     */
    public NodoPokemon(Pokemon pokemon) {
        this.pokemon = pokemon;
        this.siguiente = null;
    }

    /**
     * Getter del pokemon almacenado
     *
     * @return pokemon : el pokemon almacenado en el nodo
     */
    public Pokemon getPokemon() {
        return pokemon;
    }

    /**
     * Getter del Nodo de pokemon siguiente
     *
     * @return siguiente : el nodo de pokemon siguiente al actual
     */
    public NodoPokemon getSiguiente() {
        return siguiente;
    }

    /**
     * Setter del Nodo de pokemon siguiente
     *
     * @param siguiente : Set del siguiente nodo al que apunta el actual
     */
    public void setSiguiente(NodoPokemon siguiente) {
        this.siguiente = siguiente;
    }
}
