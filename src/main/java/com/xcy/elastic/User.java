package com.xcy.elastic;

/**
 * Created by xuchunyang on 2018/8/31 11点05分
 */
public class User {

    private Long id;

    private String anme;

    public User() {
        super();
    }

    public User(Long id, String anme) {
        this.id = id;
        this.anme = anme;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnme() {
        return anme;
    }

    public void setAnme(String anme) {
        this.anme = anme;
    }


}
