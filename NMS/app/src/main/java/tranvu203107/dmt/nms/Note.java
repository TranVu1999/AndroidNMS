package tranvu203107.dmt.nms;

public class Note {
    private String name;
    private String category;
    private String priority;
    private String status;
    private String planDate;
    private String createDate;

    public Note(String name, String category, String priority, String status, String planDate, String createDate) {
        this.name = name;
        this.category = category;
        this.priority = priority;
        this.status = status;
        this.planDate = planDate;
        this.createDate = createDate;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getPriority() {
        return priority;
    }

    public String getStatus() {
        return status;
    }

    public String getPlanDate() {
        return planDate;
    }

    public String getCreateDate() {
        return createDate;
    }
}
