package tranvu203107.dmt.nms;

public class Note {
    private String name;
    private String category;
    private String priority;
    private String status;
    private String planDate;
    private String createDate;
    private String iD;

    public Note(String status, String category, String name, String priority, String planDate, String createDate, String iD) {
        this.name = name;
        this.category = category;
        this.priority = priority;
        this.status = status;
        this.planDate = planDate;
        this.createDate = createDate;
        this.iD = iD;
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

    public int getiD(){ return Integer.parseInt(iD); }
}
