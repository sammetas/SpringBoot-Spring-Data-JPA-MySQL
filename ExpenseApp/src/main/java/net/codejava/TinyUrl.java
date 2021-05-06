package net.codejava;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "TINYURL")
public class TinyUrl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name="tinyurl")
    String tinyUrl;
    //@JsonIgnore
    @Column(name="mainurl")
    String mainUrl;

    public TinyUrl() {
    }

    public TinyUrl(Long id, String tinyUrl, String mainUrl) {
        this.id = id;
        this.tinyUrl = tinyUrl;
        this.mainUrl = mainUrl;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTinyUrl() {
        return tinyUrl;
    }

    public void setTinyUrl(String tinyUrl) {
        this.tinyUrl = tinyUrl;
    }

    public String getMainUrl() {
        return mainUrl;
    }

    public void setMainUrl(String mainUrl) {
        this.mainUrl = mainUrl;
    }

}
