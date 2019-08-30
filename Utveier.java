class Utvei implements Comparable<Utvei> {
    private String utvei;

    Utvei(String utvei) {
        this.utvei = utvei;
    }



    public int compareTo(Utvei annenUtvei) {
        if (utvei.length() < annenUtvei.length()) return -1;

        else if (utvei.length() > annenUtvei.length()) return 1;

        else return 0;
    }



    public int length() {
        return utvei.length();
    }



    public String toString() {
        return utvei;
    }

}
