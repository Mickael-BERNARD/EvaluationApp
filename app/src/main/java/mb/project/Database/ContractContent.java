package mb.project.Database;

/**
 * Created by Mickaël BERNARD on 19-Aug-16.
 */
public class ContractContent {
  private int userId, ID;
  private String country, city, description, placesOfInterest , accommodations,transport , business , education;



  public ContractContent(){}

  public ContractContent(int userId, String country, String city, String description, String placesOfInterest , String accommodations , String transport, String business , String education){
    this.userId = userId;
    this.country = country;
    this.city = city;
    this.description = description;
    this.placesOfInterest = placesOfInterest;
    this.accommodations = accommodations;
    this.transport = transport;
    this.business = business;
    this.education = education;
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

  public String getPlacesOfInterest() {
    return placesOfInterest;
  }

  public String getAccommodations() {
    return accommodations;
  }

  public String getTransport() {
    return transport;
  }

  public String getBusiness() {
    return business;
  }

  public String getEducation() {
    return education;
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

  public void setPlacesOfInterest(String placesOfInterest) {
    this.placesOfInterest = placesOfInterest;
  }

  public void setAccommodations(String accommodations) {
    this.accommodations = accommodations;
  }

  public void setTransport(String transport) {
    this.transport = transport;
  }

  public void setBusiness(String business) {
    this.business = business;
  }

  public void setEducation(String education) {
    this.education = education;
  }



  @Override
  public String toString() {
    return("User: [ID]= "+getID()+" [UserId]= "+getUserId()+" [Country]= "+getCountry()+" [City]= "+getCities()+" [Description]= "+getDescription() + " [PlacesOfInterest]= "+getPlacesOfInterest() +" [Accommodations]= "+ getAccommodations()+" [Transport] "+getTransport()+" [Business]= "+getBusiness()+" [Education]= "+getEducation());
  }
}
