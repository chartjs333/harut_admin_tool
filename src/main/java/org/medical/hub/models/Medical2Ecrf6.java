package org.medical.hub.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "medical_2_ecrf6",   uniqueConstraints = @UniqueConstraint(columnNames = "survey_two_id"))
@Cacheable(false)
public class Medical2Ecrf6 implements java.io.Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="medical_2_ecrf6_id_seq")
	@SequenceGenerator(name="medical_2_ecrf6_id_seq", sequenceName="medical_2_ecrf6_id_seq", allocationSize=1)
	@Column(name = "id", unique = true, nullable = false, columnDefinition = "serial")
	private int id;

	@Column(name = "filling_status", columnDefinition = "varchar", length = 128)
	private String fillingStatus;

	@Column(name = "survey_two_id", columnDefinition = "varchar", length = 128)
	private String surveyTwoId;

	@Column(name = "approximate_number_of_probands", columnDefinition = "varchar", length = 128)
	private String approximateNumberOfProbands;

	@Column(name = "code", columnDefinition = "varchar", length = 128)
	private String code;

	@Column(name = "contact_persons", columnDefinition = "varchar", length = 128)
	private String contactPersons;

	@Column(name = "contacted_by", columnDefinition = "varchar", length = 128)
	private String contactedBy;

	@Column(name = "country", columnDefinition = "varchar", length = 128)
	private String country;

	@Column(name = "date_day", columnDefinition = "varchar", length = 128)
	private String dateDay;

	@Column(name = "date_month", columnDefinition = "varchar", length = 128)
	private String dateMonth;

	@Column(name = "date_year", columnDefinition = "varchar", length = 128)
	private String dateYear;

	@Column(name = "email", columnDefinition = "varchar", length = 128)
	private String email;

	@Column(name = "email_2", columnDefinition = "varchar", length = 128)
	private String email2;

	@Column(name = "email_3", columnDefinition = "varchar", length = 128)
	private String email3;

	@Column(name = "follow_up", columnDefinition = "varchar", length = 128)
	private String followUp;

	@Column(name = "institute", columnDefinition = "varchar", length = 128)
	private String institute;

	@Column(name = "interested_in_contributing", columnDefinition = "varchar", length = 128)
	private String interestedInContributing;

	@Column(name = "interested_in_contributing_no", columnDefinition = "varchar", length = 128)
	private String interestedInContributingNo;

	@Column(name = "interested_in_contributing_other", columnDefinition = "varchar", length = 128)
	private String interestedInContributingOther;

	@Column(name = "involvement_of_other_0", columnDefinition = "varchar", length = 128)
	private String involvementOfOther0;

	@Column(name = "involvement_of_other_1", columnDefinition = "varchar", length = 128)
	private String involvementOfOther1;

	@Column(name = "involvement_of_other_2", columnDefinition = "varchar", length = 128)
	private String involvementOfOther2;

	@Column(name = "involvement_of_other_3", columnDefinition = "varchar", length = 128)
	private String involvementOfOther3;

	@Column(name = "involvement_of_other_4", columnDefinition = "varchar", length = 128)
	private String involvementOfOther4;

	@Column(name = "involvement_of_other_5", columnDefinition = "varchar", length = 128)
	private String involvementOfOther5;

	@Column(name = "link_sent_out", columnDefinition = "varchar", length = 128)
	private String linkSentOut;

	@Column(name = "logInCode", columnDefinition = "varchar", length = 128)
	private String logInCode;

	@Column(name = "number", columnDefinition = "varchar", length = 128)
	private String number;

	@Column(name = "others", columnDefinition = "varchar", length = 128)
	private String others;

	@Column(name = "preference", columnDefinition = "varchar", length = 128)
	private String preference;

	@Column(name = "registered", columnDefinition = "varchar", length = 128)
	private String registered;

	@Column(name = "status_of_approval", columnDefinition = "varchar", length = 128)
	private String statusOfApproval;

	@Column(name = "status_of_ethics_comliance_approval", columnDefinition = "varchar", length = 128)
	private String statusOfEthicsComlianceApproval;

	@Column(name = "status_of_mta", columnDefinition = "varchar", length = 128)
	private String statusOfMta;

	@Column(name = "team_members", columnDefinition = "varchar", length = 128)
	private String teamMembers;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
}