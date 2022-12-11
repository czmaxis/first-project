import java.util.ArrayList;
import java.util.List;

public class State {
    private String shortcut;
    private String name;
    private Double higherVat;
    private Double lowerVat;
    private boolean haveSpecialVat;


    public State(String shortcut, String name, Double higherVat, Double lowerVat, boolean haveSpecialVat) {
        this.shortcut = shortcut;
        this.name = name;
        this.higherVat = higherVat;
        this.lowerVat = lowerVat;
        this.haveSpecialVat = haveSpecialVat;
    }

    public String getShortcut() {
        return shortcut;
    }

    public void setShortcut(String shortcut) {
        this.shortcut = shortcut;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getHigherVat() {
        return higherVat;
    }

    public void setHigherVat(Double higherVat) {
        this.higherVat = higherVat;
    }

    public Double getLowerVat() {
        return lowerVat;
    }

    public void setLowerVat(Double lowerVat) {
        this.lowerVat = lowerVat;
    }

    public boolean isHaveSpecialVat() {
        return haveSpecialVat;
    }

    public void setHaveSpecialVat(boolean haveSpecialVat) {
        this.haveSpecialVat = haveSpecialVat;
    }




}
