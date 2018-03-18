package beans;

import com.mysql.cj.core.util.StringUtils;
import domain.Kweet;
import org.primefaces.event.SelectEvent;
import services.KweetService;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class KweetsBean implements Serializable {
    @Inject
    private KweetService kweetService;

    private Kweet selectedKweet;
    private String selectedText;

    public void onKweetSelect(SelectEvent event) {
        Kweet kweet = (Kweet) event.getObject();
        this.setSelectedKweet(kweet);
    }

    public List<Kweet> getKweets() {
        return kweetService.getKweetsOfUser(1);
    }

    public void saveKweet() {
        if (selectedKweet != null && !StringUtils.isNullOrEmpty(this.selectedText)) {
            selectedKweet.setText(this.selectedText);
            kweetService.editKweet(selectedKweet);
            FacesMessage message = new FacesMessage("Kweet text updated to", this.selectedText);
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public void removeKweet(Kweet kweet) {
        this.kweetService.removeKweet(kweet.getId());
    }

    public Kweet getSelectedKweet() {
        return selectedKweet;
    }

    public void setSelectedKweet(Kweet selectedKweet) {
        this.selectedKweet = selectedKweet;
    }

    public String getSelectedText() {
        return selectedText;
    }

    public void setSelectedText(String selectedText) {
        this.selectedText = selectedText;
    }
}
