import ucn.ArchivoEntrada;
import ucn.Registro;
import ucn.StdIn;
import ucn.StdOut;
import java.io.IOException;
import java.util.*;

/**
 * Sistema del programa, implementa a Pokedex
 */
public class PokedexImpl implements Pokedex{
    private final ListaNexoSimplePokemon listaPokemon;

    /**
     * The Constructor
     */
    public PokedexImpl() {
        this.listaPokemon = new ListaNexoSimplePokemon();
    }

    /**
     * inicio del programa
     */
    public void iniciar(){

        try{ // Intenta la lectura del archivop´+
            lecturaDeArchivo();

        } catch (IOException e){ // Si no existe el archivo, se cierra el programa, ya que no habran pokemons en el sistema
            StdOut.println("No existe el archivo kanto.txt, agreguelo manualmente.");
            return;
        }

        StdOut.println("""  
                **************************** Menu ****************************
                
                 [1] Desplegar pokemons dado un rango
                 [2] Desplegar todos los pokemons ordenados alfabeticamente
                 [3] Desplegar pokemons dado un tipo en particular
                 [4] Desplegar pokemons de primera evolucion
                 [5] Busqueda personalizada por nombre o id
                 [6] Salir del programa
                 """);
        String opcion = StdIn.readString();

        // Si la opcion es 6 finaliza el programa
        if (opcion.equalsIgnoreCase("6")){
            StdOut.println("Programa finalizado, muchas gracias");
        }

        while (!opcion.equalsIgnoreCase("6")){

            switch (opcion) {
                case "1" -> {
                    StdOut.println("Ingrese el primer valor del rango");
                    String primerValorString = StdIn.readString();
                    int primerValor = validarEntero(primerValorString);
                    if (primerValor >= 0) { // El primer valor es valido para el rango
                        StdOut.println("Ingrese el segundo valor del rango");
                        String segundoValorString = StdIn.readString();
                        int segundoValor = validarEntero(segundoValorString);

                        if (segundoValor >= 0) { // El segundo valor es valido para el rango

                            if (primerValor >= segundoValor) { // El primer valor es el mayor, o es igual
                                desplegarPorRango(segundoValor, primerValor);

                            } else { // El segundo valor es el mayor
                                desplegarPorRango(primerValor, segundoValor);
                            }
                        } else { // El segundo valor no es valido para el rango
                            StdOut.println("Error!!: los numeros deben ser enteros y mayores a 0");
                        }
                    } else { // El primer valor no es valido para el rango
                        StdOut.println("Error!!: los numeros deben ser enteros y mayores a 0");
                    }
                }
                case "2" -> desplegarPokemonPorLetra();
                case "3" -> {
                    StdOut.println("Ingrese el tipo de pokemon que desea imprimir");
                    String tipo = StdIn.readString();
                    if (tipo != null) {
                        desplegarPorTipo(tipo);

                    } else {
                        StdOut.println("Error!!: debe ingresar un tipo");
                    }
                }
                case "4" -> desplegarPrimeraEvolucion();
                case "5" -> busquedaPersonalizada();
                default -> StdOut.println("Error!!: no existe dicha opcion, ingrese nuevamente");
            }

            StdOut.println("""
                
                **************************** Menu ****************************
                
                 [1] Desplegar pokemons dado un rango
                 [2] Desplegar todos los pokemons ordenados alfabeticamente
                 [3] Desplegar pokemons dado un tipo en particular
                 [4] Desplegar pokemons de primera evolucion
                 [5] Busqueda personalizada por nombre o id
                 [6] Salir del programa
                 """);
            opcion = StdIn.readString();

            if (opcion.equalsIgnoreCase("6")){

                StdOut.println("Programa finalizado, muchas gracias");
            }
        }
    }

    /**
     * Desplegar pokemons en un rango de numeros, ordenados por ID
     *
     * @param menor : valor menor del rango
     * @param mayor : valor mayor del rango
     */
    @Override
    public void desplegarPorRango(int menor, int mayor) {

        ArrayList<Pokemon> arrayList = listaPokemon.toArray();

        if (arrayList != null){

            if (menor >= arrayList.size()){
                return;
            }

            arrayList = (ArrayList<Pokemon>) sortIDCreciente(arrayList);

            ListIterator<Pokemon> listIterator;

            if (menor == 0){
                listIterator = arrayList.listIterator(menor);

            } else {
                listIterator = arrayList.listIterator(menor - 1);
            }

            desplegarPokemonPorRango(listIterator,menor,mayor);
        } else {
            StdOut.println("Error!!: no se ingreso ningun pokemon al sistema");
        }
    }

    /**
     * Desplegar pokemons ordenados por letra
     */
    @Override
    public void desplegarPokemonPorLetra() {

        LinkedList<Pokemon> linkedList = listaPokemon.toLinked();

        if (linkedList != null){
            linkedList = (LinkedList<Pokemon>) sortOrdenAlfabetico(linkedList);
            ListIterator<Pokemon> listIterator = linkedList.listIterator();
            StdOut.println("********************** Pokemons **********************\n");

            while (listIterator.hasNext()){
                Pokemon pokemon = listIterator.next();
                desplegarPokemon(pokemon);
            }
        } else {
            StdOut.println("Error!!: no se ingreso ningun pokemon al sistema");
        }
    }

    /**
     * Desplegar pokemons dado un tipo en particular
     *
     * @param tipo : tipo de pokemon
     */
    @Override
    public void desplegarPorTipo(String tipo) {

        LinkedList<Pokemon> linkedList = listaPokemon.toLinked();

        if (linkedList != null) {

            linkedList = (LinkedList<Pokemon>) sortIDCreciente(linkedList);

            for (Pokemon pokemon : linkedList) {
                if (pokemon.getTipo1().equalsIgnoreCase(tipo) || pokemon.getTipo2().equalsIgnoreCase(tipo)) {
                    desplegarPokemon(pokemon);
                }
            }
        } else {
            StdOut.println("Error!!: no se ingreso ningun pokemon al sistema");
        }
    }

    public List<Pokemon> sortIDCreciente(List<Pokemon> lista){

        for(int i = 0; i<(lista.size()-1); i++){

            for (int j = (i+1); j<lista.size(); j++){

                if (lista.get(i).getID() > lista.get(j).getID()){

                    Pokemon aux = lista.get(j);
                    lista.set(j,lista.get(i));
                    lista.set(i,aux);
                }
            }
        }

        return lista;
    }

    public List<Pokemon> sortIDDecreciente(List<Pokemon> lista){

        for(int i = 0; i<(lista.size()-1); i++){

            for (int j = (i+1); j<lista.size(); j++){

                if (lista.get(i).getID() < lista.get(j).getID()){

                    Pokemon aux = lista.get(j);
                    lista.set(j,lista.get(i));
                    lista.set(i,aux);
                }
            }
        }

        return lista;
    }

    public List<Pokemon> sortOrdenAlfabetico(List<Pokemon> lista) {

        for (int i = 0; i < (lista.size() - 1); i++) {

            for (int j = (i + 1); j < lista.size(); j++) {

                if (lista.get(i).getNombre().compareTo(lista.get(j).getNombre()) > 0) {

                    Pokemon aux = lista.get(j);
                    lista.set(j, lista.get(i));
                    lista.set(i, aux);
                }
            }
        }

        return lista;
    }


        /**
         * Desplegar pokemons en su primera evolucion
         */
    @Override
    public void desplegarPrimeraEvolucion() {

        ArrayList<Pokemon> arrayList = listaPokemon.toArray();

        if (arrayList != null) {
            arrayList = (ArrayList<Pokemon>) sortIDDecreciente(arrayList);

            for (Pokemon pokemon : arrayList) {
                if (pokemon.getEtapa().equalsIgnoreCase("Primera Evolucion")) {
                    desplegarPokemon(pokemon);
                }
            }
        } else {
            StdOut.println("Error!!: no se ingreso ningun pokemon al sistema");
        }
    }

