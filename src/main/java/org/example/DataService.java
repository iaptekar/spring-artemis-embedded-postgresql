package org.example;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("embedded")
public class DataService {

    @Autowired
    SessionFactory sessionFactory;


    public void doStuff() {

    }
}
