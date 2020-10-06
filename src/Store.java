import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Store {
    public Words words;
    Map<Integer, Words> booksMap;
    private int total;
    private int borrow;

    public Store(Words words) {
        this.words = words;

    }

    public void listBooks() {
        System.out.println("------ " + this.total + " ------");
        this.booksMap.entrySet().stream().forEach(book -> System.out.println(book.toString()));
    }

    public Words getWord(){
        return this.words;

    }

    public Map<Integer, Words> getWords() {
        Map<Integer, Words> books = new HashMap<Integer, Words>();
        for (Map.Entry<Integer, Words> entry : this.booksMap.entrySet()) {
            Words words = entry.getValue();
            System.out.println(words.toString());

//            if (book.isBorrow() == false) {
//                books.put(Integer.parseInt(book.getId()), book);
//            }
        }
        return books;
    }



}
