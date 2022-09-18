package model.courier;

public class CreateCourierResponse {
    private Boolean ok;

    public CreateCourierResponse(Boolean ok) {
        this.ok = ok;
    }

    public CreateCourierResponse() {
    }

    public Boolean getOk() {
        return ok;
    }

    public void setOk(Boolean ok) {
        this.ok = ok;
    }

    @Override
    public String toString() {
        return "CreateCourierResponse{" +
                "ok=" + ok +
                '}';
    }

}