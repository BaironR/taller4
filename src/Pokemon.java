/**
 * Clase abstracta Pokemon
 */
public abstract class Pokemon {

    private final int ID;
    private final String nombre;
    private final String etapa;
    private final String tipo1;
    private final String tipo2;

    /**
     * Variables generales de los pokemon
     *
     * @param ID : ID del pokemon
     * @param nombre : Nombre del pokemon
     * @param etapa: Etapa del pokemon
     * @param tipo1: Tipo de pokemon
     * @param tipo2: Tipo de pokemon
     */
    public Pokemon(int ID, String nombre, String etapa, String tipo1, String tipo2) {
        this.ID = ID;
        this.nombre = nombre;
        this.etapa = etapa;
        this.tipo1 = tipo1;
        this.tipo2 = tipo2;
    }

    /**
     *
     * @return ID : ID del pokemon
     */
    public int getID() {
        return ID;
    }

    /**
     *
     * @return nombre : nombre del pokemon
     */
    public String getNombre() {
        return nombre;
    }

    /**
     *
     * @return etapa : etapa del pokemon
     */
    public String getEtapa() {
        return etapa;
    }

    /**
     *
     * @return tipo1 : tipo de pokemon
     */
    public String getTipo1() {
        return tipo1;
    }

    /**
     *
     * @return tipo2 : tipo de pokemon
     */
    public String getTipo2() {
        return tipo2;
    }
}
