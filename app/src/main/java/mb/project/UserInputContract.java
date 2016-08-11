package mb.project;

/**
 * Created by Mickaël BERNARD on 10-Aug-16.
 */
public class UserInputContract {

  private String ID, firstName, lastName;

  public String getID(){
    return ID;
  }

  public String getFirstName(){
    return firstName;
  }

  public String getLastName(){
    return lastName;
  }

  public void setID(String id){
    this.ID =  id;
  }

  public void setFirstName(String firstName){
    this.firstName = firstName;
  }

  public void setLastName(String lastName){
    this.lastName = lastName;
  }
}
