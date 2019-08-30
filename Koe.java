class Koe<T> extends Stabel<T> {

    private Node siste;

    /**
     * first in, first out list
     */
    Koe() {
    }


    /**
     * insert element in list
     * @param   element     element to insert
     */
    public void settInn(T element) {
        if (foerste == null) {
            foerste = new Node(element);
            siste = foerste;
        }
        else {
            siste.neste = new Node(element);        // setter inn node bakerst
            siste = siste.neste;                    // siste peker alltid paa den bakerste noden
        }
    }
}
