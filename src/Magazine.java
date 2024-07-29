public class Magazine extends LibraryItem {
    private String issue;

    public Magazine(String title, String issue, int itemID) {
        super(title, issue, itemID);
        this.issue = issue;
    }

    public String getIssue() {
        return issue;
    }

    @Override
    public String toString() {
        return "Magazine{" +
                "title='" + getTitle() + '\'' +
                ", issue='" + issue + '\'' +
                ", itemID=" + getItemID() +
                '}';
    }
}
