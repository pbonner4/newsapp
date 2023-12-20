package newsApp.model;

public class StockIndex {
    private int id;
    private float closing_price;
    private String index_of;
    private String date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getClosing_price() {
        return closing_price;
    }

    public void setClosing_price(float closing_price) {
        this.closing_price = closing_price;
    }

    public String getIndex_of() {
        return index_of;
    }

    public void setIndex_of(String index_of) {
        this.index_of = index_of;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
