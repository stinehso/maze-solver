import java.util.Iterator;


class Stabel<T> implements Liste<T> {
    protected Node foerste;

    /**
     * Stabel is a last in, first out list (LIFO)
     */
    Stabel() {
    }


    /**
     * Return the length of the list
     * @return  number of elements
     */
    public int storrelse() {
        Iterator it = iterator();
        int str = 0;
        while (it.hasNext()) {
            str++;
            it.next();
        }
        return str;
    }


    /**
     * Tell whether or not the list is empty
     * @return true if empty, false otherwise
     */
    public boolean erTom() {
        return (foerste == null);
    }


    /**
     * Insert element in list
     * @param element what you want to insert
     */
    public void settInn(T element) {
        Node<T> temp = foerste;
        foerste = new Node(element);
        foerste.neste = temp;
    }


    /**
     * Remove and return the last inserted element from the list
     * @return element last in the list
     */
    public T fjern() {
        Node<T> temp = foerste;
        foerste = foerste.neste;
        return (T)temp.data;                // caster temp.data fordi kompilatoren tror det er Object
    }


    /**
     * Create custom iterator of subclass ListeIterator
     * @return  iterator
     */
    public Iterator<T> iterator() {
        return new ListeIterator();
    }






    class Node<T> {
        protected Node neste;
        protected T data;

        /**
         * Subclass Node can hold data and points to the next node
         * @param   data    element held by the node
         */
        Node(T data) {
            this.data = data;
        }
    }





    class ListeIterator implements Iterator<T> {
        protected Node neste;
        protected Node forrige;

        /**
         * ListeIterator is an iterator for a linked list
         */
        public ListeIterator() {
            neste = foerste;            // startposisjon = foer foerste node
        }

        /**
         * Check if there is a next node
         * @return  true if there is a next one, false if not
         */
        public boolean hasNext() {
            return (neste != null);
        }

        /**
         * Move to the next node in list
         * @return  the next node
         */
        public T next() {
            forrige = neste;
            neste = neste.neste;
            return (T)forrige.data;
        }

        /**
         * Remove element - not implemented
         */
        public void remove() {

        }
    }
}
