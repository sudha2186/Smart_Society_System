package model;

public class Complaint extends FlatOwner {
    private int complaintID;
    //private String flatNo;
    private String username; 
    private String description;
    private String status;

    public int getComplaintID() {
        return complaintID;
    }

    public void setComplaintID(int complaintID) {
        this.complaintID = complaintID;
    }

   /* public String getFlatNo() {
        return flatNo;
    }

    public void setFlatNo(String flatNo) {
        this.flatNo = flatNo;
    }*/

    public String getUsername() {  
        return username;
    }

    public void setUsername(String username) {  
        this.username = username;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
