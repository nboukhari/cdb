package com.excilys.cdb2.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="user")
public class User {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "id")
private long id;

@Column(name = "name")
private String name;

public User() {

}

public User(long id, String name) {
this.id = id;
this.name = name;
}

public long getId() {
return id;
}

public String getName() {
return name;
}


@Override
public String toString() {
return "User [id=" + id + ", name=" + name + "]";
}
}
