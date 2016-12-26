package appbazaar.com.appbazaar.model;

import java.io.Serializable;

/**
 * Created by braj.kishore on 4/27/2016.
 */
public class Instruction implements Serializable {

    private String message;

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    private String colorCode;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Instruction{" +
                "message='" + message + '\'' +
                ", colorCode='" + colorCode + '\'' +
                '}';
    }
}
