package views.usermgmt.ui.pages;

public enum Status {

    //TODO approve
    UNAUTHORIZED("Unauthorized");

    private String contains;

    private Status(String contains){
        this.contains = contains;
    }

    public String getContains(){
        return contains;
    }


}
