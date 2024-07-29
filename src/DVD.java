public class DVD extends LibraryItem {
    private String director;

    public DVD(String title, String director, int itemID) {
        super(title, director, itemID);
        this.director = director;
    }

    public String getDirector() {
        return director;
    }

    @Override
    public String toString() {
        return "DVD{" +
                "title='" + getTitle() + '\'' +
                ", director='" + director + '\'' +
                ", itemID=" + getItemID() +
                '}';
    }
}
