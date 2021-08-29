package licenta.service.dto;

import licenta.domain.City;
import licenta.domain.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Size;

/**
 * A DTO representing a user, with only the public attributes.
 */


public class UserDTO {

    private Long id;

    private String login;

    @Size(max = 50)
    private String firstName;

    @Size(max = 50)
    private String lastName;

    @Size(max=50)
    private String address;

    @Size(min=10,max=10)
    private String phone;

    private String judet;

    private String city;

    private Boolean infoCompleted;

    public UserDTO() {
        // Empty constructor needed for Jackson.
    }

    public UserDTO(User user) {
        this.id = user.getId();
        // Customize it here if you need, or not, firstName/lastName/etc
        this.login = user.getLogin();
        this.firstName=user.getFirstName();
        this.lastName=user.getLastName();
        this.address=user.getAddress();
        this.phone=user.getPhone();
        this.judet=user.getJudet();
        this.city=user.getCity();
        this.infoCompleted=user.getInfoCompleted();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getJudet() {
        return judet;
    }

    public void setJudet(String judet) {
        this.judet = judet;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Boolean isInfoCompleted() {
        return infoCompleted;
    }

    public void setInfoCompleted(Boolean infoCompleted) {
        this.infoCompleted = infoCompleted;
    }
}
