class Utvei implements Comparable<Utvei> {
    private String utvei;

    /**
     * Class Utvei holds the path as a String, and provides comparison of length,
     * avoiding alphabetical comparison
     * @param   utvei   a string with the coordinates through the path
     */
    Utvei(String utvei) {
        this.utvei = utvei;
    }


    /**
     * Compare length of path to another path
     * @param   annenUtvei   another path to compare this to
     * @return  same as you would expect from comparing two sizes using < and >
     */
    public int compareTo(Utvei annenUtvei) {
        if (utvei.length() < annenUtvei.length()) return -1;

        else if (utvei.length() > annenUtvei.length()) return 1;

        else return 0;
    }


    /**
     * Length of this path
     * @return number of squares in path
     */
    public int length() {
        return utvei.length();
    }


    /**
     * List all squares in this path
     * @return coordinates of path formatted as String
     */
    public String toString() {
        return utvei;
    }

}
