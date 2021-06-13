package org.mnode.jot4j.dynamodb.mapper;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.*;
import net.fortuna.ical4j.model.component.VEvent;

import java.util.Date;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class Event extends AbstractMapper {

    @DynamoDBAttribute(attributeName = "Uid")
    @DynamoDBAutoGeneratedKey
    private String uid;

    @DynamoDBVersionAttribute(attributeName = "Sequence")
    protected Integer sequence;

    @DynamoDBAttribute(attributeName = "Data")
    @DynamoDBTypeConverted(converter = VEventConverter.class)
    private VEvent data;

    @DynamoDBAttribute(attributeName = "CalendarUid")
    private String calendarUid;

    @DynamoDBAttribute(attributeName = "Organizer")
    private String organizer;

    @DynamoDBAttribute(attributeName = "DtStart")
    private Date startDate;

    @DynamoDBAttribute(attributeName = "Categories")
    private Set<String> categories;

    @DynamoDBAttribute(attributeName = "Resources")
    private Set<String> resources;

    @DynamoDBAttribute(attributeName = "Related")
    private Set<String> related;

    @DynamoDBAttribute(attributeName = "Class")
    private String classification;

    @Override
    @DynamoDBHashKey(attributeName = "PK")
    public String getPK() {
        return "GROUP#" + getGroupId() + "#EVENT#" + uid;
    }

    @Override
    @DynamoDBRangeKey(attributeName = "SK")
    public String getSK() {
        return "SEQUENCE#" + sequence;
    }

    @DynamoDBIndexHashKey(attributeName = "GSI2_PK", globalSecondaryIndexName = "GSI2")
    public String getGSI2PK() {
        return "GROUP#" + getGroupId() + "#CALENDAR#" + getCalendarUid();
    }

    @DynamoDBIndexRangeKey(attributeName = "GSI2_SK", globalSecondaryIndexName = "GSI2")
    public Date getGSI2SK() {
        return getStartDate();
    }

    @DynamoDBIndexHashKey(attributeName = "GSI3_PK", globalSecondaryIndexName = "GSI3")
    public String getGSI3PK() {
        return getPK();
    }

    @DynamoDBIndexRangeKey(attributeName = "GSI3_SK", globalSecondaryIndexName = "GSI3")
    public String getGSI3SK() {
        return getSK();
    }

    @Override
    @DynamoDBAttribute(attributeName = "TYPE")
    public String getType() {
        return "EVENT";
    }
}
