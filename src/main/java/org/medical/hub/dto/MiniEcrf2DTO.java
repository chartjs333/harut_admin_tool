package org.medical.hub.dto;

public class MiniEcrf2DTO {

  private String relationship;
  private String otherRelationship;
  private String yearOfBirth;

  public MiniEcrf2DTO(){

  }

  public MiniEcrf2DTO(String relationship, String otherRel, String yob) {
    this.relationship = relationship;
    this.otherRelationship = otherRel;
    this.yearOfBirth = yob;
  }


  public String getRelationship() {
    return relationship;
  }

  public void setRelationship(String relationship) {
    this.relationship = relationship;
  }

  public String getOtherRelationship() {
    return otherRelationship;
  }

  public void setOtherRelationship(String otherRelationship) {
    this.otherRelationship = otherRelationship;
  }

  public String getYearOfBirth() {
    return yearOfBirth;
  }

  public void setYearOfBirth(String yearOfBirth) {
    this.yearOfBirth = yearOfBirth;
  }
}
