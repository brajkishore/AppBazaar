package appbazaar.com.appbazaar.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by braj.kishore on 4/27/2016.
 */
public class DownloadInstructions implements Serializable {

    private String header;
    private List<Instruction> instructions;

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public List<Instruction> getInstructions() {
        return instructions;
    }

    public void setInstructions(List<Instruction> instructions) {
        this.instructions = instructions;
    }

}
