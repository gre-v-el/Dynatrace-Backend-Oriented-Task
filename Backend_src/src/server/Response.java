package server;

public class Response {
    String body;
    int code;

    public Response(String body, int code) {
        this.body = body;
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if(o == this) return true;
        if(! (o instanceof Response)) return false;
        Response r = (Response) o;
        return r.body.equals(body) && r.code == code;
    }

    @Override
    public String toString() {
        return "Response{" +
                "body='" + body + '\'' +
                ", code=" + code +
                '}';
    }
}
