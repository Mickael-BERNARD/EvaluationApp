package mb.project.Database;

/**
 * Created by Mickaël BERNARD on 19-Aug-16.
 */
public class ContractContent {
  private int userId, ID;
  private String country, city, description;



  public ContractContent(){}

  public ContractContent(int userId, String country, String city, String description){
    this.userId = userId;
    this.country = country;
    this.city = city;
    this.description = description;
  }

  public int getID (){
    return ID;
  }

  public int getUserId() {
    return userId;
  }

  public String getCountry() {
    return country;
  }


  public String getCities() {
    return city;
  }

  public String getDescription() {
    return description;
  }

  public void setID(int ID) {
    this.ID = ID;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public String toString() {
    return("User: [ID]= "+getID()+" [UserId]= "+getUserId()+" [Country]= "+getCountry()+" [City]= "+getCities()+" [Description]= "+getDescription());
  }
}
