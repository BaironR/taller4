import java.util.Comparator;

/**
 * Tipo de comparador por ID, ordena de manera decreciente
 */
public class PokemonComparatorIDDecreciente implements Comparator<Pokemon> {

    /**
     *
     * @param o1 the first object to be compared.
     * @param o2 the second object to be compared.
     * @return a negative integer, zero, or a positive integer as the
     *      * first argument is less than, equal to, or greater than the
     *      * second.
     */
    @Override
    public int compare(Pokemon o1, Pokemon o2) {
        return o2.getID() - o1.getID();
    }
}
