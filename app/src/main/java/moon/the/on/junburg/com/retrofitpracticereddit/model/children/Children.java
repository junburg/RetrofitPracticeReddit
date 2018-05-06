package moon.the.on.junburg.com.retrofitpracticereddit.model.children;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Junburg on 2018. 5. 6..
 */

public class Children {

    @SerializedName("data")
    @Expose
    private Data data;

    @SerializedName("kind")
    @Expose
    private String kind;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    @Override
    public String toString() {
        return "Children{" +
                "data=" + data +
                ", kind='" + kind + '\'' +
                '}';
    }
}
