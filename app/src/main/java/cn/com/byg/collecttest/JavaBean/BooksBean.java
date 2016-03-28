package cn.com.byg.collecttest.JavaBean;

/**
 * @author NeoPi.
 * @date 2015/9/1
 */
public class BooksBean {

    private int id ;
    private String author;
    private String name;


    public BooksBean() {
    }

    public BooksBean(int id, String author, String name) {
        this.id = id;
        this.author = author;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "BooksBean{" +
                "id=" + id +
                ", author='" + author + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
