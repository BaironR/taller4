
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Lista de nodos de pokemon
 */
public class ListaNexoSimplePokemon {

    private NodoPokemon cabeza;

    /**
     * The Constructor
     */
    public ListaNexoSimplePokemon(){
        this.cabeza = null;
    }

    /**
     * Agrega el pokemon a la lista de nodos
     *
     * @param pokemon : pokemon a agregar
     */
    public void agregar(Pokemon pokemon){

        NodoPokemon pokemonNuevo = new NodoPokemon(pokemon);

        if (this.cabeza == null){

            this.cabeza = pokemonNuevo;
            return;
        }

        NodoPokemon aux = this.cabeza;

        while (aux.getSiguiente() != null){

            aux = aux.getSiguiente();
        }

        aux.setSiguiente(pokemonNuevo);
    }

    /**
     * Transforma la lista de nodos a ArrayList
     *
     * @return lista : tipo ArrayList
     */
    public ArrayList<Pokemon> toArray(){

        if (this.cabeza == null){
            return null;
        }

        NodoPokemon aux = this.cabeza;
        ArrayList<Pokemon> lista = new ArrayList<>(151);

        while (aux != null){

            lista.add(aux.getPokemon());
            aux = aux.getSiguiente();
        }
        return lista;
    }

    /**
     * Transforma la lista de nodos a LinkedList
     *
     * @return lista : tipo LinkedList
     */
    public LinkedList<Pokemon> toLinked(){

        if (this.cabeza == null){
            return null;
        }

        NodoPokemon aux = this.cabeza;
        LinkedList<Pokemon> lista = new LinkedList<>();

        while (aux != null){

            lista.add(aux.getPokemon());
            aux = aux.getSiguiente();
        }
        return lista;
    }
}
