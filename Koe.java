class Koe<T> extends Stabel<T> {
// first in, first out
    private Node siste;

    Koe() {

    }



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
