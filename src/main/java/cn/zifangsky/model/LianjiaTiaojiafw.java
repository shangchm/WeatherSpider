package cn.zifangsky.model;

public class LianjiaTiaojiafw {
    private Integer id;

    private String fangwubh;

    private Integer dangjia;

    private Integer zongjia;

    private String tiaojiasj;

    private String tiaojialx;

    private Integer tiaojiaje;

    private String caozuosj;

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

    public Integer getDangjia() {
        return dangjia;
    }

    public void setDangjia(Integer dangjia) {
        this.dangjia = dangjia;
    }

    public Integer getZongjia() {
        return zongjia;
    }

    public void setZongjia(Integer zongjia) {
        this.zongjia = zongjia;
    }

    public String getTiaojiasj() {
        return tiaojiasj;
    }

    public void setTiaojiasj(String tiaojiasj) {
        this.tiaojiasj = tiaojiasj == null ? null : tiaojiasj.trim();
    }

    public String getTiaojialx() {
        return tiaojialx;
    }

    public void setTiaojialx(String tiaojialx) {
        this.tiaojialx = tiaojialx == null ? null : tiaojialx.trim();
    }

    public Integer getTiaojiaje() {
        return tiaojiaje;
    }

    public void setTiaojiaje(Integer tiaojiaje) {
        this.tiaojiaje = tiaojiaje;
    }

    public String getCaozuosj() {
        return caozuosj;
    }

    public void setCaozuosj(String caozuosj) {
        this.caozuosj = caozuosj == null ? null : caozuosj.trim();
    }
}