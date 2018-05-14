package config;

public class Link {
    private String href;
    private String rel;
    private String method;

    public Link(String href, String rel, String method) {
        this.href = href;
        this.rel = rel;
        this.method = method;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getRel() {
        return rel;
    }

    public void setRel(String rel) {
        this.rel = rel;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
