import java.util.Iterator;


class Stabel<T> implements Liste<T> {
// last in, first out
    protected Node foerste;

    Stabel() {

    }



    public int storrelse() {
        Iterator it = iterator();
        int str = 0;
        while (it.hasNext()) {
            str++;
            it.next();
        }
        return str;
    }



    public boolean erTom() {
        return (foerste == null);
    }



    public void settInn(T element) {
        Node<T> temp = foerste;
        foerste = new Node(element);
        foerste.neste = temp;
    }



    public T fjern() {
        Node<T> temp = foerste;
        foerste = foerste.neste;
        return (T)temp.data;                // caster temp.data fordi kompilatoren tror det er Object
    }






    public Iterator<T> iterator() {
        return new ListeIterator();
    }





    class Node<T> {
        protected Node neste;
        protected T data;

        Node(T data) {
            this.data = data;
        }
    }



    class ListeIterator implements Iterator<T> {
        protected Node neste;
        protected Node forrige;

        public ListeIterator() {
            neste = foerste;            // startposisjon = foer foerste node
        }

        public boolean hasNext() {
            return (neste != null);
        }

        public T next() {
            forrige = neste;
            neste = neste.neste;
            return (T)forrige.data;
        }

        public void remove() {

        }
    }
}
