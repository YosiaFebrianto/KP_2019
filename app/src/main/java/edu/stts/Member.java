package edu.stts;

public class Member {
    private String nama,tgl,nohp;
    private int id;
    private static int counter;


    public Member(String nama, String tgl, String nohp) {
        this.nama = nama;
        this.tgl = tgl;
        this.nohp = nohp;
        id=++counter;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getTgl() {
        return tgl;
    }

    public void setTgl(String tgl) {
        this.tgl = tgl;
    }

    public String getNohp() {
        return nohp;
    }

    public void setNohp(String nohp) {
        this.nohp = nohp;
    }

    public int getId() {
        return id;
    }
}
