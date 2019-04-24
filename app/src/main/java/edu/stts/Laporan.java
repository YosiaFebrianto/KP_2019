package edu.stts;

public class Laporan {
    private String tgl,jumhadir;
    private int id;
    private static int counter;


    public Laporan(String tgl, String jumhadir) {
        this.tgl = tgl;
        this.jumhadir = jumhadir;
        id=++counter;
    }

    public String getTgl() {
        return tgl;
    }

    public void setTgl(String tgl) {
        this.tgl = tgl;
    }

    public String getJumhadir() {
        return jumhadir;
    }

    public void setJumhadir(String jumhadir) {
        this.jumhadir = jumhadir;
    }

    public static int getCounter() {
        return counter;
    }

    public static void setCounter(int counter) {
        Laporan.counter = counter;
    }

    public int getId() {
        return id;
    }
}
