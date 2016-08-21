package mb.project.Database;

import android.database.Cursor;

/**
 * Created by Mickaël BERNARD on 10-Aug-16.
 */


public class ContractAccount {

  private int ID;
  private String firstName, lastName, email, tel;


  public ContractAccount(){}

  public ContractAccount(String firstName, String lastName,String email ,String tel){
    super();
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.tel = tel;

  }





  public int getID(){
    return ID;
  }

  public String getFirstName(){
    return firstName;
  }

  public String getLastName(){
    return lastName;
  }

  public String getEmail() {return email;}

  public String getTel() {return tel;}

  public void setID(int id){
    this.ID =  id;
  }

  public void setFirstName(String firstName){
    this.firstName = firstName;
  }

  public void setLastName(String lastName){
    this.lastName = lastName;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setTel(String tel) {
    this.tel = tel;
  }

  @Override
  public String toString(){
    return("User: [ID]= "+getID()+" [FirstName]= "+getFirstName()+" [LastName]= "+getLastName() + " [e-mail]= "+getEmail()+" [tel]= "+getTel());
  }


  public static ContractAccount fromCursor(Cursor cursor){
    ContractAccount contract =  new ContractAccount();
    contract.setID(Integer.parseInt(cursor.getString(0)));
    contract.setFirstName(cursor.getString(1));
    contract.setLastName(cursor.getString(2));
    contract.setEmail(cursor.getString(3));
    contract.setTel(cursor.getString(4));
    return contract;
  }

}
