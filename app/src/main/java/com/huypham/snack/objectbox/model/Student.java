package com.huypham.snack.objectbox.model;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToMany;

/**
 * Snack project_object box
 * Created by HuyPhamNA on 07/02/2018.
 */

@Entity
public class Student {
    @Id
    public long id;

    public ToMany<Teacher> teachers;
}
