package com.assignment.nasaapi.model;

import com.assignment.nasaapi.constants.Constants;
import com.fasterxml.jackson.annotation.JsonTypeId;
import com.fasterxml.jackson.databind.JsonNode;
import com.vladmihalcea.hibernate.type.json.JsonNodeStringType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import com.vladmihalcea.hibernate.type.json.JsonType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = Constants.TBL_SPACE_OBJECTS)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Asteroid {

    @Id
    @Column(name = "object_id")
    String objectId;

    @Column(columnDefinition = "json")
    @Type(type = "json")
    String data;

}
