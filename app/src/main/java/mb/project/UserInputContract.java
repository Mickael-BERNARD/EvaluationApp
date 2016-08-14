package mb.project;

/**
 * Created by Mickaël BERNARD on 10-Aug-16.
 */
public class UserInputContract {
  public UserInputContract(){}

  public UserInputContract(String firstName, String lastName){
    super();
    this.firstName = firstName;
    this.lastName = lastName;
  }

  private int ID;
  private String firstName, lastName;


  public int getID(){
    return ID;
  }

  public String getFirstName(){
    return firstName;
  }

  public String getLastName(){
    return lastName;
  }

  public void setID(int id){
    this.ID =  id;
  }

  public void setFirstName(String firstName){
    this.firstName = firstName;
  }

  public void setLastName(String lastName){
    this.lastName = lastName;
  }

  @Override
  public String toString(){
    return("User: [ID]= "+getID()+" [FirstName]= "+getFirstName()+" [LastName]= "+getLastName());
  }
}
