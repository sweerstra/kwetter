package beans;

import com.mysql.cj.core.util.StringUtils;
import domain.Kweet;
import domain.User;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.CellEditEvent;
import services.KweetService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class KweetsBean implements Serializable {
    @Inject
    private KweetService kweetService;

    private List<Kweet> kweets;
    private Kweet kweet;
    private String selectedText;

    @PostConstruct
    public void init() {
        this.kweets = new ArrayList<>();
    }

    public void onTextCellEdit(CellEditEvent event) {
        String oldUsername = event.getOldValue().toString();
        String newUsername = event.getNewValue().toString();

        if (!oldUsername.equals(newUsername)) {
            Kweet entity = (Kweet) ((DataTable) event.getComponent()).getRowData();
            editKweetText(entity.getId(), newUsername);
        }
    }

    public void onUserRowSelect(User user) {
        this.kweets = kweetService.getKweetsOfUser(user.getUsername());
    }

    public void editKweetText(long id, String username) {
        Kweet kweet = kweetService.getKweet(id);

        if (kweet != null && !StringUtils.isNullOrEmpty(username)) {
            kweet.setText(username);
            kweetService.editKweet(kweet);
            FacesMessage message = new FacesMessage("Kweet text updated to", username);
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public void onRemoveKweet(Kweet kweet) {
        this.kweetService.removeKweet(kweet.getId());
        this.kweets.remove(kweet);
    }

    public List<Kweet> getKweets() {
        return kweets;
    }

    public void setKweets(List<Kweet> kweets) {
        this.kweets = kweets;
    }

    public Kweet getSelectedKweet() {
        return kweet;
    }

    public void setSelectedKweet(Kweet selectedKweet) {
        this.kweet = selectedKweet;
    }

    public String getSelectedText() {
        return selectedText;
    }

    public void setSelectedText(String selectedText) {
        this.selectedText = selectedText;
    }
}
