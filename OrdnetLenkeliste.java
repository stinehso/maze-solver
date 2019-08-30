import java.lang.Comparable;
import java.util.Iterator;


class OrdnetLenkeliste<T extends Comparable<T>> extends Stabel<T> {
// sortert liste - minste element foerst, stoerste element sist


    OrdnetLenkeliste() {

    }



    public void settInn(T element) {
        Iterator it = iterator();
        if (!it.hasNext()) {                    // hvis lista er tom, legg til med en gang
            foerste = new Node<T>(element);
            return;
        }
        Node<T> forrige = null;                 // noden jeg bruker til aa sette inn
        Node<T> denne = foerste;

        while (it.hasNext()) {
            int compare = denne.data.compareTo(element);
            if (compare > 0) {                 // denne > element - element skal inn foer
                Node<T> temp = new Node(element);
                temp.neste = denne;
                if (forrige == null) {
                    foerste = temp;
                    return;
                }
                forrige.neste = temp;
                return;
            }
            forrige = denne;                    // flytter nodepekere og iterator et hakk
            denne = denne.neste;
            it.next();
        }
        forrige.neste = new Node(element);      // setter inn ny node til slutt
    }



    // fjern() tar ut det minste elementet (foerste)



}
