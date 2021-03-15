package com.accountManagement.testTask.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Immutable;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Immutable
@Table(name = "be_roles")
public class Role  implements GrantedAuthority {

    @Id
    @Column(name = "name")
    private String name;

    @Override
    public String getAuthority() {
        return this.getName();
    }

    public Role(String name) {
        this.name = name;
    }
}
