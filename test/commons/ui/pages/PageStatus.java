package commons.ui.pages;

public enum PageStatus {

    //TODO approve
    UNAUTHORIZED("Unauthorized");

    private String contains;

    private PageStatus(String contains){
        this.contains = contains;
    }

    public String getContains(){
        return contains;
    }


}
