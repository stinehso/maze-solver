class HvitRute extends Rute {

    /**
     * Class HvitRute is a subclass of Rute (square), representing open space
     * @param   rad     row of this square
     * @param   kolonne column of this square
     */
    HvitRute(int rad, int kolonne) {
        super(rad, kolonne);
    }

    /**
     * Returns a character representing white space or path for ASCII visualisation
     * @return  symbol representing white space or path
     */
    public char tilTegn() {
        if (paaVeien) return '.';
        return ' ';
    }
}
