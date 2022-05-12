package edu.volstate.flickrliker.models;

public class Favorites {
    private String id;
    private String secret;
    private String server;
    private String title;

    public Favorites(String id, String secret, String server, String title) {
        this.id = id;
        this.secret = secret;
        this.server = server;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
