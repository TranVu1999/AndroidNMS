package tranvu203107.dmt.nms.model;

public class Priority {
    private int id;
    private String priority;
    private String createdDate;
    private boolean isDeleted;
    private int userId;

    public  Priority(){}

    public Priority(int id, String priority, String createdDate, int userId) {
        this.id = id;
        this.priority = priority;
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

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
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
