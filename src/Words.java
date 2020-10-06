public class Words {
    private String id;
    private String word;
    private String type;
    private String mean;
    private String exemple;
    private String mean_exemple;

    public Words(){

    }

    public Words(String id, String word, String type, String mean,
                 String exemple, String mean_exemple) {
        this.id = id;
        this.word = word;
        this.type = type;
        this.mean = mean;
        this.exemple = exemple;
        this.mean_exemple = mean_exemple;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMean() {
        return mean;
    }

    public void setMean(String mean) {
        this.mean = mean;
    }

    public String getExemple() {
        return exemple;
    }

    public void setExemple(String exemple) {
        this.exemple = exemple;
    }

    public String getMean_exemple() {
        return mean_exemple;
    }

    public void setMean_exemple(String mean_exemple) {
        this.mean_exemple = mean_exemple;
    }

    @Override
    public String toString() {
        return "Words [ " +
                " word='" + word + '\'' +
                ", type='" + type + '\'' +
                ", mean='" + mean + '\'' +
                ", exemple='" + exemple + '\'' +
                ", mean_exemple='" + mean_exemple + '\'' +
                ']';
    }
}
