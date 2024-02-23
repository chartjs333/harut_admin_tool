package org.medical.hub.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.medical.hub.annotation.ExcelColumn;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "medical_2_ecrf8", uniqueConstraints = @UniqueConstraint(columnNames = "survey_two_id"))
@Cacheable(false)
public class Medical2Ecrf8 implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "medical_2_ecrf8_id_seq")
    @SequenceGenerator(name = "medical_2_ecrf8_id_seq", sequenceName = "medical_2_ecrf8_id_seq", allocationSize = 1)
    @Column(name = "id", unique = true, nullable = false)
    private int id;

    @Column(name = "filling_status", columnDefinition = "varchar", length = 128)
    private String fillingStatus;

    @Column(name = "survey_two_id", columnDefinition = "varchar", length = 128)
    private String surveyTwoId;

    @ExcelColumn(name = "Anxiety-X", map_from = {"Yes", "No", "Uncertain"}, map_to = {"1", "2", "3"})
    @Column(name = "anxiety", columnDefinition = "varchar", length = 128)
    private String anxiety;

    @Column(name = "anxiety_year", columnDefinition = "varchar", length = 128)
    private String anxietyYear;

    @Column(name = "caffeine_before", columnDefinition = "varchar", length = 128)
    private String caffeineBefore;

    @ExcelColumn(name = "Regular caffeine drinker (at least one cup/day)-T", map_from = {"Yes", "No", "Uncertain"}, map_to = {"1", "2", "3"})
    @Column(name = "caffeine_present", columnDefinition = "varchar", length = 128)
    private String caffeinePresent;

    @Column(name = "date_completed_day", columnDefinition = "varchar", length = 128)
    private String dateCompletedDay;

    @Column(name = "date_completed_month", columnDefinition = "varchar", length = 128)
    private String dateCompletedMonth;

    @Column(name = "date_completed_year", columnDefinition = "varchar", length = 128)
    private String dateCompletedYear;

    @ExcelColumn(name = "Depression-Y", map_from = {"Yes", "No", "Uncertain"}, map_to = {"1", "2", "3"})
    @Column(name = "depression", columnDefinition = "varchar", length = 128)
    private String depression;

    @Column(name = "depression_year", columnDefinition = "varchar", length = 128)
    private String depressionYear;

    @ExcelColumn(name = "Head injury-V", map_from = {"Yes", "No", "Uncertain"}, map_to = {"1", "2", "3"})
    @Column(name = "head_injury", columnDefinition = "varchar", length = 128)
    private String headInjury;

    @Column(name = "head_injury_year", columnDefinition = "varchar", length = 128)
    private String headInjuryYear;

    @Column(name = "lifetime_exposed", columnDefinition = "varchar", length = 128)
    private String lifetimeExposed;

    @ExcelColumn(name = "Pesticide exposure for >6 months-P", map_from = {"Yes", "No", "Uncertain"}, map_to = {"1", "2", "3"})
    @Column(name = "lifetime_job", columnDefinition = "varchar", length = 128)
    private String lifetimeJob;

    @ExcelColumn(name = "Chemical solvent exposure for >6 months-Q", map_from = {"Yes", "No", "Uncertain"}, map_to = {"1", "2", "3"})
    @Column(name = "lifetime_worked_chemical", columnDefinition = "varchar", length = 128)
    private String lifetimeWorkedChemical;

    @ExcelColumn(name = "Heavy metal exposure for >6 months-R", map_from = {"Yes", "No", "Uncertain"}, map_to = {"1", "2", "3"})
    @Column(name = "lifetime_worked_heavy", columnDefinition = "varchar", length = 128)
    private String lifetimeWorkedHeavy;

    @ExcelColumn(name = "Other chemical or fumes exposure for >6 months-S", map_from = {"Yes", "No", "Uncertain"}, map_to = {"1", "2", "3"})
    @Column(name = "lifetime_worked_other", columnDefinition = "varchar", length = 128)
    private String lifetimeWorkedOther;

    @Column(name = "lifetime_worked_specify", columnDefinition = "varchar", length = 128)
    private String lifetimeWorkedSpecify;

    @Column(name = "ovaries_removed", columnDefinition = "varchar", length = 128)
    private String ovariesRemoved;

    @Column(name = "ovaries_removed_year", columnDefinition = "varchar", length = 128)
    private String ovariesRemovedYear;

    @ExcelColumn(name = "Smoking (at least 1 cigarette a day for >6 months)-U", map_from = {"Yes", "No", "Uncertain"}, map_to = {"1", "2", "3"})
    @Column(name = "smoke_before", columnDefinition = "varchar", length = 128)
    private String smokeBefore;

    @Column(name = "smoke_now", columnDefinition = "varchar", length = 128)
    private String smokeNow;

    @Column(name = "smoke_per_day", columnDefinition = "varchar", length = 128)
    private String smokePerDay;

    @Column(name = "smoke_regulary", columnDefinition = "varchar", length = 128)
    private String smokeRegulary;

    @Column(name = "smoke_with", columnDefinition = "varchar", length = 128)
    private String smokeWith;

    @Column(name = "smoke_years", columnDefinition = "varchar", length = 128)
    private String smokeYears;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
