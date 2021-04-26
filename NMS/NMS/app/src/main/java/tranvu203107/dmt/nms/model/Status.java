package tranvu203107.dmt.nms.model;

public class Status {
    private int id;
    private String status;
    private String createdDate;

    public  Status(){}

    public Status(int id, String status, String createdDate) {
        this.id = id;
        this.status = status;
        this.createdDate = createdDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createDate) {
        this.createdDate = createdDate;
    }
}
