package org.example.absensi;

public class LogAbsensi {
    private String nip_pegawai;
    private String waktu_masuk;
    private String waktu_pulang;
    private String status;
    private String foto_bukti_masuk;
    private String foto_bukti_pulang;

    public LogAbsensi(String nip_pegawai, String waktu_masuk, String waktu_pulang, String status, String foto_bukti_masuk, String foto_bukti_pulang) {
        this.nip_pegawai = nip_pegawai;
        this.waktu_masuk = waktu_masuk;
        this.waktu_pulang = waktu_pulang;
        this.status = status;
        this.foto_bukti_masuk = foto_bukti_masuk;
        this.foto_bukti_pulang = foto_bukti_pulang;
    }

    public String getNip_pegawai() {
        return nip_pegawai;
    }

    public void setNip_pegawai(String nip_pegawai) {
        this.nip_pegawai = nip_pegawai;
    }

    public String getWaktu_masuk() {
        return waktu_masuk;
    }

    public void setWaktu_masuk(String waktu_masuk) {
        this.waktu_masuk = waktu_masuk;
    }

    public String getWaktu_pulang() {
        return waktu_pulang;
    }

    public void setWaktu_pulang(String waktu_pulang) {
        this.waktu_pulang = waktu_pulang;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFoto_bukti_masuk() {
        return foto_bukti_masuk;
    }

    public void setFoto_bukti_masuk(String foto_bukti_masuk) {
        this.foto_bukti_masuk = foto_bukti_masuk;
    }

    public String getFoto_bukti_pulang() {
        return foto_bukti_pulang;
    }

    public void setFoto_bukti_pulang(String foto_bukti_pulang) {
        this.foto_bukti_pulang = foto_bukti_pulang;
    }
}