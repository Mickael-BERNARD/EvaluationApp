package mb.project.Database;

import java.util.Date;

/**
 * Created by Mickaël BERNARD on 26-Aug-16.
 */
public class ContractComment {
  private int ID ,userId, postId;
  // We will need to sort out the comments in a chronological order.
  private Date date;
  private String content;

  public ContractComment(){};

  public ContractComment(int userId, int postId, Date date, String content){
    this.userId = userId;
    this.postId = postId;
    this.date = date;
    this.content = content;
  }

  //GETTERS
  public int getID() {
    return ID;
  }
  public int getUserId() {
    return userId;
  }
  public int getPostId() {
    return postId;
  }
  public Date getDate() {
    return date;
  }
  public String getContent() {
    return content;
  }

  //SETTERS
  public void setID(int ID) {
    this.ID = ID;
  }
  public void setUserId(int userId) {
    this.userId = userId;
  }
  public void setPostId(int postId) {
    this.postId = postId;
  }
  public void setDate(Date date) {
    this.date = date;
  }
  public void setContent(String content) {
    this.content = content;
  }

  //TOSTRING

  @Override
  public String toString() {
    return("Comment: [ID]= "+getID()+" [UserId]= "+getUserId()+" [PostID]= "+getPostId()+" [Date]= "+getDate().toString()+" [Content]= "+getContent());
  }
}

