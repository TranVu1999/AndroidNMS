package tranvu203107.dmt.nms.model;

public class Status {
    private int id;
    private String status;
    private String createdDate;
    private boolean isDeleted;
    private int userId;

    public  Status(){}

    public Status(int id, String status, String createdDate, int userId) {
        this.id = id;
        this.status = status;
        this.createdDate = createdDate;
        this.isDeleted = false;
        this.userId = userId;
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

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
