import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "good")
public class Good
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
//    @Column(name = "category_id")
//    private int categoryId;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private GoodCategory category;
    private String name;
    private int count;
    private int price;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public GoodCategory getCategory() {
        return category;
    }

    public void setCategory(GoodCategory category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

}
