class HvitRute extends Rute {

    HvitRute(int rad, int kolonne) {
        super(rad, kolonne);
    }

    public char tilTegn() {
        if (paaVeien) return '*';
        return ' ';
    }
}
