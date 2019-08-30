class SortRute extends Rute {

    /**
     * Class SortRute is a subclass of Rute (square), representing closed space
     * @param   rad     row of this square
     * @param   kolonne column of this square
     */
    SortRute(int rad, int kolonne) {
        super(rad, kolonne);
    }


    /**
     * Returns a character representing black space for ASCII visualisation
     * @return  symbol representing black space
     */
    public char tilTegn() {
        return '#';
    }
}
