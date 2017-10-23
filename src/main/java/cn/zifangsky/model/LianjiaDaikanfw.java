package cn.zifangsky.model;

public class LianjiaDaikanfw {
    private Integer id;

    private String fangwubh;

    private String daikansj;

    private Integer daikancs;

    private String caozuosj;

    private String dailir;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFangwubh() {
        return fangwubh;
    }

    public void setFangwubh(String fangwubh) {
        this.fangwubh = fangwubh == null ? null : fangwubh.trim();
    }

    public String getDaikansj() {
        return daikansj;
    }

    public void setDaikansj(String daikansj) {
        this.daikansj = daikansj == null ? null : daikansj.trim();
    }

    public Integer getDaikancs() {
        return daikancs;
    }

    public void setDaikancs(Integer daikancs) {
        this.daikancs = daikancs;
    }

    public String getCaozuosj() {
        return caozuosj;
    }

    public void setCaozuosj(String caozuosj) {
        this.caozuosj = caozuosj == null ? null : caozuosj.trim();
    }

    public String getDailir() {
        return dailir;
    }

    public void setDailir(String dailir) {
        this.dailir = dailir == null ? null : dailir.trim();
    }
}