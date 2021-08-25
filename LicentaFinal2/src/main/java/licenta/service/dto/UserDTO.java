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

    private JudetDTO judet;

    private CityDTO city;

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
        if(user.getJudet() != null) {
            this.judet.setId(user.getJudet().getId());
            this.judet.setCode(user.getJudet().getCode());
            this.judet.setName(user.getJudet().getName());
        }
        if(user.getCity() != null){
            this.city.setId(user.getCity().getId());
            this.city.setName(user.getCity().getName());
        }
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

    public JudetDTO getJudet() {
        return judet;
    }

    public void setJudet(JudetDTO judet) {
        this.judet = judet;
    }

    public CityDTO getCity() {
        return city;
    }

    public void setCity(CityDTO city) {
        this.city = city;
    }

    public Boolean isInfoCompleted() {
        return infoCompleted;
    }

    public void setInfoCompleted(Boolean infoCompleted) {
        this.infoCompleted = infoCompleted;
    }
}
