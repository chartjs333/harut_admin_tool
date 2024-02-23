package org.medical.hub.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "medical_2_ecrf9", uniqueConstraints = @UniqueConstraint(columnNames = "survey_two_id"))
@Cacheable(false)
public class Medical2Ecrf9 implements java.io.Serializable {

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="medical_2_ecrf9_id_seq")
    @SequenceGenerator(name="medical_2_ecrf9_id_seq", sequenceName="medical_2_ecrf9_id_seq", allocationSize=1)
	@Column(name = "id", unique = true, nullable = false, columnDefinition = "serial")
	private int id;

	@Column(name = "filling_status",  columnDefinition = "varchar", length = 128)
	private String fillingStatus;

	@Column(name = "survey_two_id",  columnDefinition = "varchar", length = 128)
	private String surveyTwoId;

	@Column(name = "date_completed_day",  columnDefinition = "varchar", length = 128)
	private String dateCompletedDay;

	@Column(name = "date_completed_month",  columnDefinition = "varchar", length = 128)
	private String dateCompletedMonth;

	@Column(name = "date_completed_year",  columnDefinition = "varchar", length = 128)
	private String dateCompletedYear;

	@Column(name = "less_36",  columnDefinition = "varchar", length = 128)
	private String less36;

	@Column(name = "less_46",  columnDefinition = "varchar", length = 128)
	private String less46;

	@Column(name = "less_56",  columnDefinition = "varchar", length = 128)
	private String less56;

	@Column(name = "less_66",  columnDefinition = "varchar", length = 128)
	private String less66;

	@Column(name = "moderate1",  columnDefinition = "varchar", length = 128)
	private String moderate1;

	@Column(name = "moderate2",  columnDefinition = "varchar", length = 128)
	private String moderate2;

	@Column(name = "moderate3",  columnDefinition = "varchar", length = 128)
	private String moderate3;

	@Column(name = "moderate4",  columnDefinition = "varchar", length = 128)
	private String moderate4;

	@Column(name = "moderate5",  columnDefinition = "varchar", length = 128)
	private String moderate5;

	@Column(name = "moderate6",  columnDefinition = "varchar", length = 128)
	private String moderate6;

	@Column(name = "sleep1",  columnDefinition = "varchar", length = 128)
	private String sleep1;

	@Column(name = "sleep2",  columnDefinition = "varchar", length = 128)
	private String sleep2;

	@Column(name = "sleep3",  columnDefinition = "varchar", length = 128)
	private String sleep3;

	@Column(name = "sleep4",  columnDefinition = "varchar", length = 128)
	private String sleep4;

	@Column(name = "sleep5",  columnDefinition = "varchar", length = 128)
	private String sleep5;

	@Column(name = "sleep6",  columnDefinition = "varchar", length = 128)
	private String sleep6;

	@Column(name = "vigorous1",  columnDefinition = "varchar", length = 128)
	private String vigorous1;

	@Column(name = "vigorous2",  columnDefinition = "varchar", length = 128)
	private String vigorous2;

	@Column(name = "vigorous3",  columnDefinition = "varchar", length = 128)
	private String vigorous3;

	@Column(name = "vigorous4",  columnDefinition = "varchar", length = 128)
	private String vigorous4;

	@Column(name = "vigorous5",  columnDefinition = "varchar", length = 128)
	private String vigorous5;

	@Column(name = "vigorous6",  columnDefinition = "varchar", length = 128)
	private String vigorous6;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
}