    /**
     * Desplegar pokemons por ID o nombre del pokemon
     */
    @Override
    public void busquedaPersonalizada() {

        StdOut.println("""
                            
                            Ingrese por que opcion desea realizar la busqueda
                            [1] Nombre
                            [2] ID
                            [3] Volver al menu principal
                            """);
        String opcionBusqueda = StdIn.readString();

        while (!opcionBusqueda.equalsIgnoreCase("3")){

            switch (opcionBusqueda) {
                case "1" -> { // Busqueda por nombre
                    StdOut.println("Ingrese el nombre que desea buscar");
                    String nombreABuscar = StdIn.readString();
                    if (nombreABuscar != null) { // El usuario escribio un nombre a buscar
                        desplegarPokemonPorNombre(nombreABuscar);

                    } else { // El usuario no escribio nada
                        StdOut.println("Error!!: debe ingresar un nombre");
                    }
                }
                case "2" -> { // Busqueda por id
                    StdOut.println("Ingrese el ID que desea buscar");
                    String idABuscarString = StdIn.readString();

                    if (idABuscarString != null) { // El usuario escribio por teclado
                        int idABuscar = validarEntero(idABuscarString);

                        if (idABuscar != -1) { // El usuario escribio id valida
                            desplegarPokemonPorID(idABuscar);

                        } else { // El usuario escribio una id invalida
                            StdOut.println("Error!!: el id debe ser un numero entero y mayor a 0");
                        }
                    } else { // El usuario no escribio nada
                        StdOut.println("Error!!: debe ingresar un ID");
                    }
                }
                // Opcion invalida
                default -> StdOut.println("Error!! no existe dicha opcion, ingrese nuevamente");
            }
            StdOut.println("""
                            
                            Ingrese por que opcion desea realizar la busqueda
                            [1] Nombre
                            [2] ID
                            [3] Volver al menu principal
                            """);
            opcionBusqueda = StdIn.readString();
        }
    }

