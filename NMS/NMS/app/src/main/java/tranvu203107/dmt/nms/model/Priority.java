package tranvu203107.dmt.nms.model;

public class Priority {
    private int id;
    private String priority;
    private String createdDate;

    public Priority() {
    }

    public Priority(int id, String priority, String createdDate) {
        this.id = id;
        this.priority = priority;
        this.createdDate = createdDate;
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
}