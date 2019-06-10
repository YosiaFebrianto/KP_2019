package edu.stts;

public class Member {
    private String nama,tgl,nohp, id;


    public Member(String id, String nama, String tgl, String nohp) {
        this.id = id;
        this.nama = nama;
        this.tgl = tgl;
        this.nohp = nohp;
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

    public String getId() {
        return id;
    }
}