    /**
     * Lectura del archivo kanto.txt
     *
     * @throws IOException : en caso de error
     */
    public void lecturaDeArchivo() throws IOException {
        // Lectura del archivo
        ArchivoEntrada archivoEntrada = new ArchivoEntrada("kanto.txt");

        while (!archivoEntrada.isEndFile()){
            Registro registro = archivoEntrada.getRegistro();
            String idString = sinEspacio(registro.getString());

            if (idString != null){
                int id = validarEntero(idString);

                if (id <= 151 && id > 0){
                    String nombre = sinEspacio(registro.getString());

                    if (nombre != null){
                        String etapa = sinEspacio(registro.getString());

                        if (etapa != null){

                            if (etapa.equalsIgnoreCase("Basico")){ // El pokemon esta en etapa basica
                                // Puede ser etapa1 si no tiene evoluciones o primera evolucion si tiene alguna
                                String varIndefinida1 = sinEspacio(registro.getString());

                                if (varIndefinida1 != null){
                                    // Puede ser etapa2 si no tiene evoluciones, etapa1 si tiene una evolucion, o segunda evolucion si tiene dos
                                    String varIndefinida2 = sinEspacio(registro.getString());

                                    if (varIndefinida2 != null){
                                        // Puede ser null si no tiene evoluciones, etapa1 si tiene segunda evolucion, o etapa2 si tiene una evolucion
                                        String varIndefinida3 = sinEspacio(registro.getString());

                                        if (varIndefinida3 != null){ // Tiene al menos una evolucion
                                            // Puede ser null si tiene una evolucion, o etapa2 si tiene segunda evolucion
                                            String varIndefinida4 = sinEspacio(registro.getString());

                                            if (varIndefinida4 != null){ // Tiene dos o mas evoluciones
                                                // Si tiene 2 evoluciones es null, si tiene mas entonces tiene multiples evoluciones y no se agrega a la lista
                                                String multiplesEvoluciones = sinEspacio(registro.getString());

                                                if (multiplesEvoluciones == null){ // Tiene dos evoluciones
                                                    Pokemon pokemon = new PokemonBasico(id,nombre,etapa,varIndefinida1,varIndefinida2,varIndefinida3,varIndefinida4);
                                                    listaPokemon.agregar(pokemon);
                                                }

                                            }else{ // Tiene una sola evolucion
                                                Pokemon pokemon = new PokemonBasico(id,nombre,etapa,varIndefinida1,varIndefinida2,varIndefinida3);
                                                listaPokemon.agregar(pokemon);
                                            }

                                        }else{ // No tiene evoluciones
                                            Pokemon pokemon = new PokemonBasico(id,nombre,etapa,varIndefinida1,varIndefinida2);
                                            listaPokemon.agregar(pokemon);
                                        }
                                    }
                                }

                            } else if (etapa.equalsIgnoreCase("Primera Evolucion")){ // El pokemon es de primera evolucion
                                String etapaBasica = sinEspacio(registro.getString());

                                if (etapaBasica != null){
                                    // Puede ser segunda evolucion, o etapa1 si no tiene siguiente evolucion
                                    String varIndefinida1 = sinEspacio(registro.getString());

                                    if (varIndefinida1 != null){
                                        // Puede ser etapa1 si tiene segunda evolucion, o etapa2 si no tiene siguiente evolucion
                                        String varIndefinida2 = sinEspacio(registro.getString());

                                        if (varIndefinida2 != null){
                                            // Puede ser etapa2 si tiene segunda evolucion, o null si no tiene siguiente evolucion
                                            String varIndefinida3 = sinEspacio(registro.getString());

                                            if(varIndefinida3 != null){ // Tiene dos o mas evoluciones
                                                // Si tiene 2 evoluciones es null, si tiene mas entonces tiene multiples evoluciones y no se agrega a la lista
                                                String multiplesEvoluciones = sinEspacio(registro.getString());

                                                if (multiplesEvoluciones == null){ // Tiene dos evoluciones
                                                    Pokemon pokemon = new PokemonPrimeraEvolucion(id,nombre,etapa,etapaBasica,varIndefinida1,varIndefinida2,varIndefinida3);
                                                    listaPokemon.agregar(pokemon);
                                                }
                                            }else{ // No tiene siguiente evolucion
                                                Pokemon pokemon = new PokemonPrimeraEvolucion(id,nombre,etapa,etapaBasica,varIndefinida1,varIndefinida2);
                                                listaPokemon.agregar(pokemon);
                                            }
                                        }
                                    }
                                }

                            } else if (etapa.equalsIgnoreCase("Segunda Evolucion")) { // El pokemon es de segunda evolucion
                                String primeraEvolucion = sinEspacio(registro.getString());

                                if (primeraEvolucion != null){
                                    String etapaBasica = sinEspacio(registro.getString());

                                    if (etapaBasica != null){
                                        String tipo1 = sinEspacio(registro.getString());

                                        if (tipo1 != null){
                                            String tipo2 = sinEspacio(registro.getString());

                                            if (tipo2 != null){
                                                // Si tiene 2 evoluciones es null, si tiene mas entonces tiene multiples evoluciones y no se agrega a la lista
                                                String multiplesEvoluciones = registro.getString();

                                                if (multiplesEvoluciones == null){
                                                    Pokemon pokemon = new PokemonSegundaEvolucion(id,nombre,etapa,primeraEvolucion,etapaBasica,tipo1,tipo2);
                                                    listaPokemon.agregar(pokemon);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Valida si la palabra ingresada es un numero entero
     *
     * @param numeroString : variable String a validar
     * @return numero : -1 si no es un numero o es invalido
     */
    public int validarEntero(String numeroString){

        int numero;

        try{
            numero = Integer.parseInt(numeroString);
        }catch (Exception e){
            return -1;
        }
        return numero;
    }

    /**
     *
     * @param conEspacio : String con espacios
     * @return conEspacio.trim() : Quita los espacios del inicio y el final, pero no los del medio
     */
    public String sinEspacio(String conEspacio){

        if (conEspacio == null){
            return null;
        }
        return conEspacio.trim();
    }

    /**
     * Imprime los pokemon ordenados por ID, en un rango dado
     *
     * @param listIterator : lista de pokemons
     * @param menor : numero menor del rango
     * @param mayor : numero mayor del rango
     */
    public void desplegarPokemonPorRango(ListIterator<Pokemon> listIterator, int menor, int mayor){

        StdOut.println("********************** Pokemons dentro del rango **********************\n");

        while (listIterator.hasNext()){
            Pokemon pokemon = listIterator.next();

            if (pokemon.getID() >= menor && pokemon.getID() <= mayor){ // Solo imprime los valores dentro del rango
                desplegarPokemon(pokemon);

            } else if (pokemon.getID() > mayor){ // si el id es mayor que el numero mayor del rango, rompe el loop
                break;
            }
        }
    }

    /**
     * Reconoce el tipo de pokemon y lo imprime
     *
     * @param pokemon a imprimir
     */
    public void desplegarPokemon(Pokemon pokemon){

        if (pokemon instanceof PokemonSegundaEvolucion){ // Tiene un solo constructor
            StdOut.println("ID: "+pokemon.getID()+" | Nombre: "+pokemon.getNombre()+ " | Etapa: "+pokemon.getEtapa()+
                    " | Primera evolucion: "+((PokemonSegundaEvolucion) pokemon).getPrimeraEvolucion()+
                    " | Etapa basica: "+((PokemonSegundaEvolucion) pokemon).getEtapaBasica()+" | Tipo 1: "+pokemon.getTipo1()+" | Tipo 2: "+pokemon.getTipo2());

        } else if (pokemon instanceof PokemonPrimeraEvolucion){ // Requiere validar los dos tipos de constructores

            if (((PokemonPrimeraEvolucion) pokemon).getSegundaEvolucion() == null){ // No tiene segunda evolucion

                StdOut.println("ID: "+pokemon.getID()+" | Nombre: "+pokemon.getNombre()+ " | Etapa: "+pokemon.getEtapa()+
                        " | Etapa Basica: "+((PokemonPrimeraEvolucion) pokemon).getEtapaBasica()+" | Tipo 1: "+pokemon.getTipo1()+" | Tipo 2: "+pokemon.getTipo2());

            }else{ // Tiene una segunda evolucion
                StdOut.println("ID: "+pokemon.getID()+" | Nombre: "+pokemon.getNombre()+ " | Etapa: "+pokemon.getEtapa()+
                        " | Etapa Basica: "+((PokemonPrimeraEvolucion) pokemon).getEtapaBasica()+" | Segunda evolucion: "+((PokemonPrimeraEvolucion) pokemon).getSegundaEvolucion()+
                        " | Tipo 1: "+pokemon.getTipo1()+" | Tipo 2: "+pokemon.getTipo2());
            }
        } else if (pokemon instanceof PokemonBasico) { // Requiere validar los tres tipos de constructores

            if (((PokemonBasico) pokemon).getEvolucion1() == null){ // No tiene ni primera ni segunda evolucion
                StdOut.println("ID: "+pokemon.getID()+" | Nombre: "+pokemon.getNombre()+ " | Etapa: "+pokemon.getEtapa()+
                        " | Tipo 1: "+pokemon.getTipo1()+" | Tipo 2: "+pokemon.getTipo2());

            } else if (((PokemonBasico) pokemon).getEvolucion2() == null){ // Tiene primera evolucion pero no segunda
                StdOut.println("ID: "+pokemon.getID()+" | Nombre: "+pokemon.getNombre()+ " | Etapa: "+pokemon.getEtapa()+
                        " | Primera evolucion: "+((PokemonBasico) pokemon).getEvolucion1()+" | Tipo 1: "+pokemon.getTipo1()+" | Tipo 2: "+pokemon.getTipo2());

            } else { // Tiene dos evoluciones
                StdOut.println("ID: "+pokemon.getID()+" | Nombre: "+pokemon.getNombre()+ " | Etapa: "+pokemon.getEtapa()+
                        " | Primera evolucion: "+((PokemonBasico) pokemon).getEvolucion1()+" | Segunda evolucion: "+((PokemonBasico) pokemon).getEvolucion2()+
                        " | Tipo 1: "+pokemon.getTipo1()+" | Tipo 2: "+pokemon.getTipo2());
            }
        }
    }

    /**
     * Busca y si encuentra un pokemon con el nombre recibido como parametro, lo imprime
     *
     * @param nombre : nombre que desea buscar en la lista
     */
    public void desplegarPokemonPorNombre(String nombre){

        ArrayList<Pokemon> arrayList = listaPokemon.toArray();

        if (arrayList != null){ // Se ingresaron pokemons al sistema
            ListIterator<Pokemon> listIterator = arrayList.listIterator();
            Pokemon pokemonEncontrado = null; // Almacena el pokemon a encontrar

            while (listIterator.hasNext()){
                Pokemon pokemon = listIterator.next();

                if (pokemon.getNombre().equalsIgnoreCase(nombre)){ // Se encontro el pokemon
                    pokemonEncontrado = pokemon;
                    break;
                }
            }

            if (pokemonEncontrado != null) { // Se almaceno el pokemon buscado
                pokemonEncontrado(pokemonEncontrado);

            } else { // No se encontro el pokemon
                StdOut.println("No se encontro el pokemon buscado");
            }
        } else { // No se ingresaron pokemons
            StdOut.println("Error!!: no se ingreso ningun pokemon al sistema");
        }
    }

    /**
     * Busca y si encuentra un pokemon con la id recibida como parametro, lo imprime
     *
     * @param id : id que desea buscar en la lista
     */
    public void desplegarPokemonPorID(int id){

        if (id >= 152 || id <= 0){

            StdOut.println("No se encontro el pokemon buscado");
            return;
        }

        ArrayList<Pokemon> arrayList = listaPokemon.toArray();

        if (arrayList != null){ // Se ingresaron pokemons al sistema

            ListIterator<Pokemon> listIterator = arrayList.listIterator();
            Pokemon pokemonEncontrado = null; // Almacena el pokemon a encontrar

            while (listIterator.hasNext()){
                Pokemon pokemon = listIterator.next();

                if (pokemon.getID() == id){ // Se encontro el pokemon
                    pokemonEncontrado = pokemon;
                    break;
                }
            }

            if (pokemonEncontrado != null) { // Se almaceno el pokemon encontrado
                pokemonEncontrado(pokemonEncontrado);

            } else { // No se encontro el pokemon
                StdOut.println("No se encontro el pokemon buscado");
            }
        } else { // No se ingresaron pokemons
            StdOut.println("Error!!: no se ingreso ningun pokemon al sistema");
        }
    }

    /**
     * Imprime el pokemon encontrado y pregunta al usuario la evolucion que desea desplegar informacion
     *
     * @param pokemonEncontrado : pokemon encontrado ya sea por id o por nombre
     */
    public void pokemonEncontrado(Pokemon pokemonEncontrado){

        // Se imprime el pokemon encontrado
        desplegarPokemon(pokemonEncontrado);

        StdOut.println("""
            
            Ingrese la evolucion que desea desplegar informacion
            [1] Primera evolucion
            [2] Segunda evolucion
            [3] Ninguna
            """);
        String opcion = StdIn.readString();

        while (!opcion.equalsIgnoreCase("3")){

            if (pokemonEncontrado instanceof PokemonSegundaEvolucion){ // El pokemon encontrado es de segunda evolucion
                switch (opcion) {

                    // Los pokemon de segunda evolucion poseen si o si primera evolucion y segunda evolucion
                    case "1" -> { // Desplegar informacion de la primera evolucion
                        String primeraEvolucion = ((PokemonSegundaEvolucion) pokemonEncontrado).getPrimeraEvolucion();
                        informacionPokemon(primeraEvolucion);
                    }
                    case "2" -> { // Desplegar informacion de la segunda evolucion
                        String segundaEvolucion = pokemonEncontrado.getNombre();
                        informacionPokemon(segundaEvolucion);
                    }
                    default -> // Se escribio una opcion invalida
                            StdOut.println("Error!! no existe dicha opcion, ingrese nuevamente");
                }
            } else if (pokemonEncontrado instanceof PokemonPrimeraEvolucion){ // El pokemon encontrado es de primera evolucion

                switch (opcion) {
                    // Los pokemon de primera evolucion no necesariamente poseen segunda evolucion
                    case "1" -> { // Desplegar informacion de la primera evolucion
                        String primeraEvolucion = pokemonEncontrado.getNombre();
                        informacionPokemon(primeraEvolucion);
                    }
                    case "2" -> { // Desplegar informacion de la segunda evolucion (Si es que tiene)
                        String segundaEvolucion = ((PokemonPrimeraEvolucion) pokemonEncontrado).getSegundaEvolucion();

                        // Se valida si posee segunda evolucion antes de desplegar informacion
                        if (segundaEvolucion != null) {
                            informacionPokemon(segundaEvolucion);

                        } else {
                            StdOut.println("Error!!: dicho pokemon no posee segunda evolucion");
                        }
                    }
                    default -> // Se escribio una opcion invalida
                            StdOut.println("Error!! no existe dicha opcion, ingrese nuevamente");
                }
            } else if (pokemonEncontrado instanceof PokemonBasico){ // El pokemon encontrado es de etapa basica
                switch (opcion) {

                    // Los pokemon en la etapa basica no necesariamente poseen evoluciones
                    case "1" -> { // Desplegar informacion de la primera evolucion (Si es que tiene)
                        String primeraEvolucion = ((PokemonBasico) pokemonEncontrado).getEvolucion1();

                        // Se valida si posee primera evolucion antes de desplegar informacion
                        if (primeraEvolucion != null) {
                            informacionPokemon(primeraEvolucion);

                        } else {
                            StdOut.println("Error!!: dicho pokemon no posee primera evolucion");
                        }
                    }
                    case "2" -> { // Desplegar informacion de la segunda evolucion (Si es que tiene)
                        String segundaEvolucion = ((PokemonBasico) pokemonEncontrado).getEvolucion2();

                        // Se valida si posee segunda evolucion antes de desplegar informacion
                        if (segundaEvolucion != null) {
                            informacionPokemon(segundaEvolucion);

                        } else {
                            StdOut.println("Error!!: dicho pokemon no posee segunda evolucion");
                        }
                    }
                    default -> // Se escribio una opcion invalida
                            StdOut.println("Error!! no existe dicha opcion, ingrese nuevamente");
                }
            }

            StdOut.println("""
                
                Ingrese la evolucion que desea desplegar informacion
                [1] Primera evolucion
                [2] Segunda evolucion
                [3] Ninguna
                """);
            opcion = StdIn.readString();
        }
    }

    /**
     * Contiene la informacion de la pokedex, busca el nombre dentro de la pokedex, y si contiene dicho nombre imprime la informacion
     *
     * @param nombre : nombre a buscar en el HashMap (pokedex)
     */
    public void informacionPokemon(String nombre){

        // < Key, Value >, reconoce los nombres por la key, y si contiene dicho nombre imprime el value
        HashMap<String, String> pokedex = new HashMap<>();

        // Se agrega uno a uno la key, y el value, < "Nombre del pokemon", "su informacion" >
        pokedex.put("Ivysaur", """
                Ivysaur:
                Cuando le crece bastante el bulbo del lomo, pierde la capacidad de erguirse sobre las patas traseras.
                ---------------------------------------------------
                |  Altura: 1.0 m        Categoria: Semilla        |
                |                                                 |
                |  Peso: 13.0 kg        Habilidad: Espesura       |
                |                                                 |
                |  Sexo: Masculino, femenino                      |
                ---------------------------------------------------
                Tipo: Planta, veneno
                Debilidades: Fuego, psiquico, volador, hielo
                """);
        pokedex.put("Venusaur", """
                Venusaur:
                La planta florece cuando absorbe energia solar, lo cual le obliga a buscar siempre la luz del sol.
                ---------------------------------------------------
                |  Altura: 2.0 m        Categoria: Semilla        |
                |                                                 |
                |  Peso: 100.0 kg       Habilidad: Espesura       |
                |                                                 |
                |  Sexo: Masculino, femenino                      |
                ---------------------------------------------------
                Tipo: Planta, veneno
                Debilidades: Fuego, psiquico, volador, hielo
                """);
        pokedex.put("Charmeleon", """
                Charmeleon:
                Este Pokemon de naturaleza agresiva ataca en combate con su cola llameante y hace trizas al rival con sus afiladas garras.
                ---------------------------------------------------
                |  Altura: 1.1 m       Categoria: Llama           |
                |                                                 |
                |  Peso: 19.0 kg       Habilidad: Mar llamas      |
                |                                                 |
                |  Sexo: Masculino, femenino                      |
                ---------------------------------------------------
                Tipo: Fuego
                Debilidades: Agua, tierra, roca
                """);
        pokedex.put("Charizard", """
                Charizard:
                Escupe un fuego tan caliente que funde las rocas. Causa incendios forestales sin querer.
                ---------------------------------------------------
                |  Altura: 1.7 m       Categoria: Llama           |
                |                                                 |
                |  Peso: 90.5 kg       Habilidad: Mar llamas      |
                |                                                 |
                |  Sexo: Masculino, femenino                      |
                ---------------------------------------------------
                Tipo: Fuego, volador
                Debilidades: Agua, electrico, roca (hace danio x4)
                """);
        pokedex.put("Wartortle", """
                Wartortle:
                Se lo considera un simbolo de longevidad. Los ejemplares mas ancianos tienen musgo sobre el caparazon.
                ---------------------------------------------------
                |  Altura: 1.0 m       Categoria: Tortuga         |
                |                                                 |
                |  Peso: 22.5 kg       Habilidad: Torrente        |
                |                                                 |
                |  Sexo: Masculino, femenino                      |
                ---------------------------------------------------
                Tipo: Agua
                Debilidades: Planta, electrico
                """);
        pokedex.put("Blastoise", """
                Blastoise:
                Para acabar con su enemigo, lo aplasta con el peso de su cuerpo. En momentos de apuro, se esconde en el caparazon.
                ---------------------------------------------------
                |  Altura: 1.6 m       Categoria: Armazon         |
                |                                                 |
                |  Peso: 85.5 kg       Habilidad: Torrente        |
                |                                                 |
                |  Sexo: Masculino, femenino                      |
                ---------------------------------------------------
                Tipo: Agua
                Debilidades: Planta, electrico
                """);
        pokedex.put("Metapod", """
                Metapod:
                Como en este estado solo puede endurecer su coraza, permanece inmovil a la espera de evolucionar.
                ---------------------------------------------------
                |  Altura: 0.7 m      Categoria: Capullo          |
                |                                                 |
                |  Peso: 9.9 kg       Habilidad: Mudar            |
                |                                                 |
                |  Sexo: Masculino, femenino                      |
                ---------------------------------------------------
                Tipo: Insecto
                Debilidades: Fuego, volador, roca
                """);
        pokedex.put("Butterfree", """
                Butterfree:
                Aletea a gran velocidad para lanzar al aire sus escamas extremadamente toxicas.
                ---------------------------------------------------
                |  Altura: 1.1 m       Categoria: Mariposa        |
                |                                                 |
                |  Peso: 32.0 kg       Habilidad: Ojo opuesto     |
                |                                                 |
                |  Sexo: Masculino, femenino                      |
                ---------------------------------------------------
                Tipo: Insecto, volador
                Debilidades: Fuego, volador, electrico, hielo, roca (hace danio x4)
                """);
        pokedex.put("Kakuna", """
                Kakuna:
                Aunque es casi incapaz de moverse, en caso de sentirse amenazado puede envenenar a los enemigos con su aguijon.
                ---------------------------------------------------
                |  Altura: 0.6 m       Categoria: Capullo         |
                |                                                 |
                |  Peso: 10.0 kg       Habilidad: Mudar           |
                |                                                 |
                |  Sexo: Masculino, femenino                      |
                ---------------------------------------------------
                Tipo: Insecto, veneno
                Debilidades: Fuego, psiquico, volador, roca
                """);
        pokedex.put("Beedrill", """
                Beedrill:
                Tiene tres aguijones venenosos, dos en las patas anteriores y uno en la parte baja del abdomen, con los que ataca a sus enemigos una y otra vez.
                ---------------------------------------------------
                |  Altura: 1.0 m       Categoria: Abeja veneno    |
                |                                                 |
                |  Peso: 29.5 kg       Habilidad: Enjambre        |
                |                                                 |
                |  Sexo: Masculino, femenino                      |
                ---------------------------------------------------
                Tipo: Insecto, veneno
                Debilidades: Fuego, psiquico, volador, roca
                """);
        pokedex.put("Pidgeotto", """
                Pidgeotto:
                Su extraordinaria vitalidad y resistencia le permiten cubrir grandes distancias del territorio que habita en busca de presas.
                ---------------------------------------------------
                |  Altura: 1.1 m       Categoria: Pajaro          |
                |                                                 |
                |  Peso: 30.0 kg       Habilidad: Vista lince     |
                |                                 Tumbos          |
                |  Sexo: Masculino, femenino                      |
                ---------------------------------------------------
                Tipo: Normal, volador
                Debilidades: Electrico, hielo, roca
                """);
        pokedex.put("Pidgeot", """
                Pidgeot:
                Este Pokemon vuela a una velocidad de 2 mach en busca de presas. Sus grandes garras son armas muy peligrosas.
                ---------------------------------------------------
                |  Altura: 1.5 m       Categoria: Pajaro          |
                |                                                 |
                |  Peso: 39.5 kg       Habilidad: Vista lince     |
                |                                 Tumbos          |
                |  Sexo: Masculino, femenino                      |
                ---------------------------------------------------
                Tipo: Normal, volador
                Debilidades: Electrico, hielo, roca
                """);
        pokedex.put("Raticate", """
                Raticate:
                Gracias a las pequeñas membranas de las patas traseras, puede nadar por los rios para capturar presas.
                ---------------------------------------------------
                |  Altura: 0.7 m       Categoria: Raton           |
                |                                                 |
                |  Peso: 18.5 kg       Habilidad: Fuga            |
                |                                 Agallas         |
                |  Sexo: Masculino, femenino                      |
                ---------------------------------------------------
                Tipo: Normal
                Debilidades: Lucha
                """);
        pokedex.put("Fearow", """
                Fearow:
                Este Pokemon ha existido desde tiempos remotos. Al menor atisbo de peligro, alza el vuelo y huye.
                ---------------------------------------------------
                |  Altura: 1.2 m       Categoria: Pico            |
                |                                                 |
                |  Peso: 38.0 kg       Habilidad: Vista lince     |
                |                                                 |
                |  Sexo: Masculino, femenino                      |
                ---------------------------------------------------
                Tipo: Normal, volador
                Debilidades: Electrico, hielo, roca
                """);
        pokedex.put("Arbok", """
                Arbok:
                Se han llegado a identificar hasta seis variaciones distintas de los espeluznantes dibujos de su piel.
                ---------------------------------------------------
                |  Altura: 3.5 m       Categoria: Cobra           |
                |                                                 |
                |  Peso: 65.0 kg       Habilidad: Mudar           |
                |                                 Intimidacion    |
                |  Sexo: Masculino, femenino                      |
                ---------------------------------------------------
                Tipo: Veneno
                Debilidades: Psiquico, tierra
                """);
        pokedex.put("Raichu", """
                Raichu:
                Su cola actua como toma de tierra y descarga electricidad al suelo, lo que le protege de los calambrazos.
                ---------------------------------------------------
                |  Altura: 0.8 m       Categoria: Raton           |
                |                                                 |
                |  Peso: 30.0 kg       Habilidad: Elec. estatica  |
                |                                                 |
                |  Sexo: Masculino, femenino                      |
                ---------------------------------------------------
                Tipo: Electrico
                Debilidades: Tierra
                """);
        pokedex.put("Sandslash", """
                Sandslash:
                Cuanto mas seco es el terreno en el que habita, mas duras y lisas se vuelven las puas que le recubren la espalda.
                ---------------------------------------------------
                |  Altura: 1.0 m       Categoria: Raton           |
                |                                                 |
                |  Peso: 29.5 kg       Habilidad: Velo arena      |
                |                                                 |
                |  Sexo: Masculino, femenino                      |
                ---------------------------------------------------
                Tipo: Tierra
                Debilidades: Agua, planta, hielo
                """);
        pokedex.put("Nidorina", """
                Nidorina:
                Se cree que el cuerno de la frente se le ha atrofiado para evitar herir a sus crias al alimentarlas.
                ---------------------------------------------------
                |  Altura: 0.8 m       Categoria: Pin veneno      |
                |                                                 |
                |  Peso: 20.0 kg       Habilidad: Punto toxico    |
                |                                 Rivalidad       |
                |  Sexo: Femenino                                 |
                ---------------------------------------------------
                Tipo: Veneno
                Debilidades: Psiquico, tierra
                """);
        pokedex.put("Nidoqueen", """
                Nidoqueen:
                Su defensa destaca sobre la capacidad ofensiva. Usa las escamas del cuerpo como una coraza para proteger a su prole de cualquier ataque.
                ---------------------------------------------------
                |  Altura: 1.3 m       Categoria: Taladro         |
                |                                                 |
                |  Peso: 60.0 kg       Habilidad: Punto toxico    |
                |                                 Rivalidad       |
                |  Sexo: Femenino                                 |
                ---------------------------------------------------
                Tipo: Veneno, tierra
                Debilidades: Agua, psiquico, hielo, tierra
                """);
        pokedex.put("Nidorino", """
                Nidorino:
                Dondequiera que va, parte rocas con su cuerno, mas duro que un diamante, en busca de una Piedra Lunar.
                ---------------------------------------------------
                |  Altura: 0.9 m       Categoria: Pin veneno      |
                |                                                 |
                |  Peso: 19.5 kg       Habilidad: Punto toxico    |
                |                                 Rivalidad       |
                |  Sexo: Masculino                                |
                ---------------------------------------------------
                Tipo: Veneno
                Debilidades: Psiquico, tierra
                """);
        pokedex.put("Nidoking", """
                Nidoking:
                Una vez que se desboca, no hay quien lo pare. Solo se calma ante Nidoqueen, su compañera de toda la vida.
                ---------------------------------------------------
                |  Altura: 1.4 m       Categoria: Taladro         |
                |                                                 |
                |  Peso: 62.0 kg       Habilidad: Punto toxico    |
                |                                 Rivalidad       |
                |  Sexo: Masculino                                |
                ---------------------------------------------------
                Tipo: Veneno, tierra
                Debilidades: Agua, psiquico, hielo, tierra
                """);
        pokedex.put("Clefable", """
                Clefable:
                Este Pokemon de aspecto feerico, raramente visto por los humanos, corre a esconderse en cuanto detecta que hay alguien cerca.
                ---------------------------------------------------
                |  Altura: 1.3 m       Categoria: Hada            |
                |                                                 |
                |  Peso: 40.0 kg       Habilidad: Gran encanto    |
                |                                 Muro magico     |
                |  Sexo: Masculino, femenino                      |
                ---------------------------------------------------
                Tipo: Hada
                Debilidades: Acero, veneno
                """);
        pokedex.put("Ninetales", """
                Ninetales:
                Cuentan que llega a vivir hasta mil años y que cada una de las colas posee poderes sobrenaturales.
                ---------------------------------------------------
                |  Altura: 1.1 m       Categoria: Zorro           |
                |                                                 |
                |  Peso: 19.9 kg       Habilidad: Absorbe fuego   |
                |                                                 |
                |  Sexo: Masculino, femenino                      |
                ---------------------------------------------------
                Tipo: Fuego
                Debilidades: Agua, tierra, roca
                """);
        pokedex.put("Wigglytuff", """
                Wigglytuff:
                Tiene un pelaje muy fino. Se recomienda no enfadarlo, o se inflara y golpeara con todo su cuerpo.
                ---------------------------------------------------
                |  Altura: 1.0 m       Categoria: Globo           |
                |                                                 |
                |  Peso: 12.0 kg       Habilidad: Gran encanto    |
                |                                 Tenacidad       |
                |  Sexo: Masculino, femenino                      |
                ---------------------------------------------------
                Tipo: Normal, hada
                Debilidades: Acero, veneno
                """);
        pokedex.put("Golbat", """
                Golbat:
                Le encanta chuparles la sangre a los seres vivos. En ocasiones comparte la preciada colecta con otros congeneres hambrientos.
                ---------------------------------------------------
                |  Altura: 1.6 m       Categoria: Murcielago      |
                |                                                 |
                |  Peso: 55.0 kg       Habilidad: Fuerza mental   |
                |                                                 |
                |  Sexo: Masculino, femenino                      |
                ---------------------------------------------------
                Tipo: Veneno, volador
                Debilidades: Psiquico, electrico, hielo, roca
                """);
        pokedex.put("Gloom", """
                Gloom:
                Libera un fetido olor por los pistilos. El fuerte hedor hace perder el conocimiento a cualquiera que se encuentre en un radio de 2 km.
                ---------------------------------------------------
                |  Altura: 0.8 m       Categoria: Hierbajo        |
                |                                                 |
                |  Peso: 8.6 kg        Habilidad: Clorofila       |
                |                                                 |
                |  Sexo: Masculino, femenino                      |
                ---------------------------------------------------
                Tipo: Planta, veneno
                Debilidades: Fuego, psiquico, volador, hielo
                """);
        pokedex.put("Vileplume", """
                Vileplume:
                Tiene los petalos mas grandes del mundo. Al caminar, de ellos se desprenden densas nubes de polen toxico.
                ---------------------------------------------------
                |  Altura: 1.2 m       Categoria: Flor            |
                |                                                 |
                |  Peso: 18.6 kg        Habilidad: Clorofila      |
                |                                                 |
                |  Sexo: Masculino, femenino                      |
                ---------------------------------------------------
                Tipo: Planta, veneno
                Debilidades: Fuego, psiquico, volador, hielo
                """);
        pokedex.put("Parasect", """
                Parasect:
                Tras largo tiempo absorbiendo la energia del huesped, la seta parasita del lomo es la que parece controlar la voluntad de este Pokemon.
                ---------------------------------------------------
                |  Altura: 1.0 m       Categoria: Hongo           |
                |                                                 |
                |  Peso: 29.5 kg       Habilidad: Efecto espora   |
                |                                 Piel seca       |
                |  Sexo: Masculino, femenino                      |
                ---------------------------------------------------
                Tipo: Insecto, planta
                Debilidades: Fuego (hace danio x4), volador (hace danio x4), hielo, veneno, roca, insecto
                """);
        pokedex.put("Venomoth", """
                Venomoth:
                Tiene las alas cubiertas de escamas. Cada vez que las bate, esparce un polvillo sumamente venenoso.
                ----------------------------------------------------
                |  Altura: 1.5 m       Categoria: Polilla venenosa |
                |                                                  |
                |  Peso: 12.5 kg       Habilidad: Polvo escudo     |
                |                                 Cromolente       |
                |  Sexo: Masculino, femenino                       |
                ----------------------------------------------------
                Tipo: Insecto, veneno
                Debilidades: Fuego, psiquico, volador, roca
                """);
        pokedex.put("Dugtrio", """
                Dugtrio:
                Sus tres cabezas suben y bajan para remover la tierra cercana y facilitar asi la excavacion.
                ----------------------------------------------------
                |  Altura: 0.7 m       Categoria: Topo             |
                |                                                  |
                |  Peso: 33.3 kg       Habilidad: Velo arena       |
                |                                 Trampa arena     |
                |  Sexo: Masculino, femenino                       |
                ----------------------------------------------------
                Tipo: Tierra
                Debilidades: Agua, planta, hielo
                """);
        pokedex.put("Persian", """
                Persian:
                Aunque es muy admirado por el pelaje, es dificil de entrenar como mascota porque enseguida suelta arañazos.
                ----------------------------------------------------
                |  Altura: 1.0 m       Categoria: Gato fino        |
                |                                                  |
                |  Peso: 32.0 kg       Habilidad: Experto          |
                |                                 Flexibilidad     |
                |  Sexo: Masculino, femenino                       |
                ----------------------------------------------------
                Tipo: Normal
                Debilidades: Lucha
                """);
        pokedex.put("Golduck", """
                Golduck:
                Cuando nada a toda velocidad usando sus largas extremidades palmeadas, su frente comienza a brillar.
                ----------------------------------------------------
                |  Altura: 1.7 m       Categoria: Pato             |
                |                                                  |
                |  Peso: 76.6 kg       Habilidad: Humedad          |
                |                                 Aclimatacion     |
                |  Sexo: Masculino, femenino                       |
                ----------------------------------------------------
                Tipo: Agua
                Debilidades: Planta, electrico
                """);
        pokedex.put("Primeape", """
                Primeape:
                Se pone furioso si nota que alguien lo esta mirando. Persigue a cualquiera que establezca contacto visual con el.
                ----------------------------------------------------
                |  Altura: 1.0 m       Categoria: Mono cerdo       |
                |                                                  |
                |  Peso: 32.0 kg       Habilidad: Espiritu vital   |
                |                                 Irascible        |
                |  Sexo: Masculino, femenino                       |
                ----------------------------------------------------
                Tipo: Lucha
                Debilidades: Psiquico, volador, hada
                """);
        pokedex.put("Arcanine", """
                Arcanine:
                Cuenta un antiguo pergamino que la gente se quedaba fascinada al verlo correr por las praderas.
                ----------------------------------------------------
                |  Altura: 1.9 m       Categoria: Leyenda          |
                |                                                  |
                |  Peso: 155.0 kg      Habilidad: Intimidacion     |
                |                                  Absorbe fuego   |
                |  Sexo: Masculino, femenino                       |
                ----------------------------------------------------
                Tipo: Fuego
                Debilidades: Agua, tierra, roca
                """);
        pokedex.put("Poliwhirl", """
                Poliwhirl:
                Mirar fijamente la espiral de su vientre provoca somnolencia, por lo que puede usarse como alternativa a las nanas para dormir a los niños
                ----------------------------------------------------
                |  Altura: 1.0 m       Categoria: Renacuajo        |
                |                                                  |
                |  Peso: 20.0 kg       Habilidad: Humedad          |
                |                                 Absorbe agua     |
                |  Sexo: Masculino, femenino                       |
                ----------------------------------------------------
                Tipo: Agua
                Debilidades: Planta, electrico
                """);
        pokedex.put("Poliwrath", """
                Poliwrat:
                Su cuerpo es puro musculo. Logra abrirse paso por aguas gelidas partiendo el hielo con sus fornidos brazos.
                ----------------------------------------------------
                |  Altura: 1.3 m       Categoria: Renacuajo        |
                |                                                  |
                |  Peso: 54.0 kg       Habilidad: Humedad          |
                |                                 Absorbe agua     |
                |  Sexo: Masculino, femenino                       |
                ----------------------------------------------------
                Tipo: Agua, lucha
                Debilidades: Hada, planta, volador, psiquico, electrico
                """);
        pokedex.put("Kadabra", """
                Kadabra:
                Duerme suspendido en el aire gracias a sus poderes psiquicos. La cola, de una flexibilidad extraordinaria, hace las veces de almohada.
                ----------------------------------------------------
                |  Altura: 1.3 m       Categoria: Psi              |
                |                                                  |
                |  Peso: 56.5 kg       Habilidad: Fuerza mental    |
                |                                 Sincronia        |
                |  Sexo: Masculino, femenino                       |
                ----------------------------------------------------
                Tipo: Psiquico
                Debilidades: Fantasma, siniestro, insecto
                """);
        pokedex.put("Alakazam", """
                Alakazam:
                Posee una capacidad intelectual fuera de lo comun que le permite recordar todo lo sucedido desde el instante de su nacimiento.
                ----------------------------------------------------
                |  Altura: 1.5 m       Categoria: Psi              |
                |                                                  |
                |  Peso: 48.0 kg       Habilidad: Fuerza mental    |
                |                                 Sincronia        |
                |  Sexo: Masculino, femenino                       |
                ----------------------------------------------------
                Tipo: Psiquico
                Debilidades: Fantasma, siniestro, insecto
                """);
        pokedex.put("Machoke", """
                Machoke:
                Su musculoso cuerpo es tan fuerte que usa un cinto antifuerza para controlar sus movimientos.
                ----------------------------------------------------
                |  Altura: 1.5 m       Categoria: Superpoder       |
                |                                                  |
                |  Peso: 70.5 kg       Habilidad: Agallas          |
                |                                 Indefenso        |
                |  Sexo: Masculino, femenino                       |
                ----------------------------------------------------
                Tipo: Lucha
                Debilidades: Psiquico, volador, hada
                """);
        pokedex.put("Machamp", """
                Machamp:
                Mueve rapidamente sus cuatro brazos para asestar incesantes golpes y puñetazos desde todos los angulos.
                ----------------------------------------------------
                |  Altura: 1.6 m       Categoria: Superpoder       |
                |                                                  |
                |  Peso: 130.0 kg      Habilidad: Agallas          |
                |                                 Indefenso        |
                |  Sexo: Masculino, femenino                       |
                ----------------------------------------------------
                Tipo: Lucha
                Debilidades: Psiquico, volador, hada
                """);
        pokedex.put("Weepinbell", """
                Weepinbell:
                Cuando tiene hambre, engulle a todo lo que se mueve. La pobre presa acaba disuelta en sus acidos.
                ----------------------------------------------------
                |  Altura: 1.0 m       Categoria: Matamoscas       |
                |                                                  |
                |  Peso: 6.4 kg        Habilidad: Clorofila        |
                |                                                  |
                |  Sexo: Masculino, femenino                       |
                ----------------------------------------------------
                Tipo: Planta, veneno
                Debilidades: Fuego, psiquico, volador, hielo
                """);
        pokedex.put("Victreebel", """
                Victreebel:
                Atrae a su presa con un dulce aroma a miel. Una vez atrapada en la boca, la disuelve en tan solo un dia, huesos incluidos.
                ----------------------------------------------------
                |  Altura: 1.7 m       Categoria: Matamoscas       |
                |                                                  |
                |  Peso: 15.5 kg       Habilidad: Clorofila        |
                |                                                  |
                |  Sexo: Masculino, femenino                       |
                ----------------------------------------------------
                Tipo: Planta, veneno
                Debilidades: Fuego, psiquico, volador, hielo
                """);
        pokedex.put("Tentacruel", """
                Tentacruel:
                Si las esferas rojas que tiene a ambos lados de la cabeza brillan con intensidad, indica que esta a punto de lanzar ondas ultrasonicas.
                ----------------------------------------------------
                |  Altura: 1.6 m       Categoria: Medusa           |
                |                                                  |
                |  Peso: 55.0 kg       Habilidad: Cuerpo puro      |
                |                                 Viscosecrecion   |
                |  Sexo: Masculino, femenino                       |
                ----------------------------------------------------
                Tipo: Agua, veneno
                Debilidades: Psiquico, electrico, tierra
                """);
        pokedex.put("Graveler", """
                Graveler:
                Se le suele ver rodando montaña abajo. No evita los obstaculos, sino que los arrolla.
                ----------------------------------------------------
                |  Altura: 1.0 m       Categoria: Roca             |
                |                                                  |
                |  Peso: 105.0 kg      Habilidad: Cabeza roca      |
                |                                 Robustez         |
                |  Sexo: Masculino, femenino                       |
                ----------------------------------------------------
                Tipo: Roca, tierra
                Debilidades: Acero, lucha, agua (hace danio x4), hielo, planta (hace danio x4), tierra
                """);
        pokedex.put("Golem", """
                Golem:
                Nada mas mudar la piel, su cuerpo se vuelve blando y blanquecino, pero se endurece al poco tiempo de entrar en contacto con el aire.
                ----------------------------------------------------
                |  Altura: 1.4 m       Categoria: Megaton          |
                |                                                  |
                |  Peso: 300.0 kg      Habilidad: Cabeza roca      |
                |                                 Robustez         |
                |  Sexo: Masculino, femenino                       |
                ----------------------------------------------------
                Tipo: Roca, tierra
                Debilidades: Acero, lucha, agua (hace danio x4), hielo, planta (hace danio x4), tierra
                """);
        pokedex.put("Rapidash", """
                Rapidash:
                Su ardiente crin ondea al viento mientras atraviesa extensas praderas a una velocidad de 240 km/h.
                ----------------------------------------------------
                |  Altura: 1.7 m       Categoria: Caballo fuego    |
                |                                                  |
                |  Peso: 95.0 kg       Habilidad: Fuga             |
                |                                 Absorbe fuego    |
                |  Sexo: Masculino, femenino                       |
                ----------------------------------------------------
                Tipo: Fuego
                Debilidades: Agua, tierra, roca
                """);
        pokedex.put("Slowbro", """
                Slowbro:
                Segun parece, cuando Slowpoke fue a pescar al mar, un Shellder le mordio la cola y asi evoluciono a Slowbro.
                ----------------------------------------------------
                |  Altura: 1.6 m       Categoria: Ermitanio        |
                |                                                  |
                |  Peso: 78.5 kg       Habilidad: Despiste         |
                |                                 Ritmo propio     |
                |  Sexo: Masculino, femenino                       |
                ----------------------------------------------------
                Tipo: Agua, psiquico
                Debilidades: Fantasma, siniestro, planta, electrico, insecto
                """);
        pokedex.put("Magneton", """
                Magneton:
                Tres Magnemite se enlazan mediante una intensa fuerza magnetica. Provoca un fuerte pitido en los oidos a quien se le acerque.
                ----------------------------------------------------
                |  Altura: 1.0 m       Categoria: Iman             |
                |                                                  |
                |  Peso: 60.0 kg       Habilidad: Robustez         |
                |                                 Iman             |
                |  Sexo: Desconocido                               |
                ----------------------------------------------------
                Tipo: Electrico, acero
                Debilidades: Fuego, lucha, tierra (hace danio x4)
                """);
        pokedex.put("Dodrio", """
                Dodrio:
                Este Pokemon surge al dividirse una de las cabezas de Doduo. Es capaz de correr por las praderas a 60 km/h.
                ----------------------------------------------------
                |  Altura: 1.8 m       Categoria: Ave triple       |
                |                                                  |
                |  Peso: 85.2 kg       Habilidad: Fuga             |
                |                                 Madrugar         |
                |  Sexo: Masculino, femenino                       |
                ----------------------------------------------------
                Tipo: Normal, volador
                Debilidades: Electrico, hielo, roca
                """);
        pokedex.put("Dewgong", """
                Dewgong:
                Su cuerpo es blanco como la nieve. Puede nadar placidamente en mares gelidos gracias a su resistencia al frio
                ----------------------------------------------------
                |  Altura: 1.7 m       Categoria: Leon marino      |
                |                                                  |
                |  Peso: 120.0 kg      Habilidad: Sebo             |
                |                                 Hidratacion      |
                |  Sexo: Masculino, femenino                       |
                ----------------------------------------------------
                Tipo: Agua, hielo
                Debilidades: Planta, electrico, lucha, roca
                """);
        pokedex.put("Muk", """
                Muk:
                Esta cubierto por un repugnante lodo. Es tan toxico que hasta su rastro es venenoso.
                ----------------------------------------------------
                |  Altura: 1.2 m       Categoria: Lodo             |
                |                                                  |
                |  Peso: 30.0 kg       Habilidad: Hedor            |
                |                                 Viscosidad       |
                |  Sexo: Masculino, femenino                       |
                ----------------------------------------------------
                Tipo: Veneno
                Debilidades: Psiquico, tierra
                """);
        pokedex.put("Cloyster", """
                Cloyster:
                A los Cloyster que viven en las fuertes corrientes marinas les crecen largas y afiladas puas en la concha.
                ----------------------------------------------------
                |  Altura: 1.5 m       Categoria: Bivalvo          |
                |                                                  |
                |  Peso: 132.5 kg      Habilidad: Caparazon        |
                |                                 Encadenado       |
                |  Sexo: Masculino, femenino                       |
                ----------------------------------------------------
                Tipo: Agua, hielo
                Debilidades: Planta, electrico, lucha, roca
                """);
        pokedex.put("Haunter", """
                Haunter:
                Le gusta acechar en la oscuridad y tocarles el hombro a sus víctimas con su mano gaseosa. Estas se quedan temblando para siempre.
                ----------------------------------------------------
                |  Altura: 1.6 m       Categoria: Gas              |
                |                                                  |
                |  Peso: 0.1 kg        Habilidad: Levitacion       |
                |                                                  |
                |  Sexo: Masculino, femenino                       |
                ----------------------------------------------------
                Tipo: Fantasma, veneno
                Debilidades: Fantasma, siniestro, psiquico, tierra
                """);
        pokedex.put("Gengar", """
                Gengar:
                Para quitarle la vida a su presa, se desliza en su sombra y espera su oportunidad en silencio.
                ----------------------------------------------------
                |  Altura: 1.5 m       Categoria: Sombra           |
                |                                                  |
                |  Peso: 40.5 kg       Habilidad: Cuerpo maldito   |
                |                                                  |
                |  Sexo: Masculino, femenino                       |
                ----------------------------------------------------
                Tipo: Fantasma, veneno
                Debilidades: Fantasma, siniestro, psiquico, tierra
                """);
        pokedex.put("Hypno", """
                Hypno:
                Cuando mira al enemigo, usa diversos poderes psíquicos como la hipnosis.
                ----------------------------------------------------
                |  Altura: 1.6 m       Categoria: Hipnosis         |
                |                                                  |
                |  Peso: 75.6 kg       Habilidad: Insomnio         |
                |                                 Alerta           |
                |  Sexo: Masculino, femenino                       |
                ----------------------------------------------------
                Tipo: Psiquico
                Debilidades: Fantasma, siniestro, insecto
                """);
        pokedex.put("Kingler", """
                Kingler:
                La pinza tan grande que tiene posee una fuerza de 10 000 CV, pero le cuesta moverla por su gran tamaño.
                ----------------------------------------------------
                |  Altura: 1.3 m       Categoria: Tenaza           |
                |                                                  |
                |  Peso: 60.0 kg       Habilidad: Caparazon        |
                |                                 Corte fuerte     |
                |  Sexo: Masculino, femenino                       |
                ----------------------------------------------------
                Tipo: Agua
                Debilidades: Planta, electrico
                """);
        pokedex.put("Electrode", """
                Electrode:
                Cuanta más energía almacena, mayor velocidad alcanza, aunque aumenta también el riesgo de que explote.
                ----------------------------------------------------
                |  Altura: 1.2 m       Categoria: Bola             |
                |                                                  |
                |  Peso: 66.6 kg       Habilidad: Elec. estatica   |
                |                                 Insonorizar      |
                |  Sexo: Desconocido                               |
                ----------------------------------------------------
                Tipo: Electrico
                Debilidades: Tierra
                """);
        pokedex.put("Exeggutor", """
                Exeggutor:
                Cada una de las tres cabezas piensa de forma independiente y apenas muestra interés por el resto.
                ----------------------------------------------------
                |  Altura: 2.0 m       Categoria: Coco             |
                |                                                  |
                |  Peso: 120.0 kg      Habilidad: Clorofila        |
                |                                                  |
                |  Sexo: Masculino, femenino                       |
                ----------------------------------------------------
                Tipo: Planta, psiquico
                Debilidades: Fantasma, fuego, volador, hielo, siniestro, veneno, insecto (hace danio x4)
                """);
        pokedex.put("Marowak", """
                Marowak:
                Ha evolucionado tras fortalecerse y superar su pena. Ahora lucha con arrojo blandiendo su hueso a modo de arma.
                ----------------------------------------------------
                |  Altura: 1.0 m       Categoria: Apilahueso       |
                |                                                  |
                |  Peso: 45.0 kg       Habilidad: Cabeza roca      |
                |                                 Pararrayos       |
                |  Sexo: Masculino, femenino                       |
                ----------------------------------------------------
                Tipo: Tierra
                Debilidades: Agua, planta, hielo
                """);
        pokedex.put("Hitmonchan", """
                Hitmonchan:
                Sus puñetazos cortan el aire. Son tan veloces que el mínimo roce podría causar una quemadura.
                ----------------------------------------------------
                |  Altura: 1.4 m       Categoria: Puñetazo         |
                |                                                  |
                |  Peso: 50.2 kg       Habilidad: Vista lince      |
                |                                 Punio ferreo     |
                |  Sexo: Masculino                                 |
                ----------------------------------------------------
                Tipo: Lucha
                Debilidades: Psiquico, volador, hada
                """);
        pokedex.put("Weezing", """
                Weezing:
                Usa sus dos cuerpos para mezclar gases. Según parece, en el pasado podían hallarse ejemplares por todos los rincones de Galar.
                ----------------------------------------------------
                |  Altura: 1.2 m       Categoria: Gas venenoso     |
                |                                                  |
                |  Peso: 9.5 kg        Habilidad: Levitacion       |
                |                                 Gas reactivo     |
                |  Sexo: Masculino, femenino                       |
                ----------------------------------------------------
                Tipo: Veneno
                Debilidades: Psiquico, tierra
                """);
        pokedex.put("Rhydon", """
                Rhydon:
                Cuando evoluciona, comienza a andar con las patas traseras. Es capaz de horadar rocas con el cuerno que tiene.
                ----------------------------------------------------
                |  Altura: 1.9 m       Categoria: Taladro          |
                |                                                  |
                |  Peso: 120.0 kg      Habilidad: Cabeza roca      |
                |                                 Pararrayos       |
                |  Sexo: Masculino, femenino                       |
                ----------------------------------------------------
                Tipo: Tierra, roca
                Debilidades: Acero, hielo, agua (hace danio x4), lucha, planta (hace danio x4), tierra
                """);
        pokedex.put("Seadra", """
                Seadra:
                En esta especie, es el macho quien se ocupa de la prole. Durante la época de cría, el veneno de las púas de su espalda se vuelve más potente.
                ----------------------------------------------------
                |  Altura: 1.2 m       Categoria: Dragon           |
                |                                                  |
                |  Peso: 25.0 kg       Habilidad: Punto toxico     |
                |                                 Francotirador    |
                |  Sexo: Masculino, femenino                       |
                ----------------------------------------------------
                Tipo: Agua
                Debilidades: Planta, electrico
                """);
        pokedex.put("Seaking", """
                Seaking:
                En otoño gana algo de peso para atraer a posibles parejas y se cubre de llamativos colores.
                ----------------------------------------------------
                |  Altura: 1.3 m       Categoria: Pez color        |
                |                                                  |
                |  Peso: 39.0 kg       Habilidad: Nado rapido      |
                |                                 Velo agua        |
                |  Sexo: Masculino, femenino                       |
                ----------------------------------------------------
                Tipo: Agua
                Debilidades: Planta, electrico
                """);
        pokedex.put("Starmie", """
                Starmie:
                Su órgano central, conocido como núcleo, brilla con los colores del arcoíris cuando se dispone a liberar sus potentes poderes psíquicos.
                ----------------------------------------------------
                |  Altura: 1.1 m       Categoria: Misterioso       |
                |                                                  |
                |  Peso: 80.0 kg       Habilidad: Cura natural     |
                |                                 Iluminacion      |
                |  Sexo: Desconocido                               |
                ----------------------------------------------------
                Tipo: Agua, psiquico
                Debilidades: Fantasma, siniestro, planta, electrico, insecto
                """);
        pokedex.put("Gyarados", """
                Gyarados:
                Cuando aparece, monta en cólera. No deja de estar furioso hasta que lo destruye todo.
                ----------------------------------------------------
                |  Altura: 6.5 m       Categoria: Atrocidad        |
                |                                                  |
                |  Peso: 235.0 kg      Habilidad: Intimidacion     |
                |                                                  |
                |  Sexo: Masculino, femenino                       |
                ----------------------------------------------------
                Tipo: Agua, volador
                Debilidades: Electrico (hace danio x4), roca
                """);
        pokedex.put("Vaporeon", """
                Vaporeon:
                Vive cerca del agua. Su cola termina en una aleta parecida a la de un pez, por lo que hay gente que lo confunde con una sirena.
                ----------------------------------------------------
                |  Altura: 1.0 m       Categoria: Burbuja          |
                |                                                  |
                |  Peso: 29.0 kg       Habilidad: Absorbe agua     |
                |                                                  |
                |  Sexo: Masculino, femenino                       |
                ----------------------------------------------------
                Tipo: Agua
                Debilidades: Planta, electrico
                """);
        pokedex.put("Jolteon", """
                Jolteon:
                Concentra la débil actividad eléctrica de sus células para lanzar dañinas descargas.
                ----------------------------------------------------
                |  Altura: 0.8 m       Categoria: Relampago        |
                |                                                  |
                |  Peso: 24.5 kg       Habilidad: Absorbe elec     |
                |                                                  |
                |  Sexo: Masculino, femenino                       |
                ----------------------------------------------------
                Tipo: Electrico
                Debilidades: Tierra
                """);
        pokedex.put("Flareon", """
                Flareon:
                Calienta el aire que inhala en su saca de fuego y lo expulsa a 1700ºC.
                ----------------------------------------------------
                |  Altura: 0.9 m       Categoria: Llama            |
                |                                                  |
                |  Peso: 25.0 kg       Habilidad: Absorbe fuego    |
                |                                                  |
                |  Sexo: Masculino, femenino                       |
                ----------------------------------------------------
                Tipo: Fuego
                Debilidades: Agua, tierra, roca
                """);
        pokedex.put("Omastar", """
                Omastar:
                Se cree que se extinguió porque el excesivo tamaño y peso de su concha le impedían moverse con rapidez para capturar presas.
                ----------------------------------------------------
                |  Altura: 1.0 m       Categoria: Espiral          |
                |                                                  |
                |  Peso: 35.0 kg       Habilidad: Caparazon        |
                |                                 Nado rapido      |
                |  Sexo: Masculino, femenino                       |
                ----------------------------------------------------
                Tipo: Roca, agua
                Debilidades: Planta (hace danio x4), electrico, lucha, tierra
                """);
        pokedex.put("Kabutops", """
                Kabutops:
                Despedaza a las presas que atrapa para sorber sus fluidos y deja los restos para que otros Pokémon den buena cuenta de ellos.
                ----------------------------------------------------
                |  Altura: 1.3 m       Categoria: Armazon          |
                |                                                  |
                |  Peso: 40.5 kg       Habilidad: Nado rapido      |
                |                                 Armadura batalla |
                |  Sexo: Masculino, femenino                       |
                ----------------------------------------------------
                Tipo: Roca, agua
                Debilidades: Planta (hace danio x4), electrico, lucha, tierra
                """);
        pokedex.put("Dragonair", """
                Dragonair:
                Dicen que, cuando su cuerpo desprende un aura, el tiempo empieza a cambiar inmediatamente.
                ----------------------------------------------------
                |  Altura: 4.0 m       Categoria: Dragon           |
                |                                                  |
                |  Peso: 16.5 kg       Habilidad: Mudar            |
                |                                                  |
                |  Sexo: Masculino, femenino                       |
                ----------------------------------------------------
                Tipo: Dragon
                Debilidades: Hada, hielo, dragon
                """);
        pokedex.put("Dragonite", """
                Dragonite:
                Dicen que viven en una isla en algún lugar del océano que solo ellos habitan.
                ----------------------------------------------------
                |  Altura: 2.2 m       Categoria: Dragon           |
                |                                                  |
                |  Peso: 210.0 kg      Habilidad: Fuerza mental    |
                |                                                  |
                |  Sexo: Masculino, femenino                       |
                ----------------------------------------------------
                Tipo: Dragon, volador
                Debilidades: Hada, dragon, hielo (hace danio x4), roca
                """);
        // Si encuentra el nombre dentro de las key, imprime el valor de la key, si no, imprime el valor default
        StdOut.println(pokedex.getOrDefault(nombre, "Dicho pokemon no posee esa evolucion"));
    }
}


