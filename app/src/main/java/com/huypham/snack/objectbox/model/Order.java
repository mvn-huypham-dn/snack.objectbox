package com.huypham.snack.objectbox.model;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToOne;

/**
 * Snack project_object box
 * Created by HuyPhamNA on 07/02/2018.
 */

@Entity
public class Order {
    @Id
    public long id;

    public ToOne<Customer> customer;
}
