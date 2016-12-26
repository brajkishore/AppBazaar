package appbazaar.com.appbazaar.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by braj.kishore on 4/27/2016.
 */
public class AppOffer implements Serializable {

    private String unique_promo_id;
    private String promo_image_url;
    private String promo_name;
    private String promo_short_info;
    private String promo_price;
    private String promo_type;
    private String promo_action_text;
    private String promo_detail;
    private String rating;

    public String getPromo_action_text() {
        return promo_action_text;
    }

    public void setPromo_action_text(String promo_action_text) {
        this.promo_action_text = promo_action_text;
    }


    private DownloadInstructions downloadInstructions;

    public TnCInstructions getTncInstructions() {
        return tncInstructions;
    }

    public void setTncInstructions(TnCInstructions tncInstructions) {
        this.tncInstructions = tncInstructions;
    }

    private TnCInstructions tncInstructions;

    public String getUnique_promo_id() {
        return unique_promo_id;
    }

    public void setUnique_promo_id(String unique_promo_id) {
        this.unique_promo_id = unique_promo_id;
    }

    public String getPromo_image_url() {
        return promo_image_url;
    }

    public void setPromo_image_url(String promo_image_url) {
        this.promo_image_url = promo_image_url;
    }

    public String getPromo_name() {
        return promo_name;
    }

    public void setPromo_name(String promo_name) {
        this.promo_name = promo_name;
    }

    public String getPromo_short_info() {
        return promo_short_info;
    }

    public void setPromo_short_info(String promo_short_info) {
        this.promo_short_info = promo_short_info;
    }

    public String getPromo_price() {
        return promo_price;
    }

    public void setPromo_price(String promo_price) {
        this.promo_price = promo_price;
    }

    public String getPromo_type() {
        return promo_type;
    }

    public void setPromo_type(String promo_type) {
        this.promo_type = promo_type;
    }

    public String getPromo_detail() {
        return promo_detail;
    }

    public void setPromo_detail(String promo_detail) {
        this.promo_detail = promo_detail;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public DownloadInstructions getDownloadInstructions() {
        return downloadInstructions;
    }

    public void setDownloadInstructions(DownloadInstructions downloadInstructions) {
        this.downloadInstructions = downloadInstructions;
    }
}
